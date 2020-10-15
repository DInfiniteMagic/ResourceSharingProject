package com.dzk.web.service.Impl;

import com.dzk.common.utils.EncryptionUtils;
import com.dzk.common.utils.StringUtils;
import com.dzk.web.entity.*;
import com.dzk.web.repository.AccountRepository;
import com.dzk.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dzk
 * @date 2020/7/4 21:59
 * @description 关于账号的业务处理
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account getAccountByUsername(String username) {/*提供账号信息*/
        return accountRepository.findByUsername(username);
    }
    @Override
    public boolean isOnlyUsername(String username) {
        Account account=getAccountByUsername(username);
        if(account!=null){//用户名存在
            return false;
        }
        return true;//用户名不存在
    }

    @Override
    public void registerAccount(Register register) {
        Encryption encryption= EncryptionUtils.encryptionByMd5(register.getPassword());//对密码进行加密
        Account account=new Account(0,register.getUsername(),encryption.getEncryptionStr(),encryption.getSalt(),"正常");//账号
        accountRepository.insertAccount(account);//存入账号  高并发情况下用户名重复
        long accountId=((Account)accountRepository.findByUsername(register.getUsername())).getId();//得到存入账号的用户名 关联 用户信息
        PersonalInfor personalInfor=new PersonalInfor(0,accountId,register.getName(),register.getMailbox(),register.getPhoneNum(),new Date(new java.util.Date().getTime()));
        accountRepository.insertPersonInfor(personalInfor);//存入账号关联信息
        //赋予用户身份
        setRoleToAccount(accountId,3);//刚刚注册的账户都是user 角色
    }

    @Override
    public List<String> forgetUsername(String mail) {
        List<Long> idList=accountRepository.findIdByMail(mail);
        if(idList.isEmpty()){//如果位找到该邮箱对应的用户id 返回一个空的集合
            return new  ArrayList<String>();
        }
        List<String> usernameList=accountRepository.findUsernameById(idList);
        return usernameList;
    }

    @Override
    public String getMailById(long id) {
        return accountRepository.findMailById(id);
    }

    @Override
    public void modifyPasswordById(long id, Encryption encryption) {
       accountRepository.updatePasswordById(id,encryption);
    }

    @Override
    public void setRoleToAccount(long accountId, long roleId) {
        accountRepository.setRoleToAccount(accountId,roleId);
    }

    @Override
    public List<String> getSubjectRoles(String username) {
        return accountRepository.getSubjectRole(username);
    }

    @Override
    public AccountLogin getAccountLogin(String username) {
        long id=accountRepository.findByUsername(username).getId();
        AccountLogin accountLogin=accountRepository.getAccountLogin(id);
        return accountLogin;
    }
}
