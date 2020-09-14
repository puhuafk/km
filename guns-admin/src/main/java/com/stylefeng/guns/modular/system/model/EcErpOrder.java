package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 电商、erp订单表
 * </p>
 *
 * @author chengshuai
 * @since 2018-11-09
 */
@Getter
@Setter
@ToString
@TableName("ec_erp_order")
public class EcErpOrder extends Model<EcErpOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     *电商单号
     */
    @TableField("ec_no")
    private String ecNo;
    /**
     * 销售单号
     */
    @TableField("sale_no")
    private String saleNo;
    /**
     * 订单创建日期
     */
    private Date createdate;
    /**
     * 总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 来源1电商、2erp
     */
    @TableField("data_source")
    private Integer dataSource;
    /**
     * 客户名称
     */
    @TableField("customer_name")
    private String customerName;
    /**
     * 收货地址
     */
    @TableField("delivery_address")
    private String deliveryAddress;
    /**
     * 出库人
     */
    @TableField("out_store_per")
    private String outStorePer;
    /**
     * 出库时间
     */
    @TableField("out_store_time")
    private Date outStoreTime;
    /**
     * 结算方式（json）
     */
    @TableField("pay_mode")
    private String payMode;
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
    @TableField("sent_per")
    private String sentPer;
    /**
     * 开单门店
     */
    @TableField("open_bill_store")
    private String openBillStore;
    /**
     * 付款方式
     */
    @TableField("pay_type")
    private String payType;
    /**
     * 销售日期
     */
    @TableField("sale_date")
    private Date saleDate;
    /**
     * 库区
     */
    private String lib;
    /**
     * 总数量
     */
    @TableField("total_qnty")
    private Integer totalQnty;
    /**
     * 优惠金额
     */
    @TableField("discount_amount")
    private BigDecimal discountAmount;
    /**
     * 实际付款金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    /**
     * 0未核销、1已核销
     */
    @TableField("if_check")
    private Boolean ifCheck;
    /**
     * 0未出库、1已出库
     */
    @TableField("if_out_store")
    private Boolean ifOutStore;
    /**
     * 红包金额
     */
    @TableField("red_pack_amount")
    private BigDecimal redPackAmount;
    /**
     * 订单是否关闭
     */
    @TableField("if_close")
    private Boolean ifClose;
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
    /**
     * 承运单号
     */
    @TableField("t_no")
    private String tNo;
    /**
     * 收货人
     */
    @TableField("t_man")
    private String tMan;
    /**
     * 收款方式
     */
    @TableField("p_method")
    private String pMethod;
    /**
     * 库区ID
     */
    @TableField("d_library")
    private Long dLibrary;
    /**
     * 订单状态
     */
    @TableField("order_state")
    private Integer orderState;
    /**
     * 出库状态
     */
    @TableField("stock_status")
    private Integer stockStatus;
    /**
     * 审核状态
     */
    @TableField("verify_status")
    private Integer verifyStatus;
    /**
     * 折扣百分比
     */
    @TableField("discount_p")
    private BigDecimal discountP;
    /**
     * 核销时间
     */
    @TableField("check_time")
    private Date checkTime;

    /**
     * 退商品标记，核销报表用到
     */
    @TableField("return_goods_flag")
    private Boolean returnGoodsFlag;

    /**
     * 财务核销金额，核销报表用到
     */
    @TableField("check_money")
    private BigDecimal checkMoney;

    /**
     * 订单是否为异常订单
     */
    @TableField("ex_order_sign")
    private Boolean exOrderSign;

    /**
     * 数据进入系统时间
     */
    @TableField("enter_system_time")
    private Date enterSystemTime;

    /**
     * 核销备注
     */
    @TableField("check_node")
    private String checkNode;

    /**
     * 退单品或者整单退款金额
     */
    @TableField("refund_money")
    private BigDecimal refundMoney;

    /**
     * 退红包方式
     */
    @TableField("return_red_type")
    private String returnRedType;


    private transient ExOrder exOrder;

    /**
     * 退红包金额
     */
    @TableField("return_red_money")
    private BigDecimal returnRedMoney;

    /**
     * 退单品金额
     */
    @TableField("return_single_money")
    private BigDecimal returnSingleMoney;

    /**
     * 退单品方式
     */
    @TableField("return_single_type")
    private String returnSingleType;

    /**
     * 异常金额
     */
    @TableField("ex_money")
    private BigDecimal exMoney;


    /**
     * 异常订单id
     */
    private transient Integer exOrderId;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
