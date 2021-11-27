package net.thinklog.log.annotation;

import java.lang.annotation.*;

/**
 * @author azhao
 * @date 2020/2/3

 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作信息
     */
    String operation();
}
