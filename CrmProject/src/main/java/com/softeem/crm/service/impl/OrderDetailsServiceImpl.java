package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.pojo.OrderDetails;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.OrderDetailsService;
import com.softeem.crm.mapper.OrderDetailsMapper;
import com.softeem.crm.vo.OrderDetailsQuery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_order_details】的数据库操作Service实现
* @createDate 2022-12-27 14:38:29
*/
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetails>
    implements OrderDetailsService{

    @Override
    public Map<String, Object> queryByParamsForTable(OrderDetailsQuery orderDetailsQuery) {
        Page<OrderDetails> page = new Page<>(orderDetailsQuery.getPage(),orderDetailsQuery.getLimit());
        LambdaQueryWrapper<OrderDetails> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderDetails::getOrderId,orderDetailsQuery.getOrderId())
                .orderByDesc(OrderDetails::getId);
        Page<OrderDetails> orderDetailsPage = this.baseMapper.selectPage(page,lambdaQueryWrapper);
       Map<String, Object> result = new HashMap<>();
        result.put("count", orderDetailsPage.getTotal());
        result.put("data", orderDetailsPage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}




