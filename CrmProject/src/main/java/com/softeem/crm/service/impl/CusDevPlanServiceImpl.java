package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.mapper.SaleChanceMapper;
import com.softeem.crm.pojo.CusDevPlan;
import com.softeem.crm.pojo.SaleChance;
import com.softeem.crm.service.CusDevPlanService;
import com.softeem.crm.mapper.CusDevPlanMapper;
import com.softeem.crm.service.SaleChanceService;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.vo.CusDevPlanQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 30468
 * @description 针对表【t_cus_dev_plan】的数据库操作Service实现
 * @createDate 2022-12-27 14:38:29
 */
@Service
public class CusDevPlanServiceImpl extends ServiceImpl<CusDevPlanMapper, CusDevPlan>
        implements CusDevPlanService {

    @Resource
    private SaleChanceMapper saleChanceMapper;
    @Override
    public Map<String, Object> queryByParamsForTable(CusDevPlanQuery cusDevPlanQuery) {
        Page<CusDevPlan> page = new Page<>(cusDevPlanQuery.getPage(), cusDevPlanQuery.getLimit());
        LambdaQueryWrapper<CusDevPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CusDevPlan::getSaleChanceId, cusDevPlanQuery.getSid()).orderByDesc(CusDevPlan::getId);
        Page<CusDevPlan> cusDevPlanPage = this.baseMapper.selectPage(page, queryWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("count", cusDevPlanPage.getTotal());
        result.put("data", cusDevPlanPage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Transactional
    public void saveOrUpdateCusDevPlan(CusDevPlan cusDevPlan) {
        checkParams(cusDevPlan.getSaleChanceId(), cusDevPlan.getPlanItem(), cusDevPlan.getPlanDate());
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(!this.saveOrUpdate(cusDevPlan), "计划项操作失败!");
    }


    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(null == saleChanceId || null == saleChanceMapper.selectById(saleChanceId), "请设置营销机会id");
        AssertUtil.isTrue(StringUtils.isBlank(planItem), "请输入计划项内容 !");
        AssertUtil.isTrue(null == planDate, "请指定计划项日期!");
    }



//    @Transactional
//    @Override
//    public void delCusDevPlan(Integer[] ids) {
//        AssertUtil.isTrue(null == ids || ids.length == 0, "请选择待删除的记录!");
//        AssertUtil.isTrue(this.baseMapper.deleteBatchIds(Arrays.asList(ids)) < ids.length, "计划项记录删除失败!");
//    }

}




