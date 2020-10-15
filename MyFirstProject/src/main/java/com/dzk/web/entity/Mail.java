package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/10 20:51
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private String title;//邮箱标题
    private String mail;//邮箱
    private String sendMessage;//发送的信息
}
