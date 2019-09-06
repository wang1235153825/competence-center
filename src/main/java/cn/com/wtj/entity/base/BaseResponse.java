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

    public static final String SUCCESS_CODE = "00000";
    public static final String FAILD_CODE = "00001";

    public static final String SUCCESS_MESSAGE = "成功";
    public static final String FAILD_MESSAGE = "失败";

    private String message;

    private String code;

    private T body;

    public BaseResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
