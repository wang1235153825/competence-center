package cn.com.wtj.core.service.impl;

import cn.com.wtj.core.http.config.ShiroValueConfig;
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

    private final ShiroValueConfig svConfig;

    /**
     * 创建user账户
     * @param request the request
     */
    @Override
    public void createUser(CreateUserRequest request) {
        log.info("创建用户账户--开始 request:[{}]",request);
        //TODO 校验信息 将使用vaild框架

        User user = new User();
        BeanUtils.copyProperties(request,user);

        //对密码进行盐值加密 MD5
        ByteSource source = ByteSource.Util.bytes(svConfig.getSaltValue());
        SimpleHash md5Pw = new SimpleHash("MD5", request.getPassword(), source,svConfig.getHash());

        user.setPassword(md5Pw.toString());
        user.setStatus(User.TYPE_OK);

        //入库
        User save = userRepository.save(user);
        log.info("创建用户账户--完成 user : [{}]",save);
    }
}
