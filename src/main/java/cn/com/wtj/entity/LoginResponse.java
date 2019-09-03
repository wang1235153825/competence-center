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
public class LoginResponse extends BaseBean {
    private static final long serialVersionUID = 5836472389058525378L;

    private TokenDetail tokenDetail;


}
