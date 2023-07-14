package com.softeem.crm.controller;

import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.pojo.CustomerContact;
import com.softeem.crm.service.CustomerContactService;
import com.softeem.crm.vo.CustomerContactQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("contact")
@Controller
public class CustomerContactController extends BaseController {
    @Resource
    private CustomerContactService customerContactService ;


    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerOrdersByParams(CustomerContactQuery contactQuery) {
        return customerContactService.queryByParamsForTable(contactQuery);
    }

    @RequestMapping("addOrUpdatePage")
    public String addOrUpdatePage(Integer cusId,Integer id, Model model){
        if(null !=id){
            model.addAttribute("contact",customerContactService.getById(id));
        }else {
            model.addAttribute("cusId",cusId);
        }
        return "contact/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(CustomerContact customerContact) {
        customerContactService.save(customerContact);
        return success("记录添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(CustomerContact customerContact) {
        customerContactService.updateById(customerContact);
        return success("记录更新成功");
    }


    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteBatch(Integer[] ids){
        customerContactService.deleteUserByIds(ids);
        return success("记录记录删除成功");
    }
}
