package cn.com.wtj.core.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode
public class User {

    private Long id;

    private String userName;

    private String passWord;

    private String age;

    private Short active;

    private Short status;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

}
