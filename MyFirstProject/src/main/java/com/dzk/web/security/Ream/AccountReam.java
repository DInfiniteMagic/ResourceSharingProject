package com.dzk.web.security.Ream;

import com.dzk.common.myException.AccountFreezeException;
import com.dzk.web.controller.LoginController;
import com.dzk.web.entity.Account;
import com.dzk.web.service.AccountService;
import lombok.val;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author dzk
 * @date 2020/7/4 20:29
 * @description
 */
@Service
public class AccountReam extends AuthorizingRealm {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(AccountReam.class);
    @Autowired
    private AccountService accountService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {/*授权*/
        String primaryPrincipal =(String) principalCollection.getPrimaryPrincipal();//获取当前主体的身份
        //去dao 层获取当前主体的身份信息
        List<String> list=new ArrayList<>();
        list=accountService.getSubjectRoles(primaryPrincipal);//查找当前主体的角色信息
        if(list.isEmpty()){
            logger.info(primaryPrincipal+"身份信息有误！");
            return null;
        }
        AuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        ((SimpleAuthorizationInfo) authorizationInfo).addRoles(list);//给当前主体添加角色
        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {/*认证*/
        String principle=(String)authenticationToken.getPrincipal();//得到当前认证主体的登录身份信息
        Account account=accountService.getAccountByUsername(principle);//查询该主体是否存在
        if (account!=null){
            if("冻结".equals(account.getStatus())){//账号被冻结
                throw new AccountFreezeException("该账号已被冻结！");
            }
            else {
                AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(principle, account.getPassword(), ByteSource.Util.bytes(account.getSalt()),this.getName());
                //密码验证并非在此
                return authenticationInfo;
            }
        }
        return null;
    }
}
