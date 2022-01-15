package net.thinklog.common.bean.model.user;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
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
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "门店id")
    @JsonProperty("shop_id")
    @TableField("shop_id")
    private Long shopId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色名称")
    @JsonProperty("role_name")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "错误次数")
    @JsonProperty("error_count")
    @TableField("error_count")
    private Integer errorCount;

    @ApiModelProperty(value = "错误时间")
    @JsonProperty("error_time")
    @TableField("error_time")
    private Date errorTime;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
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
    private Integer status;


}