package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dzk
 * @date 2020/7/6 18:29
 * @description 处理 菜单 模块的请求
 */
@Controller
@RequestMapping("menu")
public class MenuController {
    @OperateLogAnnotation(moduleType = "/mainPageRedirect",operateContent = "重定向进入主菜单",functionName = "mainMenuRedirect()")  //这边添加切入点接口的注解
    @RequestMapping("/mainPageRedirect")
    public String mainMenuRedirect(){
        return "redirect:mainPage";
    }
    @OperateLogAnnotation(moduleType = "/mainPage",operateContent = "转发向进入主菜单",functionName = "mainMenu()")  //这边添加切入点接口的注解
    @RequestMapping("/mainPage")
    public String mainMenu(){
        return "menu/mainPage";
    }

}
