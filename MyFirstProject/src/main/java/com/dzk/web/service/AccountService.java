package com.dzk.web.service;

import com.dzk.web.entity.Account;
import com.dzk.web.entity.AccountLogin;
import com.dzk.web.entity.Encryption;
import com.dzk.web.entity.Register;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/4 21:59
 * @description
 */
public interface AccountService {
    Account getAccountByUsername(String username);
    boolean isOnlyUsername(String username);
    void registerAccount(Register register);//注册账号
    List<String> forgetUsername(String mail);//找回账号
    String getMailById(long id);
    void modifyPasswordById(long id, Encryption encryption);
    void setRoleToAccount(long accountId,long roleId);//赋予账户角色
    List<String> getSubjectRoles(String username);
    AccountLogin getAccountLogin(String username);
}
