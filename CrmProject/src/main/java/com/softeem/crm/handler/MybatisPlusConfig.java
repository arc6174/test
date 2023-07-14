package com.softeem.crm.handler;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.softeem.mybatis_plus.mapper")//可以将主类中的注解移到此处
public class MybatisPlusConfig {
    //添加分页插件
    //<bean id='mybatisPlusInterceptor' class='com.baomidou.mybatis_plus.extension.plugins.MybatisPlusInterceptor'>
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//分页插件
//        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//乐观锁
        return interceptor;
    }
}