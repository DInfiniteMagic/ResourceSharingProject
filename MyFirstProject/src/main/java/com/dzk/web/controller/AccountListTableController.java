package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.web.entity.AccessRecordsTable;
import com.dzk.web.entity.AccountListTable;
import com.dzk.web.service.SystemManagementService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dzk
 * @date 2020/7/29 17:27
 * @description 用户列表相关炒作
 */
@RequiresRoles(value = {"admin"})//拥有admin 角色的才能访问
@RestController
@RequestMapping("/SystemManagement")
public class AccountListTableController {
    @Autowired
    private SystemManagementService systemManagementService;
    @OperateLogAnnotation(moduleType = "/initUserListTable",operateContent = "进入用户列表页面",functionName = "initUserListTable()")  //这边添加切入点接口的注解
    @RequestMapping("/initUserListTable")
    public AccountListTable initUserListTable(Integer page, Integer rows, String sidx, String sord){
        return systemManagementService.getAccountListTable(page,rows,sidx,sord);
    }

    @OperateLogAnnotation(moduleType = "/searchAccountListTable",operateContent = "搜索用户列表",functionName = "searchAccountListTable()")  //这边添加切入点接口的注解
    @PostMapping("/searchAccountListTable")
    public AccountListTable searchAccountListTable(String searchContext,Integer page,Integer rows,String sidx,String sord){
        return systemManagementService.getSearchAccountListTableData(searchContext,page,rows,sidx,sord);
    }


    @OperateLogAnnotation(moduleType = "/editAccountListTable",operateContent = "编辑修改用户列表",functionName = "editAccountListTable()")  //这边添加切入点接口的注解
    @PostMapping("/editAccountListTable")
    @ResponseBody
    public void editAccountListTable(long id,String roleName,String status, String oper) {
        if ("edit".equals(oper)) {
            systemManagementService.modifyAccountListTable(id,roleName,status);
        }
    }

}
