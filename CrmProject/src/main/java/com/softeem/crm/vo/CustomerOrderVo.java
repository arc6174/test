package com.softeem.crm.vo;

import com.softeem.crm.pojo.CustomerOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerOrderVo extends CustomerOrder {
    private Integer total;
}
