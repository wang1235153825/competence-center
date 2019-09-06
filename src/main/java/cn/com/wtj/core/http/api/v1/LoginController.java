package cn.com.wtj.core.http.api.v1;

import cn.com.wtj.client.base.LoginDef;
import cn.com.wtj.core.service.LoginService;
import cn.com.wtj.entity.LoginRequest;
import cn.com.wtj.entity.LoginResponse;
import cn.com.wtj.entity.RefreshTokenRequest;
import cn.com.wtj.entity.TokenDetail;
import cn.com.wtj.entity.base.BaseResponse;
import cn.com.wtj.entity.base.BaseResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class LoginController implements LoginDef {

    private final LoginService loginService;

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        LoginResponse response = loginService.login(request);
        return BaseResponseBuilder.success(BaseResponse.SUCCESS_CODE,BaseResponse.SUCCESS_MESSAGE,response);
    }

    @Override
    public BaseResponse<TokenDetail> login(RefreshTokenRequest request) {
        TokenDetail detail = loginService.refreshToken(request);
        return null;
    }
}
