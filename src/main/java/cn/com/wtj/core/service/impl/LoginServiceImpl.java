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
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;


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

        LoginResponse response = new LoginResponse();
        String clientId = request.getClientId();
        clientRepository.findByClientId(clientId).orElseThrow(
                () -> new RuntimeException("client id 不存在")
        );


        //TODO 密码暂时为明文,后期增加加密
        try{
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(request.getUserName(), request.getPassWord());
            subject.login(token);

            //TODO 返回值token中暂时只增加少量信息
            User user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new RuntimeException("用户名不存在"));
            TokenDetail tokenDetail = tokenService.createToken(user, clientId);
            response.setTokenDetail(tokenDetail);
            response.setUserName(user.getUserName());
            response.setUserId(user.getId());
        }catch (Exception e){
            throw new RuntimeException("登陆失败:"+e.getMessage());
        }
        return response;
    }



    /**
     * 刷新token
     * @param request the request
     * @return
     */
    @Override
    public TokenDetail refreshToken(RefreshTokenRequest request){
        JwtItem item = tokenService.vaildRS256(request.getRefreshToken());

        User user = userRepository.findById(item.getUserId()).orElseThrow(RuntimeException::new);
        return tokenService.refreshToken(request.getRefreshToken(), user,request.getClientId());
    }
}
