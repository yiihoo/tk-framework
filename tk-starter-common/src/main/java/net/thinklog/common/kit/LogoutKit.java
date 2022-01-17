package net.thinklog.common.kit;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.Data;
import net.thinklog.common.config.AuthConstant;

import java.io.Serializable;

/**
 * @author yiihoo
 */
public class LogoutKit {
    @Data
    public static class TokenDTO implements Serializable {
        private Integer exp;
        private String hashKey;
        private String token;
    }

    /**
     * 获取token过期时间
     *
     * @param token jwt字符
     * @return 大于零表示可以设置，返回距离到期时间的描述
     */
    public static TokenDTO getTokenExp(String token) {
        String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        try {
            JWT jwtObject = JWTUtil.parseToken(realToken);
            int timestamp = (int) jwtObject.getPayload().getClaim("exp");
            int exp = timestamp - (int) (System.currentTimeMillis() / 1000);
            TokenDTO dto = new TokenDTO();
            dto.setExp(exp);
            dto.setToken(realToken);
            dto.setHashKey(SecureUtil.md5(realToken));
            return dto;
        } catch (Exception e) {
            return null;
        }
    }
}
