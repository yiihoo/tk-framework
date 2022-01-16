package net.thinklog.common.config;

/**
 * 权限相关常量定义
 *
 * @author azhao
 * @date 2020/6/19
 */
public interface AuthConstant {
    /**
     * 超级管理员ID
     */
    Long SUPER_ADMIN_ID = 1L;
    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台管理client_id
     */
    String CLIENT_ADMIN = "admin";

    /**
     * 前台APP的client_id
     */
    String CLIENT_APP = "app";
    /**
     * 退出地址
     */
    String LOGOUT_URL = "/oauth/logout";
    /**
     * 注销账号前缀
     */
    String LOGOUT_CACHE_PREFIX = "logout:";
    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/*/admin/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * 缓存的body标志
     */
    String CACHED_REQUEST_BODY_KEY = "cached-request-body";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 签名时间戳
     */
    String SIGNATURE_TIMESTAMP = "timestamp";
    /**
     * 签名随机字符串
     */
    String SIGNATURE_NONCE_STRING = "noncestr";

    /**
     * 签名签名字符串
     */
    String SIGNATURE_SIGN_STRING = "signstr";


    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";

    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "x-traceId-header";
    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-userid-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * 租户信息头(应用)
     */
    String TENANT_HEADER = "x-tenant-header";
    /**
     * 平台信息头(应用)
     */
    String PLATFORM_HEADER = "x-platform-header";
    /**
     * feign错误信息
     */
    String ERROR_CODE_HEADER = "x-error-code";
    /**
     * feign错误信息
     */
    String ERROR_MSG_HEADER = "x-error-msg";
}
