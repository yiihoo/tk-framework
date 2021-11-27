package net.thinklog.common.kit;

import net.thinklog.common.exception.Asserts;

/**
 * @author azhao
 * 2021/9/19 10:29
 */
public class MobileCryptKit {
    //设置字符数组
    //可以添加任意不重复字符，提高能转换的进制的上限
    static char[] encodeChars = "234578abcdefghjkmnprstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();


    /**
     * 手机号转为字符串
     * 第一位去掉，只保留后10位，目的为了保证加密后的字符为6个
     *
     * @param mobile 手机号
     * @return 加密后字符串
     */
    public static String encode(String mobile) {
        long number = Long.parseLong(mobile) - 10000000000L;
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            int idx = (int) (number % encodeChars.length);
            sb.append(encodeChars[idx]);
            number = number / encodeChars.length;
        }
        return sb.reverse().toString();
    }

    private static int getCharIndex(char c) {
        int i = 0;
        for (char cr : encodeChars) {
            if (cr == c) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 字符串转为手机号
     *
     * @param decodeStr 待转化的字符串
     * @return 手机号
     */
    public static String decode(String decodeStr) {
        validCode(decodeStr);
        char[] chars = decodeStr.toCharArray();
        long n = 0L;
        for (int i = 0; i < chars.length; i++) {
            int prefix = getCharIndex(chars[i]);
            double base = Math.pow(encodeChars.length, chars.length - i - 1);
            n += prefix * base;
        }
        return "1" + n;
    }

    private static void validCode(String str) {
        if (StrKit.isBlank(str) || str.length() > 6) {
            Asserts.fail("请输入正确的字符");
        }
        char[] chars = str.toCharArray();
        for (char s : chars) {
            if (getCharIndex(s) < 0) {
                Asserts.fail("请输入有效的字符");
            }
        }
    }

    //测试
    public static void main(String[] args) {
        System.out.println(encode("19999999999"));
        System.out.println(encode("13656441929"));
        System.out.println("ykHJSk=======>" + decode("ykHJSk"));
        System.out.println("dE7yuX=======>" + decode("dE7yuX"));
    }
}
