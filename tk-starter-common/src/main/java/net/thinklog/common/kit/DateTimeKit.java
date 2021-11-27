package net.thinklog.common.kit;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间相关
 *
 * @author 董华健 2012-9-7 下午1:58:46
 */
public abstract class DateTimeKit {

    /**
     * 将日期格式转为long类型的日期时间格式20201019084635
     *
     * @param datetime
     * @return
     */
    public static Long gmtDate(Date datetime) {
        return Long.parseLong(DateUtil.format(datetime, DatePattern.PURE_DATETIME_PATTERN));
    }

    /**
     * long类型的日期时间格式20201019084635转为日期字符串
     *
     * @param datetimeLong
     * @return
     */
    public static String getGmtDateFromLong(Long datetimeLong) {
        if (datetimeLong == null) {
            return null;
        }
        return DateUtil.format(DateUtil.parse(datetimeLong.toString()), DatePattern.NORM_DATETIME_FORMAT);
    }


    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    public static Long getNowForLong() {
        return Long.parseLong(DateUtil.format(getNow(), DatePattern.PURE_DATETIME_MS_PATTERN));
    }

    /**
     * 获取今天开始时间
     */
    public static Long getStartDayTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取今天结束时间
     */
    public static Long getEndDayTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    public static String getPeriod() {
        return getPeriod(getNow());
    }

    public static String getPeriod(Date payTime) {
        return DateUtil.format(payTime, DatePattern.PURE_DATE_FORMAT);
    }

    public static Integer getPeriodInt(Date payTime) {
        return Convert.toInt(getPeriod(payTime));
    }

    public static Integer getPeriodInt() {
        return Convert.toInt(getPeriod());
    }
}
