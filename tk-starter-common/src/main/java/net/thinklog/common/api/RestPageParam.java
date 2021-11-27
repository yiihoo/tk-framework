package net.thinklog.common.api;

import net.thinklog.common.config.BaseConstant;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页参数
 *
 * @param <T>
 * @author azhao
 */
@Data
public class RestPageParam<T> implements Serializable {
    /**
     * 当前页
     */
    private Integer page = 1;
    /**
     * 分页大小
     */
    private Integer size = BaseConstant.PAGE_SIZE;
    /**
     * 搜索参数
     */
    @Valid
    @NotNull(message = "搜索参数不能为空")
    private T param;
}
