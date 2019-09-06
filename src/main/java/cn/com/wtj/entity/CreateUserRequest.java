package cn.com.wtj.entity;

import cn.com.wtj.core.repository.entity.base.BaseEntity;
import cn.com.wtj.entity.base.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

/**
 * Created on 2019/9/6.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode
public class CreateUserRequest extends BaseBean {
    private static final long serialVersionUID = 517520464808983412L;

    private String userName;

    private String password;

    private String age;

}
