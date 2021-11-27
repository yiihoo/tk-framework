package net.thinklog.notify.bean;

import lombok.Getter;

/**
 * @author azhao
 * 2021/8/17 14:52
 */
@Getter
public enum EventNotifyTypeEnum {
    /**
     * 通知类型
     */
    USER_RECOMMEND("USER_RECOMMEND", "用户推荐等级"),
    USER_PROFILE("USER_PROFILE", "用户完善资料"),
    USER_PROFILE_FORM("USER_PROFILE_FORM", "用户完善资料表单"),
    ORDER_PAY("ORDER_PAY", "订单支付"),
    ORDER_BEFORE_SUBMIT("ORDER_BEFORE_SUBMIT", "订单提交之前"),
    ORDER_PAY_FORM("ORDER_PAY_FORM", "支付表单"),
    ORDER_PAY_CHECK("ORDER_PAY_CHECK", "支付检查"),
    ORDER_REFUND("ORDER_REFUND", "订单退款"),
    BONUS_RECHARGE("BONUS_RECHARGE", "奖金充值"),
    WALLET_RECHARGE("WALLET_RECHARGE", "钱包充值"),
    WECHAT_PAY("WECHAT_PAY", "微信支付"),
    ;
    private final String value;
    private final String title;

    EventNotifyTypeEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }
}
