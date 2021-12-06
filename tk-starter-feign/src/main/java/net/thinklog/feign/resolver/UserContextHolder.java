package net.thinklog.feign.resolver;

import com.alibaba.ttl.TransmittableThreadLocal;
import net.thinklog.common.api.RestCode;
import net.thinklog.common.bean.dto.LoginUserDTO;
import net.thinklog.common.exception.Asserts;

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
        checkUser();
        return CONTEXT.get();
    }

    private static void checkUser() {
        if (CONTEXT.get() == null) {
            Asserts.fail(RestCode.UNAUTHORIZED);
        }
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static Long getUserId() {
        checkUser();
        return CONTEXT.get().getId();
    }
}
