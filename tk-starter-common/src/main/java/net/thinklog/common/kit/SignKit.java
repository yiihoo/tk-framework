package net.thinklog.common.kit;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * 参数签名
 *
 * @author azhao
 */
@Slf4j
public class SignKit {
    /**
     * 支付异步通知时校验sign
     *
     * @param params      参数
     * @param paternerKey 支付密钥
     * @return {boolean}
     */
    public static boolean verifySign(Map<String, String> params, String paternerKey) {
        String sign = params.get("sign");
        String localSign = createSign(params, paternerKey);
        return sign.equals(localSign);
    }

    /**
     * 生成签名
     *
     * @param params 参数
     * @param secret 支付密钥
     * @return sign
     */
    public static String createSign(Map<String, String> params, String secret) {
        // 生成签名前先去除sign
        params.remove("sign");
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + secret;
        return SecureUtil.md5(stringSignTemp).toUpperCase();
    }

    /**
     * 组装签名的字段
     *
     * @param params     参数
     * @param urlEncoder 是否urlEncoder
     * @return String
     */
    public static String packageSign(Map<String, String> params, boolean urlEncoder) {
        // 先将参数以其参数名的字典序升序进行排序
        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> param : sortedParams.entrySet()) {
            String value = param.getValue();
            if (StrKit.isBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                try {
                    value = urlEncode(value);
                } catch (UnsupportedEncodingException ignored) {
                }
            }
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * urlEncode
     *
     * @param src 微信参数
     * @return String
     * @throws UnsupportedEncodingException 编码错误
     */
    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, String.valueOf(StandardCharsets.UTF_8)).replace("+", "%20");
    }

    private static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        String str = o.toString().trim();
        if ("null".equalsIgnoreCase(str)) {
            return true;
        }
        return StrKit.isBlank(str);
    }

    /**
     * 生成签名
     * map中不能含有如下字段：json/signstr/timestamp/token
     *
     * @return sign
     */
    public static String createSign(String token, String timestamp, String noncestr, String json, TreeMap<String, Object> map) {
        if (map == null) {
            map = new TreeMap<>();
        }
        System.out.println(System.currentTimeMillis());
        if (!isEmpty(json)) {
            map.put("_json", json);
        }
        map.put("_noncestr", noncestr);
        map.put("_timestamp", timestamp);
        map.put("_token", token);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (isEmpty(entry.getKey()) || isEmpty(entry.getValue())) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey().trim()).append("=").append(entry.getValue().toString().trim());
        }
        //空白字符，%，+，\,/
        String md5str = sb.toString().replaceAll("[\\s\\/\\%\\+\\\\]", "");
        String md5 = SecureUtil.md5(md5str).toLowerCase();
        //log.info("=====组装参数=====\n{}\n====长度{}===md5=={}", md5str, md5str.length(), md5);
        long num = Long.parseLong(timestamp);
        int startNum = (int) (num % 22);
        if (startNum < 10) {
            startNum = 10;
        }
        //log.info("====验签加密====\n{}",md5.substring(startNum));
        return md5.substring(startNum);
    }

    /**
     * 验证签名是否一致
     */
    public static String makeSign(HttpServletRequest request) throws IOException {
        String timestamp = request.getHeader("timestamp");
        String noncestr = request.getHeader("noncestr");
        String token = request.getHeader("Authorization");
        TreeMap<String, Object> map = new TreeMap<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = URLDecoder.decode(request.getParameter(name), "UTF-8");
            log.info("{}=>>>{}", name, value);
            map.put(name, value);
        }
        //return  true;
        return SignKit.createSign(token, timestamp, noncestr, "", map);
    }


    public static String createSignSimple(String url, String token, String timestamp, String noncestr, String method) {
        String md5str = StrUtil.format("url{}token{}timestamp{}noncestr{}method{}", url, token, timestamp, noncestr, method.toUpperCase());
        String md5 = SecureUtil.md5(md5str).toLowerCase();
        //log.info("=====组装参数=====\n{}\n====长度{}===md5=={}", md5str, md5str.length(), md5);
        long num = Convert.toLong(timestamp);
        int startNum = (int) (num % 22);
        if (startNum < 10) {
            startNum = 10;
        }
        return md5.substring(startNum);
    }
}
