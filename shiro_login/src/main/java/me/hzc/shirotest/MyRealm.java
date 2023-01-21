package me.hzc.shirotest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * <p>
 *  自定义 Realm
 * </p>
 *
 * @author hzc
 * @since 2023-01-21 19:36
 */
public class MyRealm extends AuthenticatingRealm {

    /**
     * 自定义登录认证方法，shiro的org.apache.shiro.subject.Subject#login()方法底层会调用这个自定义认证登录方法
     * 需要配置自定义的 realm 对象使其生效，可以在 ini 文件中配置，也可以在 springboot 中配置
     * 该方法只是获取进行对比的信息，认证逻辑还是按照 shiro 底层认证逻辑完成
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 获取身份信息
        String principal = token.getPrincipal().toString();

        // 2. 获取凭证信息
        String password = new String((char[])token.getCredentials());
        System.out.println("认证用户信息：" + principal + ":" + password);

        // 3. 获取数据库中存储的用户信息
        if("simpleUser".equals(principal)) {
            // 3.1 从数据库中查询加盐三次迭代的密码
            String pwdInfo = "04b5c7f595a8b31ef001c2d6cc98157e";

            // 4. 创建封装校验逻辑对象，封装数据返回
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    token.getPrincipal(),
                    pwdInfo,
                    ByteSource.Util.bytes("salt"),
                    token.getPrincipal().toString()
            );

            return info;
        }

        return null;
    }
}
