package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.pojo.Permission;
import com.softeem.crm.service.PermissionService;
import com.softeem.crm.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30468
* @description 针对表【t_permission】的数据库操作Service实现
* @createDate 2022-12-27 14:38:30
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public List<String> queryUserHasRolesHasPermissions(Integer userId) {
        return permissionMapper.queryUserHasRolesHasPermissions(userId);
    }
}




