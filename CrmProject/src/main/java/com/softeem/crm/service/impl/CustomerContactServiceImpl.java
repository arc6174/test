package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.pojo.CustomerContact;
import com.softeem.crm.pojo.CustomerContact;
import com.softeem.crm.service.CustomerContactService;
import com.softeem.crm.mapper.CustomerContactMapper;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.vo.CustomerContactQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_contact】的数据库操作Service实现
* @createDate 2022-12-27 14:38:29
*/
@Service
public class CustomerContactServiceImpl extends ServiceImpl<CustomerContactMapper, CustomerContact>
    implements CustomerContactService{

    @Override
    public Map<String, Object> queryByParamsForTable(CustomerContactQuery contactQuery) {
        Page<CustomerContact> page = new Page<>(contactQuery.getPage(), contactQuery.getLimit());
        LambdaQueryWrapper<CustomerContact> contactLambdaQueryWrapper = new LambdaQueryWrapper<>();
        contactLambdaQueryWrapper
                .eq(CustomerContact::getCusId, contactQuery.getCusId())
                .like(StringUtils.isNotBlank(contactQuery.getAddress()), CustomerContact::getAddress, contactQuery.getAddress())
                .orderByDesc(CustomerContact::getId);
        Page<CustomerContact> CustomerContactPage = this.baseMapper.selectPage(page, contactLambdaQueryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("count", CustomerContactPage.getTotal());
        result.put("data", CustomerContactPage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的记录!");
        AssertUtil.isTrue(this.baseMapper.deleteBatchIds(Arrays.asList(ids))<ids.length,"记录删除失败!");
    }
}




