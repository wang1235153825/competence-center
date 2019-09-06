package cn.com.wtj.core.repository.entity;

import cn.com.wtj.core.repository.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private String age;

    @Column(name = "name")
    private String name;

    @Column(name = "adress")
    private String address;

    @Column(name = "status")
    private Short status;

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(name = "modif_time")
    private LocalDateTime modifyTime;

}
