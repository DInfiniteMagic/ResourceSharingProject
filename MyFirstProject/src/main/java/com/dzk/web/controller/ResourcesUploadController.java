package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.common.utils.JsonUtils;
import com.dzk.common.utils.PropertiesLoader;
import com.dzk.web.entity.ModifyUploadTable;
import com.dzk.web.entity.UploadInfor;
import com.dzk.web.entity.UploadTableInfor;
import com.dzk.web.service.ResourceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dzk
 * @date 2020/7/14 20:54
 * @description  资源上传
 */
@Controller
@RequestMapping("/resources")
public class ResourcesUploadController {
    @Autowired
    private ResourceService resourceService;

    @OperateLogAnnotation(moduleType = "/uploadPageRedirect",operateContent = "重定向进入上传页面",functionName = "uploadPageRedirect()")  //这边添加切入点接口的注解
    @RequestMapping("/uploadPageRedirect")
    public String uploadPageRedirect(){
        return "redirect:uploadPage";
    }

    @OperateLogAnnotation(moduleType = "/uploadPage",operateContent = "转发进入上传页面",functionName = "uploadPage()")  //这边添加切入点接口的注解
    @RequestMapping("/uploadPage")
    public String uploadPage(){
        return "menu/uploadPage";
    }

    @OperateLogAnnotation(moduleType = "/upload",operateContent = "上传资源",functionName = "upload()")  //这边添加切入点接口的注解
    @PostMapping("/upload")
    @ResponseBody
    public String upload(UploadInfor uploadInfor, HttpServletRequest request, BindingResult bindingResult){
        StringBuffer stringBuffer=new StringBuffer();//存储信息
        Subject subject= SecurityUtils.getSubject();//获取当前主体对象
        if(!subject.isAuthenticated() && !subject.isRemembered()){//未登录(未验证 或者 没有rememberMe)
            stringBuffer.append("请登录！");
            return stringBuffer.toString();
        }
        if(bindingResult.hasErrors() || uploadInfor.getFileUpload().getSize()<=0){//后台数据校验
            for (ObjectError error:bindingResult.getAllErrors()) {
                stringBuffer.append(error.getDefaultMessage());
            }
            if(uploadInfor.getFileUpload().getSize()<=0){
                stringBuffer.append("上传资源不为空！");
            }
            return stringBuffer.toString();
        }
        String SubjectName=(String)subject.getPrincipal();//得到当前主体的用户名
        resourceService.saveUploadResource(uploadInfor,SubjectName);//存储
        stringBuffer.append("文件上传完毕");
        return stringBuffer.toString();
    }


    @OperateLogAnnotation(moduleType = "/initUploadTable",operateContent = "查看未审核的表",functionName = "initUploadTable()")  //这边添加切入点接口的注解
    @PostMapping("/initUploadTable")//未审核的表初始化
    @ResponseBody
    public String initUploadTable(Integer page,Integer rows,String sidx,String sord){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        String returnMessage="";
        if(username==null){
            return "";//"请登录！"
        }
        UploadTableInfor uploadTableInfor=resourceService.getInitUploadTableData(page,rows,sidx,sord,username,"未审核");
        return JsonUtils.toJson(uploadTableInfor);
    }

    @OperateLogAnnotation(moduleType = "/editUploadTable",operateContent = "编辑修改未审核的资源表",functionName = "editUploadTable()")  //这边添加切入点接口的注解
    @PostMapping("/editUploadTable")
    @ResponseBody
    public void editUploadTable(ModifyUploadTable modifyUploadTable, String oper) {
        if ("edit".equals(oper)) {
            resourceService.modifyUploadTable(modifyUploadTable);
        }
        else if("del".equals(oper)){
            resourceService.deleteUploadTable(modifyUploadTable.getId());
        }
    }

    @OperateLogAnnotation(moduleType = "/initUploadAuditedTable",operateContent = "查看已审核的表",functionName = "initUploadAuditedTable()")  //这边添加切入点接口的注解
    @PostMapping("/initUploadAuditedTable")//已审核的表初始化
    @ResponseBody
    public String initUploadAuditedTable(Integer page,Integer rows,String sidx,String sord){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        String returnMessage="";
        if(username==null){
            return "";//"请登录！"
        }
        UploadTableInfor uploadTableInfor=resourceService.getInitUploadTableData(page,rows,sidx,sord,username,"已审核");
        return JsonUtils.toJson(uploadTableInfor);
    }


    @OperateLogAnnotation(moduleType = "/initUploadReturnTable",operateContent = "查看退回的表",functionName = "initUploadReturnTable()")  //这边添加切入点接口的注解
    @PostMapping("/initUploadReturnTable")//未审核的表初始化
    @ResponseBody
    public String initUploadReturnTable(Integer page,Integer rows,String sidx,String sord){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        String returnMessage="";
        if(username==null){
            return "";//"请登录！"
        }
        UploadTableInfor uploadTableInfor=resourceService.getInitUploadTableData(page,rows,sidx,sord,username,"退回");
        return JsonUtils.toJson(uploadTableInfor);
    }
}
