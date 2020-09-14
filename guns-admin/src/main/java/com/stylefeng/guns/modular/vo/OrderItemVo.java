package com.stylefeng.guns.modular.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.stylefeng.guns.modular.system.model.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 商品销售汇总表
 */
@ToString
@Getter
@Setter
public class OrderItemVo extends OrderItem {

    @TableField("money_total")
    private BigDecimal moneyTotal;//销售金额

    @TableField("amount_total")
    private Integer amountTotal;//销售数量

    @TableField("return_rate")
    private BigDecimal returnRate;//销售退货率

    @TableField("return_num_total")
    private Integer returnNumTotal ;//退货数量

    @TableField("return_money_totle")
    private BigDecimal returnMoneyTotal;//退货金额

    @TableField("avg_sale_price")
    private BigDecimal avgSalePrice ;//平均销售单价

}
