package net.thinklog.notify.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author azhao
 * 2021/8/17 22:31
 */
@Data
public class NotifyParam implements Serializable {
    private Long id;
    private String url;
    private String title;
    private String moduleName;
    private String params;
    private String result;
    private Date createTime;
    private Date updateTime;
    private Date nextTime;
    private Integer status;
    private Integer version;
    private String method;
}
