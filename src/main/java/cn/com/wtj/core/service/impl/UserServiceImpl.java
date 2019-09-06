package cn.com.wtj.core.service.impl;

import cn.com.wtj.core.repository.UserRepository;
import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.core.service.UserService;
import cn.com.wtj.entity.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/9/6.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 创建user账户
     * @param request the request
     */
    @Override
    public void createUser(CreateUserRequest request) {
        //TODO 校验信息 将使用vaild框架
        User user = new User();
        BeanUtils.copyProperties(request,user);

        //TODO 这个是盐 先瞎写咯
        ByteSource source = ByteSource.Util.bytes("aaaaaaaaaa");
        SimpleHash md5Pw = new SimpleHash("MD5", request.getPassword(), source, 1024);
        System.out.println(md5Pw.toString());

        user.setPassword(md5Pw.toString());
        user.setStatus(new Short("1"));
        //TODO 入库操作
        User save = userRepository.save(user);
        System.out.println(save);
    }
}
