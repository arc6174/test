package com.softeem.crm.mapper;

import com.softeem.crm.pojo.Module;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softeem.crm.vo.TreeDto;

import java.util.List;

/**
* @author 30468
* @description 针对表【t_module】的数据库操作Mapper
* @createDate 2022-12-27 14:38:29
* @Entity com.softeem.crm.pojo.Module
*/
public interface ModuleMapper extends BaseMapper<Module> {


    List<TreeDto> queryAllModules();
}




