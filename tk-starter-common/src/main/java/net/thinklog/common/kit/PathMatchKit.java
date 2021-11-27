package net.thinklog.common.kit;

import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * spring原生的路径匹配代码
 *
 * @author azhao
 */
public class PathMatchKit {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher("/");

    /**
     * 实际验证路径匹配权限
     *
     * @param matchPath 权限url
     * @param path      访问路径
     * @return 是否拥有权限
     */
    public static boolean match(String matchPath, String path) {
        return PATH_MATCHER.match(matchPath, path);
    }

    /**
     * 实际验证路径匹配权限
     *
     * @param list 权限url
     * @param path 访问路径
     * @return 是否拥有权限
     */
    public static boolean matches(Collection<String> list, String path) {
        for (String s : list) {
            if (PATH_MATCHER.match(s, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 实际验证路径匹配权限
     *
     * @param list 权限url
     * @param path 访问路径
     * @return 是否拥有权限
     */
    public static boolean matches(String[] list, String path) {
        for (String s : list) {
            if (PATH_MATCHER.match(s, path)) {
                return true;
            }
        }
        return false;
    }
}
