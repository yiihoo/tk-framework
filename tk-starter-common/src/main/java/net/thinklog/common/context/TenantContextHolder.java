package net.thinklog.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 租户holder
 *
 * @author azhao
 * @date 2019/8/5
 */
public class TenantContextHolder {
    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<Long> CONTEXT = new TransmittableThreadLocal<>();

    public static void setTenant(Long tenant) {
        CONTEXT.set(tenant);
    }

    public static Long getTenant() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
