package com.dzk.common.utils;

import com.dzk.web.entity.Encryption;
import com.dzk.web.entity.Register;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author dzk
 * @date 2020/7/10 23:10
 * @description
 */
public class EncryptionUtils {
    public static Encryption encryptionByMd5(String str){//对字符串进行md5加密
        Encryption encryption=new Encryption();
        String salt= RandomStringUtils.randomAlphanumeric(4);//随机盐
        encryption.setSalt(salt);//存入随机盐
        Md5Hash md5Hash=new Md5Hash(str,salt,1024);//使用md5+salt(随机盐)+散列加密
        encryption.setEncryptionStr(md5Hash.toString());//存入加密后的字符串
        return encryption;
    }
}
