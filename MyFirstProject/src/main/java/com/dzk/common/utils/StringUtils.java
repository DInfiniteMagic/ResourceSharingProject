package com.dzk.common.utils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dzk
 * @date 2020/7/4 0:20
 * @description
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static boolean regExCheck(String regEx,String check){
        Pattern pattern=Pattern.compile(regEx);// 编译正则表达式
        Matcher matcher=pattern.matcher(check);
        Boolean flag=matcher.matches();// 字符串是否与正则表达式相匹配
        return flag;
    }

    public static String dataChange(Date date,String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        String time=sdf.format(date);
        return time;
    }
    public static String dataToString(Calendar cale){
        StringBuffer stringBuffer=new StringBuffer();
        int year = cale.get(Calendar.YEAR);
        stringBuffer.append(year);
        int month = cale.get(Calendar.MONTH) + 1;
        stringBuffer.append(month);
        int day = cale.get(Calendar.DATE);
        stringBuffer.append(day);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        stringBuffer.append(hour);
        int minute = cale.get(Calendar.MINUTE);
        stringBuffer.append(minute);
        int second = cale.get(Calendar.SECOND);
        stringBuffer.append(second);
        return stringBuffer.toString();
    }
}
