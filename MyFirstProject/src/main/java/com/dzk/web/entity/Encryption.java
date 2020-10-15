package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/10 23:13
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encryption {
    private String encryptionStr;//加密后的字符串
    private String salt;//随机盐
}
