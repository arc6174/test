package com.softeem.crm.service;

import com.softeem.crm.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CustomerQuery;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CustomerService extends IService<Customer> {

    Map<String, Object> queryByParamsForTable(CustomerQuery customerQuery);

    @Transactional(propagation = Propagation.REQUIRED)
    void saveCustomer(Customer customer);

    @Transactional(propagation = Propagation.REQUIRED)
    void updateCustomer(Customer customer);

    @Transactional(propagation = Propagation.REQUIRED)
    void deleteCustomer(Integer cid);

    @Transactional(propagation = Propagation.REQUIRED)
    void updateCustomerState();

    Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery);

    // 折线图数据处理
    Map<String,Object> countCustomerMake();

    Map<String, Object> countCustomerMake02();
}
