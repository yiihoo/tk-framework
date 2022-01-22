package net.thinklog.searcher.service;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.searcher.bean.DbField;
import net.thinklog.common.kit.StrKit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字段条件解析器
 * 针对复杂的查询，带有子表，子表带有下级分类
 *
 * @author yiihoo
 */
public class ParamsParserResolver<T> {

    /**
     * 排序字段参数名
     */
    private final String sortName = "sort";

    /**
     * 排序方法字段参数名
     */
    private final String orderName = "order";

    /**
     * 参数名分割符
     * v1.2.0之前默认值是下划线："_"，自 v1.2.0之后默认值更新为中划线："-"
     */
    private final String separator = "-";

    /**
     * 过滤运算符参数名后缀
     */
    private final String operatorSuffix = "op";
    /**
     * 字段条件
     */
    private Map<String, ParamOperatorValues> fieldsMap;
    /**
     * 条件列表
     */
    private Map<String, Object> params;
    /**
     * 默认实现类
     */
    private Class<T> clazz;

    public ParamsParserResolver() {

    }

    public ParamsParserResolver(Map<String, Object> params, Class<T> clazz) {
        this.params = params;
        this.clazz = clazz;
        this.fieldsMap = new HashMap<>(8);
    }

    public List<ParamOperatorValues> parseParams() {
        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            DbField dbField = field.getAnnotation(DbField.class);
            fieldsMap.put(field.getName(), new ParamOperatorValues());
            if (dbField != null) {
                fieldsMap.get(field.getName()).setField(dbField.value());
            } else {
                fieldsMap.get(field.getName()).setField(field.getName());
            }
        }
        fieldsMap.forEach((fieldName, values) -> {
            values.setOperator("eq");
            params.forEach((k, v) -> {
                if (StrKit.isBlank(v)) {
                    return;
                }
                if (Objects.equals(k, fieldName)) {
                    values.addValue(v);
                }
                if (Objects.equals(k, fieldName + separator + operatorSuffix)) {
                    values.setOperator(v.toString());
                }
                if (regExp(k, "^" + fieldName + separator + "\\d+$")) {
                    values.addValue(v);
                }
            });
        });
        return fieldsMap.values().stream().filter(v -> v.getValues().size() > 0).collect(Collectors.toList());
    }

    private boolean regExp(String content, String regExp) {
        if (content == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("id-0", 252000L);
        params.put("id-1", 1300L);
        params.put("id-2", 1895666L);
        params.put("maxId", 1380L);
        params.put("maxId-op", "gt");
        params.put("userId", "");
        params.put("userId-0", 252000L);
        params.put("createTime-op", "bt");
        params.put("createTime-0", "2021-12-25");
        params.put("createTime-1", "2022-12-25");
        ParamsParserResolver<TestDTO> t = new ParamsParserResolver<>(params, TestDTO.class);
        List<ParamOperatorValues> arr = t.parseParams();
        System.out.println("JSONObject.toJSONString(arr) = " + JSONObject.toJSONString(arr));
    }
}
