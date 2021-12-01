package net.thinklog.common.config;

/**
 * @author azhao
 */
public class BaseConstant {
    public static final String YES = "Y";
    public static final String NO = "N";

    public static final String HOST = "https://api.qqeg369.com";
    public static final String OSS_PATH = "https://oss.qqeg369.com/";
    public static final String OSS_AVATAR = "wechat/avatar.png";
    public static final String OSS_IMAGE_SMALL = "?x-oss-process=image/resize,m_fill,h_200,w_200";

    public static final String REDIS_PREFIX = "st-cache";
    public static final String TABLE_PREFIX = "st_";
    public static final int PAGE_SIZE = 15;
    public static final int PAGE_SIZE_MIN = 10;
    public static final int PAGE_SIZE_MAX = 100;
    /**
     * 短信验证码最小时间间隔
     */
    public static final Integer SMS_MIN_SECONDS = 120;
    public static final Integer SMS_MAX_SECONDS = 600;

    public static final String NOTIFY_SUCCESS = "SUCCESS";
    public static final String NOTIFY_FAILED = "FAILED";

    public static final String TIME_ZONE_GMT8 = "GMT+8";
    public static final String VERSION = "1.0.5";
    public static final String LOCK_KEY_PREFIX = "LOCK_KEY:";
    /**
     * 默认用户名
     */
    public static final String DEFAULT_USER_NAME = "未设置";
    public static final Long PLATFORM_ID = 1L;
}