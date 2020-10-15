package com.dzk.common.config;

import com.dzk.common.utils.PropertiesLoader;
import com.dzk.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dzk
 * @date 2020/7/3 23:13
 * @description 用于将properties文件中的数据传入前端
 */
public class Global {
    private static volatile Global global;
    private Global(){}
    public static Global getInstance(){//单例模式 double lock
        synchronized (Global.class){
            if (global==null){
                return new Global();
            }
            return global;
        }
    }
    /*存储全局属性值*/
    private static Map<String,String> map=new HashMap<>();

    /*加载属性文件*/
    private static PropertiesLoader loader = new PropertiesLoader("applicationProperties.properties");

    public static String getCongig(String key){
        String value=map.get(key);
        if(value==null){
            value=loader.getProperty(key);/*使用map作为缓存*/
            map.put(key,value!=null?value: StringUtils.EMPTY);
        }
        return value;
    }

}
