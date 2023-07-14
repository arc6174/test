package com.softeem.crm.service;

import com.softeem.crm.pojo.CusDevPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.CusDevPlanQuery;

import java.util.Map;

/**
* @author 30468
* @description 针对表【t_cus_dev_plan】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface CusDevPlanService extends IService<CusDevPlan> {

    Map<String, Object> queryByParamsForTable(CusDevPlanQuery cusDevPlanQuery);

    void saveOrUpdateCusDevPlan(CusDevPlan cusDevPlan);

//    void updateSaleChanceDevResult(Integer id, Integer devResult);

//    void delCusDevPlan(Integer[] ids);
}
