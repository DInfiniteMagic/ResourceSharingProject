package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/22 15:08
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Download {
    private String uploadResourceName;
    private String resourceUrl;
}
