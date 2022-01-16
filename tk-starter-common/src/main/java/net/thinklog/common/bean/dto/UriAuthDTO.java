package net.thinklog.common.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yiihoo
 */
@Data
public class UriAuthDTO implements Serializable {
    private String uri;
    private Boolean auth;
}
