package net.thinklog.feign.service.fallback;

import net.thinklog.common.bean.model.user.AppUser;
import lombok.extern.slf4j.Slf4j;
import net.thinklog.feign.service.UserBaseFeignRpcService;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author DELL
 */
@Slf4j
public class UserBaseFeignRpcServiceFallBack implements FallbackFactory<UserBaseFeignRpcService> {
    @Override
    public UserBaseFeignRpcService create(Throwable cause) {
        return new UserBaseFeignRpcService() {
            @Override
            public AppUser getById(Long id) {
                log.error("通过ID获取用户异常：{}", id, cause);
                return null;
            }

            @Override
            public AppUser getByMobile(String mobile) {
                log.error("通过Mobile获取用户异常：{}", mobile, cause);
                return null;
            }
        };
    }
}
