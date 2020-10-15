package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dzk
 * @date 2020/7/29 17:29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountListTable {
    private int page;//当前页
    private long total;//总页数
    private long records;//总条数
    private List<AccountListTableData> rows;//包含实际数据的数组
}
