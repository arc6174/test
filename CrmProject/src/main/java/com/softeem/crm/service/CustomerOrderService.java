package com.softeem.crm.service;

import com.softeem.crm.pojo.CustomerOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CustomerOrderQuery;
import com.softeem.crm.vo.CustomerOrderVo;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_order】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CustomerOrderService extends IService<CustomerOrder> {

    Map<String, Object> queryByParamsForTable(CustomerOrderQuery customerOrderQuery);

    CustomerOrderVo queryOrderDetailByOrderId(Integer orderId);
}
