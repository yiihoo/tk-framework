package net.thinklog.common.bean.enums;

import lombok.Getter;

/**
 * @author azhao
 */
@Getter
public enum UserRankEnum {
    /**
     * 支付方式
     */
    LV1(1,"会员"),
    LV2(2,"VIP商户"),
    LV3(3,"社区服务中心"),
    LV4(4,"乡镇服务中心"),
    LV5(5,"区级服务中心"),
    LV6(6,"市级服务中心"),
    ;
    private final Integer level;
    private final String title;

    UserRankEnum(Integer level, String title) {
        this.title = title;
        this.level = level;
    }
    public static String getTitle(Integer level) {
        UserRankEnum[] rs = UserRankEnum.values();
        for (UserRankEnum r : rs) {
            if (r.getLevel().equals(level)) {
                return r.getTitle();
            }
        }
        return LV1.getTitle();
    }
}
