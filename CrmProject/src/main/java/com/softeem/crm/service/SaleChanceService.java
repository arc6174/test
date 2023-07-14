package com.softeem.crm.service;

import com.softeem.crm.base.BaseQuery;
import com.softeem.crm.pojo.SaleChance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.vo.SaleChanceQuery;

import java.util.Map;

/**
 * @author 30468
 * @description 针对表【t_sale_chance】的数据库操作Service
 * @createDate 2022-12-27 14:38:30
 */
public interface SaleChanceService extends IService<SaleChance> {
    Map<String, Object> queryByParamsForTable(SaleChanceQuery saleChanceQuery);

    void saveSaleChance(SaleChance saleChance);

    void updateSaleChance(SaleChance saleChance);

    void deleteSaleChancesByIds(Integer[] ids);
    void updateSaleChanceDevResult(Integer id, Integer devResult);
}
