package net.thinklog.feign.resolver;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import net.thinklog.common.bean.dto.LoginUserDTO;
import net.thinklog.common.config.AuthConstant;
import net.thinklog.common.context.PlatformContextHolder;
import net.thinklog.common.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * 匹配controller包下面吗的
 *
 * @author DELL
 */
@Aspect
@Slf4j
@ConditionalOnClass({WebMvcConfigurer.class})
public class UserContextHolderAspect implements Ordered {
    @Around("(execution(* *..controller..*(..)))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();
            String platformId = request.getHeader(AuthConstant.PLATFORM_HEADER);
            if (StrKit.notBlank(platformId)) {
                PlatformContextHolder.setPlatformId(platformId);
            }
            String jsonStr = Base64.decodeStr(request.getHeader(AuthConstant.USER_TOKEN_HEADER));
            if (JSONObject.isValid(jsonStr)) {
                LoginUserDTO dto = JSONObject.parseObject(jsonStr, LoginUserDTO.class);
                if (dto != null) {
                    UserContextHolder.setUser(dto);
                }
            }
            return point.proceed();
        } finally {
            UserContextHolder.clear();
            PlatformContextHolder.clear();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
