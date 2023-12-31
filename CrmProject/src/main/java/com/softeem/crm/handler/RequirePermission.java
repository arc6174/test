package com.softeem.crm.handler;

import java.lang.annotation.*;

//自定义注解类
@Target({ElementType.METHOD})//作用在方法上面
@Retention(RetentionPolicy.RUNTIME)//运行时
@Documented
public @interface RequirePermission {
    String code() default "";
}
