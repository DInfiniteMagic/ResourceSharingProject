package com.dzk.web.repository.impl;

import com.dzk.web.entity.Account;
import com.dzk.web.entity.AccountLogin;
import com.dzk.web.entity.Encryption;
import com.dzk.web.entity.PersonalInfor;
import com.dzk.web.repository.AccountRepository;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/4 21:52
 * @description
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public AccountLogin getAccountLogin(long id) {
        return sqlSession.getMapper(AccountRepository.class).getAccountLogin(id);
    }

    @Override
    public Account findByUsername(String username) {
        return sqlSession.getMapper(AccountRepository.class).findByUsername(username);
    }

    @Override
    public void insertAccount(Account account) {
        sqlSession.getMapper(AccountRepository.class).insertAccount(account);
    }

    @Override
    public void insertPersonInfor(PersonalInfor personalInfor) {
        sqlSession.getMapper(AccountRepository.class).insertPersonInfor(personalInfor);
    }

    @Override
    public List<Long> findIdByMail(String mail) {
        return sqlSession.getMapper(AccountRepository.class).findIdByMail(mail);
    }

    @Override
    public List<String> findUsernameById(List<Long> idList) {
        return sqlSession.getMapper(AccountRepository.class).findUsernameById(idList);
    }

    @Override
    public String findMailById(long id) {
        return sqlSession.getMapper(AccountRepository.class).findMailById(id);
    }

    @Override
    public void updatePasswordById(long id, Encryption encryption) {
        sqlSession.getMapper(AccountRepository.class).updatePasswordById(id,encryption);
    }

    @Override
    public void setRoleToAccount(long accountId, long roleId) {
        sqlSession.getMapper(AccountRepository.class).setRoleToAccount(accountId,roleId);
    }

    @Override
    public List<String> getSubjectRole(String username) {
        return sqlSession.getMapper(AccountRepository.class).getSubjectRole(username);
    }
}
