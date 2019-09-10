package cn.com.wtj.core.http.config;

import cn.com.wtj.core.service.TokenService;
import cn.com.wtj.core.shiro.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2019/9/9.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private TokenService tokenService;
    @Bean
    public JWTFilter jwtFilter(){
        JWTFilter jwtFilter = new JWTFilter();
        jwtFilter.setJwtHeader(jwtConfig.getJwtHeader());
        jwtFilter.setTokenService(tokenService);
        return jwtFilter;
    }

}
