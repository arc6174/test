package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.base.BaseQuery;
import com.softeem.crm.enums.DevResult;
import com.softeem.crm.enums.StateStatus;
import com.softeem.crm.mapper.UserMapper;
import com.softeem.crm.pojo.SaleChance;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.SaleChanceService;
import com.softeem.crm.mapper.SaleChanceMapper;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.utils.PhoneUtil;
import com.softeem.crm.vo.SaleChanceQuery;
import com.softeem.crm.vo.SaleChanceVo;
import javafx.scene.transform.Scale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author 30468
 * @description 针对表【t_sale_chance】的数据库操作Service实现
 * @createDate 2022-12-27 14:38:30
 */
@Service
public class SaleChanceServiceImpl extends ServiceImpl<SaleChanceMapper, SaleChance>
        implements SaleChanceService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryByParamsForTable(SaleChanceQuery saleChanceQuery) {
        Page<SaleChance> pageParam = new Page<>(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        LambdaQueryWrapper<SaleChance> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(saleChanceQuery.getCustomerName()), SaleChance::getCustomerName, saleChanceQuery.getCustomerName())
                .eq(StringUtils.isNotBlank(saleChanceQuery.getCreateMan()), SaleChance::getCreateMan, saleChanceQuery.getCreateMan())
                .eq(StringUtils.isNotBlank(saleChanceQuery.getState()), SaleChance::getState, saleChanceQuery.getState())
                .eq(StringUtils.isNotBlank(saleChanceQuery.getAggsinMan()), SaleChance::getAssignMan, saleChanceQuery.getAggsinMan())
                .eq(saleChanceQuery.getDevResult() != null, SaleChance::getDevResult, saleChanceQuery.getDevResult())
                .orderByDesc(SaleChance::getId);
        Page<SaleChance> saleChancePage = this.baseMapper.selectPage(pageParam, lambdaQueryWrapper);
        List<SaleChance> saleChances = saleChancePage.getRecords();
        ArrayList<SaleChanceVo> chanceVoArrayList = new ArrayList<>();
        for (SaleChance saleChance : saleChances) {
            SaleChanceVo saleChanceVo = new SaleChanceVo();
            BeanUtils.copyProperties(saleChance, saleChanceVo);
            User user = userMapper.selectById(saleChance.getAssignMan());
            if (user != null) {
                saleChanceVo.setUname(user.getUserName());
            }
            chanceVoArrayList.add(saleChanceVo);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("count", saleChancePage.getTotal());
        result.put("data", chanceVoArrayList);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Transactional
    @Override
    public void saveSaleChance(SaleChance saleChance) {
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(0);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(!this.save(saleChance), "机会数据添加失败!");
    }

    @Transactional
    @Override
    public void updateSaleChance(SaleChance saleChance) {
        AssertUtil.isTrue(null == saleChance.getId(), "待更新记录不存在!");
        SaleChance temp = this.baseMapper.selectById(saleChance.getId());
//        saleChance.setAssignTime(temp.getAssignTime());
        AssertUtil.isTrue(null == temp, "待更新记录不存在!");
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        if (StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        } else if (StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())) {
            saleChance.setAssignMan("");
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        } else if (StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isNotBlank(saleChance.getAssignMan())) {
            if (!temp.getAssignMan().equals(saleChance.getAssignMan())) {
                saleChance.setAssignTime(new Date());
            }
        }

//        AssertUtil.isTrue(this.baseMapper.updateById(saleChance) < 1, "机会数据更新失败!");
        int update = this.baseMapper.update(saleChance,
                Wrappers.<SaleChance>lambdaUpdate().
                        set(SaleChance::getAssignTime, saleChance.getAssignTime()).
                        eq(SaleChance::getId, saleChance.getId()));
        AssertUtil.isTrue(update < 1, "机会数据更新失败!");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChancesByIds(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length == 0, "请选择待删除的机会数据!");
        AssertUtil.isTrue(this.baseMapper.deleteBatchIds(Arrays.asList(ids))<ids.length, "机会数据删除失败!");
    }

    @Transactional
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        AssertUtil.isTrue( null ==id,"待更新记录不存在!");
        SaleChance temp = this.getById(id);
        AssertUtil.isTrue( null ==temp,"待更新记录不存在!");
        temp.setDevResult(devResult);
        temp.setUpdateDate(new Date());
        AssertUtil.isTrue(!this.updateById(temp),"机会数据更新失败!");
    }

    /*
      表单基本参数校验
    */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "请输入客户名!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输入联系人!");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输入联系电话!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)), "手机号格式不合法!");
    }

}




