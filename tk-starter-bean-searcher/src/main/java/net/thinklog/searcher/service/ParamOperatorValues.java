package net.thinklog.searcher.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yiihoo
 */
public class ParamOperatorValues implements Serializable {
    private String operator;
    private String field;
    private List<Object> values;

    public ParamOperatorValues() {
        values = new ArrayList<>();
    }

    public void addValue(Object v) {
        values.add(v);
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
