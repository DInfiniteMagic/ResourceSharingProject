package com.dzk.web.controller;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.common.utils.PropertiesLoader;
import com.dzk.common.utils.WebUtils;
import com.dzk.web.entity.Account;
import com.dzk.web.entity.Mail;
import com.dzk.web.entity.Register;
import com.dzk.web.service.AccountService;
import com.dzk.web.service.Impl.RegisterCount;
import com.dzk.web.service.mail.SendMail;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dzk
 * @date 2020/7/6 18:34
 * @description
 */

@Validated
@Controller
@RequestMapping("/tourist")
public class RegisterController {
    //加载属性文件
    private PropertiesLoader loader=new PropertiesLoader("applicationProperties.properties");
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private AccountService accountService;

    @Autowired
    private SendMail sendMail;/*发送邮件的 service*/


    @Autowired
    private RegisterCount registerCount;
    @GetMapping("/register")
    public String register(){//用户注册   从游客身份--->系统用户
        registerCount.getAtomicIntegerModifyMail().set(0);//重新注册时 清除之前请求修改邮箱号的计数
        registerCount.getAtomicIntegerSendMail().set(0);//重新注册时 清除之前请求修发送激活码的计数
        registerCount.getAtomicIntegerActivationCode().set(0);//重新注册时.将邮箱激活码验证次数重置
        return "user/register";
    }

    @PostMapping("/checkSameUsername")
    @ResponseBody
    public Boolean checkSameUsername(@RequestBody String username){
        Account account=accountService.getAccountByUsername(username);//查找是否有相同的用户名
        if(account!=null){
            return false;
        }
        return true;
    }
    @PostMapping("/checkRegister")
    public String checkRegister(@Valid Register register, BindingResult bindingResult, HttpSession session, HttpServletRequest request){//获取用户注册栏的信息
        String captcha=(String)session.getAttribute("captcha");
        if(register==null || captcha==null){//长时间后刷新 register的值为空
            return "redirect:../tourist/register";
        }

        //注意转义字符
        //后台数据验证 判断传来的值是否合法

        List<String> errorMessage = new ArrayList<String>();
        // 检验 数据是否合法  两次密码是否相同 验证码是否正确
        if(bindingResult.hasErrors() || !register.getPassword().equals(register.getCheckPassword()) || !captcha.equalsIgnoreCase(register.getCaptcha())){
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError error:list) {
                logger.info(error.toString());
                errorMessage.add(error.getDefaultMessage());
            }
            if(!captcha.equalsIgnoreCase(register.getCaptcha())){
                errorMessage.add("验证码错误");
            }
            if(!register.getPassword().equals(register.getCheckPassword())){
                logger.info("password:"+register.getPassword()+" checkPassword:"+register.getCheckPassword());
                errorMessage.add("两次密码不一致");
            }
            request.setAttribute("errorMessage",errorMessage);//输出错位信息
            return "user/register";//返回注册页面
        }

