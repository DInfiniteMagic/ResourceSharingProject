package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/28 17:12
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessRecordsTableData {
    private long id;
    private String userAccount;
    private String ipHost;
    private String userRole;
    private String operateTime;
    private String moduleType;
    private String operateContent;
}
