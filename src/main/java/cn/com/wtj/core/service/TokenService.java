package cn.com.wtj.core.service;

import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.core.service.bean.JwtItem;
import cn.com.wtj.entity.TokenDetail;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public interface TokenService {

    TokenDetail createToken(User user, String clientId);

    TokenDetail refreshToken(String refreshToken,User user,String clientId,JwtItem jwtItem);

    JwtItem obtain(String token) ;

}
