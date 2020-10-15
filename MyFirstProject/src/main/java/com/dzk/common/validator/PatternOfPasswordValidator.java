package com.dzk.common.validator;

import com.dzk.common.annotation.PatternOfPassword;
import com.dzk.common.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author dzk
 * @date 2020/7/9 0:25
 * @description
 */
public class PatternOfPasswordValidator implements ConstraintValidator<PatternOfPassword,String> {
    @Override
    public void initialize(PatternOfPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regEx="^(?:\\d+|[a-zA-Z]+|[!@#$%^&*]+)$";//纯数字，或者纯字符

        if(!StringUtils.regExCheck(regEx,s)){//不能是纯数字 或者纯字符
            return true;
        }
        return false;
    }
}
