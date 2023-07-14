package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.mapper.CustomerMapper;
import com.softeem.crm.mapper.CustomerOrderMapper;
import com.softeem.crm.pojo.Customer;
import com.softeem.crm.pojo.CustomerLoss;
import com.softeem.crm.pojo.CustomerOrder;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.CustomerLossService;
import com.softeem.crm.mapper.CustomerLossMapper;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.utils.PhoneUtil;
import com.softeem.crm.vo.CustomerLossQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 30468
 * @description 针对表【t_customer_loss】的数据库操作Service实现
 * @createDate 2022-12-27 14:38:29
 */
@Service
public class CustomerLossServiceImpl extends ServiceImpl<CustomerLossMapper, CustomerLoss>
        implements CustomerLossService {


    @Override
    public Map<String, Object> queryByParamsForTable(CustomerLossQuery customerLossQuery) {
        Page<CustomerLoss> page = new Page<>(customerLossQuery.getPage(), customerLossQuery.getLimit());
        LambdaQueryWrapper<CustomerLoss> queryWrapper = Wrappers.<CustomerLoss>lambdaQuery()
                .eq(StringUtils.isNotBlank(customerLossQuery.getCusNo()), CustomerLoss::getCusNo, customerLossQuery.getCusNo())
                .like(StringUtils.isNotBlank(customerLossQuery.getCusName()), CustomerLoss::getCusName, customerLossQuery.getCusName())
                .eq(null != customerLossQuery.getState(), CustomerLoss::getState, customerLossQuery.getState());
        Page<CustomerLoss>  customerLossPage = this.baseMapper.selectPage(page,queryWrapper);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("count", customerLossPage.getTotal());
        result.put("data", customerLossPage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateState(CustomerLoss loss) {
        AssertUtil.isTrue(null==loss.getId()||null==this.getById(loss.getId()),"待修改的记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(loss.getLossReason()), "流失原因不能为空!");
        loss.setState(1);
        loss.setConfirmLossTime(new Date());
        AssertUtil.isTrue(!this.updateById(loss),"角色记录更新失败!");
    }

}




