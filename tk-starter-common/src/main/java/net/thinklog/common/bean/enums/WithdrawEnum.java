package net.thinklog.common.bean.enums;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 提现配置
 *
 * @author azhao
 * 2021/9/15 15:14
 */
@Getter
public enum WithdrawEnum {
    WECHAT("微信", new BigDecimal("0.006")),
    ALIPAY("支付宝", new BigDecimal("0.006")),
    ZLB("众乐邦", new BigDecimal("0.03")),
    ZLB_INVOICE("众乐邦开票", new BigDecimal("0.06")),
    ;
    private final BigDecimal feeRate;
    private final String name;

    WithdrawEnum(String name, BigDecimal feeRate) {
        this.feeRate = feeRate;
        this.name = name;
    }

}
