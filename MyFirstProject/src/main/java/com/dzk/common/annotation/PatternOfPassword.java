package com.dzk.common.annotation;

import com.dzk.common.validator.IsOnlyUsernameValidator;
import com.dzk.common.validator.PatternOfPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author dzk
 * @date 2020/7/9 0:24
 * @description
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PatternOfPasswordValidator.class})
public @interface PatternOfPassword {
    //如果校验不通过返回的提示信息
    String message() default "密码不符合规范";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
