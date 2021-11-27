package net.thinklog.common.bean.model.attachment;

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
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(BaseConstant.TABLE_PREFIX + "upload")
@ApiModel(value = "Upload对象", description = "")
public class AttachmentUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @JsonProperty("user_id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "文件名")
    @JsonProperty("name")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "md5哈希")
    @JsonProperty("md5_hash")
    @TableField("md5_hash")
    private String md5Hash;

    @ApiModelProperty(value = "文件大小")
    @JsonProperty("size")
    @TableField("size")
    private String size;

    @ApiModelProperty(value = "文件路径")
    @JsonProperty("url")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "文件扩展名")
    @JsonProperty("ext")
    @TableField("ext")
    private String ext;

    @ApiModelProperty(value = "图片宽度")
    @JsonProperty("width")
    @TableField("width")
    private Integer width;

    @ApiModelProperty(value = "图片高度")
    @JsonProperty("height")
    @TableField("height")
    private Integer height;

    @ApiModelProperty(value = "上传时间")
    @JsonProperty("create_time")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "是否为图片")
    @JsonProperty("is_image")
    @TableField("is_image")
    private String isImage;


}
