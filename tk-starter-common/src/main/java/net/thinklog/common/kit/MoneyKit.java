package net.thinklog.common.kit;

import java.math.BigDecimal;

public class MoneyKit {
    /**
     * 是否大于零
     *
     * @param money
     * @return
     */
    public static boolean gtZero(BigDecimal money) {
        if (money == null) {
            return false;
        }
        return money.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 金额是否小于0
     * @param money
     * @return
     */
    public static boolean ltZero(BigDecimal money) {
        if (money == null) {
            return false;
        }
        return money.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 金额比较
     *
     * @param money
     * @param targetMoney
     * @return
     */
    public static boolean gt(BigDecimal money, BigDecimal targetMoney) {
        return money.compareTo(targetMoney) > 0;
    }
}
