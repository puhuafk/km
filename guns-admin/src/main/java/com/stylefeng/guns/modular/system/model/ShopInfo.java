package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 市场部日常巡店记录表
 * </p>
 *
 * @author cheng
 * @since 2018-11-30
 */
@Getter
@Setter
@ToString
@TableName("shop_info")
public class ShopInfo extends Model<ShopInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 店铺地址
     */
    @TableField("shop_address")
    private String shopAddress;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 营业执照图片
     */
    @TableField("business_license")
    private String businessLicense;
    /**
     * 店铺类型
     */
    @TableField("shop_type")
    private String shopType;
    /**
     * 店铺周边信息
     */
    @TableField("around_info")
    private String aroundInfo;
    /**
     * 店铺周边流量
     */
    @TableField("around_flow")
    private String aroundFlow;
    /**
     * 店铺面积
     */
    @TableField("shop_area")
    private String shopArea;
    /**
     * 店铺经营年限
     */
    @TableField("shop_life")
    private String shopLife;
    /**
     * 店员数量
     */
    @TableField("shop_staff_num")
    private String shopStaffNum;
    /**
     * 店主姓名
     */
    @TableField("owner_name")
    private String ownerName;
    /**
     * 店主性别
     */
    @TableField("owner_sex")
    private String ownerSex;
    /**
     * 店主年龄
     */
    @TableField("owner_age")
    private String ownerAge;
    /**
     * 店主籍贯
     */
    @TableField("owner_place")
    private String ownerPlace;
    /**
     * 店主性格
     */
    @TableField("owner_character")
    private String ownerCharacter;
    /**
     * 店主类型（沟通）
     */
    @TableField("owner_type")
    private String ownerType;
    /**
     * 店主文化
     */
    @TableField("owner_culture")
    private String ownerCulture;
    /**
     * 营业时间
     */
    @TableField("business_hours")
    private String businessHours;
    /**
     * 经营品种
     */
    @TableField("business_scope")
    private String businessScope;
    /**
     * 社区服务
     */
    @TableField("community_service")
    private String communityService;
    /**
     * 是否注册津小超
     */
    @TableField("if_registes")
    private String ifRegistes;
    /**
     * 是否下单
     */
    @TableField("if_place_order")
    private String ifPlaceOrder;
    /**
     * 是否习惯独立下单
     */
    @TableField("if_independent_order")
    private String ifIndependentOrder;
    /**
     * 常用平台
     */
    @TableField("common_platform")
    private String commonPlatform;
    /**
     * 平台订货比例
     */
    @TableField("platform_order_ratio")
    private String platformOrderRatio;
    /**
     * 手机平台
     */
    @TableField("mobile_platform")
    private String mobilePlatform;
    /**
     * 每日营业额
     */
    @TableField("daily_turnover")
    private String dailyTurnover;
    /**
     * 周边便利店
     */
    @TableField("nearby_shop")
    private String nearbyShop;
    /**
     * 億米潜力预估
     */
    @TableField("potential_prediction")
    private String potentialPrediction;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lng;
    /**
     * 用户活跃度
     */
    @TableField("user_activity")
    private String userActivity;
    /**
     * 其他描述
     */
    private String memo;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 创建人
     */
    private String creater;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
