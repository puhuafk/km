package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 异常订单收付款表

 * </p>
 *
 * @author chengshuai
 * @since 2018-10-16
 */
@Getter
@Setter
@ToString
@TableName("ex_order_pay")
public class ExOrderPay extends Model<ExOrderPay> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
    /**
     * 异常订单id
     */
    @TableField("ex_order_id")
    private String exOrderId;
    /**
     * 交款人
     */
    private String payer;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 收款时间
     */
    @TableField("pay_time")
    private Date payTime;
    /**
     * 交款人id
     */
    @TableField("payer_id")
    private Integer payerId;
    /**
     * 额度
     */
    private Long amount;
    /**
     * pay_type
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人
     */
    private String operator;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
