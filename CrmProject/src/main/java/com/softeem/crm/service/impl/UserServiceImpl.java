package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.mapper.RoleMapper;
import com.softeem.crm.mapper.UserRoleMapper;
import com.softeem.crm.pojo.CusDevPlan;
import com.softeem.crm.pojo.UserRole;
import com.softeem.crm.service.UserRoleService;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.utils.Md5Util;
import com.softeem.crm.utils.PhoneUtil;
import com.softeem.crm.utils.UserIDBase64;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.UserService;
import com.softeem.crm.mapper.UserMapper;
import com.softeem.crm.vo.UserModel;
import com.softeem.crm.vo.UserQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 30468
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2022-12-27 14:38:30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserRoleService userRoleService;
    @Override
    public UserModel login(String userName, String userPwd) {
        checkLoginParams(userName, userPwd);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, userName);
        User user = this.baseMapper.selectOne(lambdaQueryWrapper);
        AssertUtil.isTrue(null == user || user.getIsValid() == 1, "用户已注销或不存在!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))), "密码错误!");
        return buildUserModelInfo(user);//调用buildUserModelInfo()方法来构建UserModel对象
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        checkParams(userId, oldPassword, newPassword, confirmPassword);
        User user = this.baseMapper.selectById(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        int i = this.baseMapper.updateById(user);
        AssertUtil.isTrue(i < 1, "密码更新失败!");
    }

    @Override
    public List<Map> queryAllSales() {
        return this.baseMapper.queryAllSales();
    }

    @Override
    public Map<String, Object> userList(UserQuery userQuery) {
        Page<User> page = new Page<>(userQuery.getPage(), userQuery.getLimit());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(userQuery.getUserName()), User::getUserName, userQuery.getUserName())
                .eq(StringUtils.isNotBlank(userQuery.getPhone()), User::getPhone, userQuery.getPhone())
                .eq(StringUtils.isNotBlank(userQuery.getEmail()), User::getEmail, userQuery.getEmail())
                .orderByDesc(User::getId);
        Page<User> userPage = this.baseMapper.selectPage(page, lambdaQueryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("count", userPage.getTotal());
        result.put("data", userPage.getRecords());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user,String roleIds) {
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, user.getUserName());
        User temp = this.baseMapper.selectOne(lambdaQueryWrapper);
        AssertUtil.isTrue(null != temp && (temp.getIsValid() == 0), "该用户已存在!");
        user.setIsValid(0);
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(!this.save(user), "用户添加失败!");
        relaionUserRole(user.getId(), roleIds);
    }


    private void relaionUserRole(int useId, String roleIds) {
        /**
         * 用户角色分配
         *   原始角色不存在   添加新的角色记录
         *   原始角色存在     添加新的角色记录
         *   原始角色存在     清空所有角色
         *   原始角色存在     移除部分角色
         * 如何进行角色分配???
         *  如果用户原始角色存在  首先清空原始所有角色  添加新的角色记录到用户角色表
         */
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId,useId);
        int count = userRoleMapper.selectCount(lambdaQueryWrapper).intValue();
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.delete(lambdaQueryWrapper) != count, "用户角色分配失败!");
        }
        if (StringUtils.isNotBlank(roleIds)) {
            //重新添加新的角色
            List<UserRole> userRoles = new ArrayList<UserRole>();
            for (String s : roleIds.split(",")) {
                UserRole userRole = new UserRole();
                userRole.setUserId(useId);
                userRole.setRoleId(Integer.parseInt(s));
                userRoles.add(userRole);
            }
            AssertUtil.isTrue(!userRoleService.saveBatch(userRoles), "用户角色分配失败!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user,String roleIds) {
        AssertUtil.isTrue(null != user.getId() && (null == this.getById(user.getId())), "该用户已存在!");
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, user.getUserName());
        User temp = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (null != temp && temp.getIsValid() == 1) {
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())), "该用户已存在!");
        }
        AssertUtil.isTrue(!this.updateById(user), "用户更新失败!");
        relaionUserRole(user.getId(), roleIds);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除的用户记录!");
        AssertUtil.isTrue(this.baseMapper.deleteBatchIds(Arrays.asList(ids))<ids.length,"用户记录删除失败!");
    }

    /*
      基本参数校验
    */
    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(email), "请输入邮箱地址!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)), "手机号格式不合法!");
    }


    /**
     * 构建userModel对象的,此对象会传入到前端网页
     *
     * @param user
     * @return
     */
    private UserModel buildUserModelInfo(User user) {
        return new UserModel(UserIDBase64.encoderUserID(user.getId()), user.getUserName(), user.getTrueName());
    }

    /**
     * 查询用户名与密码是否为空。如果有一个为空就报异常
     *
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空!");
    }

    /**
     * 检查用户输入的旧密码，新密码是否达到要求
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        //通过user的主键id查询用户信息
        User user = this.baseMapper.selectById(userId);
        AssertUtil.isTrue(null == userId || null == user, "用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入原始密码!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "请输入确认密码!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "新密码输入不一致!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))), "原始密码不正确!");
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码不能与旧密码相同!");
    }
}




