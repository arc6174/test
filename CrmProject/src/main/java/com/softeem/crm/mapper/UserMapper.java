package com.softeem.crm.mapper;

import com.softeem.crm.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2022-12-27 14:38:30
* @Entity com.softeem.crm.pojo.User
*/
@Mapper
@SuppressWarnings("all")
public interface UserMapper extends BaseMapper<User> {
    List<Map> queryAllSales();

}




