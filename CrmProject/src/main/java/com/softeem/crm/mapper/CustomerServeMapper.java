package com.softeem.crm.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.softeem.crm.pojo.CustomerServe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softeem.crm.vo.CustomerQuery;
import com.softeem.crm.vo.CustomerServeQuery;
import com.softeem.crm.vo.CustomerServeVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 30468
 * @description 针对表【t_customer_serve】的数据库操作Mapper
 * @createDate 2022-12-27 14:38:29
 * @Entity com.softeem.crm.pojo.CustomerServe
 */
public interface CustomerServeMapper extends BaseMapper<CustomerServe> {
    IPage<CustomerServeVo> selectByParams(IPage<CustomerServeVo> page, @Param("customerServeQuery") CustomerServeQuery customerServeQuery);
}




