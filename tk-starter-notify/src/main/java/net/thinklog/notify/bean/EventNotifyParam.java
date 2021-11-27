package net.thinklog.notify.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author azhao
 * 2021/11/2 8:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventNotifyParam implements Serializable {
    private Long id;
    private String name;
    private Object data;
}