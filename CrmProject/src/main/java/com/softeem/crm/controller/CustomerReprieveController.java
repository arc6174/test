package com.softeem.crm.controller;

import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.pojo.CustomerLinkman;
import com.softeem.crm.pojo.CustomerReprieve;
import com.softeem.crm.service.CustomerReprieveService;
import com.softeem.crm.vo.CustomerReprieveQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("cus_reprieve")
public class CustomerReprieveController extends BaseController {
    @Resource
    private CustomerReprieveService customerReprieveService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerReprieveQuery customerReprieveQuery){
        return customerReprieveService.queryByParamsForTable(customerReprieveQuery);
    }

    @RequestMapping("addOrUpdatePage")
    public String addOrUpdatePage(Integer lossId,Integer id, Model model){
        if(null !=id){
            model.addAttribute("reprieve",customerReprieveService.getById(id));
        }else {
            model.addAttribute("lossId",lossId);
        }
        return "customerLoss/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo save(CustomerReprieve customerReprieve) {
        customerReprieveService.save(customerReprieve);
        return success("联系人添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(CustomerReprieve customerReprieve) {
        customerReprieveService.updateById(customerReprieve);
        return success("联系人更新成功");
    }


    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteBatch(Integer[] ids){
        customerReprieveService.deleteUserByIds(ids);
        return success("联系人记录删除成功");
    }
}
