package com.stylefeng.guns.modular.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.modular.system.model.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EcStoepVo  {
    private Integer detailsFlag=2;//查看审核流程
    /**
     * id
     */
    private Long id;
    /**
     *电商单号
     */
    private String ecNo;
    /**
     * 销售单号
     */
    private String saleNo;
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
     * 客户名称
     */
    private String customerName;
    /**
     * 收货地址
     */
    private String deliveryAddress;
    /**
     * 出库人
     */
    private String outStorePer;
    /**
     * 出库时间
     */
    private Date outStoreTime;
    /**
     * 结算方式（json）
     */
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
     * 销售日期
     */
    private Date saleDate;
    /**
     * 库区
     */
    private String lib;
    /**
     * 总数量
     */
    private Integer totalQnty;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 实际付款金额
     */
    private BigDecimal actualAmount;
    /**
     * 0未核销、1已核销
     */
    private Boolean ifCheck;
    /**
     * 0未出库、1已出库
     */
    private Boolean ifOutStore;
    /**
     * 订单异常标识0无异常，1处于异常审核中
     */
    private Boolean exOrderSign;
    /**
     * 红包金额
     */
    private BigDecimal redPackAmount;
    /**
     * 订单是否关闭
     */
    private Boolean ifClose;
    /**
     * 运费
     */
    private BigDecimal tCost;
    /**
     * 折扣前总金额
     */
    private BigDecimal tPrice;

    /**
     * 折扣前总金额
     */
    private BigDecimal dPrice;
    /**
     * 承运单号
     */
    private String tNo;
    /**
     * 收货人
     */
    private String tMan;
    /**
     * 收款方式
     */
    private String pMethod;
    /**
     * 库区ID
     */
    private Long dLibrary;
    /**
     * 订单状态
     */
    private Integer orderState;
    /**
     * 出库状态
     */
    private Integer stockStatus;
    /**
     * 审核状态
     */
    private Integer verifyStatus;
    /**
     * 折扣百分比
     */
    private BigDecimal discountP;

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
     * 数量
     * @return
     */
    private Integer quantity;
    /**
     * list
     * @return
     */
    private List<OrderItems> orderItems;

    /**
     * 支付方法名称
     */
    private  String paymentMethodName;


    private Boolean returnAllButton=false;//已出库退单

    private Boolean returnSingleButton=false;//退单品

    private Boolean redPacketButton=false;//退红包

    private Boolean examButton=false;//审核按钮

    private Boolean checkButton=false;//核销按钮

    private Boolean confirmOutButton=false;//确认出库

    private Boolean closeOrderButton=false;//关闭订单

    private Boolean examFlowButton=false;//查看审核流程


    private String nextDepName; //下个待审核部门名称

    private String orderStateInfo; //下个待审核部门名称

    private Integer exType;//下个待审核部门名称

    private Integer exOrderId;//异常订单id

  //  private List<OrderItems> orderItems;

    private List<OrderItems> orderItemList;//订单列表


    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEcNo() {
        return ecNo;
    }

    public void setEcNo(String ecNo) {
        this.ecNo = ecNo;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOutStorePer() {
        return outStorePer;
    }

    public void setOutStorePer(String outStorePer) {
        this.outStorePer = outStorePer;
    }

    public Date getOutStoreTime() {
        return outStoreTime;
    }

    public void setOutStoreTime(Date outStoreTime) {
        this.outStoreTime = outStoreTime;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
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

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public Integer getTotalQnty() {
        return totalQnty;
    }

    public void setTotalQnty(Integer totalQnty) {
        this.totalQnty = totalQnty;
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

    public Boolean getIfCheck() {
        return ifCheck;
    }

    public void setIfCheck(Boolean ifCheck) {
        this.ifCheck = ifCheck;
    }

    public Boolean getIfOutStore() {
        return ifOutStore;
    }

    public void setIfOutStore(Boolean ifOutStore) {
        this.ifOutStore = ifOutStore;
    }

    public Boolean getExOrderSign() {
        return exOrderSign;
    }

    public void setExOrderSign(Boolean exOrderSign) {
        this.exOrderSign = exOrderSign;
    }

    public BigDecimal getRedPackAmount() {
        return redPackAmount;
    }

    public void setRedPackAmount(BigDecimal redPackAmount) {
        this.redPackAmount = redPackAmount;
    }

    public Boolean getIfClose() {
        return ifClose;
    }

    public void setIfClose(Boolean ifClose) {
        this.ifClose = ifClose;
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

    public String gettNo() {
        return tNo;
    }

    public void settNo(String tNo) {
        this.tNo = tNo;
    }

    public String gettMan() {
        return tMan;
    }

    public void settMan(String tMan) {
        this.tMan = tMan;
    }

    public String getpMethod() {
        return pMethod;
    }

    public void setpMethod(String pMethod) {
        this.pMethod = pMethod;
    }

    public Long getdLibrary() {
        return dLibrary;
    }

    public void setdLibrary(Long dLibrary) {
        this.dLibrary = dLibrary;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public BigDecimal getDiscountP() {
        return discountP;
    }

    public void setDiscountP(BigDecimal discountP) {
        this.discountP = discountP;
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

    public Integer getDetailsFlag() {
        return detailsFlag;
    }

    public void setDetailsFlag(Integer detailsFlag) {
        this.detailsFlag = detailsFlag;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getReturnAllButton() {
        return returnAllButton;
    }

    public void setReturnAllButton(Boolean returnAllButton) {
        this.returnAllButton = returnAllButton;
    }

    public Boolean getReturnSingleButton() {
        return returnSingleButton;
    }

    public void setReturnSingleButton(Boolean returnSingleButton) {
        this.returnSingleButton = returnSingleButton;
    }

    public Boolean getRedPacketButton() {
        return redPacketButton;
    }

    public void setRedPacketButton(Boolean redPacketButton) {
        this.redPacketButton = redPacketButton;
    }

    public Boolean getExamButton() {
        return examButton;
    }

    public void setExamButton(Boolean examButton) {
        this.examButton = examButton;
    }

    public Boolean getCheckButton() {
        return checkButton;
    }

    public void setCheckButton(Boolean checkButton) {
        this.checkButton = checkButton;
    }

    public Boolean getConfirmOutButton() {
        return confirmOutButton;
    }

    public void setConfirmOutButton(Boolean confirmOutButton) {
        this.confirmOutButton = confirmOutButton;
    }

    public Boolean getCloseOrderButton() {
        return closeOrderButton;
    }

    public void setCloseOrderButton(Boolean closeOrderButton) {
        this.closeOrderButton = closeOrderButton;
    }

    public Boolean getExamFlowButton() {
        return examFlowButton;
    }

    public void setExamFlowButton(Boolean examFlowButton) {
        this.examFlowButton = examFlowButton;
    }

    public String getNextDepName() {
        return nextDepName;
    }

    public void setNextDepName(String nextDepName) {
        this.nextDepName = nextDepName;
    }

    public String getOrderStateInfo() {
        return orderStateInfo;
    }

    public void setOrderStateInfo(String orderStateInfo) {
        this.orderStateInfo = orderStateInfo;
    }

    public Integer getExType() {
        return exType;
    }

    public void setExType(Integer exType) {
        this.exType = exType;
    }

    public Integer getExOrderId() {
        return exOrderId;
    }

    public void setExOrderId(Integer exOrderId) {
        this.exOrderId = exOrderId;
    }

    public List<OrderItems> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItems> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "EcStoepVo{" +
                "detailsFlag=" + detailsFlag +
                ", id=" + id +
                ", ecNo='" + ecNo + '\'' +
                ", saleNo='" + saleNo + '\'' +
                ", createdate=" + createdate +
                ", amount=" + amount +
                ", dataSource=" + dataSource +
                ", customerName='" + customerName + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", outStorePer='" + outStorePer + '\'' +
                ", outStoreTime=" + outStoreTime +
                ", payMode='" + payMode + '\'' +
                ", payer='" + payer + '\'' +
                ", payee='" + payee + '\'' +
                ", sentPer='" + sentPer + '\'' +
                ", openBillStore='" + openBillStore + '\'' +
                ", payType='" + payType + '\'' +
                ", saleDate=" + saleDate +
                ", lib='" + lib + '\'' +
                ", totalQnty=" + totalQnty +
                ", discountAmount=" + discountAmount +
                ", actualAmount=" + actualAmount +
                ", ifCheck=" + ifCheck +
                ", ifOutStore=" + ifOutStore +
                ", exOrderSign=" + exOrderSign +
                ", redPackAmount=" + redPackAmount +
                ", ifClose=" + ifClose +
                ", tCost=" + tCost +
                ", tPrice=" + tPrice +
                ", dPrice=" + dPrice +
                ", tNo='" + tNo + '\'' +
                ", tMan='" + tMan + '\'' +
                ", pMethod='" + pMethod + '\'' +
                ", dLibrary=" + dLibrary +
                ", orderState=" + orderState +
                ", stockStatus=" + stockStatus +
                ", verifyStatus=" + verifyStatus +
                ", discountP=" + discountP +
                ", productName='" + productName + '\'' +
                ", sn='" + sn + '\'' +
                ", unit='" + unit + '\'' +
                ", unitState='" + unitState + '\'' +
                ", noValency=" + noValency +
                ", salesPrice=" + salesPrice +
                ", disPrice=" + disPrice +
                ", money=" + money +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", reArea='" + reArea + '\'' +
                ", exErpOrderId=" + exErpOrderId +
                ", ifReturn=" + ifReturn +
                ", remark='" + remark + '\'' +
                ", unitE=" + unitE +
                ", returnNum=" + returnNum +
                ", quantity=" + quantity +
                ", returnAllButton=" + returnAllButton +
                ", returnSingleButton=" + returnSingleButton +
                ", redPacketButton=" + redPacketButton +
                ", examButton=" + examButton +
                ", checkButton=" + checkButton +
                ", confirmOutButton=" + confirmOutButton +
                ", closeOrderButton=" + closeOrderButton +
                ", examFlowButton=" + examFlowButton +
                ", nextDepName='" + nextDepName + '\'' +
                ", orderStateInfo='" + orderStateInfo + '\'' +
                ", exType=" + exType +
                ", exOrderId=" + exOrderId +
                ", orderItemList=" + orderItemList +
                '}';
    }
}
