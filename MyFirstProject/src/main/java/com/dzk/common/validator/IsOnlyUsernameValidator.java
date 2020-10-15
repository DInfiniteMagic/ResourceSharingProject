package com.dzk.common.validator;

import com.dzk.common.annotation.IsOnlyUsername;
import com.dzk.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author dzk
 * @date 2020/7/9 0:04
 * @description
 */
//IsOnlyAccount：自定义的注解
//String：注解参数类型
public class IsOnlyUsernameValidator implements ConstraintValidator<IsOnlyUsername,String> {
    @Autowired
    private AccountService accountService;
    @Override//1、初始化方法：通过该方法我们可以拿到我们的注解
    public void initialize(IsOnlyUsername constraintAnnotation) {

    }
    @Override //2、逻辑处理
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return accountService.isOnlyUsername(s);//判断账号是否存在
    }
}
