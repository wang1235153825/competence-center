package cn.com.wtj.entity.base;

/**
 * Created on 2019/9/3.
 *
 * @author wangtingjun
 * @since 1.0.0
 */
public class BaseResponseBuilder {

    private static final long serialVersionUID = -4723457426024724578L;

    private String code;

    private String message;

    private static BaseResponseBuilder getResponse() {
        return new BaseResponseBuilder();
    }

    private BaseResponseBuilder code(String code) {
        this.code = code;
        return this;
    }

    private BaseResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    private <T> BaseResponse<T> build(T body) {
        BaseResponse<T> response = new BaseResponse<>(code, message);
        response.setBody(body);
        return response;
    }

    public static <T> BaseResponse<T> success(String code, String message, T body) {
        return getResponse().code(code).message(message).build(body);
    }
}
