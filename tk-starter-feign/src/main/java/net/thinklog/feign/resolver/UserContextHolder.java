package net.thinklog.feign.resolver;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.saite.common.bean.dto.LoginUserDTO;
import com.saite.common.bean.enums.PlatformEnum;

/**
 * 用户holder
 *
 * @author azhao
 * @date 2019/8/5
 */
public class UserContextHolder {
    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<LoginUserDTO> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(LoginUserDTO userDTO) {
        CONTEXT.set(userDTO);
    }

    public static LoginUserDTO getUser() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static Long getUserId() {
        if (CONTEXT.get() != null) {
            return CONTEXT.get().getId() == null ? 0L : CONTEXT.get().getId();
        }
        return 0L;
    }

    public static Integer getPlatformId() {
        if (CONTEXT.get() != null) {
            return CONTEXT.get().getPlatformId() == null ? PlatformEnum.DEFAULT.getValue() : CONTEXT.get().getPlatformId();
        }
        return PlatformEnum.DEFAULT.getValue();
    }
}
