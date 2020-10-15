package com.dzk.web.service.Impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dzk
 * @date 2020/7/16 0:36
 * @description
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST,proxyMode = ScopedProxyMode.TARGET_CLASS)//为每一个请求创建一个bean
@Data
@NoArgsConstructor
public class RegisterCount {
    private AtomicInteger atomicIntegerSendMail=new AtomicInteger(0);//请求发送邮箱的次数
    private AtomicInteger atomicIntegerModifyMail=new AtomicInteger(0);//请求修改邮箱的次数
    private AtomicInteger atomicIntegerActivationCode=new AtomicInteger(0);//请求激活码次数
}
