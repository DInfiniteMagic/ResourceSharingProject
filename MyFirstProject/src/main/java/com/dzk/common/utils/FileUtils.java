package com.dzk.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author dzk
 * @date 2020/7/15 19:59
 * @description
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    public static File creatFile(String path,String fileStyle){
        File f1=new File(path);//创建目录
        if(!f1.exists()){
            f1.mkdirs();
        }
        File f2=new File(path,fileStyle);//文件名及类型
        if(!f2.exists()){
            try {
                f2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f2;
    }

}
