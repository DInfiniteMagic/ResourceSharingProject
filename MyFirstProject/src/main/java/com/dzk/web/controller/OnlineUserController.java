package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.web.entity.OnlineUserListTable;
import com.dzk.web.service.SystemManagementService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dzk
 * @date 2020/7/30 17:18
 * @description
 */
@RequiresRoles(value = {"admin"})//拥有admin 角色的才能访问
@RestController
@RequestMapping("/SystemManagement")
public class OnlineUserController {
    @Autowired
    private SystemManagementService systemManagementService;
    @OperateLogAnnotation(moduleType = "/initOnlineUserListTable",operateContent = "进入在线用户列表页面",functionName = "initOnlineUserListTable()")  //这边添加切入点接口的注解
    @RequestMapping("/initOnlineUserListTable")
    public OnlineUserListTable  initOnlineUserListTable(Integer page, Integer rows, String sidx, String sord){
        return systemManagementService.getOnlineUserListTableData(page,rows,sidx,sord);
    }

    @OperateLogAnnotation(moduleType = "/forceQuitOnlineUser",operateContent = "强制退出在线用户",functionName = "forceQuitOnlineUser()")  //这边添加切入点接口的注解
    @PostMapping("/forceQuitOnlineUser")
    public void forceQuitOnlineUser(@RequestBody String username){
        System.out.println(username);
        systemManagementService.forceQuitOnlineUser(username);
    }
}
