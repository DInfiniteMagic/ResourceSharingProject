package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.web.entity.AccessRecordsTable;
import com.dzk.web.service.SystemManagementService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dzk
 * @date 2020/7/28 17:07
 * @description
 */
@RequiresRoles(value = {"admin"})//拥有admin 角色的才能访问
@RestController
@RequestMapping("/SystemManagement")
public class AccessRecordsController {
    @Autowired
    private SystemManagementService systemManagementService;
    @OperateLogAnnotation(moduleType = "/initAccessRecordsTable",operateContent = "进入访问记录页面",functionName = "initAccessRecordsTable()")  //这边添加切入点接口的注解
    @RequestMapping("/initAccessRecordsTable")
    public AccessRecordsTable initAccessRecordsTable(Integer page, Integer rows, String sidx, String sord){
        return systemManagementService.getAccesssRecordsTableData(page,rows,sidx,sord);
    }

    @OperateLogAnnotation(moduleType = "/searchAccessRecordsTable",operateContent = "查找访问记录",functionName = "searchAccessRecordsTable()")  //这边添加切入点接口的注解
    @PostMapping("/searchAccessRecordsTable")
    public AccessRecordsTable searchAccessRecordsTable(String searchContext,Integer page,Integer rows,String sidx,String sord){
        return systemManagementService.getSearchAccessRecordsTableData(searchContext,page,rows,sidx,sord);
    }
}
