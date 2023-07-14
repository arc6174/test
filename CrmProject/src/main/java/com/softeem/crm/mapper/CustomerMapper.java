package com.softeem.crm.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softeem.crm.pojo.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softeem.crm.vo.CustomerQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer】的数据库操作Mapper
* @createDate 2022-12-27 14:38:29
* @Entity com.softeem.crm.pojo.Customer
*/
@SuppressWarnings("all")
public interface CustomerMapper extends BaseMapper<Customer> {

    List<Customer> queryLossCustomers();
    IPage<Map<String,Object>> queryCustomerContributionByParams(IPage page, @Param("csq") CustomerQuery customerQuery);

    List<Map<String, Object>> countCustomerMake();
}




