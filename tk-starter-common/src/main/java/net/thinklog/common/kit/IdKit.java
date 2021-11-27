package net.thinklog.common.kit;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.time.LocalDateTime;

/**
 * id生成器
 * 必须单例模式，否则重复
 *
 * @author yiihoo
 */
public class IdKit {
    private static final Snowflake SNOWFLAKE = IdUtil.createSnowflake(1, 1);

    public static Long getSnowflakeId() {
        return SNOWFLAKE.nextId();
    }

    public static String getDateId() {
        return DateUtil.format(LocalDateTime.now(), "yyyyMMddHH") + getSnowflakeId().toString().substring(10);
    }
}
