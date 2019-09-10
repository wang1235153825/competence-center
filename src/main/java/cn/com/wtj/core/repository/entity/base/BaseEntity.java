package cn.com.wtj.core.repository.entity.base;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created on 2019/9/4.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 3671548893215053077L;

    /**
     * 状态  可用
     */
    public static final Short TYPE_OK = 1;


    /**
     * 状态 禁用
     */
    public static final Short TYPE_DISABLE = 0;
}