        int activationCode=RandomUtils.nextInt(100000,999999);//生成一个6位数的激活码
        register.setActivationCode(activationCode);//存入激活码
        session.setAttribute("register",register);//将注册信息存入session中
        String sendMessage="<h4>您好!欢迎您在"+loader.getProperty("productName")+"上注册账户!</h4><h4>您的用户名为:"+register.getUsername()+"</h4><h4>您的账户激活验证码为:<a>"+register.getActivationCode()+"</a></h4>";
        sendMail.setRegister(new Mail(loader.getProperty("productName")+"账号激活邮件",register.getMailbox(),sendMessage));
        sendMail.run();//发送邮件
        //使用线程池有些问题  后期需要改进
       /* ExecutorService pool= Executors.newCachedThreadPool();//创建一个缓存线程池
        pool.execute(sendMail);//将线程放入线程池中进行执行   发送邮件*/
        return "redirect:../tourist/registerActivation";//转发到邮箱验证页面
    }

    @PostMapping("/sendMailAgain")
    @ResponseBody
    public String sendMailAgain(HttpServletRequest request,HttpSession session){
        registerCount.getAtomicIntegerActivationCode().set(0);//将邮箱激活码验证次数重置
        request.removeAttribute("errorMessage");//清除上一个请求的错误信息
        String errorMessage;
        if(registerCount.getAtomicIntegerSendMail().incrementAndGet()>=3){//当验证码请求次数超过三次时
            logger.info(WebUtils.getIpAddress(request) +":该ip邮件发送异常");
            errorMessage="邮件发送次数过多,请检查邮箱是否正确！";
            return errorMessage;
        }
        Register register=(Register) session.getAttribute("register");//提出注册信息
        int activationCode=RandomUtils.nextInt(100000,999999);//生成一个6位数的激活码
        register.setActivationCode(activationCode);//存入激活码
        session.setAttribute("register",register);//将注册信息存入session中
        String sendMessage="<h4>您好!欢迎您在"+loader.getProperty("productName")+"上注册账户!</h4><h4>您的用户名为:"+register.getUsername()+"</h4><h4>您的账户激活验证码为:<a>"+register.getActivationCode()+"</a></h4>";
        sendMail.setRegister(new Mail(loader.getProperty("productName")+"账号激活邮件",register.getMailbox(),sendMessage));
        sendMail.run();//发送邮件
        return "";
    }

    @OperateLogAnnotation(moduleType = "/changeMailbox",operateContent = "请求修改邮箱",functionName = "changeMailBox()")  //这边添加切入点接口的注解
    @PostMapping("/changeMailbox")/*请求修改邮箱*/
    @ResponseBody
    public String changeMailBox( @NotEmpty(message = "邮箱不为空！") @Pattern(regexp = "[\\w!#\\$%&amp;'\\*\\+\\-\\/=\\^_`{\\|}~.]+@([\\w-]+\\.)+(com|net|cn|org|me|cc|biz)$",message = "邮箱地址格式有误或不支持此类型邮箱!") @RequestBody String mailbox,BindingResult bindingResult,HttpSession session,HttpServletRequest request){
        StringBuffer stringBuffer=new StringBuffer();//存储返回信息
        if(registerCount.getAtomicIntegerModifyMail().incrementAndGet()>=3){//请求次数超过三次
            logger.info(WebUtils.getIpAddress(request) +":该ip多次申请修改邮箱！");
            stringBuffer.append("申请修改邮箱号次数过多！请返回注册页面重新注册！");
            return stringBuffer.toString();
        }
        if(bindingResult.hasErrors()){//对发来的邮箱进行后端验证
            List<ObjectError> list=bindingResult.getAllErrors();
            for (ObjectError o:list) {
                stringBuffer.append(o.getDefaultMessage());
            }
            return stringBuffer.toString();//返回错误信息
        }

        //无异常的情况下 修改邮箱号
        Register register=(Register) session.getAttribute("register");//提出注册信息
        register.setMailbox(mailbox);//修改邮箱
        int activationCode=RandomUtils.nextInt(100000,999999);//生成一个6位数的激活码
        register.setActivationCode(activationCode);//存入激活码
        session.setAttribute("register",register);//将注册信息存入session中
        registerCount.getAtomicIntegerSendMail().set(0);//将新邮箱的发送次数重置
        registerCount.getAtomicIntegerActivationCode().set(0);//将邮箱激活码验证次数重置
        stringBuffer.append("邮箱修改成功");
        return stringBuffer.toString();
    }

    @RequestMapping("/registerActivation")
    public String registerActivation(HttpSession session){
        //长时间后 可能session 生命周期到期了
        if((Register)session.getAttribute("register")==null){//当注册信息不为空时，不再重复发送邮件
            return "redirect:../tourist/register";
        }
        return "/user/registerActivation";
    }


    @RequestMapping("/checkActivationCode")
    public String checkActivationCode(@RequestParam("activationCode") int activationCode,HttpSession session,HttpServletRequest request){
        String errorMessage;
        request.removeAttribute("errorMessage");
        Register register=(Register)session.getAttribute("register");
        if(registerCount.getAtomicIntegerActivationCode().incrementAndGet()>=3){
            errorMessage="错误次数过多,请选择重新发送，或更换邮箱后再发送！";
            request.setAttribute("errorMessage",errorMessage);//返回错误信息
            return "/user/registerActivation";//返回验证界面
        }
        if(register==null){//当注册信息不为空时，不再重复发送邮件
            errorMessage="验证超时！请重新注册！";
            request.setAttribute("errorMessage",errorMessage);
            return "/user/registerActivation";//返回验证界面
        }
        if((register.getActivationCode()==activationCode)){
            boolean flag= false;//注册
            try {
                accountService.registerAccount(register);
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage="用户名重复！";
                request.setAttribute("errorMessage",errorMessage);
                return "/user/registerActivation";//返回验证界面
            }
            if(logger.isDebugEnabled()){
                logger.debug(register+":注册异常！");
            }
            return "redirect:../user/login";
        }else{
            errorMessage="激活码错误！";
            request.setAttribute("errorMessage",errorMessage);//返回错误信息
            return "/user/registerActivation";//返回验证界面
        }

    }
}
