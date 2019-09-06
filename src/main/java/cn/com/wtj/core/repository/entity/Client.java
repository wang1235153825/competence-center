package cn.com.wtj.core.repository.entity;

import cn.com.wtj.core.repository.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created on 2019/9/4.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString
@Entity
@Table(name = "client")
public class Client extends BaseEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_des")
    private String clientDes;

    @Column(name = "client_status")
    private Short clientStatus;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
}
