package cn.com.wtj.core.http.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/9/6.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties("encryption")
public class ShiroValueConfig {

    private String saltValue;

    private int hash;

}
