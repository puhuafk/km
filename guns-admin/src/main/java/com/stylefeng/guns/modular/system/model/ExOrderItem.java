package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-26
 */
@TableName("ex_order_item")
public class ExOrderItem extends Model<ExOrderItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("ex_order_id")
    private Integer exOrderId;
    @TableField("order_item_id")
    private Integer orderItemId;
    @TableField("return_number")
    private Integer returnNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExOrderId() {
        return exOrderId;
    }

    public void setExOrderId(Integer exOrderId) {
        this.exOrderId = exOrderId;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(Integer returnNumber) {
        this.returnNumber = returnNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExOrderItem{" +
        "id=" + id +
        ", exOrderId=" + exOrderId +
        ", orderItemId=" + orderItemId +
        ", returnNumber=" + returnNumber +
        "}";
    }
}
