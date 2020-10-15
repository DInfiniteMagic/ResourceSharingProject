package com.dzk.web.security.filter;

import com.dzk.common.myException.AccountFreezeException;
import com.dzk.common.myException.CaptchaException;
import com.dzk.common.myException.LockUsernameException;
import lombok.SneakyThrows;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.shiro.cache.Cache;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dzk
 * @date 2020/7/4 21:00
 * @description 自定义FormAuthenticationFilter 继承与shiro的FormAuthenticationFilter
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);
    private Cache<String,AtomicInteger> passwordRetryCache;//集群中可能会导致出现验证多过3次的现象，因为AtomicInteger只能保证单节点并发
    private static  Boolean FLAG=false;//判断是否登录失败超过三次
    public FormAuthenticationFilter(CacheManager cacheManager) {
        this.passwordRetryCache =cacheManager.getCache("passwordRetryCache");
    }
    @SneakyThrows
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("exception", e);
        }
        String username=(String) token.getPrincipal();//获取当前失败的主体名
        AtomicInteger loginCount=passwordRetryCache.get(username);

        if(StringUtils.isEmpty(loginCount)){//如果缓存中没有该账号的失败计数
            loginCount=new AtomicInteger(0);/*在被冻结期间 每次调用都会重新计算时间*/
            passwordRetryCache.put(username,loginCount);

        }
        if(loginCount.incrementAndGet()>=3){//连续登入失败三次
            log.info("username:"+username+"tried to login more than 3 times in period");
            FLAG=true;
        }

        String exceptionName=e.getClass().getName();
        String message="";
        if(exceptionName.equals(CaptchaException.class.getName())){
            log.info("验证码错误！");
            message="验证码错误！";
        }
        else if(exceptionName.equals(LockUsernameException.class.getName())){
            log.info("登录失败次数过多,账号已被锁定,请三分钟后再次尝试！");
            message="登录失败次数过多,账号已被锁定,请三分钟后再次尝试！";

        }
        else if(exceptionName.equals(UnknownAccountException.class.getName())){
            log.info("账号错误！");
            message="账号错误！";
        }
        else if(exceptionName.equals(IncorrectCredentialsException.class.getName())){
            log.info("密码错误！");
            message="密码错误！";
        }
        else if(exceptionName.equals(AccountFreezeException.class.getName())){
            log.info("账号已被冻结！");
            message="账号已被冻结！";
        }
        this.setFailureAttribute(request, e);
        if (isAjax(request)){//判断请求是否是ajax
            message="{\"success\":\"false\",\"message\":\""+message+"\"}";
            request.setAttribute("loginMessage",message);//将登录验证状态存入
        }

        return super.onLoginFailure(token,e,request,response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        String username=(String)token.getPrincipal();
        if(StringUtils.isEmpty(passwordRetryCache.get(username))){
            FLAG=false;//当缓存内无该账号时 解锁
        }
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            String captcha=(String) SecurityUtils.getSubject().getSession().getAttribute("captcha");//获取session中的验证码
            String sub_captcha=request.getParameter("captcha");//获取表单提交的验证码
            try {
                if(FLAG==true){//账号锁定
                    throw new LockUsernameException();
                }
                else if(captcha.equalsIgnoreCase(sub_captcha)){//判断验证码是否正确
                    Subject subject = this.getSubject(request, response);
                    subject.login(token);
                    return this.onLoginSuccess(token, subject, request, response);
                }else{
                    throw new CaptchaException();
                }
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String username=(String) token.getPrincipal();//获取当前失败的主体名
        AtomicInteger loginCount=passwordRetryCache.get(username);
        if(!StringUtils.isEmpty(loginCount)){//如果缓存中有该账号的失败计数.清除他
            FLAG=false;//清除返回 锁定账号 的信息
            passwordRetryCache.remove(username);
        }
        if (isAjax(request)){//判断请求是否是ajax
            String message="{\"success\":\"true\",\"message\":\"成功\"}";
            request.setAttribute("loginMessage",message);//将登录验证状态存入
        }
        return true;
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request,response,getSuccessUrl(),null,true);//成功后跳转的地址
    }

    /**
     * 异步判断
     */
    private Boolean isAjax(ServletRequest request) {
        return ("XMLHttpRequest").equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

}
