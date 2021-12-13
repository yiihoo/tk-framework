package net.thinklog.common.bean.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 上传bas64编码
 *
 * @author yiihoo
 */
@Data
public class UploadBase64Param implements Serializable {
    @NotEmpty(message = "图片数据不能为空")
    private String data;
    @NotEmpty(message = "图片标题不能为空")
    private String title;
    private Integer chunk;
    private Integer chunks;
    @JsonProperty("uni_name")
    private String uniName;
}
