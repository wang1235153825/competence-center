package cn.com.wtj.core.service.impl;

import cn.com.wtj.common.UuidUtils;
import cn.com.wtj.core.http.config.JwtConfig;
import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.core.service.TokenService;
import cn.com.wtj.entity.TokenDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.KeyPair;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    public TokenDetail createToken(User user, Long clientId) {
        log.info("生成 JWT TOKEN 开始");

        String uuid = UuidUtils.createUUID();
        TokenItem tokenItem = createTokenItem(user, clientId);
        TokenItem refreshToken = createRefreshToken(uuid, user.getId(), clientId);

        //TODO 生成的refreeshtoken入库

        TokenDetail tokenDetail = new TokenDetail();
        tokenDetail.setAccessToken(tokenItem.getToken());
        tokenDetail.setRefreshToken(refreshToken.getToken());
        tokenDetail.setExpiresTime(tokenItem.getExpiresIn());
        return tokenDetail;
    }


    //TODO 同样一个简化的刷新jwt。。。。。OMG
    public TokenItem createRefreshToken(String tokenId,Long userId,Long clientId){
        log.info("生成 refresh token 开始");

        LocalDateTime expires = LocalDateTime.now().plusMinutes(jwtConfig.getTokenExpiresTime());
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtConfig.getKey())    //SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))     //expTime是过期时间
                .setIssuer(tokenId)
                .claim("userId",userId)
                .claim("clientId",clientId)
                .compact();

        return TokenItem.builder().token(jwt).expiresIn(expires.getMinute()).build();
    }

    //TODO 简化版获取jwt 后期增加
    public TokenItem createTokenItem(User user, Long clientId) {
        log.info("生成 access token 开始");

        LocalDateTime expires = LocalDateTime.now().plusMinutes(jwtConfig.getTokenExpiresTime());
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtConfig.getKey())    //SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
                .setExpiration(Date.from(expires.atZone(ZoneId.systemDefault()).toInstant()))     //expTime是过期时间
                .claim("username",user.getUserName())
                .claim("age",user.getAge())
                .claim("active", user.getActive()) //该方法是在JWT中加入值为vaule的key字段
                .claim("clientId",clientId)
                .compact();

        return TokenItem.builder().token(jwt).expiresIn(expires.getMinute()).build();
    }

    @Data
    @Builder
    private static class TokenItem implements Serializable {

        private static final long serialVersionUID = 6057308812986214717L;

        private final String token;

        private final long expiresIn;
    }
}
