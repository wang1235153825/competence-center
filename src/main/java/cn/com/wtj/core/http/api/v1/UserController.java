package cn.com.wtj.core.http.api.v1;

import cn.com.wtj.client.base.UserDef;
import cn.com.wtj.core.service.UserService;
import cn.com.wtj.entity.CreateUserRequest;
import cn.com.wtj.entity.base.BaseResponse;
import cn.com.wtj.entity.base.BaseResponseBuilder;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/9/6.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserController implements UserDef {

    private final UserService userService;

    @Override
    public BaseResponse<Void> createUser(CreateUserRequest request) {
        userService.createUser(request);
        return BaseResponseBuilder.success(BaseResponse.SUCCESS_CODE,BaseResponse.SUCCESS_MESSAGE,null);
    }
}
