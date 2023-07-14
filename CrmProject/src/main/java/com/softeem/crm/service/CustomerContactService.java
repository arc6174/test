package com.softeem.crm.service;

import com.softeem.crm.pojo.CustomerContact;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CustomerContactQuery;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_contact】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CustomerContactService extends IService<CustomerContact> {

    Map<String, Object> queryByParamsForTable(CustomerContactQuery contactQuery);

    void deleteUserByIds(Integer[] ids);
}
