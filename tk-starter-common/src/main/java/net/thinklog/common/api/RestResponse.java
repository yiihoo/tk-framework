package net.thinklog.common.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.thinklog.common.bean.enums.ErrorCodeEnum;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author azhao
 * @date 2019/4/19
 */
public class RestResponse<T> implements Serializable {
    private Integer code;
    /**
     * 阿里巴巴错误代码
     */
    @JsonProperty("error_code")
    private String errorCode;
    private String message;
    private T data;
    private Long timestamp;

    protected RestResponse() {
    }

    public RestResponse(T data) {
        new RestResponse<>(RestCode.SUCCESS.getCode(), RestCode.SUCCESS.getMessage(), data);
    }

    protected RestResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        if (RestCode.SUCCESS.getCode().equals(code)) {
            this.errorCode = ErrorCodeEnum.SUCCESS.getCode();
        } else {
            this.errorCode = ErrorCodeEnum.USER_ERROR_0001.getCode();
        }
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    protected RestResponse(ErrorCodeEnum errorCodeEnum, String msg) {
        this.code = RestCode.FAILED.getCode();
        this.errorCode = errorCodeEnum.getCode();
        this.message = (msg == null ? errorCodeEnum.getDescription() : msg);
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(RestCode.SUCCESS.getCode(), RestCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> RestResponse<T> success(T data, String message) {
        return new RestResponse<>(RestCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> RestResponse<T> failed(ErrorCodeEnum errorCode) {
        return new RestResponse<T>(errorCode, null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> RestResponse<T> failed(ErrorCodeEnum errorCode, String msg) {
        return new RestResponse<>(errorCode, msg);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> RestResponse<T> failed(IErrorCode errorCode) {
        return new RestResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> RestResponse<T> failed(IErrorCode errorCode, String message) {
        return new RestResponse<>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> RestResponse<T> failed(String message) {
        return new RestResponse<>(RestCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> RestResponse<T> failed() {
        return failed(RestCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> RestResponse<T> validateFailed() {
        return failed(RestCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> RestResponse<T> validateFailed(String message) {
        return new RestResponse<>(RestCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> RestResponse<T> unauthorized(T data) {
        return new RestResponse<>(RestCode.UNAUTHORIZED.getCode(), RestCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> RestResponse<T> forbidden(T data) {
        return new RestResponse<>(RestCode.FORBIDDEN.getCode(), RestCode.FORBIDDEN.getMessage(), data);
    }

    public Integer getCode() {
        return code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public RestResponse<T> setMessage(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public T getData() {
        return data;
    }

    public RestResponse<T> setData(T data) {
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        return this;
    }
}