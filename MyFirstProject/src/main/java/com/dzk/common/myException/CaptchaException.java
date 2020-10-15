package com.dzk.common.myException;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author dzk
 * @date 2020/7/5 12:44
 * @description
 */
public class CaptchaException extends  AuthenticationException {
    public CaptchaException(String message) {
        super(message);
    }
    public CaptchaException() {
        super("验证码错误！");
    }
}
