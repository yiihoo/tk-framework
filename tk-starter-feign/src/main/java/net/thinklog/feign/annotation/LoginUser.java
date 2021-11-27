package net.thinklog.feign.annotation;

import java.lang.annotation.*;

/**
 * 请求的方法参数SysUser上添加该注解，则注入当前登录人信息
 * 例1：public void test(@ApiIgnore  @LoginUser AppUser user) //只有用户id
 * 例2：public void test(@ApiIgnore  @LoginUser(true) AppUser user) //能获取AppUser对象的所有信息
 *
 * @author azhao
 * @date 2018/7/24 16:44
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
    /**
     * 是否查询SysUser对象所有信息，true则通过rpc接口查询
     */
    boolean value() default false;
}
