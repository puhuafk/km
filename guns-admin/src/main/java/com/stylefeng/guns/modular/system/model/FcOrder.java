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
 * @since 2018-10-15
 */

@Getter
@Setter
@ToString
@TableName("fc_order")
public class FcOrder extends Model<FcOrder> {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private Long id;

    /**
     * 电商订单号
     */
    private String sn;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 客户名称
     */
    private String consignee;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 平台红包金额
     */
    @TableField("coupon_discount")
    private BigDecimal couponDiscount;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
