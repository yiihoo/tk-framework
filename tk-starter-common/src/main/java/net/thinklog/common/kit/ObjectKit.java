package net.thinklog.common.kit;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * object对象转换函数
 *
 * @author azhao
 * 2021/11/10 13:04
 */
public class ObjectKit {

    /**
     * Object对象转为List
     */
    public static <T> List<T> toList(Object obj, Class<T> clz) {
        List<T> list = new ArrayList<>();
        if (obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(BeanUtil.copyProperties(o, clz));
            }
        }
        return list;
    }

    /**
     * Object对象转为bean
     */
    public static <T> T toBean(Object obj, Class<T> clz) {
        return BeanUtil.copyProperties(obj, clz);
    }
}
