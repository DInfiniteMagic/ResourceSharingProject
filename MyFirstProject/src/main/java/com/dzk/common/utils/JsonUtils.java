package com.dzk.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @author dzk
 * @date 2020/7/17 17:35
 * @description
 */
public class JsonUtils {
    public static String toJson(Object obj){
        return toJsonCloseTimestamp(obj,"yyyy-MM-dd HH:mm:ss");
    }
    public static String toJsonCloseTimestamp(Object obj,String dateFormat){
        //创建一个jackjson的对象映射器,用来解析数据
        ObjectMapper mapper=new ObjectMapper();
        //关闭时间戳
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,false);
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);//自定义日期格式对象
        mapper.setDateFormat(sdf);//让mapper指定日期格式
        String str=null;
        try {
            //将对象解析成json 格式
            str=mapper.writeValueAsString(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

}
