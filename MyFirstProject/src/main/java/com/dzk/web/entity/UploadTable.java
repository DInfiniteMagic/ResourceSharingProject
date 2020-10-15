package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/24 15:40
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadTable {
    private long id;
    private String resourceName;
    private String resourceClass;
    private int resourceValue;
    private String uploadTime;
    private String resourceDescription;
}
