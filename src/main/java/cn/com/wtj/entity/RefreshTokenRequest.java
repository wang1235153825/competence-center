package cn.com.wtj.entity;

import cn.com.wtj.entity.base.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created on 2019/9/4.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode
public class RefreshTokenRequest extends BaseBean {
    private static final long serialVersionUID = -2118329942746908828L;

    private String userId;

    private String clientId;

    private String refreshToken;

}
