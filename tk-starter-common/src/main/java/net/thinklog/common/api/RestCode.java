package net.thinklog.common.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author azhao
 * @date 2019/4/19
 */
public enum RestCode implements IErrorCode {
    /**
     * 系统码
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private final Integer code;
    private final String message;
    private final Long timestamp;

    private RestCode(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public Long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
