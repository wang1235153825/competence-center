package cn.com.wtj.core.service.impl;

import cn.com.wtj.common.UuidUtils;
import cn.com.wtj.core.http.config.JwtConfig;
import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.core.service.TokenService;
import cn.com.wtj.core.service.bean.JwtItem;
import cn.com.wtj.entity.TokenDetail;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.KeyPair;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

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
        try {
            tokenItem = createTokenItem(user, clientId);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        TokenItem refreshToken = createRefreshToken(uuid, user.getId(), clientId);

        //TODO 生成的refreeshtoken入库

        TokenDetail tokenDetail = new TokenDetail();
        tokenDetail.setAccessToken(tokenItem.getToken());
        tokenDetail.setRefreshToken(refreshToken.getToken());
        tokenDetail.setExpiresTime(tokenItem.getExpiresIn());
        return tokenDetail;
    }

    //TODO 简化版刷新token
    @Override
    public TokenDetail refreshToken(String refreshToken, User user, String clientId, JwtItem jwtItem) {

        if (jwtItem.getExpirationTime() == null || LocalDateTime.now().isAfter(jwtItem.getExpirationTime())) {
            throw new RuntimeException("token 过期");
        }

        //超过阈值 返回新的token和refreshtoken
        long expired = (jwtItem.getExpirationTime().getMinute() - System.currentTimeMillis()) / (1000 * 60);
        if (expired <= jwtConfig.getRefreshTokenExpiresThreshold()) {
            return this.createToken(user, clientId);
        }

        TokenItem item;
        try {
            item = this.createTokenItem(user, clientId);
        }catch (Exception e){
            throw new RuntimeException("jwt token faild",e);
        }

        TokenDetail detail = new TokenDetail();
        detail.setRefreshToken(refreshToken);
        detail.setAccessToken(item.getToken());
        detail.setExpiresTime(item.getExpiresIn());

        return detail;
    }


    //TODO 简化版解析token
    @Override
    public JwtItem obtain(String token) {


//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtConfig.getKey())  //SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
//                .parseClaimsJws(token)    //jwt是JWT字符串
//                .getBody();
//
//        JwtItem jwtItem = new JwtItem();
//        if (claims != null) {
//            String clientId = claims.get("clientId", String.class);
//            String userId = claims.get("userId", String.class);
//            String jwtId = claims.getId();
//
//            LocalDateTime expiration = claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            jwtItem.setClientId(clientId);
//            jwtItem.setJwtId(jwtId);
//            jwtItem.setUserId(userId);
//            jwtItem.setExpirationTime(expiration);
//        }

        return null;
    }


    //TODO 同样一个简化的刷新jwt。。。。。OMG
    public TokenItem createRefreshToken(String tokenId, String userId, String clientId) {
        log.info("生成 refresh token 开始");

        LocalDateTime expires = LocalDateTime.now().plusMinutes(jwtConfig.getTokenExpiresTime());
//        String jwt = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())    //SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
//                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))     //expTime是过期时间
//                .setIssuer(tokenId)
//                .claim("userId", userId)
//                .claim("clientId", clientId)
//                .compact();

//        return TokenItem.builder().token(jwt).expiresIn(expires.getMinute()).build();
        return null;
    }

    //TODO 简化版获取jwt 后期增加
    public TokenItem createTokenItem(User user, String clientId) throws JOSEException {
        log.info("生成 access token 开始");
        HashMap<String, String> map = new HashMap<>();
        map.put("username",user.getUserName());
        map.put("age",user.getAge());
        map.put("clientId",clientId);
        LocalDateTime expires = LocalDateTime.now().plusMinutes(jwtConfig.getTokenExpiresTime());
        map.put("expiration",expires.toString());

        //生成token 先创建header
        //JWSHeader参数：1.加密算法法则,2.类型，3.。。。。。。。一般只需要传入加密算法法则就可以。
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        //创建载荷
        Payload payload = new Payload(new JSONObject(map));
        //将头部和载荷连接
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //建立秘钥
        MACSigner macSigner = new MACSigner(jwtConfig.getKey());
        //签名
        jwsObject.sign(macSigner);
        String token = jwsObject.serialize();


        return TokenItem.builder().token(token).expiresIn(expires.getMinute()).build();
    }

    @Data
    @Builder
    private static class TokenItem implements Serializable {

        private static final long serialVersionUID = 6057308812986214717L;

        private final String token;

        private final long expiresIn;
    }
}
