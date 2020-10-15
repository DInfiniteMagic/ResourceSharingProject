package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dzk
 * @date 2020/7/19 22:43
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadInfor {
    private long id;//资源编号
    private String downloadResourceName;//资源名称
    private String resourceClass;//资源类别
    private int resourceValue;//资源下载所需积分
    private String downloadAccountName;//资源上传者的名称
    private String resourceSize;//资源大小
    private int resourceDownloadTimes;//资源下载次数
    private String uploadTime;//资源上传时间
    private String resourceDetails;//资源描述
    private int favorites;//资源是否被当前主体收藏
    private String resourcesStatue;//资源状态
}
