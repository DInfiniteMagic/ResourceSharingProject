package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/30 17:20
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserListTable {
    private int page;//当前页
    private long total;//总页数
    private long records;//总条数
    private List<OnlineUserListTableData> rows;//包含实际数据的数组
}
