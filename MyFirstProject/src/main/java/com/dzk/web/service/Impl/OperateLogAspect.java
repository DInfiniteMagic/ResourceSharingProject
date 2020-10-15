package com.dzk.web.service.Impl;

import com.dzk.common.annotation.OperateLogAnnotation;
import com.dzk.common.utils.WebUtils;
import com.dzk.web.entity.OperateLogEntity;
import com.dzk.web.repository.OperateLogRepository;
import org.apache.shiro.SecurityUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Date;


/**
 * @author dzk
 * @date 2020/7/27 19:02
 * @description 日志记录切面
 */
@Component
@Aspect
public class OperateLogAspect {
    @Autowired
    private OperateLogRepository operateLogRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
     /**
     * 通过AOP方式，拦解注解的日志
     */
    @Pointcut(value = "@annotation(com.dzk.common.annotation.OperateLogAnnotation)")
    public void logAspect() {
        System.err.println("-----------------------------------------------------------------------");
    }
   /**
     * 后置通知：如果需要访问其他建议类型的连接点上下文，则应使用JoinPoint参数类型而不是ProceedingJoinPoint。
   */
    @After("logAspect()")
    public void recordLog(JoinPoint point){
        try {
            this.handle((ProceedingJoinPoint) point);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日志记录出错!", e);
        }
    }
     /**
     * 获取拦截方法参数,处理日志
     * @param point
     * @throws Exception
     */
     private void handle(ProceedingJoinPoint point) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object[] params = point.getArgs();
        Object target = point.getTarget();
        Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        //如果当前用户未登录，不做日志
        String username= (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(username)) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress= WebUtils.getIpAddress(request);//获取ip地址
        /**
        // 拦截的实体类
        Object target = joinPoint.getTarget();
        // 拦截的方法名称
        String methodName = joinPoint.getSignature().getName();
        // 拦截的方法参数
        Object[] args = joinPoint.getArgs();
        // 拦截的参数类型
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        **/
        //获取注解日志内容
        OperateLogAnnotation annotation = method.getAnnotation(OperateLogAnnotation.class);
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }
        //如果涉及到修改,比对修改前后值的变化内容
        String logmsg ="";

        //记录操作日志
        OperateLogEntity operateLogEntity = getOperaLog( ipAddress,username, target, method, logmsg, params );
        operateLogRepository.insertOperateLog(operateLogEntity);//存入数据库
    }
      /**
     * 封装操作日志实体对象
     *
     * @Date 2017/3/30 18:45
     */
    public static OperateLogEntity getOperaLog(String ipAddress,String username, Object target, Method method, String msg, Object[] params) {
        String classPath = target.getClass().getName(); //类名称，含路径
        String classMethod = method.getName(); //方法名(英文)
        OperateLogAnnotation annotation = method.getAnnotation( OperateLogAnnotation.class );

        OperateLogEntity operaLog = new OperateLogEntity();
        operaLog.setId(0); //主键
        operaLog.setClassPath(classPath); //类名称，含路径
        operaLog.setClassMethod( classMethod ); //方法名(英文)
        operaLog.setIpHost(ipAddress); //IP地址
        operaLog.setOperateTime( new Date() ); //操作时间
        operaLog.setUserAccount(username); //用户帐号
        operaLog.setModuleType( annotation.moduleType() );//业务模块类型
        operaLog.setFunctionName( annotation.functionName() );//业务模块名称
        operaLog.setOperateContent( annotation.operateContent() ); //操作内容
        return operaLog;
    }

    /**
     * 实体对象转成Map
     * @param obj 实体对象
     * @return
     *//*
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }*/
}
