package net.thinklog.common.exception;

import net.thinklog.common.api.IErrorCode;
import net.thinklog.common.bean.enums.ErrorCodeEnum;
import net.thinklog.common.kit.RegExpKit;
import net.thinklog.common.kit.StrKit;

import java.io.Serializable;

/**
 * 断言处理类，用于抛出各种API异常
 *
 * @author azhao
 * @date 2020/2/27
 */
public class Asserts implements Serializable {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void valid(Object o, String message) {
        if (o == null) {
            fail(message);
        }
    }

    public static void notNull(Object o, String message) {
        if (o == null) {
            fail(message);
        }
    }

    public static void regexp(Object o, String reg, String message) {
        if (!RegExpKit.regExp(o, reg)) {
            fail(message);
        }
    }

    public static void notBlank(Object o, String message) {
        if (StrKit.isBlank(o)) {
            fail(message);
        }
    }

    public static void valid(boolean o, String message) {
        if (!o) {
            fail(message);
        }
    }

    public static void fail(String message, String data) {
        throw new ApiException(message, data);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
    public static void fail(ErrorCodeEnum errorCodeEnum) {
        throw new ApiException(errorCodeEnum);
    }
}
