package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/29 17:29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountListTableData {
    private long id;//用户编号
    private String username;//用户名
    private String roleName;//用户角色
    private String registrationDate;//用户注册日期
    private String status;//账户状态
}
