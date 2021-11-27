package net.thinklog.common.kit;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import net.thinklog.common.config.AuthConstant;
import net.thinklog.common.exception.Asserts;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 封装rpc请求
 *
 * @author azhao
 */
@Slf4j
public class HttpSignKit {
    public static String post(String token, String url, Map<String, Object> params) {
        long timestamp = System.currentTimeMillis();
        String nonceStr = UUID.randomUUID().toString();
        String signStr = SignKit.createSign(token, Long.toString(timestamp), nonceStr, null, new TreeMap<>(params));
        //链式构建请求
        String res = HttpRequest.post(url)
                .header(Header.USER_AGENT, "ST-PLATFORM")
                .header(AuthConstant.SIGNATURE_TIMESTAMP, Long.toString(timestamp))
                .header(AuthConstant.JWT_TOKEN_HEADER, token)
                .header(AuthConstant.SIGNATURE_NONCE_STRING, nonceStr)
                .header(AuthConstant.SIGNATURE_SIGN_STRING, signStr)
                .form(params)
                .timeout(20000)
                .execute().body();
        log.info("===请求结果====\n{}", res);
        return res;
    }

    public static <T> String postJson(String token, String url, T obj) {
        if(obj==null){
            Asserts.fail("POST-JSON传递对象不能为空");
        }
        String jsonStr = JsonKit.toJson(obj);
        long timestamp = System.currentTimeMillis();
        String nonceStr = UUID.randomUUID().toString();
        String signStr = SignKit.createSign(token, Long.toString(timestamp), nonceStr, jsonStr, null);
        //链式构建请求
        String res = HttpRequest.post(url)
                .header(Header.USER_AGENT, "ST-PLATFORM")
                .header(AuthConstant.SIGNATURE_TIMESTAMP, Long.toString(timestamp))
                .header(AuthConstant.JWT_TOKEN_HEADER, token)
                .header(AuthConstant.SIGNATURE_NONCE_STRING, nonceStr)
                .header(AuthConstant.SIGNATURE_SIGN_STRING, signStr)
                .body(jsonStr, "application/json;charset=UTF-8")
                .timeout(20000)
                .execute().body();
        log.info("===请求结果====\n{}", res);
        return res;
    }

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<>();
        data = new HashMap<>();
        //付款人
        data.put("payer_id", "2");
        //收款人
        data.put("payee_id", "12344");
        data.put("money", "20850");
        data.put("remark", "运费转代收人");
        HttpSignKit.post("hhhh", "https://pay-app.x4fang.com/mybank/transfer", data);
    }
}
