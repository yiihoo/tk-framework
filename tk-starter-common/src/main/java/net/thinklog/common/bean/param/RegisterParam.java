package net.thinklog.common.bean.param;

import net.thinklog.common.kit.RegExpKit;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author 10186
 * 注册类
 */
@Data
public class RegisterParam implements Serializable {

    @NotNull(message = "平台ID不能为空")
    private Integer platformId;

    @Length(min = 3, max = 48, message = "用户名超度在3-48个字符之间")
    private String username;

    @Length(min = 2, max = 24, message = "用户昵称在2-24个字符之间")
    private String nickname;

    @Pattern(regexp = RegExpKit.MOBILE, message = "手机号格式错误")
    private String mobile;

}
