package net.thinklog.common.bean.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum PayTypeEnum {
    /**
     * "支付宝
     */
    WALLET(1, "余额支付"),
    /**
     * 微信支付
     */
    WECHAT(2, "微信支付"),
    /**
     * 微信支付
     */
    ALIPAY(3, "支付宝"),

    ;
    private final Integer value;

    private final String name;


    PayTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static PayTypeEnum getByValue(Integer value) {
        for (PayTypeEnum rs : PayTypeEnum.values()) {
            if (Objects.equals(value, rs.getValue())) {
                return rs;
            }
        }
        return null;
    }
}
