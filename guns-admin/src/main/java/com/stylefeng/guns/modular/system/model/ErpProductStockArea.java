package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;



@Getter
@Setter
@ToString
@TableName("erp_product_stock_area")
public class ErpProductStockArea extends Model<ErpProductStockArea> {

    private static final long serialVersionUID = 1L;


        //商品id
        @TableField("product")
        private Long product;

        //库位id
        @TableField("stock_area")
    	private Long stockArea;

        //库位名称
        @TableField("stock_area_name")
        private String stockAreaName;



        @Override
        protected Serializable pkVal() {
            return null;
        }
}
