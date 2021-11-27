package net.thinklog.common.bean.model.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.thinklog.common.config.BaseConstant;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author azhao
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(BaseConstant.TABLE_PREFIX + "admin_user")
@ApiModel(value="AdminUser模型", description="用户表")
public class AdminUser implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    @JsonProperty("id")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "门店id")
    @JsonProperty("shop_id")
    @TableField("shop_id")
    private Long shopId;

    @ApiModelProperty(value = "手机号")
    @JsonProperty("mobile")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    @JsonProperty("username")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "昵称")
    @JsonProperty("nickname")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "密码")
    @JsonProperty("password")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "角色名称")
    @JsonProperty("role_name")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "性别")
    @JsonProperty("sex")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "头像")
    @JsonProperty("avatar")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "备注信息")
    @JsonProperty("remark")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "错误次数")
    @JsonProperty("errror_count")
    @TableField("errror_count")
    private Integer errrorCount;

    @ApiModelProperty(value = "错误时间")
    @JsonProperty("error_time")
    @TableField("error_time")
    private Date errorTime;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    @JsonProperty("login_time")
    @TableField("login_time")
    private Date loginTime;

    @ApiModelProperty(value = "操作刷新时间")
    @JsonProperty("refresh_time")
    @TableField("refresh_time")
    private Date refreshTime;

    @ApiModelProperty(value = "帐号启用状态")
    @JsonProperty("status")
    @TableField("status")
    private Integer status;


}