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
 * @since 2021-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(BaseConstant.TABLE_PREFIX + "user")
@ApiModel(value = "User模型")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @JsonProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "头像")
    @JsonProperty("avatar")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "签名")
    @JsonProperty("signature")
    @TableField("signature")
    private String signature;

    @ApiModelProperty(value = "标签")
    @JsonProperty("tags")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    @TableField("remark")
    private String remark;


    @ApiModelProperty(value = "用户名")
    @JsonProperty("username")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "昵称")
    @JsonProperty("nickname")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "开放id")
    @JsonProperty("openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty(value = "手机")
    @JsonProperty("mobile")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @JsonProperty("password")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "锁定次数")
    @JsonProperty("lock_count")
    @TableField("lock_count")
    private Integer lockCount;

    @ApiModelProperty(value = "锁定时间")
    @JsonProperty("lock_time")
    @TableField("lock_time")
    private Date lockTime;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty("update_time")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "版本")
    @JsonProperty("version")
    @TableField("version")
    private Integer version;

    @ApiModelProperty(value = "是否软删除")
    @JsonProperty("is_delete")
    @TableField("is_delete")
    private Integer isDelete;


    private final static Long EXPIRE_TIME = 60 * 1000L;
    @TableField(exist = false)
    private boolean lock;

    @TableField(exist = false)
    private boolean unlock;


    public boolean isUnlock() {
        if (getLockTime() == null) {
            return true;
        }
        return (System.currentTimeMillis() - EXPIRE_TIME) > getLockTime().getTime();
    }

    /**
     * 是否锁定
     *
     * @return boolean
     */
    public boolean isLock() {
        if (getLockCount() == null) {
            return false;
        }
        return getLockCount() >= 5;
    }
}
