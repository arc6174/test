package com.softeem.crm.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName t_sale_chance
 */
@TableName(value = "t_sale_chance")
@Data
public class SaleChance implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机会来源
     */
    @TableField(value = "chance_source")
    private String chanceSource;

    /**
     *
     */
    @TableField(value = "customer_name")
    private String customerName;

    /**
     *
     */
    @TableField(value = "cgjl")
    private Integer cgjl;

    /**
     *
     */
    @TableField(value = "overview")
    private String overview;

    /**
     *
     */
    @TableField(value = "link_man")
    private String linkMan;

    /**
     *
     */
    @TableField(value = "link_phone")
    private String linkPhone;

    /**
     *
     */
    @TableField(value = "description")
    private String description;

    /**
     *
     */
    @TableField(value = "create_man")
    private String createMan;

    /**
     *
     */
    @TableField(value = "assign_man")
    private String assignMan;

    /**
     *
     */
    @DateTimeFormat(pattern= "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "assign_time")
    private Date assignTime;

    /**
     *
     */
    @TableField(value = "state")
    private Integer state;

    /**
     *
     */
    @TableField(value = "dev_result")
    private Integer devResult;

    /**
     *
     */
    @TableLogic
    @TableField(value = "is_valid")
    private Integer isValid;

    /**
     *
     */
    @DateTimeFormat(pattern= "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    /**
     *
     */
    @DateTimeFormat(pattern= "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}