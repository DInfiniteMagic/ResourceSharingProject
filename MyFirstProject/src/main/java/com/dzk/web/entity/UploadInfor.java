package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;

/**
 * @author dzk
 * @date 2020/7/14 21:00
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadInfor {
    private MultipartFile fileUpload;
    @NotEmpty
    @Length(max = 50,min = 6,message = "资源名在6-50个字符之间")
    private String resourceName;//资源名
    @NotEmpty
    private String resourceClass;//资源类别
    @NotEmpty
    private int resourceValue;//资源积分
    @NotEmpty
    @Length(min=25,max=800,message = "资源描述在25-800个字符之间")
    private String resourceDescription;//资源描述
    private String uploadResourceName;//资源的本名
    private String resourceUrl;//资源上传存放地址
    private String resourceStatue;//当前资源的状态 审核
    private String uploadTime;//上传时间
    private long uploadAccountId;//资源上传人的id
    private long resourceSize;//资源大小
}
