package com.dzk.web.repository;

import com.dzk.web.entity.Account;
import com.dzk.web.entity.AccountLogin;
import com.dzk.web.entity.Encryption;
import com.dzk.web.entity.PersonalInfor;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/4 21:44
 * @description 账号有关的dao层操作
 */
public interface AccountRepository {
    Account findByUsername(String username);
    void insertAccount(Account account);//账号注册
    void insertPersonInfor(PersonalInfor personalInfor);//账号关联信息
    List<Long> findIdByMail(String mail);//根据邮箱寻找 用户id
    List<String> findUsernameById(List<Long> idList);//根据用户id 寻找username
    String findMailById(long id);//根据id 查找 邮箱名
    void updatePasswordById(long id, Encryption encryption);//根据id 修改用户密码
    void setRoleToAccount(long accountId,long roleId);//赋予角色身份
    List<String> getSubjectRole(String username);
    AccountLogin getAccountLogin(long id);
}
