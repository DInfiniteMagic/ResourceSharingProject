package com.dzk.common.config;

import org.apache.shiro.io.ResourceUtils;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author dzk
 * @date 2020/7/5 11:12
 * @description
 */
@Configuration
public class EhcacheConfig {
    @Bean("cacheManager")
    public EhCacheManagerFactoryBean getEcache(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean=new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setShared(true);
        Resource resource=new ClassPathResource("ehcache.xml");
        ehCacheManagerFactoryBean.setConfigLocation(resource);
        return ehCacheManagerFactoryBean;
    }

}
