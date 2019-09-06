package cn.com.wtj.core.service;

import cn.com.wtj.entity.LoginRequest;
import cn.com.wtj.entity.LoginResponse;
import cn.com.wtj.entity.RefreshTokenRequest;
import cn.com.wtj.entity.TokenDetail;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public interface LoginService {

    LoginResponse login(LoginRequest request);

    TokenDetail refreshToken(RefreshTokenRequest request);
}
