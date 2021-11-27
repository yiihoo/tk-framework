package net.thinklog.feign.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSONObject;
import com.saite.common.config.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author azhao
 * 2021/8/3 9:30
 */
@Slf4j
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) {
                //屏蔽错误信息401等错误
                //super.handleError(response);

            }
        });
        this.restTemplate = restTemplate;
    }

    private String getToken(Long userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", userId);
        return Base64.encodeUrlSafe(jsonObject.toJSONString());
    }

    /**
     * post 请求
     *
     * @param url 请求路径
     * @param uid JWT所需的Token，不需要的可去掉
     */
    public String postForm(String url, Map<String, Object> params, Long uid) {
        return postForm(url, params, uid, String.class);
    }

    /**
     * post 请求
     *
     * @param url 请求路径
     * @param uid JWT所需的Token，不需要的可去掉
     */
    public <T> T postForm(String url, Map<String, Object> params, Long uid, Class<T> tClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        if (uid != null) {
            headers.add(AuthConstant.USER_TOKEN_HEADER, getToken(uid));
        }
        String data = URLUtil.buildQuery(params, CharsetUtil.CHARSET_UTF_8);
        HttpEntity<String> requestEntity = new HttpEntity<>(data, headers);
        try {
            return restTemplate.postForObject(url, requestEntity, tClass);
        } catch (Exception e) {
            log.error("===post请求错误==={}", e.getMessage());
        }
        return null;
    }

    /**
     * post 请求
     *
     * @param url  请求路径
     * @param data body数据
     */
    public String postJson(String url, String data) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<>(data, headers);
        try {
            return restTemplate.postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            log.error("===post请求错误==={}", e.getMessage());
        }
        return null;
    }

    /**
     * post 请求
     *
     * @param url  请求路径
     * @param data body数据
     * @param uid  JWT所需的Token，不需要的可去掉
     */
    public String postJson(String url, String data, Long uid) {
        return postJson(url, data, uid, String.class);
    }

    /**
     * post 请求
     *
     * @param url  请求路径
     * @param data body数据
     * @param uid  JWT所需的Token，不需要的可去掉
     */
    public <T> T postJson(String url, String data, Long uid, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        if (uid != null) {
            headers.add(AuthConstant.USER_TOKEN_HEADER, getToken(uid));
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(data, headers);
        try {
            return restTemplate.postForObject(url, requestEntity, clazz);
        } catch (Exception e) {
            log.error("===post请求错误==={}", e.getMessage());
        }
        return null;
    }

    /**
     * get 请求
     *
     * @param url 请求路径
     * @param uid JWT所需的Token，不需要的可去掉
     */
    public String get(String url, Long uid) {
        return get(url, uid, String.class);
    }

    /**
     * get 请求
     *
     * @param url 请求路径
     * @param uid JWT所需的Token，不需要的可去掉
     */
    public <T> T get(String url, Long uid, Class<T> tClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Encoding", "UTF-8");
        headers.add("Content-Type", "application/json; charset=UTF-8");
        if (uid != null) {
            headers.add(AuthConstant.USER_TOKEN_HEADER, getToken(uid));
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, tClass);
            return response.getBody();
        } catch (Exception e) {
            log.error("===get请求错误==={}", e.getMessage());
        }
        return null;
    }
}
