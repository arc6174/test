package com.softeem.crm.service;

import com.softeem.crm.pojo.OrderDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.OrderDetailsQuery;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_order_details】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface OrderDetailsService extends IService<OrderDetails> {

    Map<String, Object> queryByParamsForTable(OrderDetailsQuery orderDetailsQuery);
}
