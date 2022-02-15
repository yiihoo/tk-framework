package net.thinklog.searcher.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.boot.BeanSearcherProperties;
import net.thinklog.common.api.RestPageResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yiihoo
 */
public class SearchResultService {
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_NUM = 1;

    @Resource
    private MapSearcher mapSearcher;
    @Resource
    private BeanSearcherProperties beanSearcherProperties;

    /**
     * Map<String, Object> params = new HashMap<>();
     * params.put("age", 18);
     * params.put("age-op", "gt");     // gt: 大于，ge: 大于等于，lt: 小于，le: 小于等于，ne: 不等于
     * params.put("age-ic", true);     // gt: 大于，ge: 大于等于，lt: 小于，le: 小于等于，ne: 不等于
     * params.put("age-0", 18);
     * params.put("age-1", 30);
     * FieldOp条件或者FieldIc忽略大小写或者FieldN2第几个类型
     */
    private Pattern getPattern() {
        BeanSearcherProperties.Params configParam = beanSearcherProperties.getParams();
        String opKey = CharSequenceUtil.upperFirst(configParam.getOperatorKey());
        String icKey = CharSequenceUtil.upperFirst(configParam.getIgnoreCaseKey());
        String numKey = "(N)(\\d+)";
        return Pattern.compile("(.+?)(" + opKey + "|" + numKey + "|" + icKey + ")$");
    }

    private String[] getStrings(String str) {
        String[] strings = new String[3];
        Matcher m = getPattern().matcher(str);
        while (m.find()) {
            strings[0] = m.group(1);
            strings[1] = m.group(2);
            //数字准备
            strings[2] = m.group(4);
        }
        return strings;
    }

    private Map<String, Object> parseParams(Map<String, Object> params) {
        //转为驼峰式
        Map<String, Object> newParams = new HashMap<>(16);
        params.forEach((key, val) -> {
            String[] keys = getStrings(key);
            if (keys[1] != null) {
                key = CharSequenceUtil.toCamelCase(keys[0])
                        + beanSearcherProperties.getParams().getSeparator()
                        + (keys[2] == null ? keys[1].toLowerCase() : keys[2]);
            } else {
                key = CharSequenceUtil.toCamelCase(key);
            }

            newParams.put(key, val);
        });
        return newParams;
    }

    public <T> RestPageResult<T> pageResult(Map<String, Object> params, Class<T> clazz) {
        int pageSize = params.get("size") == null ? PAGE_SIZE : Convert.toInt(params.get("size"));
        int pageNum = params.get("page") == null ? PAGE_NUM : Convert.toInt(params.get("page"));
        params.put("size", pageSize);
        params.put("page", Math.max(pageNum - 1, 0));
        SearchResult<Map<String, Object>> searchResult = mapSearcher.search(clazz, parseParams(params));
        List<T> dataList = new ArrayList<>();
        searchResult.getDataList().forEach(rs -> {
            //必须用JSON转，用beanSearch日期解析有问题
            String json = JSONObject.toJSONString(rs);
            dataList.add(JSONObject.parseObject(json, clazz));
        });
        RestPageResult<T> pageResult = new RestPageResult<>();
        pageResult.setTotal(searchResult.getTotalCount().intValue());
        pageResult.setList(dataList);
        pageResult.setSize(pageSize);
        pageResult.setCurrent(pageNum);
        return pageResult;
    }
}
