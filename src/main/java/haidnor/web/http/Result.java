package haidnor.web.http;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

public class Result<T> {

    /**
     * 成功状态码
     */
    public static Integer SUCCEED_CODE = 0;

    /**
     * 失败状态码
     */
    public static Integer FAIL_CODE = 1;

    /**
     * 响应码. 0 成功, 1 失败
     */
    private Integer code;

    /**
     * 异常堆栈信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String stack;

    /**
     * 接口信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;


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

    public static Integer getSucceedCode() {
        return SUCCEED_CODE;
    }

    public static void setSucceedCode(Integer succeedCode) {
        SUCCEED_CODE = succeedCode;
    }

    public static Integer getFailCode() {
        return FAIL_CODE;
    }

    public static void setFailCode(Integer failCode) {
        FAIL_CODE = failCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return Objects.equals(this.code, SUCCEED_CODE);
    }

    /**
     * 检查响应结果, 如果失败了, 则抛出异常
     */
    public T get() {
        if (Objects.equals(this.code, FAIL_CODE)) {
            throw new RuntimeException(message);
        }
        return data;
    }

}