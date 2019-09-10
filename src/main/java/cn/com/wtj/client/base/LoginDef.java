package cn.com.wtj.client.base;

import cn.com.wtj.entity.LoginRequest;
import cn.com.wtj.entity.LoginResponse;
import cn.com.wtj.entity.RefreshTokenRequest;
import cn.com.wtj.entity.TokenDetail;
import cn.com.wtj.entity.base.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public interface LoginDef {

    @PostMapping
    BaseResponse<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("/refresh")
    BaseResponse<TokenDetail> refresh(@RequestBody RefreshTokenRequest request);

}
