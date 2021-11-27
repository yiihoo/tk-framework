package net.thinklog.feign.resolver;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import net.thinklog.feign.annotation.LoginUser;
import net.thinklog.common.api.RestCode;
import net.thinklog.common.bean.dto.LoginUserDTO;
import net.thinklog.common.bean.enums.ErrorCodeEnum;
import net.thinklog.common.bean.model.user.AppUser;
import net.thinklog.common.config.AuthConstant;
import net.thinklog.common.config.BaseConstant;
import net.thinklog.common.exception.Asserts;
import net.thinklog.common.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.thinklog.feign.service.UserBaseFeignRpcService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Token转化AppUser
 *
 * @author DELL
 */
@Slf4j
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserBaseFeignRpcService userBaseFeignRpcService;

    public TokenArgumentResolver(UserBaseFeignRpcService userBaseFeignRpcService) {
        this.userBaseFeignRpcService = userBaseFeignRpcService;
    }

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class)
                && (methodParameter.getParameterType().equals(LoginUserDTO.class) || methodParameter.getParameterType().equals(AppUser.class));
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);
        if (loginUser == null) {
            return null;
        }
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }
        String jsonStr = Base64.decodeStr(request.getHeader(AuthConstant.USER_TOKEN_HEADER));
        if (StrUtil.isEmpty(jsonStr) || !JSONObject.isValid(jsonStr)) {
            Asserts.fail(RestCode.UNAUTHORIZED);
        }
        LoginUserDTO dto = JSONObject.parseObject(jsonStr, LoginUserDTO.class);
        if (dto == null) {
            log.info("====user异常登录===={}", jsonStr);
            Asserts.fail(RestCode.UNAUTHORIZED);
        }
        boolean isFull = loginUser.value();
        AppUser appUser = new AppUser();
        appUser.setId(dto.getId());
        if (isFull) {
            appUser = userBaseFeignRpcService.getById(dto.getId());
            if (appUser == null) {
                Asserts.fail(RestCode.UNAUTHORIZED);
            }
            //判断资料是否齐全
            if (StrKit.isBlank(appUser.getMobile())
                    || StrKit.isBlank(appUser.getNickname())
                    || appUser.getNickname().contains(BaseConstant.DEFAULT_USER_NAME)) {
                Asserts.fail(ErrorCodeEnum.USER_ERROR_A0101);
            }
        }
        return appUser;
    }
}
