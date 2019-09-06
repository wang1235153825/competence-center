package cn.com.wtj.client.base;

import cn.com.wtj.entity.CreateUserRequest;
import cn.com.wtj.entity.base.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 2019/9/6.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public interface UserDef {

    @PostMapping("/create")
    BaseResponse<Void> createUser(@RequestBody CreateUserRequest request);

}
