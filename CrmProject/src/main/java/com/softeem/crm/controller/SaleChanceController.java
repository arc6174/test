package com.softeem.crm.controller;

import com.softeem.crm.base.BaseController;
import com.softeem.crm.base.ResultInfo;
import com.softeem.crm.handler.RequirePermission;
import com.softeem.crm.pojo.SaleChance;
import com.softeem.crm.service.SaleChanceService;
import com.softeem.crm.service.UserService;
import com.softeem.crm.utils.LoginUserUtil;
import com.softeem.crm.vo.SaleChanceQuery;
import lombok.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    /**
     * 营销管理主页面转发
     */
    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }

    /*
       营销管理数据查询
    */
    @RequestMapping("list")
    @RequirePermission(code = "101001")
    @ResponseBody
    public Map<String, Object> querySaleChancesByParams(Integer flag, HttpServletRequest request, SaleChanceQuery saleChanceQuery) {
        if (flag != null && flag == 1) {
            saleChanceQuery.setAggsinMan(LoginUserUtil.releaseUserIdFromCookie(request) + "");
        }
        return saleChanceService.queryByParamsForTable(saleChanceQuery);
    }

    /*
   机会数据添加与更新表单页面视图转发
*/
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id, Model model) {
        if (null != id) {
            model.addAttribute("saleChance", saleChanceService.getById(id));
        }
        return "saleChance/add_update";
    }


    @RequestMapping("save")
    @RequirePermission(code = "101002")
    @ResponseBody
    public ResultInfo saveSaleChance(SaleChance saleChance, HttpServletRequest request) {
        saleChance.setCreateMan(userService.getById(
                LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        saleChanceService.saveSaleChance(saleChance);
        return success("机会数据添加成功");
    }

    /*
        机会数据更新
    */
    @RequestMapping("update")
    @RequirePermission(code = "101004")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {
        saleChanceService.updateSaleChance(saleChance);
        return success("机会数据更新成功");
    }

    @RequestMapping("delete")
    @RequirePermission(code = "101003")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids) {
        saleChanceService.deleteSaleChancesByIds(ids);
        return success("机会数据删除成功");
    }

    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id, Integer devResult) {
        saleChanceService.updateSaleChanceDevResult(id, devResult);
        return success("开发状态更新成功");
    }
}