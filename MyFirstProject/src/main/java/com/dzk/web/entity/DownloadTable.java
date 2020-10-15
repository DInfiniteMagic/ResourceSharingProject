package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/19 22:54
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTable {
    private int page;//当前页
    private int total;//总页数
    private int records;//总条数
    private List<DownloadInfor> rows;//包含实际数据的数组
    private  boolean login;
}
