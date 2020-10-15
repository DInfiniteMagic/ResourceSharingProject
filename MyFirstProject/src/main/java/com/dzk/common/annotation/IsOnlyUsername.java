package com.dzk.common.annotation;

import com.dzk.common.validator.IsOnlyUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author dzk
 * @date 2020/7/8 23:58
 * @description
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsOnlyUsernameValidator.class})
public @interface IsOnlyUsername {
    //如果校验不通过返回的提示信息
    String message() default "该账号已存在";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
