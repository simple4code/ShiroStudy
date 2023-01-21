package me.hzc.shirotest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * <p>
 *  Shiro 加密
 * </p>
 *
 * @author hzc
 * @since 2023-01-21 19:23
 */
public class ShiroMD5 {
    public static void main(String[] args) {
        // 密码明文
        String password = "chan";

        // 使用Shiro封装的MD5工具进行加密
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println("使用MD5加密之后的密码：" + md5Hash);

        // 带盐的md5加密，盐就是在密码明文后拼接新字符串，然后再进行加密
        Md5Hash md5Hash2 = new Md5Hash(password, "salt");
        System.out.println("使用MD5加密之后的密码（加盐）：" + md5Hash2);

        // 为了保证安全，避免被破解还可以多次迭代加密，保证数据安全
        Md5Hash md5Hash3 = new Md5Hash(password, "salt", 3);
        System.out.println("使用MD5加密之后的密码（加盐三次加密）：" + md5Hash3);

        // 使用父类实现加密
        SimpleHash simpleHash = new SimpleHash("MD5", password, "salt", 3);
        System.out.println("父类带盐三次加密：" + simpleHash.toHex());
    }
}
