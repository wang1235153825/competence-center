package cn.com.wtj.entity.base;

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
public class BaseResponse<T> extends BaseBean{
    private static final long serialVersionUID = -3276454401241312505L;

    private String message;

    private String code;

    private T body;

    public BaseResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
