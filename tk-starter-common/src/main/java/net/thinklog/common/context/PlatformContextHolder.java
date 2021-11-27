package net.thinklog.common.context;

import cn.hutool.core.convert.Convert;
import com.alibaba.ttl.TransmittableThreadLocal;
import net.thinklog.common.bean.enums.PlatformEnum;
import net.thinklog.common.kit.StrKit;

/**
 * 租户holder
 *
 * @author azhao
 * @date 2019/8/5
 */
public class PlatformContextHolder {
    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<Integer> CONTEXT = new TransmittableThreadLocal<>();

    public static void setPlatformId(String platformId) {
        if (StrKit.isBlank(platformId)) {
            CONTEXT.set(PlatformEnum.DEFAULT.getValue());
            return;
        }
        CONTEXT.set(Convert.toInt(platformId));
    }

    public static Integer getPlatformId() {
        return CONTEXT.get() == null ? PlatformEnum.DEFAULT.getValue() : CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
