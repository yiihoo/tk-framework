package net.thinklog.common.api;

import net.thinklog.common.config.BaseConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 *
 * @author azhao
 */
@Data
public class RestPageResult<T> implements Serializable {
    private static final long serialVersionUID = -275582248840137389L;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 页码
     */
    private Integer current;
    /**
     * 分页大小
     */
    private Integer size = BaseConstant.PAGE_SIZE;
    /**
     * 当前页结果集
     */
    private List<T> list;


    public Integer getPages() {
        return (total / size) + (total % size == 0 ? 0 : 1);
    }

    public Integer getOffset() {
        return Math.max(getCurrent() - 1, 0) * getSize();
    }
}
