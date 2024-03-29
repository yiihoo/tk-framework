package net.thinklog.common.bean.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 *
 * @author azhao
 * @date 2020/6/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LoginUserDTO implements Serializable {
  private Long id;
  private String name;
  private String avatar;
  private Integer platformId;
  private Long tenantId;
  private String mobile;
  private String clientId;
  private List<String> roles;
}
