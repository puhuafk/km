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

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author chengshuai
 * @since 2018-11-18
 */
@TableName("order_item")
@Getter
@Setter
@ToString
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 条形码
     */
    private String sn;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单位说明
     */
    @TableField("unit_state")
    private String unitState;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 正常价
     */
    @TableField("no_valency")
    private BigDecimal noValency;
    /**
    /**
     * 销售价
     */
    @TableField("sales_price")
    private BigDecimal salesPrice;
    /**
     * 折后价
     */
    @TableField("dis_price")
    private BigDecimal disPrice;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 规格/型号
     */
    private String model;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 产地
     */
    @TableField("re_area")
    private String reArea;
    /**
     * 订单表id
     */
    @TableField("ec_erp_order_id")
    private Long exErpOrderId;
    /**
     * 库区
     */
    private String lib;
//    /**
//     * 库位
//     */
//    private String kuwei;

    /**
     * 是否退单
     */
    @TableField("if_return")
    private boolean ifReturn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 换算数量
     */
    @TableField("unit_e")
    private Integer unitE;
    /**
     * 退货数量
     */
    @TableField("return_num")
    private Integer returnNum;

    /**
     * 待退货数量
     */
    @TableField("for_return_num")
    private Integer forReturnNum;

    /**
     * 商品类别分类id
     * @return
     */
    @TableField("product_category_id")
    private Integer productCategoryId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
