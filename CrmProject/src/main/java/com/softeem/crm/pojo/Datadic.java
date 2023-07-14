package com.softeem.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_datadic
 */
@TableName(value ="t_datadic")
@Data
public class Datadic implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "data_dic_name")
    private String dataDicName;

    /**
     * 
     */
    @TableField(value = "data_dic_value")
    private String dataDicValue;

    /**
     * 
     */
    @TableField(value = "is_valid")
    private Integer isValid;

    /**
     * 
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 
     */
    @TableField(value = "update_date")
    private Date updateDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}