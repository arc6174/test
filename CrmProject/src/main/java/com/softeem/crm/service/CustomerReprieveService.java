package com.softeem.crm.service;

import com.softeem.crm.pojo.CustomerReprieve;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CustomerReprieveQuery;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_reprieve】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CustomerReprieveService extends IService<CustomerReprieve> {

    Map<String, Object> queryByParamsForTable(CustomerReprieveQuery customerReprieveQuery);

    void deleteUserByIds(Integer[] ids);
}
