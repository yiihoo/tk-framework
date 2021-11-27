package net.thinklog.common.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import net.thinklog.common.api.IErrorCode;
import net.thinklog.common.api.RestCode;
import net.thinklog.common.bean.enums.ErrorCodeEnum;

/**
 * 自定义API异常
 *
 * @author azhao
 * @date 2020/2/27
 */

public class ApiException extends RuntimeException {
    private IErrorCode error;
    private String data;

    @JsonProperty("error_code")
    private ErrorCodeEnum errorCode;

    public ApiException(IErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApiException(ErrorCodeEnum errorCode) {
        super(errorCode.getDescription());
        this.error = RestCode.FAILED;
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
        this.error = RestCode.FAILED;
        this.errorCode = ErrorCodeEnum.USER_ERROR_0001;
    }

    public ApiException(String message, String msg) {
        super(message);
        this.error = RestCode.FAILED;
        this.errorCode = ErrorCodeEnum.USER_ERROR_0001;
        this.data = msg;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getError() {
        return error;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setError(IErrorCode error) {
        this.error = error;
    }
}
