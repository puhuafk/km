package com.stylefeng.guns.modular.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;



@ToString
@Getter
@Setter
public class SaleCollectVo {

    private BigDecimal saleAmount;//销售金额

    private Integer saleNum;//销售数量

    private BigDecimal salesReturnRate;//销售退货率

    private Integer returnAmount ;//退货数量

    private BigDecimal returnGoodsNum ;//退货金额


}
