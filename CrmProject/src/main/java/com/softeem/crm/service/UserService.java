package com.softeem.crm.service;

import com.softeem.crm.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.softeem.crm.vo.UserModel;
import com.softeem.crm.vo.UserQuery;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_user】的数据库操作Service
* @createDate 2022-12-27 14:38:30
*/
public interface UserService extends IService<User> {

    UserModel login(String userName, String userPwd);

    @Transactional(propagation = Propagation.REQUIRED)
    void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPassword);

    List<Map> queryAllSales();

    Map<String, Object> userList(UserQuery userQuery);

    void saveUser(User user,String roleIds);
    void updateUser(User user,String roleIds);

    void deleteUserByIds(Integer[] ids);
}
