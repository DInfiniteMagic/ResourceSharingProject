package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


/**
 * @author dzk
 * @date 2020/7/10 23:22
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfor{
    private long id;//序号
    private long account_id;
    private String name;//昵称
    private String email;//邮箱
    private String phoneNum;//电话
    private Date registrationDate;//注册日期
}
