package com.softeem.crm.handler;

import com.softeem.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TaskService {

    @Resource
    private CustomerService customerService;


    @Scheduled(cron = "0/2 * * * * ?")
    public void job(){
        System.out.println("定时任务开始执行-->"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        customerService.updateCustomerState();
    }
}