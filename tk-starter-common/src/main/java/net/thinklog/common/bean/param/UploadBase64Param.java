package net.thinklog.common.bean.param;

import lombok.Data;
import net.thinklog.common.validator.NotEmptyPattern;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 上传bas64编码
 *
 * @author yiihoo
 */
@Data
public class UploadBase64Param implements Serializable {
    @NotEmptyPattern(regexp = "^image/(png|jpg|jpeg|gif);base64,.+", message = "图片格式错误")
    private String data;
    @NotEmpty(message = "图片标题不能为空")
    private String title;
}
