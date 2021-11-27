package net.thinklog.common.bean.enums;

import lombok.Getter;

/**
 * @author azhao
 * 2021/8/1 16:53
 */
@Getter
public enum PlatformEnum {
    /**
     * 平台枚举类
     */
    DEFAULT(1, "拼购系统"),
    FAMILY(2, "美家爱心"),
    ;
    private final String name;
    private final Integer value;

    PlatformEnum(Integer value, String name) {
        this.name = name;
        this.value = value;
    }
}
