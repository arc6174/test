package com.softeem.crm.service;

import com.softeem.crm.pojo.Module;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.TreeDto;

import java.util.List;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_module】的数据库操作Service
* @createDate 2022-12-27 14:38:29
*/
public interface ModuleService extends IService<Module> {

    List<TreeDto> queryAllModules();

    List<TreeDto> queryAllModules02(Integer roleId);

    Map<String, Object> moduleList();

    void saveModule(Module module);

    List<Map<String, Object>> queryAllModulesByGrade(Integer grade);

    void updateModule(Module module);

    void deleteModuleById(Integer id);
}
