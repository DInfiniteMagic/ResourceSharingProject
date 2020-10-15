package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/24 15:57
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadTableInfor {
    private int page;//当前页
    private int total;//总页数
    private int records;//总条数
    private List<UploadTable> rows;//包含实际数据的数组
}
