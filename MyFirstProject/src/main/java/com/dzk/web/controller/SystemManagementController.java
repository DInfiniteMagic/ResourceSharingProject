package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.common.utils.JsonUtils;
import com.dzk.web.entity.DownloadTable;
import com.dzk.web.entity.HistorySystemMonitor;
import com.dzk.web.entity.ModifyUploadTable;
import com.dzk.web.entity.SystemMonitoring;
import com.dzk.web.service.ResourceService;
import com.dzk.web.service.SystemManagementService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dzk
 * @date 2020/7/26 19:49
 * @description
 */
@RequiresRoles(value = {"admin"})//拥有admin 角色的才能访问
@Controller
@RequestMapping("/SystemManagement")
public class SystemManagementController {
    @Autowired
    private SystemManagementService systemManagementService;
    @Autowired
    private ResourceService resourceService;
    @OperateLogAnnotation(moduleType = "/SystemMonitoring",operateContent = "转发进入系统监控页面",functionName = "SystemMonitoring()")  //这边添加切入点接口的注解
    @RequestMapping("/SystemMonitoring")
    public String SystemMonitoring(){
        return "backstageManagement/backstageManagementPage";
    }

    @OperateLogAnnotation(moduleType = "/SystemMonitoringRedirect",operateContent = "重定向进入系统监控页面",functionName = "SystemMonitoringRedirect()")  //这边添加切入点接口的注解
    @RequestMapping("/SystemMonitoringRedirect")
    public String SystemMonitoringRedirect(){
        return "redirect:SystemMonitoring";
    }


    @RequestMapping("/getNowSystemMonitoring")
    @ResponseBody
    public SystemMonitoring getNowSystemMonitoring(){
        SystemMonitoring systemMonitoring=systemManagementService.getSystemMonitoring();//得到当前的系统属性
        return systemMonitoring;
    }
    @RequestMapping("/getHistorySystemMonitoring")
    @ResponseBody
    public HistorySystemMonitor getHistorySystemMonitoring(){
        HistorySystemMonitor historySystemMonitor=systemManagementService.getHistorySystemMonitor();
        return historySystemMonitor;
    }

    @OperateLogAnnotation(moduleType = "/initResourcesReviewTable",operateContent = "进入资源审核页面",functionName = "initResourcesReviewTable()")  //这边添加切入点接口的注解
    @PostMapping("/initResourcesReviewTable")
    @ResponseBody
    public String initResourcesReviewTable(Integer page,Integer rows,String sidx,String sord) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        boolean flag=true;
        if(username==null){
            username="";
            flag=false;
        }
        DownloadTable downloadTable = resourceService.getInitDownloadTableData(page, rows, username,sidx,sord,"未审核");
        downloadTable.setLogin(flag);
        return JsonUtils.toJson(downloadTable);
    }



    @OperateLogAnnotation(moduleType = "/editResourcesReviewTable",operateContent = "编辑资源审核表",functionName = "editResourcesReviewTable()")  //这边添加切入点接口的注解
    @PostMapping("/editResourcesReviewTable")
    @ResponseBody
    public void editResourcesReviewTable(int id,String resourcesStatue, String oper) {
        if ("edit".equals(oper)) {
            resourceService.modifyUploadResoucesStatueById(id,resourcesStatue);
        }
        else if("del".equals(oper)){
            resourceService.deleteUploadTable(id);
        }
    }



    @OperateLogAnnotation(moduleType = "/initResourcesReturnTable",operateContent = "进入资源退回页面",functionName = "initResourcesReturnTable()")  //这边添加切入点接口的注解
    @PostMapping("/initResourcesReturnTable")
    @ResponseBody
    public String initResourcesReturnTable(Integer page,Integer rows,String sidx,String sord) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        boolean flag=true;
        if(username==null){
            username="";
            flag=false;
        }
        DownloadTable downloadTable = resourceService.getInitDownloadTableData(page, rows, username,sidx,sord,"退回");
        downloadTable.setLogin(flag);
        return JsonUtils.toJson(downloadTable);
    }


}
