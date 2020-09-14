package com.stylefeng.guns.modular.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@ToString
public class OrderItemSumVo {

    @TableField("money_total")
    private BigDecimal moneyTotal;//销售金额

    @TableField("check_money_total")
    private BigDecimal checkMoneyTotal;//销售金额

    @TableField("amount_total")
    private Integer amountTotal;//销售数量

    @TableField("return_goods_rate")
    private BigDecimal returnGoodsRate;//销售退货率

    @TableField("return_bill_rate")
    private BigDecimal returnBillRate;//销售退单率

    @TableField("return_num_total")
    private Integer returnNumTotal ;//退货数量

    @TableField("return_money_totle")
    private BigDecimal returnMoneyTotal;//退货金额

    @TableField("avg_sale_price")
    private BigDecimal avgSalePrice ;//平均销售单价

    List<OrderItemVo> orderItemVoList;

}
