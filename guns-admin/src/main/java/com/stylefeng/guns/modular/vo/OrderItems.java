package com.stylefeng.guns.modular.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItems {

    /**
     * id
     */
    private Integer id;
    /**
     * 商品名称
     */
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
    private String unitState;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 正常价
     */
    private BigDecimal noValency;
    /**
     * 销售价
     */
    private BigDecimal salesPrice;
    /**
     * 折后价
     */
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
    private String reArea;
    /**
     * 订单表id
     */
    private Long exErpOrderId;
    /**
     * 库区
     */
    private String lib;
    /**
     * 是否退单
     */
    private boolean ifReturn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 换算数量
     */
    private Integer unitE;
    /**
     * 换单数量
     */
    private Integer returnNum;
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
     * 总数量
     */
    private Integer totalQnty;
    /**
     * 订单创建日期
     */
    private Date createdate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitState() {
        return unitState;
    }

    public void setUnitState(String unitState) {
        this.unitState = unitState;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getNoValency() {
        return noValency;
    }

    public void setNoValency(BigDecimal noValency) {
        this.noValency = noValency;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getDisPrice() {
        return disPrice;
    }

    public void setDisPrice(BigDecimal disPrice) {
        this.disPrice = disPrice;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getReArea() {
        return reArea;
    }

    public void setReArea(String reArea) {
        this.reArea = reArea;
    }

    public Long getExErpOrderId() {
        return exErpOrderId;
    }

    public void setExErpOrderId(Long exErpOrderId) {
        this.exErpOrderId = exErpOrderId;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public boolean isIfReturn() {
        return ifReturn;
    }

    public void setIfReturn(boolean ifReturn) {
        this.ifReturn = ifReturn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUnitE() {
        return unitE;
    }

    public void setUnitE(Integer unitE) {
        this.unitE = unitE;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public String getZqpSn() {
        return zqpSn;
    }

    public void setZqpSn(String zqpSn) {
        this.zqpSn = zqpSn;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public BigDecimal getDiscountP() {
        return discountP;
    }

    public void setDiscountP(BigDecimal discountP) {
        this.discountP = discountP;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getdLibrary() {
        return dLibrary;
    }

    public void setdLibrary(Long dLibrary) {
        this.dLibrary = dLibrary;
    }

    public String getpMethod() {
        return pMethod;
    }

    public void setpMethod(String pMethod) {
        this.pMethod = pMethod;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public String gettMan() {
        return tMan;
    }

    public void settMan(String tMan) {
        this.tMan = tMan;
    }

    public String gettNo() {
        return tNo;
    }

    public void settNo(String tNo) {
        this.tNo = tNo;
    }

    public BigDecimal gettCost() {
        return tCost;
    }

    public void settCost(BigDecimal tCost) {
        this.tCost = tCost;
    }

    public BigDecimal gettPrice() {
        return tPrice;
    }

    public void settPrice(BigDecimal tPrice) {
        this.tPrice = tPrice;
    }

    public BigDecimal getdPrice() {
        return dPrice;
    }

    public void setdPrice(BigDecimal dPrice) {
        this.dPrice = dPrice;
    }

    public Integer getTotalQnty() {
        return totalQnty;
    }

    public void setTotalQnty(Integer totalQnty) {
        this.totalQnty = totalQnty;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getSentPer() {
        return sentPer;
    }

    public void setSentPer(String sentPer) {
        this.sentPer = sentPer;
    }

    public String getOpenBillStore() {
        return openBillStore;
    }

    public void setOpenBillStore(String openBillStore) {
        this.openBillStore = openBillStore;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Boolean getExOrderSign() {
        return exOrderSign;
    }

    public void setExOrderSign(Boolean exOrderSign) {
        this.exOrderSign = exOrderSign;
    }
}
