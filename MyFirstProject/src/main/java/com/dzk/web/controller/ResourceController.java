package com.dzk.web.controller;

import com.dzk.common.utils.VerifyCodeUtils;

import com.dzk.web.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author dzk
 * @date 2020/7/5 10:28
 * @description
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @RequestMapping("/captcha")
    public  void  getVerifyCode(HttpServletResponse response, HttpSession httpSession) throws IOException {
        String verifyCode= VerifyCodeUtils.generateVerifyCode(4);//生成4个字符的验证码
        httpSession.setAttribute("captcha",verifyCode);//将验证码存入session
        ServletOutputStream outputStream =response.getOutputStream();
        response.setContentType("image/png");
        VerifyCodeUtils.outputImage(400,100,outputStream,verifyCode);
    }

    @PostMapping("/checkCaptcha")
    @ResponseBody
    public String checkCaptcha(HttpSession httpSession,@RequestBody String check){
        if(check.length()==12){
            check=check.substring(8);
            String captcha=(String)httpSession.getAttribute("captcha");
            if(captcha.equalsIgnoreCase(check)){
                return "{\"valid\":true}";
            }
        }
        return "{\"valid\":false}";
    }

    @RequestMapping("/getResourcesClass")
    @ResponseBody
    public String getResourcesClass(){
        return resourceService.getAllResourcesClass();
    }
    @RequestMapping("/getResourcesClassForJqgrid")
    @ResponseBody
    public String getResourcesClassForJqgrid(){
        String str=resourceService.getAllResourcesClass();
        str="<select>"+str+"</select>";
        return str;
    }


    @RequestMapping("/getAllAcoountRolesForJqgrid")
    @ResponseBody
    public String getAllAcoountRolesForJqgrid(){
        String str=resourceService.getAllAcoountRolesForJqgrid();
        str="<select>"+str+"</select>";
        return str;
    }
}
