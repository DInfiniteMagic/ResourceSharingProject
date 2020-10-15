package com.dzk.common.myException;


import org.apache.shiro.authc.AuthenticationException;

/**
 * @author dzk
 * @date 2020/7/6 0:48
 * @description
 */
public class LockUsernameException extends AuthenticationException {
    public LockUsernameException(String message) {
        super(message);
    }
    public LockUsernameException() {
        super("登录失败次数过多,账号已被锁定,请三分钟后再次尝试！");
    }
}
