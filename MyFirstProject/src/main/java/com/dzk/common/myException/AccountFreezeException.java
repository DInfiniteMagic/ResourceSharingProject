package com.dzk.common.myException;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author dzk
 * @date 2020/7/29 17:01
 * @description
 */
public class AccountFreezeException extends AuthenticationException {
    public  AccountFreezeException(String message) {
        super(message);
    }
    public  AccountFreezeException() {
        super("账号冻结！");
    }
}

