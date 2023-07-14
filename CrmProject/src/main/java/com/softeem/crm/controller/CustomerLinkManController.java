package com.softeem.crm.controller;


import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.pojo.CustomerLinkman;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.CustomerLinkmanService;
import com.softeem.crm.service.CustomerService;
import com.softeem.crm.vo.CustomerLinkmanQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;



@RequestMapping("linkman")
@Controller
public class CustomerLinkManController extends BaseController {

    @Resource
    private CustomerLinkmanService linkmanService ;


    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerOrdersByParams(CustomerLinkmanQuery customerLinkman) {
        return linkmanService.queryByParamsForTable(customerLinkman);
    }

    @RequestMapping("addOrUpdatePage")
    public String addOrUpdatePage(Integer cusId,Integer id, Model model){
        if(null !=id){
            model.addAttribute("linkman",linkmanService.getById(id));
        }else {
            model.addAttribute("cusId",cusId);
        }
        return "linkman/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(CustomerLinkman customerLinkman) {
        linkmanService.save(customerLinkman);
        return success("联系人添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(CustomerLinkman customerLinkman) {
        linkmanService.updateById(customerLinkman);
        return success("联系人更新成功");
    }


    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteBatch(Integer[] ids){
        linkmanService.deleteUserByIds(ids);
        return success("联系人记录删除成功");
    }
}
