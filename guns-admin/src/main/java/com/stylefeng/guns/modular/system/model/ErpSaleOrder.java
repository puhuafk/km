package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author chengshuai
 * @since 2018-11-08
 */
@Getter
@Setter
@ToString
@TableName("erp_sale_order")
public class ErpSaleOrder extends Model<ErpSaleOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 单号
     */
    private String sn;
    /**
     * 订单编号
     */
    @TableField("zqp_sn")
    private String zqpSn;
    /**
     * 销售时间
     */
    @TableField("sale_date")
    private Date saleDate;
    /**
     * 总金额
     */
    @TableField("total_price")
    private BigDecimal totalPrice;
    /**
     * 订单状态，0-未确认
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 审核状态
     */
    @TableField("verify_status")
    private Integer verifyStatus;
    /**
     * 出入库状态，0-未处理
     */
    @TableField("stock_status")
    private Integer stockStatus;

    /**
     * 客户名称
     */
    @TableField("c_name")
    private String cName;
    /**
     * 折扣百分比
     */
    @TableField("discount_p")
    private BigDecimal discountP;
    /**
     * 订单来源
     */
    private Integer source;
    /**
     * 经办人
     */
    private String operator;
    /**
     * 库区名称
     */
    @TableField("library")
    private String library;
    /**
     * 收款方式
     */
    @TableField("p_method")
    private String pMethod;
    /**
     * 收货地址
     */
    @TableField("r_address")
    private String rAddress;
    /**
     * 收货人
     */
    @TableField("t_man")
    private String tMan;
    /**
     * 承运单号
     */
    @TableField("t_no")
    private String tNo;
    /**
     * 运费
     */
    @TableField("t_cost")
    private BigDecimal tCost;
    /**
     * 折扣前总金额
     */
    @TableField("t_price")
    private BigDecimal tPrice;
    /**
     * 折扣后总金额
     */
    @TableField("d_price")
    private BigDecimal dPrice;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
