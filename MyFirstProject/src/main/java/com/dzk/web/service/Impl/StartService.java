package com.dzk.web.service.Impl;

import com.dzk.common.utils.WebUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dzk
 * @date 2020/7/27 1:52
 * @description
 */
@Service
public class StartService implements ApplicationListener<ContextRefreshedEvent> {
    /*
    * 至于为什么先做判断，因为Spring存在两个容器，一个是root application context ,另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）。这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免上面提到的问题，我们可以只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理。*/
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {//保证只执行一次
            //需要执行的方法
            WebUtils.setStartTime(new Date());//设置系统启动时间
        }
    }
}
