package com.softeem.crm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softeem.crm.enums.CustomerServeStatus;
import com.softeem.crm.mapper.CustomerMapper;
import com.softeem.crm.mapper.UserMapper;
import com.softeem.crm.pojo.Customer;
import com.softeem.crm.pojo.CustomerServe;
import com.softeem.crm.pojo.User;
import com.softeem.crm.service.CustomerServeService;
import com.softeem.crm.mapper.CustomerServeMapper;
import com.softeem.crm.utils.AssertUtil;
import com.softeem.crm.vo.CustomerServeQuery;
import com.softeem.crm.vo.CustomerServeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 30468
* @description 针对表【t_customer_serve】的数据库操作Service实现
* @createDate 2022-12-27 14:38:29
*/
@Service
public class CustomerServeServiceImpl extends ServiceImpl<CustomerServeMapper, CustomerServe>
    implements CustomerServeService{

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryByParamsForTable(CustomerServeQuery customerServeQuery) {
        Page<CustomerServeVo> page = new Page<>(customerServeQuery.getPage(),customerServeQuery.getLimit());
        IPage<CustomerServeVo> customerServeIPage = this.baseMapper.selectByParams(page, customerServeQuery);
        List<CustomerServeVo> serveVoList = customerServeIPage.getRecords();
        for (CustomerServeVo customerServeVo : serveVoList) {
            if (customerServeVo.getAssigner()!=null||!customerServeVo.getAssigner().equals("")){
                Integer userId = Integer.parseInt(customerServeVo.getAssigner());
                User user = userMapper.selectById(userId);
                if(user!=null){
                    customerServeVo.setAssigner(user.getUserName());
                }
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("count", customerServeIPage.getTotal());
        result.put("data", serveVoList);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateCustomerServe(CustomerServe customerServe){
        if(null == customerServe.getId()){
            /**  服务添加操作
             * 1.参数校验
             *     客户名  非空
             *     客户类型  非空
             * 2.添加默认值
             *    state  设置状态值
             *    isValid  createDate updateDate
             *  3.执行添加 判断结果
             */
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()),"请指定客户!");
            AssertUtil.isTrue(null ==customerMapper.selectOne(Wrappers.<Customer>lambdaQuery().eq(Customer::getName,customerServe.getCustomer())),"当前客户暂不存在!");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()),"请指定服务类型!");
            customerServe.setIsValid(0);
            customerServe.setState(CustomerServeStatus.CREATED.getState());
            AssertUtil.isTrue(this.baseMapper.insert(customerServe)<1,"服务记录添加失败!");
        }else{
            /**
             * 分配  处理  反馈
             */
            CustomerServe temp =  this.getById(customerServe.getId());
            AssertUtil.isTrue(null == temp,"待处理的服务记录不存在!");
            if(customerServe.getState().equals(CustomerServeStatus.ASSIGNED.getState())){
                // 服务分配
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getAssigner())||
                        (null == userMapper.selectById(Integer.parseInt(customerServe.getAssigner()))),"待分配用户不存在");
                customerServe.setAssignTime(new Date());
                AssertUtil.isTrue(this.baseMapper.updateById(customerServe)<1,"服务分配失败!");
            } if(customerServe.getState().equals(CustomerServeStatus.PROCED.getState())){
                // 服务处理
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"请指定处理内容!");
                customerServe.setServiceProceTime(new Date());
                AssertUtil.isTrue(this.baseMapper.updateById(customerServe)<1,"服务处理失败!");
            }if(customerServe.getState().equals(CustomerServeStatus.FEED_BACK.getState())){
                // 服务处理
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"请指定反馈内容!");
                AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"请指定反馈满意度!");
                customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
                AssertUtil.isTrue(this.baseMapper.updateById(customerServe)<1,"服务反馈失败!");
            }
        }

    }
}




