package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/4 21:45
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private long id;
    private String username;//用户账号
    private String password;//用户密码 使用md5 + salt +hash散列加密
    private String salt;
    private String status;
}
