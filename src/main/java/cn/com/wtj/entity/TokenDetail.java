package cn.com.wtj.entity;

import cn.com.wtj.entity.base.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode
public class TokenDetail extends BaseBean {
    private static final long serialVersionUID = 3561623126351089934L;

    public static final String TOKEN_TYPE_BEARER = "Bearer";

    private String tokenType = TOKEN_TYPE_BEARER;

    private String accessToken;

    private Long expiresTime;

    private String refreshToken;

    private String scope;
}
