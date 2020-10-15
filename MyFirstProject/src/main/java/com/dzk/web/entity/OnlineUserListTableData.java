package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/30 17:20
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserListTableData {
    private long id;
    private String username;
    private String ipHost;
}
