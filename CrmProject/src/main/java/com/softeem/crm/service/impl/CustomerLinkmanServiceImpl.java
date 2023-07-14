package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.pojo.CustomerLinkman;
import com.softeem.crm.pojo.CustomerOrder;
import com.softeem.crm.service.CustomerLinkmanService;
import com.softeem.crm.mapper.CustomerLinkmanMapper;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.vo.CustomerLinkmanQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 30468
 * @description 针对表【t_customer_linkman】的数据库操作Service实现
 * @createDate 2022-12-27 14:38:29
 */
@Service
public class CustomerLinkmanServiceImpl extends ServiceImpl<CustomerLinkmanMapper, CustomerLinkman>
        implements CustomerLinkmanService {

    @Override
    public Map<String, Object> queryByParamsForTable(CustomerLinkmanQuery customerLinkman) {
        Page<CustomerLinkman> page = new Page<>(customerLinkman.getPage(), customerLinkman.getLimit());
        LambdaQueryWrapper<CustomerLinkman> customerLinkmanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        customerLinkmanLambdaQueryWrapper
                .eq(CustomerLinkman::getCusId, customerLinkman.getCusId())
                .like(StringUtils.isNotBlank(customerLinkman.getLinkName()), CustomerLinkman::getLinkName, customerLinkman.getLinkName())
                .orderByDesc(CustomerLinkman::getId);
        Page<CustomerLinkman> customerLinkmanPage = this.baseMapper.selectPage(page, customerLinkmanLambdaQueryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("count", customerLinkmanPage.getTotal());
        result.put("data", customerLinkmanPage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的用户记录!");
        AssertUtil.isTrue(this.baseMapper.deleteBatchIds(Arrays.asList(ids))<ids.length,"联系人记录删除失败!");
    }
}




