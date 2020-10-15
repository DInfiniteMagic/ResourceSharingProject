package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dzk
 * @date 2020/7/27 14:56
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorySystemMonitor {
    private List<String> RAMPercentageList=new ArrayList<>();
    private List<String> CPUPercentageList=new ArrayList<>();
    private List<String> JVMPercentageList=new ArrayList<>();
    private List<String> HardDiskPercentageList=new ArrayList<>();
}
