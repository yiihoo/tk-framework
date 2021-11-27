package net.thinklog.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * feign调用不走网关，默认网内安全，所以需要去掉签名，只转发token即可，也就是登录会员
 *
 * @author azhao
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    /**
     * template的对象是feign根据参数类型生成的，所以原始Content-Type是第一个
     *
     * @param template Feign生成的template对象，包含完整的请求
     */
    @Override
    public void apply(RequestTemplate template) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            //request对象只有一个，外部请求为初始的，后面经过feign调用，其实还是同一个，但是签名每次都不一样
            //所以，如何区别签名对应的那个请求是个大问题
            Enumeration<String> headerNames = request.getHeaderNames();
            //此处把原有的header新增了
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    template.header(name, request.getHeader(name));
                }
                // RequestTemplate中对应Content—Type有处理，有多个Content-Type时，取最后一个
                // Feign请求第一个Content—Type是正确的，其他的有可能是上游传过来的，所以需要把真正的Content-Type放到最后
                // a client can only produce content of one single type, so always override Content-Type and
                //only add a single type
                //this.headers.remove(name);
                //this.headers.put(name,HeaderTemplate.create(name, Collections.singletonList(values.iterator().next())));
                // return this;
                List<String> contentTypes = (List<String>) template.headers().get(HttpHeaders.CONTENT_TYPE);
                if (!contentTypes.isEmpty() && contentTypes.size() > 1) {
                    template.header(HttpHeaders.CONTENT_TYPE, contentTypes.get(0));
                }
            }
        } catch (Exception e) {
            log.info("=====非web环境访问====={}", template.request().url());
        }
    }

}