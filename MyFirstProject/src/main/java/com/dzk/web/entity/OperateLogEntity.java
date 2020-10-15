package com.dzk.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author dzk
 * @date 2020/7/27 19:19
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLogEntity {
    private long id;//主键
    private String classPath;//类名称，含路径
    private String classMethod;//方法名(英文)
    private String ipHost;//IP地址
    private Date operateTime;//操作时间
    private String userAccount;//用户账号
    private String moduleType;//业务模块类型
    private String functionName;//业务模块名称
    private String operateContent;//操作内容
}
