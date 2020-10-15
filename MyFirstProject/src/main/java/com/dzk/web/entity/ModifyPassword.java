package com.dzk.web.entity;

import com.dzk.common.annotation.PatternOfPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * @author dzk
 * @date 2020/7/11 23:24
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPassword {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "用户名只能包含大写、小写、数字和下划线")/*用户名只能包含大写、小写、数字和下划线*/
    @Length(min = 6,max = 18)
    //这里为什么不判断用户是否存在？  因为在请求发送验证码给该账户的时候已经验证过用户是否存在
    private String forgetPassword_username;//修改密码的用户名
    @NotEmpty
    @Length(min = 6,max = 18)
    @PatternOfPassword
    private String newPassword;//密码
    @NotEmpty
    @Length(min = 6,max = 18)
    @PatternOfPassword
    private String checkNewPassword;//二次验证密码
    private int validatorCode;//验证码
}
