package com.dzk.web.security.filter;

import com.dzk.common.utils.ForceQuitMap;
import com.dzk.common.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.processing.Filer;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dzk
 * @date 2020/7/30 23:49
 * @description 处理强制退出时因为rememberMe 导致重新登录问题
 */
public class RememberAuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         Subject subject =SecurityUtils.getSubject();
        //如果 isAuthenticated 为 false 证明不是登录过的，同时 isRememberd 为true 证明是没登陆直接通过记住我功能进来的
       // System.err.println("--------"+subject.getSession().getTimeout());
         //获取session看看是不是空的
         Session session = subject.getSession();
         String username=(String) SecurityUtils.getSubject().getPrincipal();
        if(!subject.isAuthenticated() && subject.isRemembered()){
            // 用于处理单一登录问题，由于使用了rememberMe功能，所以导致移除session用户身份仍然短期有效，这边做登出处理
            //System.err.println("true".equals(session.getAttribute("forceQuit"))+"---"+session.getAttribute("forceQuit"));
            //System.err.println("fil:"+session.getId());
            if("forceQuit".equals(ForceQuitMap.getForceUserMap().get((String) SecurityUtils.getSubject().getPrincipal()))){
                subject.logout();
                ForceQuitMap.getForceUserMap().remove(username);
            }
        }
        if(subject.isAuthenticated()){
            if("forceQuit".equals(ForceQuitMap.getForceUserMap().get((String) SecurityUtils.getSubject().getPrincipal()))){
                subject.logout();
                ForceQuitMap.getForceUserMap().remove(username);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
