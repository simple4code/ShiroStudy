package me.hzc.shirotest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * <p>
 *  登录认证简单测试：从 ini 读取存储的用户用户名和密码
 * </p>
 *
 * @author hzc
 * @since 2023-01-21 0:31
 */
public class ShiroRun {
    public static void main(String[] args) {
        // 1. 初始化获取 SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        // 2. 获取Subject对象
        Subject subject = SecurityUtils.getSubject();

        // 3. 创建 token 对象，web 应用用户名密码从页面传递
        AuthenticationToken token = new UsernamePasswordToken("simpleUser", "chan");

        try {
            // 4. 完成登录
            subject.login(token);
            // 5. 判断角色
            boolean hasAdminRole = subject.hasRole("guest");
            System.out.println("是否拥有此角色：" + hasAdminRole);
            // 6. 判断权限
            boolean permitted = subject.isPermitted("user:insert");
            System.out.println("是否拥有此权限：" + permitted);
            // checkPermission 方法检查权限，如果没有权限，则会抛异常 AuthenticationException
            subject.checkPermission("user:select");

            System.out.println("登录成功");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
        }
    }
}
