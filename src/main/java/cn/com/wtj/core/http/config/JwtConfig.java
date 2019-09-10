package cn.com.wtj.core.http.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties("jwt.secret")
public class JwtConfig {

    private final String JWT_VALUE_ISS = "USER_CENTER";

    private final String JWT_VALUE_AUD = "web";

    private String Key;

    private Integer tokenExpiresIn;

    private Integer refreshTokenExpiresIn;

    private Integer refreshTokenExpiresThreshold;

    private String jwtHeader;

}
