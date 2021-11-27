package net.thinklog.feign.config;

import com.alibaba.fastjson.JSONObject;
import com.saite.common.handler.DiyExceptionChainHandler;
import com.saite.common.handler.DiyExceptionHandler;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author azhao
 */
@Slf4j
public class FeignExceptionConfig {
    public FeignExceptionConfig(DiyExceptionChainHandler diyExceptionChainHandler) {
        diyExceptionChainHandler.set(new DiyExceptionHandler() {
            @Override
            public String doHandler(Exception e, String msg) {
                //返回真实的错误信息，feign调用后，500的话就直接返回服务器错误了
                //应该转换为真正的错误信息
                if (e instanceof FeignException) {
                    msg = ((FeignException) e).contentUTF8();
                    if (JSONObject.isValid(msg)) {
                        JSONObject jsonObject = JSONObject.parseObject(msg);
                        if (jsonObject.getString("message") != null) {
                            msg = jsonObject.getString("message");
                        }
                    }
                }
                //feign解析异常处理
//                if (e instanceof feign.codec.DecodeException) {
//                    msg = "远程解码失败";
//                    log.error("====远程解码失败===={}", e.getMessage());
//                }
                return msg;
            }
        });

    }
}
