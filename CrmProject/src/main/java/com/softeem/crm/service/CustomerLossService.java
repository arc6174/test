package com.softeem.crm.service;

import com.softeem.crm.pojo.CustomerLoss;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CustomerLossQuery;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_loss】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CustomerLossService extends IService<CustomerLoss> {

    Map<String, Object> queryByParamsForTable(CustomerLossQuery customerLossQuery);

    void updateState(CustomerLoss loss);
}
