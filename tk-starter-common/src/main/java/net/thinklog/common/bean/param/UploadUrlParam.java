package net.thinklog.common.bean.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.thinklog.common.kit.RegExpKit;
import net.thinklog.common.validator.NotEmptyPattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author azhao
 * 注册类
 */
@Data
public class UploadUrlParam implements Serializable {

    @NotEmptyPattern(message = "url地址格式错误",regexp = RegExpKit.URL)
    @JsonProperty("url")
    private String url;

    @Length(min = 3, max = 255, message = "文件名称")
    private String name;

    @NotEmptyPattern(message = "文件后缀不能为空",regexp = RegExpKit.STRING)
    private String ext;

}
