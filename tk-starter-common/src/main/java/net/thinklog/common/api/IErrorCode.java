package net.thinklog.common.api;

/**
 * 封装API的错误码
 *
 * @author azhao
 * @date 2019/4/19
 */
public interface IErrorCode {
    Integer getCode();
    Long getTimestamp();
    String getMessage();
}
