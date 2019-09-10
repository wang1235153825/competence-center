package cn.com.wtj.core.service.bean;

import cn.com.wtj.entity.base.BaseBean;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2019/9/4.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode
@Builder
public class JwtItem implements Serializable {
    private static final long serialVersionUID = -4083658159989084490L;

   private String jwtId;

    private String userId;

    private String clientId;

    private long expirationTime;

}
