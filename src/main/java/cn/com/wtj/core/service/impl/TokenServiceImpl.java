package cn.com.wtj.core.service.impl;

import cn.com.wtj.common.UuidUtils;
import cn.com.wtj.common.utils.JwtUtil;
import cn.com.wtj.core.http.config.JwtConfig;
import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.core.service.TokenService;
import cn.com.wtj.core.service.bean.JwtItem;
import cn.com.wtj.entity.TokenDetail;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PublicKey;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final JwtConfig jwtConfig;

    @Autowired
    public TokenServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public TokenDetail createToken(User user, String clientId) {
        log.info("生成 JWT TOKEN 开始");

        String uuid = UuidUtils.createUUID();
        TokenItem tokenItem = null;
        TokenItem refreshToken = null;
        try {
            tokenItem = createTokenItem(user, clientId);
            refreshToken = createRefreshToken(uuid, user.getId(), clientId);
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        //TODO 生成的refreeshtoken入库
        TokenDetail tokenDetail = new TokenDetail();
        tokenDetail.setAccessToken(tokenItem.getToken());
        tokenDetail.setRefreshToken(refreshToken.getToken());
        tokenDetail.setExpiresTime(tokenItem.getExpiresIn());

        return tokenDetail;
    }

    //TODO 简化版刷新token
    @Override
    public TokenDetail refreshToken(String refreshToken, User user, String clientId) {
        JWTClaimsSet parse = this.parse(refreshToken);
        //获取超时时间
        LocalDateTime expirationTime = JwtUtil.expirationTime(parse);
        if (expirationTime == null && LocalDateTime.now().isAfter(expirationTime)) {
            throw new RuntimeException("refresh token 已过期,请重新登录");
        }
        //refresh token的时间未超过阈值
        long expirIn = (expirationTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() - System.currentTimeMillis()) / (1000 * 60);
        if (expirIn <= jwtConfig.getRefreshTokenExpiresThreshold()) {
            return this.createToken(user, clientId);
        }

        TokenItem item;
        try {
            item = this.createTokenItem(user, clientId);
        } catch (Exception e) {
            throw new RuntimeException("jwt token 生成失败", e);
        }

        TokenDetail detail = new TokenDetail();
        detail.setRefreshToken(refreshToken);
        detail.setAccessToken(item.getToken());
        detail.setExpiresTime(item.getExpiresIn());

        return detail;
    }


    //TODO 同样一个简化的刷新jwt。。。。。OMG
    public TokenItem createRefreshToken(String tokenId, String userId, String clientId) throws JOSEException {
        log.info("生成 refresh token 开始");

        LocalDateTime expires = LocalDateTime.now().plusMinutes(jwtConfig.getRefreshTokenExpiresIn());
        Date expDate = Date.from(expires.atZone(ZoneId.of("+8")).toInstant());
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                //面向的用户
                .subject(userId)
                //签发者
                .issuer(jwtConfig.getJWT_VALUE_ISS())
                //设置过期时间
                .expirationTime(expDate)
                //接受jwt的一方
                .audience(jwtConfig.getJWT_VALUE_AUD())
                .jwtID(tokenId)
                .claim("clientId", clientId)
                .build();

        RSAKey rsaKey = getkey();
        String refreshToken = createTokenRS256(jwtClaimsSet, rsaKey);
        log.info("生成refresh token 完成");
        return TokenItem.builder().token(refreshToken).expiresIn(expires.getMinute()).build();
    }

    //TODO 简化版获取jwt 后期增加
    public TokenItem createTokenItem(User user, String clientId) {
        log.info("生成 access token 开始");

        try {
            LocalDateTime expires = LocalDateTime.now().plusMinutes(jwtConfig.getTokenExpiresIn());
            Date expDate = Date.from(expires.atZone(ZoneId.of("+8")).toInstant());
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    //面向的用户
                    .subject(user.getId())
                    //签发者
                    .issuer(jwtConfig.getJWT_VALUE_ISS())
                    //设置过期时间
                    .expirationTime(expDate)
                    //接受jwt的一方
                    .audience(jwtConfig.getJWT_VALUE_AUD())
                    .claim("username", user.getUserName())
                    .claim("age", user.getAge())
                    .claim("address", user.getAddress())
                    .claim("clientId", clientId)
                    .build();
            //获取秘钥
            RSAKey rsaKey;
            rsaKey = getkey();

            //创建token
            String token = createTokenRS256(jwtClaimsSet, rsaKey);
            log.info("生成access token 完成");
            return TokenItem.builder().token(token).expiresIn(expires.toInstant(ZoneOffset.of("+8")).toEpochMilli()).build();
        } catch (JOSEException e) {
            throw new RuntimeException("生成access token 失败" + e.getMessage());
        }

    }

    /**
     * 解析token
     *
     * @param token the jwt
     * @return
     * @throws ParseException
     * @throws JOSEException
     */
    @Override
    public JwtItem vaildRS256(String token) {
        JWTClaimsSet claimsSet = this.parse(token);
        Date time = claimsSet.getExpirationTime();

        long iat = 0L;
        if (time != null) {
            iat = time.getTime();
        }
        String clientId;
        try {
            clientId = claimsSet.getStringClaim("clientId");
        } catch (Exception e) {
            throw new RuntimeException("refresh token 错误:无法获取clientId");
        }
        String userId = claimsSet.getSubject();
        if (userId == null) {
            throw new RuntimeException("refresh token 错误:无法获取userId");
        }

        return JwtItem.builder()
                .expirationTime(iat)
                .jwtId(claimsSet.getJWTID())
                .clientId(clientId)
                .userId(userId)
                .build();
    }

    /**
     * 验证token并获取载荷
     *
     * @param token the jwsObject
     * @return JWTClaimsSet the retrun
     */
    public JWTClaimsSet parse(String token) {

        SignedJWT signedJWT;
        try {
            //获取公钥
            RSAKey publicKey = getkey().toPublicJWK();
            //解析token并根据公钥验证
            signedJWT = SignedJWT.parse(token);
            signedJWT.verify(new RSASSAVerifier(publicKey));

            //获取claims
            return signedJWT.getJWTClaimsSet();
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("refresh token 解析失败" + e.getMessage());
        }
    }


    /**
     * @param jwtClaimsSet the request
     * @param rsaKey       the request
     * @return token the return
     * @throws JOSEException
     */
    public String createTokenRS256(JWTClaimsSet jwtClaimsSet, RSAKey rsaKey) throws JOSEException {
        //秘钥

        RSASSASigner rsassaSigner = new RSASSASigner(rsaKey);

        SignedJWT signedJwt =
                new SignedJWT(
                        new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
                        jwtClaimsSet);

        //加密
        signedJwt.sign(rsassaSigner);

        return signedJwt.serialize();
    }

    //TODO 直接获取RSAKey速度有些慢 后期进行优化修改
    /**
     * 生成加密密钥
     *
     * @return RSAKey the jwt key
     * @throws JOSEException
     */
    public RSAKey getkey() throws JOSEException {
        RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator(2048);
        RSAKey rsaKey = rsaKeyGenerator.generate();
        return rsaKey;
    }

    @Data
    @Builder
    private static class TokenItem implements Serializable {

        private static final long serialVersionUID = 6057308812986214717L;

        private final String token;

        private final long expiresIn;
    }
}
