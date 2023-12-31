package com.softeem.crm.controller;

import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.pojo.CustomerServe;
import com.softeem.crm.service.CustomerServeService;
import com.softeem.crm.utils.LoginUserUtil;
import com.softeem.crm.vo.CustomerServeQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    // 服务管理页面转发方法
    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type) {
        if (type == 1) {
            return "customerServe/customer_serve";
        } else if (type == 2) {
            return "customerServe/customer_serve_assign";
        } else if (type == 3) {
            return "customerServe/customer_serve_proce";
        } else if (type == 4) {
            return "customerServe/customer_serve_feed_back";
        } else if (type == 5) {
            return "customerServe/customer_serve_archive";
        } else {
            return "";
        }
    }


    // 服务信息列表展示
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerServeByParams(Integer flag, HttpServletRequest request, CustomerServeQuery customerServeQuery) {
        if (null != flag && flag == 1) {
            customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return customerServeService.queryByParamsForTable(customerServeQuery);
    }

    // 服务添加页面转发
    @RequestMapping("addCustomerServePage")
    public String addCustomerServePage() {
        return "customerServe/customer_serve_add";
    }

    //服务分配页面转发
    @RequestMapping("addCustomerServeAssignPage")
    public String addCustomerServeAssignPage(Integer id, Model model) {
        model.addAttribute("customerServe", customerServeService.getById(id));
        return "customerServe/customer_serve_assign_add";
    }

    // 服务反馈页面转发
    @RequestMapping("addCustomerServeBackPage")
    public String addCustomerServeBackPage(Integer id, Model model) {
        model.addAttribute("customerServe", customerServeService.getById(id));
        return "customerServe/customer_serve_feed_back_add";
    }

    // 服务处理页面转发
    @RequestMapping("addCustomerServeProcePage")
    public String addCustomerServeProcePage(Integer id, Model model) {
        model.addAttribute("customerServe", customerServeService.getById(id));
        return "customerServe/customer_serve_proce_add";
    }


    //服务添加 分配  处理 归档处理
    @RequestMapping("saveOrUpdateCustomerServe")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerServe(CustomerServe customerServe) {
        customerServeService.saveOrUpdateCustomerServe(customerServe);
        return success("操作成功");
    }
}