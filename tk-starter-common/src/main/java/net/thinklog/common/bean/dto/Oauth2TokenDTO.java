package net.thinklog.common.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Oauth2获取Token返回信息封装
 *
 * @author azhao
 * @date 2020/7/17
 */
@Data
public class Oauth2TokenDTO implements Serializable {
    @ApiModelProperty("访问令牌")
    private String token;
    @ApiModelProperty("刷令牌")
    @JsonProperty("refresh_token")
    private String refreshToken;
    @ApiModelProperty("访问令牌头前缀")
    @JsonProperty("token_head")
    private String tokenHead;
    @ApiModelProperty("有效时间（秒）")
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @ApiModelProperty("错误信息")
    @JsonProperty("err_msg")
    private String errMsg;
}
