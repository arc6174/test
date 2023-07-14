package com.softeem.crm.controller;

import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.pojo.CustomerLinkman;
import com.softeem.crm.pojo.CustomerLoss;
import com.softeem.crm.pojo.Module;
import com.softeem.crm.service.CustomerLossService;
import com.softeem.crm.vo.CustomerLossQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;


    @RequestMapping("index")
    private String index() {
        return "/customerLoss/customer_loss";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery) {
        return customerLossService.queryByParamsForTable(customerLossQuery);
    }

    @RequestMapping("info")
    public String info(Integer id, Model model) {
        CustomerLoss customerLoss = customerLossService.getById(id);
        model.addAttribute("customerLoss",customerLoss);
        return "customerLoss/info";
    }

    @RequestMapping("info2")
    public String info2(Integer id, Model model) {
        CustomerLoss customerLoss = customerLossService.getById(id);
        model.addAttribute("customerLoss",customerLoss);
        return "customerLoss/info2";
    }

    @RequestMapping("confirm")
    public String confirm(Integer id,Model model) {
        CustomerLoss customerLoss = customerLossService.getById(id);
        model.addAttribute("customerLoss",customerLoss);
        return "customerLoss/confirm";
    }

    @RequestMapping("updateState")
    @ResponseBody
    public ResultInfo updateState(CustomerLoss loss) {
        customerLossService.updateState(loss);
        return success("状态更新成功");
    }

}