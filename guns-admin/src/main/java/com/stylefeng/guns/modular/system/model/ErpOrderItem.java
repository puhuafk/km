package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("erp_order_item")
public class ErpOrderItem extends Model<ErpOrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 商品id
     */
    @TableField("product")
    private Long product;
    /**
     * 主订单ID
     */
    @TableField("o_id")
    private Long oId;
    /**
     * 商品名
     */
    @TableField("p_name")
    private String pName;
    /**
     * 原厂件号(条形码)
     */
    @TableField("bar_code")
    private String barCode;
    /**
     * 库区
     */
    private String library;

    /**
     * 数量
     */
    private Integer num;
    /**
     * 默认单位
     */
    private String unit;
    /**
     * 换算数量
     */
    @TableField("unit_e")
    private Integer unitE;
    /**
     * 正常价
     */
    @TableField("n_price")
    private BigDecimal nPrice;
    /**
     * 销售单价
     */
    @TableField("s_price")
    private BigDecimal sPrice;
    /**
     * 折扣后单价
     */
    @TableField("d_price")
    private BigDecimal dPrice;
    /**
     * 总金额
     */
    private BigDecimal money;
    /**
     * 规格
     */
    private String spec;
    /**
     * 品牌
     */
    private String brands;
    /**
     * 产地
     */
    @TableField("made_in")
    private String madeIn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 商品分类id
     * @return
     */
    @TableField("p_category_id")
    private Integer pCategoryId;

    @Override
    protected Serializable pkVal() {
        return id;
    }

}
