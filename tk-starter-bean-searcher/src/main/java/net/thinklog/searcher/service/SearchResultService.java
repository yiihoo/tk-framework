package net.thinklog.searcher.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import net.thinklog.common.api.RestPageResult;
import net.thinklog.searcher.boot.BeanSearcherProperties;

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
    /**
     * 设置分隔符别名，方便vue端书写变量
     */
    public static final String ALIAS_SEPARATOR = "__";
    @Resource
    private MapSearcher mapSearcher;
    @Resource
    private BeanSearcherProperties beanSearcherProperties;

    private Pattern getPattern() {
        BeanSearcherProperties.Params configParam = beanSearcherProperties.getParams();
        return Pattern.compile("(.+?)" + ALIAS_SEPARATOR + "(" + configParam.getOperatorKey() + "|\\d+|" + configParam.getIgnoreCaseKey() + ")$");
    }

    private String[] getStrings(String str) {
        String[] strings = new String[2];
        Matcher m = getPattern().matcher(str);
        while (m.find()) {
            strings[0] = m.group(1);
            strings[1] = m.group(2);
        }
        return strings;
    }

    public <T> RestPageResult<T> pageResult(Map<String, Object> params, Class<T> clazz) {
        int pageSize = params.get("size") == null ? PAGE_SIZE : Convert.toInt(params.get("size"));
        int pageNum = params.get("page") == null ? PAGE_NUM : Convert.toInt(params.get("page"));
        params.put("size", pageSize);
        params.put("page", Math.max(pageNum - 1, 0));
        //转为驼峰式
        Map<String, Object> newParams = new HashMap<>(16);
        params.forEach((key, val) -> {
            if (StrUtil.contains(key, "_")) {
                String[] keys = getStrings(key);
                if (keys[1] != null) {
                    key = CharSequenceUtil.toCamelCase(keys[0]) + beanSearcherProperties.getParams().getSeparator() + keys[1];
                } else {
                    key = CharSequenceUtil.toCamelCase(key);
                }
            }
            newParams.put(key, val);
        });
        SearchResult<Map<String, Object>> searchResult = mapSearcher.search(clazz, newParams);
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
