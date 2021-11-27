package net.thinklog.common.bean.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.thinklog.common.config.BaseConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author azhao
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(BaseConstant.TABLE_PREFIX + "platform_user")
@ApiModel(value = "UserPlatform模型", description = "")
public class PlatformUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("platform_id")
    @TableField(value = "platform_id")
    private Integer platformId;

    @ApiModelProperty(value = "用户id")
    @JsonProperty("user_id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "父级用户")
    @JsonProperty("parent_id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "顶级用户")
    @JsonProperty("top_id")
    @TableField("top_id")
    private Long topId;

    @ApiModelProperty(value = "签名")
    @JsonProperty("signature")
    @TableField(exist = false)
    private String signature;

    @ApiModelProperty(value = "标签")
    @JsonProperty("tags")
    @TableField(exist = false)
    private String tags;

    @ApiModelProperty(value = "昵称")
    @TableField(exist = false)
    private String nickname;

    @ApiModelProperty(value = "手机")

    @TableField(exist = false)
    private String mobile;

    @ApiModelProperty(value = "头像")
    @TableField(exist = false)
    private String avatar;

    @ApiModelProperty(value = "状态")
    @TableField(exist = false)
    private Integer status;

    @JsonProperty("create_time")
    @TableField("create_time")
    private Date createTime;
}
