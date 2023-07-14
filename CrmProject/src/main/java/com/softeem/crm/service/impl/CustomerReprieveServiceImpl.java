package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.pojo.CustomerReprieve;
import com.softeem.crm.pojo.CustomerReprieve;
import com.softeem.crm.service.CustomerReprieveService;
import com.softeem.crm.mapper.CustomerReprieveMapper;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.vo.CustomerReprieveQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_reprieve】的数据库操作Service实现
* @createDate 2022-12-27 14:38:29
*/
@Service
public class CustomerReprieveServiceImpl extends ServiceImpl<CustomerReprieveMapper, CustomerReprieve>
    implements CustomerReprieveService{

    @Override
    public Map<String, Object> queryByParamsForTable(CustomerReprieveQuery customerReprieveQuery) {
        Page<CustomerReprieve> page = new Page<>(customerReprieveQuery.getPage(),customerReprieveQuery.getLimit());
        LambdaQueryWrapper<CustomerReprieve> queryWrapper = Wrappers.<CustomerReprieve>lambdaQuery()
                .eq(CustomerReprieve::getLossId,customerReprieveQuery.getLossId());
        Page<CustomerReprieve> customerReprievePage = this.baseMapper.selectPage(page,queryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("count", customerReprievePage.getTotal());
        result.put("data", customerReprievePage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的办法!");
        AssertUtil.isTrue(this.baseMapper.deleteBatchIds(Arrays.asList(ids))<ids.length,"办法删除失败!");
    }
}




