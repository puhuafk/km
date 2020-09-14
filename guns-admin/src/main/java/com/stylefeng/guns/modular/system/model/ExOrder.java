package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 异常订单表

 * </p>
 *
 * @author chengshuai
 * @since 2018-10-17
 */
@TableName("ex_order")
@Getter
@Setter
@ToString
public class ExOrder extends Model<ExOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    @TableField("ec_erp_order_id")
    private Long ecErpOrderId;

    /**
     * 订单单号
     */
    @TableField("order_sn")
    private String orderSn;
    /**
     * 异常类型（1红包减款、2退单品、3已出库退单、4商家拒收）
     */
    @TableField("ex_type")
    private Integer exType;
    /**
     * 异常原因
     */
    @TableField("ex_reason")
    private String exReason;
    /**
     * 异常状态（是否处理：0未处理、1已经处理）
     */
    @TableField("ex_state")
    private Boolean exState;
    /**
     * 创建人
     */
    private String operator;
    /**
     * 创建人id
     */
    @TableField("operator_id")
    private Integer operatorId;
    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    /**
     * 流程步骤标识
     */
    @TableField("step_sign")
    private Integer stepSign;
    /**
     * 流程id
     */
    @TableField("flow_id")
    private Integer flowId;
    /**
     * 销售单号
     */
    @TableField("sale_sn")
    private String saleSn;
    /**
     * 来源（1电商后台,2erp系统）
     */
    @TableField("data_statue")
    private Integer dataStatue;
    /**
     * 出库时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("out_hou_time")
    private Date outHouTime;
    /**
     * 出库人
     */
    @TableField("out_hou_per")
    private String outHouPer;
    /**
     * 审批部门id
     */
    @TableField("exam_dep_id")
    private Integer examDepId;

    /**
     * 审批部门id
     */
    @TableField("red_pack_amount")
    private BigDecimal redPackAmount;

    /**
     * 退单品信息
     */
    @TableField("delete_order_items")
    private String deleteOrderItems;


    /**
     * 审核意见
     */
    @TableField("exam_view")
    private String examView;

    /**
     * 审核是否已驳回
     */
    @TableField("if_reject")
    private Boolean ifReject;


    /**
     * 退款金额
     */
    @TableField("refund_money")
    private BigDecimal refundMoney;

    /**
     * 退款方式
     */
    @TableField("refund_type")
    private String refundType;

    /**
     * 订单
     */
    private transient EcErpOrder ecErpOrder;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
