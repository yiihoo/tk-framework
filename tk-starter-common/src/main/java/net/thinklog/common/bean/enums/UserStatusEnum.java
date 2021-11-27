package net.thinklog.common.bean.enums;

import lombok.Getter;

/**
 * 用户状态
 */
@Getter
public enum UserStatusEnum {
    /**
     * 默认0位正常
     */
    DEFAULT(0, "已注册"),
    /**
     * 默认0位正常
     */
    OK(1, "正常"),
    /**
     * 需要认证
     */
    VERIFY(2, "未认证");
    private final int status;
    private final String title;

    UserStatusEnum(int status, String tile) {
        this.status = status;
        this.title = tile;
    }
}
