package com.softeem.crm.controller;

import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.exceptions.ParamsException;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.UserService;
import com.softeem.crm.utils.LoginUserUtil;
import com.softeem.crm.vo.UserModel;
import com.softeem.crm.vo.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(("user"))
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd) {
        UserModel userModel = userService.login(userName, userPwd);
        return success("用户登录成功", userModel);
    }

    @RequestMapping("updatePassword")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);
        return success("密码更新成功");
    }

    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "password";
    }

    @RequestMapping("logout")
    public String logout(HttpServletResponse response, HttpServletRequest request,
                         @CookieValue(required = false) Cookie trueName,
                         @CookieValue(required = false) Cookie userIdStr,
                         @CookieValue(required = false) Cookie userName) {
        // 清除 cookie
        if (trueName != null) {
            trueName.setMaxAge(0);//立刻失效
            trueName.setPath(request.getContextPath()); //项目所有目录均有效，这句很关键，否则不敢保证删除
            response.addCookie(trueName);//执行
        }
        if (userIdStr != null) {
            userIdStr.setMaxAge(0);//立刻失效
            userIdStr.setPath(request.getContextPath());
            response.addCookie(userIdStr);
        }
        if (userName != null) {
            userName.setMaxAge(0);//立刻失效
            userName.setPath(request.getContextPath());
            response.addCookie(userName);
        }
        //返回 登陆页
        return "redirect:/index";
    }

    @PostMapping("queryAllCustomerManager")
    @ResponseBody
    public List<Map> queryAllSales() {
        return userService.queryAllSales();
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> userList(UserQuery userQuery) {
        return userService.userList(userQuery);
    }


    @RequestMapping("index")
    public String index() {
        return "/user/user";
    }

    @RequestMapping("addOrUpdateUserPage")
    public String addUserPage(Integer id, Model model) {
        if (null != id) {
            model.addAttribute("user", userService.getById(id));
        }
        return "user/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(User user,String roleIds) {
        userService.saveUser(user,roleIds);
        return success("用户添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(User user,String roleIds) {
        userService.updateUser(user,roleIds);
        return success("用户更新成功");
    }


    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteBatch(Integer[] ids){
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功");
    }
}