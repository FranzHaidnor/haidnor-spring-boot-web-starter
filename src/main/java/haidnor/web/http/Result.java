package haidnor.web.http;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Result<T> {

    /**
     * 成功状态码
     */
    public static final Integer SUCCEED_CODE = 0;

    /**
     * 失败状态码
     */
    public static final Integer FAIL_CODE = 1;

    /**
     * 响应码. 0 成功, 1 失败
     */
    private final Integer code;

    /**
     * 异常堆栈信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String stack;

    /**
     * 接口信息
     */
    private final String message;

    /**
     * 响应数据
     */
    private final T data;


    private Result(Integer code, String message, T data, String stack) {
        this.message = message;
        this.code = code;
        this.data = data;
        this.stack = stack;
    }

    public static Result<Object> success() {
        return new Result<>(SUCCEED_CODE, "ok", null, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCEED_CODE, "ok", data, null);
    }

    public static <T> Result<T> fail() {
        return new Result<>(FAIL_CODE, "error", null, null);
    }

    public static Result<Object> fail(String message) {
        return new Result<>(FAIL_CODE, message, null, null);
    }

    public static Result<Object> fail(String message, String stack) {
        return new Result<>(FAIL_CODE, message, null, stack);
    }

    public Integer getCode() {
        return code;
    }

    public String getStack() {
        return stack;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}