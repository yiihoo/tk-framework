package net.thinklog.feign.config;

import cn.hutool.core.codec.Base64;
import net.thinklog.common.config.AuthConstant;
import net.thinklog.common.exception.ApiException;
import net.thinklog.common.kit.JsonKit;
import net.thinklog.common.kit.StrKit;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 这个方法经历了很多版本，最核心的功能不是解码，而是拦截错误信息
 * 应用系统返回的错误信息{"code":500,"message":"系统错误等等"}
 * 这种形式的，如果对应的bean也有类似的字段比如AreaVO{"code":"370303000000","title":"淄博市张店区"}
 * 这样根据内容解码就会出错
 * 期间用了fastjson/hutool的convert，或多或少有这样那样的问题
 * spring采用的jackson，解码也得对应，还有bean也是用jackson的注解
 * 工具类和spring关于jackson配置必须统一，否则解码会不一致
 * feign的坑点比较多，究其原因还是对于http以及jackson使用不当导致的
 *
 * @author azhao
 */
@Slf4j
public class FeignResultDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.body() == null) {
            return null;
        }
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
//        log.info("=====[Feign请求开始]=====\n{}", response.request().url());
//        log.info("=====body====={}", bodyStr);
//        log.info("=====type====={}", type);
        //中途有错误，只返回json串{"code":500,"message":"密码不正确","timestamp":1624187505528}
        //如果对应的类型也有code属性，那就错误了，所以不能从请求体里面
        if (StrKit.isBlank(bodyStr)) {
            return null;
        }
        boolean isError = response.headers().containsKey(AuthConstant.ERROR_CODE_HEADER);
        if (isError) {
            //错误信息抛异常
            List<String> msgArray = (List<String>) response.headers().get(AuthConstant.ERROR_MSG_HEADER);
            String msg = "系统错误";
            if (msgArray.size() > 0) {
                //base64解码错误信息
                msg = Base64.decodeStr(msgArray.get(0));
            }
            throw new ApiException(msg);
        }
        return JsonKit.toType(type, bodyStr);
    }
}
