package com.stylefeng.guns.modular.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.stylefeng.guns.modular.system.model.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class StoepVo  {
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
    private String zqpSn;
    /**
     * 条形码
     */
    private String pn;
    /**
     * 销售时间
     */
    private Date saleDate;
    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 订单状态，0-未确认
     */
    private Integer orderStatus;
    /**
     * 审核状态
     */
    private Integer verifyStatus;
    /**
     * 出入库状态，0-未处理
     */
    private Integer stockStatus;

    /**
     * 客户名称
     */
    private String cName;
    /**
     * 折扣百分比
     */
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
     * 库区ID
     */
    private Long dLibrary;
    /**
     * 收款方式
     */
    private String pMethod;
    /**
     * 收货地址
     */
    private String rAddress;
    /**
     * 收货人
     */
    private String tMan;
    /**
     * 承运单号
     */
    private String tNo;
    /**
     * 运费
     */
    private BigDecimal tCost;
    /**
     * 折扣前总金额
     */
    private BigDecimal tPrice;
    /**
     * 折扣后总金额
     */
    private BigDecimal dPrice;
    /**
     * 库区
     */
    private String lib;
    /**
     * 总数量
     */
    private Integer totalQnty;
    /**
     * 订单创建日期
     */
    private Date createdate;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 来源1电商、2erp
     */
    private Integer dataSource;
    /**
     * 交款人
     */
    private String payer;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 送货员
     */
    private String sentPer;
    /**
     * 开单门店
     */
    private String openBillStore;
    /**
     * 付款方式
     */
    private String payType;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 实际付款金额
     */
    private BigDecimal actualAmount;
    /**
     * 订单异常标识0无异常，1处于异常审核中
     */
    private Boolean exOrderSign;


    /**
     * list
     * @return
     */
    private List<OrderItem> orderItemList;

    /**
     * 金额
     */

    private BigDecimal money;

    private Integer detailsFlag;

}
