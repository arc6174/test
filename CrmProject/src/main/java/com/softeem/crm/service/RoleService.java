package com.softeem.crm.service;

import com.softeem.crm.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.RoleQuery;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_role】的数据库操作Service
* @createDate 2022-12-27 14:38:30
*/
public interface RoleService extends IService<Role> {

    List<Map> queryAllRoles(Integer userId);

    Map<String, Object> queryByParamsForTable(RoleQuery roleQuery);

    void saveRole(Role role);
    void updateRole(Role role);

    void addGrant(Integer[] mids, Integer roleId);
}
