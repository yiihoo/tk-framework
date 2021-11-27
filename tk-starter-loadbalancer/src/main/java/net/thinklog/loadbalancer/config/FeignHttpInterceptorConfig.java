package net.thinklog.loadbalancer.config;

import com.saite.common.config.AuthConstant;
import com.saite.common.config.BaseConstant;
import com.saite.common.kit.StrKit;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * feign拦截器，只包含http相关数据
 *
 * @author azhao
 * @date 2019/10/26
 * <p>
 */
public class FeignHttpInterceptorConfig {
    protected List<String> requestHeaders = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        requestHeaders.add(AuthConstant.USER_ID_HEADER);
        requestHeaders.add(AuthConstant.USER_HEADER);
        requestHeaders.add(AuthConstant.ROLE_HEADER);
        requestHeaders.add(AuthConstant.PLATFORM_HEADER);
        requestHeaders.add(BaseConstant.VERSION);
    }

    /**
     * 使用feign client访问别的微服务时，将上游传过来的access_token、username、roles等信息放入header传递给下一个服务
     */
    @Bean
    public RequestInterceptor httpFeignInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    String headerName;
                    String headerValue;
                    while (headerNames.hasMoreElements()) {
                        headerName = headerNames.nextElement();
                        if (requestHeaders.contains(headerName)) {
                            headerValue = request.getHeader(headerName);
                            template.header(headerName, headerValue);
                        }
                    }
                }
                //传递access_token，无网络隔离时需要传递
                String token = extractHeaderToken(request);
                if (StrKit.isBlank(token)) {
                    token = request.getParameter(AuthConstant.JWT_TOKEN_HEADER);
                }
                if (StrKit.notBlank(token)) {
                    template.header(AuthConstant.JWT_TOKEN_HEADER, AuthConstant.JWT_TOKEN_PREFIX + " " + token);
                }
            }
        };
    }

    /**
     * 解析head中的token
     *
     * @param request
     */
    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AuthConstant.JWT_TOKEN_HEADER);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(AuthConstant.JWT_TOKEN_PREFIX.toLowerCase()))) {
                String authHeaderValue = value.substring(AuthConstant.JWT_TOKEN_PREFIX.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
