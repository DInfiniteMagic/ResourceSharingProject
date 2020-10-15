package com.dzk.web.entity;

import com.dzk.common.annotation.IsOnlyUsername;
import com.dzk.common.annotation.PatternOfPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * @author dzk
 * @date 2020/7/8 19:01
 * @description 用户注册实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    @NotEmpty
    @Pattern(regexp = "[\\w!#\\$%&amp;'\\*\\+\\-\\/=\\^_`{\\|}~.]+@([\\w-]+\\.)+(com|net|cn|org|me|cc|biz)$",message = "邮箱地址格式有误或不支持此类型邮箱")
    private String mailbox;//邮箱

    @NotEmpty
    @Pattern(regexp = "^1[3456789]\\d{9}$",message = "电话格式错误")
    private String phoneNum;//电话

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "用户名只能包含大写、小写、数字和下划线")/*用户名只能包含大写、小写、数字和下划线*/
    @Length(min = 6,max = 18)
    @IsOnlyUsername/*判断用户名是否存在*/
    private String username;//用户名

    @NotEmpty
    @Length(min=2,max = 10)
    private String name;//昵称

    @NotEmpty
    @Length(min = 6,max = 18)
    @PatternOfPassword
    private String password;//密码

    @NotEmpty
    @Length(min = 6,max = 18)
    @PatternOfPassword
    private String checkPassword;//确认密码
    private String captcha;//验证码
    private int activationCode;//账户激活码
}
