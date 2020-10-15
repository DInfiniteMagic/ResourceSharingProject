package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author dzk
 * @date 2020/7/26 20:46
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemMonitoring {
    private Date time;
    private String RAMPercentage;
    private String CPUPercentage;
    private String JVMPercentage;
    private String HardDiskPercentage;
    private int CPUCount;//cpu核数
    private String jdkVersion;//jdk版本
    private String OSName;
    private String dataBase;
    private String ipAddress;//服务器ip地址
    private String macAddress;//服务器mac地址
    private String startTime;//服务器启动时间
    private String runTime;//服务器运行时间
}
