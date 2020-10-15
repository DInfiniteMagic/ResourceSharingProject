package com.dzk.common.annotation;

import java.lang.annotation.*;

/**
 * @author dzk
 * @date 2020/7/27 18:56
 * @description 标记需要做业务日志的方法
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperateLogAnnotation {
    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";
    /**
     * 业务模块类型,例如:"01菜单管理"
     */
    String moduleType()  default "";

    /**
     * 业务模块功能名称,例如:"新增菜单功能"
     */
    String functionName()  default "";

    /**
     * 操作日志内容,例如:"修改菜单"
     */
    String operateContent() default "";


}

