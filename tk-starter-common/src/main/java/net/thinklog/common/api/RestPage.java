package net.thinklog.common.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @author azhao
 * @date 2019/4/19
 */
@JsonIgnoreProperties({ "records", "orders","optimizeCountSql","hitCount","searchCount" })
public class RestPage<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> {
    public RestPage() {
        super();
    }

    public RestPage(long current, long size) {
        super(current, size);
    }

    public RestPage(long current, long size, long total) {
        super(current, size, total);
    }

    public RestPage(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public RestPage(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    @Override
    public List<T> getRecords() {
        return super.getRecords();
    }

    public List<T> getList() {
        return super.getRecords();
    }

    public Page<T> setList(List<T> records) {
        return super.setRecords(records);
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        return super.setRecords(records);
    }


    @Override
    public long getTotal() {
        return super.getTotal();
    }

    @Override
    public Page<T> setTotal(long total) {
        return super.setTotal(total);
    }

    @Override
    public long getSize() {
        return super.getSize();
    }

    @Override
    public Page<T> setSize(long size) {
        return super.setSize(size);
    }

    @Override
    public long getCurrent() {
        return super.getCurrent();
    }

    @Override
    public Page<T> setCurrent(long current) {
        return super.setCurrent(current);
    }

}
