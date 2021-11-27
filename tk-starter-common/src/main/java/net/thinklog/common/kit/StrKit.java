package net.thinklog.common.kit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class StrKit {
    public static String getLastString(List<String> stringList) {
        String code = null;
        if (stringList == null || stringList.isEmpty()) {
            return null;
        }
        for (String n : stringList) {
            code = n;
        }
        return code;
    }

    public static String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINESE);
        return sdf.format(date);
    }

    public static String join(List<?> stringArray, String separator) {
        if (stringArray == null || stringArray.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringArray.size(); i++) {
            if (stringArray.get(i) == null) {
                continue;
            }
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(stringArray.get(i).toString());
        }
        return sb.toString();
    }

    public static String join(String[] stringArray, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(stringArray[i]);
        }
        return sb.toString();
    }

    public static String friendlyTime(Date time) {
        if (time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日", Locale.CHINESE);
        //判断是否是同一天
        String curDate = dateFormat.format(cal.getTime());
        String paramDate = dateFormat.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormat.format(time);
        }
        return ftime;
    }

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(Object o) {
        if (o == null) {
            return true;
        }
        return isBlank(o.toString());
    }

    /**
     * 字符串为 null 或者内部字符全部为 ' ' '\t' '\n' '\r' 这四类字符时返回 true
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    // case '\b':
                    // case '\f':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * 版本号比较，按照
     *
     * @param v1 版本号1
     * @param v2 版本号2
     * @return 0,-1,1
     */
    public static int compareVersion(String v1, String v2) {
        int i = 0, j = 0, x = 0, y = 0;
        int v1Len = v1.length();
        int v2Len = v2.length();
        char c;
        do {
            while (i < v1Len) {
                //计算出V1中的点之前的数字
                c = v1.charAt(i++);
                if (c >= '0' && c <= '9') {
                    //c-‘0’表示两者的ASCLL差值
                    x = x * 10 + (c - '0');
                } else if (c == '.') {
                    break;
                }
            }
            while (j < v2Len) {
                //计算出V2中的点之前的数字
                c = v2.charAt(j++);
                if (c >= '0' && c <= '9') {
                    y = y * 10 + (c - '0');
                } else if (c == '.') {
                    break;
                }
            }
            if (x < y) {
                return -1;
            } else if (x > y) {
                return 1;
            } else {
                x = 0;
                y = 0;
            }

        } while ((i < v1Len) || (j < v2Len));
        return 0;
    }

    public static boolean notBlank(String... strings) {
        if (strings == null || strings.length == 0) {
            return false;
        }
        for (String str : strings) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object... paras) {
        if (paras == null) {
            return false;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static String defaultIfBlank(String str, String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }

    public static String toCamelCase(String stringWithUnderline) {
        if (stringWithUnderline.indexOf('_') == -1) {
            return stringWithUnderline;
        }

        stringWithUnderline = stringWithUnderline.toLowerCase();
        char[] fromArray = stringWithUnderline.toCharArray();
        char[] toArray = new char[fromArray.length];
        int j = 0;
        for (int i = 0; i < fromArray.length; i++) {
            if (fromArray[i] == '_') {
                // 当前字符为下划线时，将指针后移一位，将紧随下划线后面一个字符转成大写并存放
                i++;
                if (i < fromArray.length) {
                    toArray[j++] = Character.toUpperCase(fromArray[i]);
                }
            } else {
                toArray[j++] = fromArray[i];
            }
        }
        return new String(toArray, 0, j);
    }

    public static String join(String[] stringArray) {
        StringBuilder sb = new StringBuilder();
        for (String s : stringArray) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static List<String> strToStringList(String str) {
        return strToStringList(str, "\\|");
    }

    public static List<String> strToStringList(String str, String tag) {
        String[] arr = str.split(tag);
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static List<Integer> strToIntegerList(String str, String tag) {
        List<Integer> list = new ArrayList<>();
        String[] arr = str.split(tag);
        for (String s : arr) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }

    public static List<Long> strToLongList(String str, String tag) {
        List<Long> list = new ArrayList<>();
        String[] arr = str.split(tag);
        for (String s : arr) {
            list.add(Long.valueOf(s));
        }
        return list;
    }

    public static List<BigDecimal> strToBigDecimalList(String str) {
        List<BigDecimal> list = new ArrayList<>();
        String[] arr = str.split("\\|");
        for (String s : arr) {
            list.add(new BigDecimal(s));
        }
        return list;
    }


    public static boolean equals(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmptyList(List<String> list) {
        int n = 0;
        if (list == null || list.isEmpty()) {
            return true;
        }
        for (String s : list) {
            if (isBlank(s)) {
                continue;
            }
            n++;
        }
        return n == 0;
    }

    /**
     * 截取字符串
     *
     * @param str    字符串
     * @param length 长度
     * @return 截取后的字符串
     */
    public static String substr(String str, int length) {
        if (str == null) {
            return "";
        }
        if (str.length() < length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}
