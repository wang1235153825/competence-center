package cn.com.wtj.core.service.impl;

import cn.com.wtj.core.repository.ClientRepository;
import cn.com.wtj.core.repository.UserRepository;
import cn.com.wtj.core.repository.entity.Client;
import cn.com.wtj.core.repository.entity.User;
import cn.com.wtj.core.service.LoginService;
import cn.com.wtj.core.service.TokenService;
import cn.com.wtj.core.service.bean.JwtItem;
import cn.com.wtj.entity.LoginRequest;
import cn.com.wtj.entity.LoginResponse;
import cn.com.wtj.entity.RefreshTokenRequest;
import cn.com.wtj.entity.TokenDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public LoginServiceImpl(TokenService tokenService, UserRepository userRepository, ClientRepository clientRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("用户登陆开始");

        String clientId = request.getClientId();
        Client client = clientRepository.findByClientId(clientId).orElseThrow(
                () -> new RuntimeException("client id 不存在")
        );


        //TODO 密码暂时为明文,后期增加加密
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(request.getUserName(), request.getPassWord());
        subject.login(token);

        //TODO 返回值中暂时只增加少量信息
//        TokenDetail token = tokenService.createToken(user, client.getClientId());
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setTokenDetail(token);
//        loginResponse.setUserId(user.getId());
//        loginResponse.setUserName(user.getUserName());

//        return loginResponse;
        return null;
    }

    @Override
    public TokenDetail refreshToken(RefreshTokenRequest request) {
        JwtItem obtain = tokenService.obtain(request.getRefreshToken());
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException());
        TokenDetail detail = tokenService.refreshToken(request.getRefreshToken(), user, obtain.getClientId(), obtain);
        return detail;
    }
}
