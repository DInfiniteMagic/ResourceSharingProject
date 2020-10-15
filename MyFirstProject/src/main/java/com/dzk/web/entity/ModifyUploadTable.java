package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/24 16:51
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUploadTable {
    private int id;
    private String resourceName;
    private String resourceClass;
    private int resourceValue;
    private String resourceDescription;
}
