package com.softeem.crm.service;

import com.softeem.crm.pojo.CustomerLinkman;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CustomerLinkmanQuery;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_linkman】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CustomerLinkmanService extends IService<CustomerLinkman> {

    Map<String, Object> queryByParamsForTable(CustomerLinkmanQuery customerLinkman);

    void deleteUserByIds(Integer[] ids);
}
