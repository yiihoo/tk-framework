package net.thinklog.searcher.service;

import com.ejlchina.searcher.bean.DbField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yiihoo
 */
@Data
public class TestDTO implements Serializable {
    private String name;
    @DbField("st.create_time")
    private String createTime;
    private Long id;
    private String address;
    private Long userId;
    private Long maxId;
}
