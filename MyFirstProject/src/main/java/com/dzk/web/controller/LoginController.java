package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.common.utils.EncryptionUtils;
import com.dzk.common.utils.JsonUtils;
import com.dzk.common.utils.PropertiesLoader;
import com.dzk.common.utils.WebUtils;
import com.dzk.web.entity.*;

import com.dzk.web.service.AccountService;

import com.dzk.web.service.mail.SendMail;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dzk
 * @date 2020/7/4 20:04
 * @description
 */
@Validated
@Controller
@RequestMapping("/user")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    private Cache<String, AtomicInteger> forgetUsernameRetryCache;//用于存储请求获取忘记账号的次数
    private Cache<String, AtomicInteger> forgetPasswordRetryCache;//用于存储请求获取忘记账号的次数
    private Cache<String, AtomicInteger> sendMailRetryCache;//用于存储请求发送激活验证码次数
    private AtomicInteger atomicInteger=new AtomicInteger(0);//请求获取忘记账号的次数
    private AtomicInteger atomicIntegerSendMail=new AtomicInteger(0);//请求获取邮箱激活码的次数
    private AtomicInteger atomicIntegerForgetPassword=new AtomicInteger(0);//请求获取忘记密码的次数
    private AccountService accountService;
    private SendMail sendMail;/*发送邮件的 service*/
    //加载属性文件
    private PropertiesLoader loader=new PropertiesLoader("applicationProperties.properties");
    @Autowired
    public LoginController(CacheManager cacheManager,AccountService accountService,SendMail sendMail) {
        this.forgetUsernameRetryCache=cacheManager.getCache("requestRetryCache");
        this.forgetPasswordRetryCache=cacheManager.getCache("requestRetryCache");
        this.sendMailRetryCache=cacheManager.getCache("requestRetryCache");
        this.accountService=accountService;
        this.sendMail=sendMail;
    }
    @GetMapping("/login")
    public  String login(Model model){
        String principal=(String) SecurityUtils.getSubject().getPrincipal();
        Boolean checkLogin=SecurityUtils.getSubject().isAuthenticated();//验证是否通过shiro的认证
        if(checkLogin=true && principal!=null){//如果已经登录则直接进入主页
            return "redirect:../menu/mainPage";//因为jsp文件放在web-inf下 不能直接重定向
        }
        return "user/login";
    }

    /*真正的登入由FormAuthenticationFilter完成 使用异步验证的方式  此 login 返回登录信息状态到前端*/
    @PostMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request) throws IOException {//如果已经登录则直接进入主页
        System.out.println(WebUtils.getIpAddress(request));
        String message=(String) request.getAttribute("loginMessage");//loginMessage在FormAuthenticationFilter中设置
        return message;
    }

    @PostMapping("/isLogin")
    @ResponseBody
    public String isLogin(){//判断是否登录
        Subject subject=SecurityUtils.getSubject();
        if(!subject.isRemembered() && !subject.isAuthenticated()){//没有被记住 或者 没有登录
            return JsonUtils.toJson(new AccountLogin());//未登录
        }
        AccountLogin accountLogin=accountService.getAccountLogin((String) subject.getPrincipal());
        return JsonUtils.toJson(accountLogin);//返回json 数据格式
    }

    @OperateLogAnnotation(moduleType = "/logout",operateContent = "退出登录",functionName = "logout()")  //这边添加切入点接口的注解
    @RequestMapping("/logout")
    public String logout(){/*退出登录*/
        SecurityUtils.getSubject().logout();
        return "redirect:/user/login";
    }


    @PostMapping("/forgetUsername")/*忘记账号*/
    @ResponseBody
    public String forgetUsername(@NotEmpty(message = "邮箱不为空！") @Pattern(regexp = "[\\w!#\\$%&amp;'\\*\\+\\-\\/=\\^_`{\\|}~.]+@([\\w-]+\\.)+(com|net|cn|org|me|cc|biz)$",message = "邮箱地址格式有误或不支持此类型邮箱!") @RequestBody String mail, BindingResult bindingResult){
        //JSR 303 后端验证
        if(StringUtils.isEmpty(forgetUsernameRetryCache.get(mail))){//如果缓存中无此key  创建它
            atomicInteger=new AtomicInteger(0);
            forgetUsernameRetryCache.put(mail,atomicInteger);
        }
        atomicInteger=forgetUsernameRetryCache.get(mail);//获取缓存中的请求计数
        if(atomicInteger.incrementAndGet()>3){//请求次数大于三次 该邮箱被冻结 5分钟 （时间由ehcache.xml 中设置）
            return "该邮箱操作次数过多！请稍后再试！";
        }

        // 邮箱数据 数据是否合法
        if(bindingResult.hasErrors()){
            List<String> errorMessage = new ArrayList<String>();
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError error:list) {
                logger.info(error.toString());
                errorMessage.add(error.getDefaultMessage());
            }
            return errorMessage.toString();//返回错误信息
        }

        //当请求次数未超过三次，并且发来是数据符合规范时 去dao层查找 该邮箱对应的账号
        List<String> username=accountService.forgetUsername(mail);//一个邮箱可能注册多个账号
        if(username.isEmpty()){
            return "该邮箱暂未注册账号！";
        }
        StringBuffer sendMessage=new StringBuffer();
        String str="<h4>您好!欢迎您在"+loader.getProperty("productName")+"上注册账户!</h4><h4>您的账号为:</h4>";
        sendMessage.append(str);
        for (String s:username) {
            sendMessage.append("<h4><a>"+s+"</a></h4>");
        }
        sendMail.setRegister(new Mail(loader.getProperty("productName")+"账号寻回邮件",mail,sendMessage.toString()));
        sendMail.run();//发送邮件
        return "账号已发送至"+mail+"邮箱中！请及时查收！";
    }


    @OperateLogAnnotation(moduleType = "/sendMail",operateContent = "发送邮件",functionName = "sendMail()")  //这边添加切入点接口的注解
    @PostMapping("/sendMail")
    @ResponseBody
    public String sendMail(@NotEmpty @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "用户名只能包含大写、小写、数字和下划线")/*用户名只能包含大写、小写、数字和下划线*/ @Length(min = 6,max = 18)  @RequestBody String forgetPassword_username, BindingResult bindingResult, HttpSession session){
        //用户名 数据是否合法  || 该账号是否存在 存在返回false
        Account account=accountService.getAccountByUsername(forgetPassword_username);
        if(bindingResult.hasErrors() || StringUtils.isEmpty(account)){
            StringBuffer errorMessage=new StringBuffer();
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError error:list) {
                logger.info(error.toString());
                errorMessage.append(error.getDefaultMessage());
            }
            if(StringUtils.isEmpty(account)){
                errorMessage.append("该账号不存在！");
            }
            return errorMessage.toString();//返回错误信息
        }
        //账户存在时 判断是否超过请求次数
        atomicIntegerSendMail=sendMailRetryCache.get(forgetPassword_username+"-Mail");
        if(StringUtils.isEmpty(atomicIntegerSendMail)){//如果为空 代表缓存中无此主体邮件发送次数
            atomicIntegerSendMail=new AtomicInteger(0);
            sendMailRetryCache.put(forgetPassword_username+"-Mail",atomicIntegerSendMail);
        }
        if(atomicIntegerSendMail.incrementAndGet()>3){//发送次数超过3次 ,锁定5分钟
            return "请求发送次数过多！请稍后再试！";
        }
        //去dao层查找该用户名对应的邮箱
        String mail=accountService.getMailById(account.getId());//根据查找到的用户信息  得到邮箱
        int validatorCode= RandomUtils.nextInt(100000,999999);//生成一个6位数的激活码
        session.setAttribute("validatorCode",validatorCode);//存入session中
        String sendMessage="<h4>您好!欢迎您在"+loader.getProperty("productName")+"上重置用户名为:"+forgetPassword_username+" 账户的密码!</h4><h4>您的重置密码的验证码为:<a>"+validatorCode+"</a></h4>";
        sendMail.setRegister(new Mail(loader.getProperty("productName")+"账号密码重置邮件",mail,sendMessage));
        sendMail.run();//发送邮件
        return "验证码已发送！请注意查收！";
    }

    @PostMapping("/modifyPassword")
    public String modifyPassword(HttpServletRequest request, @Valid ModifyPassword modifyPassword, BindingResult bindingResult, HttpSession session){
        int validatorCode=(int)session.getAttribute("validatorCode");//获取激活码
        StringBuffer message=new StringBuffer();//存储返沪信息
        if(bindingResult.hasErrors() || StringUtils.isEmpty(validatorCode)){
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError error:list) {
                logger.info(error.toString());
                message.append(error.getDefaultMessage());
            }
            if(StringUtils.isEmpty(validatorCode)){
                message.append("该激活码已过期！");
            }
            request.setAttribute("message",message.toString());
            return "user/login";//返回错误信息
        }

        atomicIntegerForgetPassword=forgetPasswordRetryCache.get(modifyPassword.getForgetPassword_username());//获取当前缓存中 该主体请求更换密码的次数
        if(StringUtils.isEmpty(atomicIntegerForgetPassword)){//缓存中没有 这创建它
            atomicIntegerForgetPassword=new AtomicInteger(0);
            forgetPasswordRetryCache.put(modifyPassword.getForgetPassword_username(),atomicIntegerForgetPassword);
        }
        if(atomicIntegerForgetPassword.incrementAndGet()>3){//请求次数大于3次时
            message.append("该账户请求更换密码次数过于频繁，请稍后再试！");
            request.setAttribute("message",message.toString());
            return "user/login";//返回错误信息
        }/*此处考虑是否重置 请求发送激活码的次数  暂未添加这个*/

        if(validatorCode!=modifyPassword.getValidatorCode()){
            message.append("验证码错误！");
            request.setAttribute("message",message.toString());
            return "user/login";//返回错误信息
        }

        if(!modifyPassword.getCheckNewPassword().equals(modifyPassword.getNewPassword())){//判断两次密码是否一致
            message.append("两次密码不一致！");
            request.setAttribute("message",message.toString());
            return "user/login";//返回错误信息
        }

        //调用dao层修改该用户密码
        Account account=accountService.getAccountByUsername(modifyPassword.getForgetPassword_username());
        if(account==null){
            logger.info("次时有可能在提交修改用户密码的同时 管理员删除了该用户");
            message.append("该用户不存在！");
            request.setAttribute("message",message.toString());
            return "user/login";//返回错误信息
        }

        Encryption encryption=EncryptionUtils.encryptionByMd5(modifyPassword.getNewPassword());//密码加密
        accountService.modifyPasswordById(account.getId(),encryption);//更改密码
        return "redirect:login";
    }
}
