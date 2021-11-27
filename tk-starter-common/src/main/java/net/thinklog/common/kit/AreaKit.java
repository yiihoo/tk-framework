package net.thinklog.common.kit;

import java.math.BigDecimal;

/**
 * @author yiihoo
 */
public class AreaKit {
    private static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        lat1 = (lat1 * Math.PI) / 180;
        lng1 = (lng1 * Math.PI) / 180;
        lat2 = (lat2 * Math.PI) / 180;
        lng2 = (lng2 * Math.PI) / 180;
        double calcLongitude = lng2 - lng1;
        double calcLatitude = lat2 - lat1;
        double stepOne = Math.pow(Math.sin(calcLatitude / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(calcLongitude / 2), 2);
        double stepTwo = 2 * Math.asin(Math.min(1, Math.sqrt(stepOne)));
        return 6371.393 * stepTwo;
    }

    public static long getDistance(BigDecimal lng1, BigDecimal lat1, BigDecimal lng2, BigDecimal lat2) {
        if (isZero(lng1) || isZero(lng2) || isZero(lat1) || isZero(lat2)) {
            return 0L;
        }
        double distance = getDistance(lng1.doubleValue(), lat1.doubleValue(), lng2.doubleValue(), lat2.doubleValue());
        return (long) distance;
    }

    /**
     * 判断是否符合经纬度要求
     *
     * @param n 经纬度
     * @return boolean
     */
    private static boolean isZero(Object n) {
        return !RegExpKit.regExp(n, "^[1-9][0-9]{0,2}\\.\\d+$");
    }

    public static long getDistance(String lng1, String lat1, String lng2, String lat2) {
        if (isZero(lng1) || isZero(lng2) || isZero(lat1) || isZero(lat2)) {
            return 0L;
        }
        double distance = getDistance(Double.parseDouble(lng1), Double.parseDouble(lat1), Double.parseDouble(lng2), Double.parseDouble(lat2));
        return (long) distance;
    }
}
