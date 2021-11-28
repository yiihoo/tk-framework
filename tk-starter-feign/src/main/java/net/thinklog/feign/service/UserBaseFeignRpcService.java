package net.thinklog.feign.service;

import net.thinklog.common.bean.model.user.AppUser;
import net.thinklog.feign.service.fallback.UserBaseFeignRpcServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author azhao
 */
@FeignClient(value = "st-user", fallbackFactory = UserBaseFeignRpcServiceFallBack.class)
@RequestMapping("/feign-rpc/user-rpc-service")
public interface UserBaseFeignRpcService {
    /**
     * 获取单个用户
     */
    @PostMapping("/get-by-id")
    AppUser getById(@RequestParam("id") Long id);

}
