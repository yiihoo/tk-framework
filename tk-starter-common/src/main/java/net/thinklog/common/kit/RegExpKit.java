package net.thinklog.common.kit;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理常用方法
 */
public abstract class RegExpKit {

    /**
     * 数字分隔符
     */
    public final static String SPLIT_NUMBER = "[^\\d\\.]+";
    /**
     * 匹配非负整数（正整数 + 0）
     */
    public final static String UNSIGNED = "^\\d{1,20}$";

    /**
     * 匹配整数
     */
    public final static String INTEGER = "^-?\\d{1,11}$";
    /**
     * 时间日期匹配
     */
    public final static String PERIOD = "^2\\d{3}[0-1]\\d[0-3]\\d$";
    /**
     * 经纬度匹配
     */
    public final static String LOCATE = "^[1-9][0-9]{1,2}\\.\\d+$";
    /**
     * 纬度匹配
     * 2、中国的经纬度范围大bai约为：纬度3.86~53.55，经度du73.66~135.05
     * LATITUDE 纬度
     * longitude  经度
     */
    public final static String LOCATE_LATITUDE = "^[1-8][0-9]?(\\.\\d{1,16})?$";
    /**
     * 经度匹配
     * 中国的经纬度范围大bai约为：纬度3.86~53.55，经度du73.66~135.05
     */
    public final static String LOCATE_LONGITUDE = "^((1[0-7][0-9]?)|([7-9][0-9]?))(\\.\\d{1,16})?$";
    /**
     * 匹配长整型(大于0）
     */
    public final static String UNSIGNED_LONG_NO_ZERO = "^[1-9]\\d{0,19}$";
    /**
     * 匹配ID
     */
    public final static String ID = "^[1-9]+[0-9]*$";
    /**
     * 匹配非负浮点数（正浮点数 + 0）
     */
    public final static String FLOAT = "^(-?\\d+)(\\.\\d+)?$";
    /**
     * 匹配由26个英文字母组成的字符串
     */
    public final static String EN = "^[A-Za-z]+$";
    /**
     * 匹配由26个英文字母的大写组成的字符串
     */
    public final static String UCASE = "^[A-Z]+$";
    /**
     * 匹配由26个英文字母的小写组成的字符串
     */
    public final static String LCASE = "^[a-z]+$";
    /**
     * 匹配由数字和26个英文字母组成的字符串
     */
    public final static String STRING = "^[A-Za-z0-9]+$";
    /**
     * 匹配email地址
     */
    public final static String EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
    /**
     * 匹配url
     */
    public final static String URL = "(https://|http://)?([\\w-]+\\.)+[\\w-]+(:\\d+)*(/[\\w- ./?%&=]*)?";
    /**
     * 匹配中文字符
     */
    public final static String CN = "[\\u4e00-\\u9fa5]";
    /**
     * 匹配企业名称
     */
    public final static String ENTERPRISE_NAME = "^[\\u4e00-\\u9fa50-9A-Za-z\\,\\.]{5,100}";
    /**
     * 金额
     */
    public final static String MONEY = "(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^0$)|(^\\d\\.\\d{1,2}$)";
    /**
     * 匹配中文字姓名 这个不行 \u4e00-\u9fa5（必须全角字符就行）
     * 麦麦提阿卜杜拉・麦麦提敏
     */
    public final static String CN_NAME = "^[\\u0391-\\uffe5]{2,24}$";
    /**
     * 匹配小数位1位
     */
    public final static String FLOAT_TWO = "^[0-9]{0,14}(\\.[0-9]{1,2})?$";
    /**
     * 匹配小数1-3位数
     */
    public final static String FLOAT_THREE = "^[0-9]{0,14}(\\.[0-9]{1,3})?$";
    /**
     * 匹配双字节字符(包括汉字在内)
     */
    public final static String CNS = "[^\\x00-\\xff]";
    /**
     * 匹配空行
     */
    public final static String SPACE = "\\n[\\s ? ]*\\r";
    /**
     * 匹配HTML标记
     */
    public final static String HTML = "/ <(.*)>.* <\\/\\1> ? <(.*) \\/>/";
    /**
     * 匹配国内电话号码，匹配形式如 0511-4405222 或 021-87888822
     */
    public final static String TEL = "\\d{3}-\\d{8} ?\\d{4}-\\d{7}";
    /**
     * 手机号1开头
     */
    public final static String MOBILE = "^1[3-9]\\d{9}$";
    /**
     * 短信验证码6位数字
     */
    public final static String SMS = "^[0-9]{6}$";
    /**
     * 匹配中国邮政编码
     */
    public final static String ZIP_CODE = "^[1-9]\\d{5}(?!\\d)$";
    /**
     * 匹配身份证, 18位
     */
    public final static String ID_CARD = "^[1-9][0-9]{16}[0-9X]$";
    /**
     * IP
     */
    public final static String IP = "\\d+\\.\\d+\\.\\d+\\.\\d+";
    /**
     * 日期时间
     */
    public final static String DATETIME = "\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}(:\\d{1,2})?";
    /**
     * 日期格式
     */
    public final static String DATE = "20\\d{2}-((0?[0-9])|(1[0-2]))-(([0-2]?[0-9])|(3[0-1]))";
    /**
     * 上传图片
     * 20210728/1420267472858255360.png
     * 16/1413789611037167616.jpg
     */
    public final static String IMAGE = "^[a-z0-9]{2,8}\\/\\d{19,20}\\.(jpg|png|gif|jpeg)$";
    /**
     * 大小写+特殊符号+数字+超过8位
     */
    public final static String PASSWORD = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    /**
     * 车牌号
     */
    public final static String TRUCK_NO = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
    /**
     * 车长
     */
    public final static String TRUCK_LENGTH = "(1.8|2.7|3.8|4.2|5|6.2|6.8|7.7|8.2|8.7|9.6|11.7|12.5|13|13.75|15|16|17.5)";
    /**
     * 车型
     */
    public final static String TRUCK_TYPE = "(平板|高栏|厢式|危险品|自卸|冷藏|保温|高低板|面包车|棉被车|爬梯车|飞翼车)";

