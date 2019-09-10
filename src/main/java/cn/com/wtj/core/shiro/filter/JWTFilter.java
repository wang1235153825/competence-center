package cn.com.wtj.core.shiro.filter;

import cn.com.wtj.core.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created on 2019/9/9.
 * the filter do check jwt
 * @author wangtingjun
 * @since 1.0.0
 */
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private String jwtHeader;

    private TokenService tokenService;

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的数据
        HttpServletRequest httpServletRequest =(HttpServletRequest) request;
        String header = httpServletRequest.getHeader(this.jwtHeader);
        if (header == null && header.equals("")){
            log.info("请求头中[{}]信息为空",jwtHeader);
            return;
        }

        //解析token
//        tokenService.obtain();
        System.out.println(header);
    }
}
