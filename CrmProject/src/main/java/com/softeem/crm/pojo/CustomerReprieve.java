package com.softeem.crm.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName t_customer_reprieve
 */
@TableName(value ="t_customer_reprieve")
@Data
public class CustomerReprieve implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "loss_id")
    private Integer lossId;

    /**
     * 
     */
    @TableField(value = "measure")
    private String measure;

    /**
     * 
     */
    @TableField(value = "is_valid")
    private Integer isValid;

    /**
     *
     */
    @TableField(value = "create_date",fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     *
     */
    @TableField(value = "update_date",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}