    public final static String LICENSE_NO = "^[0-9A-Z]{15,24}$";

    public static final String BASE64_IMAGE = "^data:image/(png|jpg|jpeg|gif);base64,.+";

    /**
     * 正则验证
     *
     * @param content 内容
     * @param regExp  正则表达式
     * @return boolean
     */
    public static boolean regExp(String content, String regExp) {
        if (content == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }


    /**
     * 正则验证
     */
    public static boolean regExp(Object content, String regExp) {
        if (content == null) {
            return false;
        }
        return regExp(content.toString(), regExp);
    }

    /**
     * 是否非零
     */
    public static boolean notZero(Object o) {
        if (!isMoney(o)) {
            return false;
        }
        return regExp(o, "[1-9]+");
    }

    /**
     * 是否为0
     */
    public static boolean isZero(Object o) {
        return !regExp(o, "[1-9]+");
    }

    /**
     * 是否为非零数字
     */
    public static boolean isId(Object o) {
        return regExp(o, UNSIGNED_LONG_NO_ZERO);
    }

    /**
     * 是否为密码6个数字
     */
    public static boolean isPwd(Object o) {
        return regExp(o, "^\\d{6}$");
    }

    /**
     * 是否为手机号
     */
    public static boolean isMobile(Object o) {
        return regExp(o, MOBILE);
    }

    /**
     * 是否为图片
     */
    public static boolean isImage(Object o) {
        return regExp(o, IMAGE);
    }

    /**
     * 是否为中文姓名2-4个汉字
     */
    public static boolean isCnName(Object o) {
        return RegExpKit.regExp(o, "^[\\u4e00-\\u9fa5]{2,4}$");
    }

    public static boolean isMoney(Object o) {
        return RegExpKit.regExp(o, MONEY);
    }


    /**
     * 是否为经纬度
     */
    public static boolean isLocate(Object o) {
        return RegExpKit.regExp(o, LOCATE);
    }
}
