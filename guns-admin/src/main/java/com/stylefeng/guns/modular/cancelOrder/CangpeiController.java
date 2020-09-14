package com.stylefeng.guns.modular.cancelOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.Base64Util;
import com.stylefeng.guns.core.util.HttpClientProxy;
import com.stylefeng.guns.core.util.HttpRequest;
import com.stylefeng.guns.modular.cancelOrder.util.CategoryUtil;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.erpOrderItem.service.IErpOrderItemService;
import com.stylefeng.guns.modular.erpOrderItem.service.impl.ErpOrderItemServiceImplWrap;
import com.stylefeng.guns.modular.erpProductStockArea.service.impl.ErpProductStockAreaServiceImplWrap;
import com.stylefeng.guns.modular.erpSaleOrder.service.IErpSaleOrderService;
import com.stylefeng.guns.modular.erpSaleOrder.service.impl.ErpSaleOrderSeriverImpWrap;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.exOrderExam.service.IExOrderExamService;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.system.dao.EcErpOrderMapper;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */

@Controller
@RequestMapping("/cancelOrder/cangpei")
public class CangpeiController extends BaseController {

    private String PREFIX = "/cancel/order/";

    @Autowired
    private IEcErpOrderService ecErpOrderService;

    @Autowired
    private IExOrderService exOrderService;

    @Qualifier("deptServiceImpl")
    @Autowired
    private IDeptService iDeptService;

    @Autowired
    private IExOrderExamService iExOrderExamService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private OrderController orderController;

    @Autowired
    private ErpSaleOrderSeriverImpWrap erpSaleOrderSeriverImpWrap;

    @Autowired
    private ErpOrderItemServiceImplWrap erpOrderItemServiceImplWrap;

    @Autowired
    ErpProductStockAreaServiceImplWrap erpProductStockAreaServiceImplWrap;

    @Autowired
    private IErpSaleOrderService erpSaleOrderService;

    @Autowired
    private IErpOrderItemService erpOrderItemService;

    @Autowired
    private EcErpOrderMapper ecErpOrderMapper;


    private static String url = "http://www.jinxiaochao.com";

    @RequestMapping("/detail/{orderId}")
    @ResponseBody
    public EcErpOrderVo detail(@PathVariable Long orderId, Integer detailsFlag) throws ParseException {
        //本地数据
        if (detailsFlag == 1) {
            return orderController.orderItemDetail(orderId);
        }
        //电商
        if (detailsFlag == 2) {
            Map<String, Object> map = new HashMap<>();
            map.put("exErpOrderId", orderId);
            Long exErpOrderId = (Long) map.get("exErpOrderId");
            EcErpOrder ecErpOrder = new EcErpOrder();
            EcErpOrderVo ecErpOrderVo = new EcErpOrderVo();
            StoepVo stoepVo = getView(exErpOrderId);
            ecErpOrderVo.setOrderItemList(stoepVo.getOrderItemList());
            ecErpOrderVo.setCustomerName(stoepVo.getCName()); //客戶名稱
            ecErpOrderVo.setDeliveryAddress(stoepVo.getRAddress());//收货地址
            ecErpOrderVo.setCreatedate(stoepVo.getCreatedate());//创建时间
            ecErpOrderVo.setSaleDate(stoepVo.getCreatedate());//销售日期
            ecErpOrderVo.setActualAmount(stoepVo.getActualAmount());
            ecErpOrderVo.setLib(stoepVo.getLib());//库区
            ecErpOrderVo.setEcNo(stoepVo.getSn());//电商单号
            ecErpOrderVo.setPayType(stoepVo.getPayType());//付款方式
            ecErpOrderVo.setTotalAmount(stoepVo.getMoney());//总额
            ecErpOrderVo.setTMan(stoepVo.getTMan());//收货人
            ecErpOrderVo.setOpenBillStore(stoepVo.getOpenBillStore());//开单门店
            System.out.println(ecErpOrderVo);
            return ecErpOrderVo;
        }

        //erp
        if (detailsFlag == 3) {
            List<ErpSaleOrder> erpSaleOrderList = new ArrayList<>();
            EcErpOrder ecErpOrder = new EcErpOrder();
            ErpSaleOrder erpSaleOrder = erpSaleOrderSeriverImpWrap.selectById(orderId);
            transferOrder(ecErpOrder, erpSaleOrder);
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            List<OrderItem> orderItemList = new ArrayList<>();

            long start = System.currentTimeMillis();
            List<ErpOrderItem> erpOrderItemList = erpOrderItemServiceImplWrap.selectByOrderId(orderId);
            long end = System.currentTimeMillis();
            System.out.println("共用时：" + (end - start));

            for (ErpOrderItem erpOrderItem : erpOrderItemList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(erpOrderItem.getPName());//商品名称
                orderItem.setSn(erpOrderItem.getBarCode());//二维码
//                orderItem.setLib(erpOrderItem.getLibrary());//库区
//                orderItem.setKuwei(erpOrderItem.getKuwei());//库位
                orderItem.setUnit(erpOrderItem.getUnit());//单位
                orderItem.setModel(erpOrderItem.getSpec());//规格型号
                orderItem.setAmount(erpOrderItem.getNum());//数量
                orderItem.setUnitE(erpOrderItem.getUnitE());//换算数量
                orderItem.setNoValency(erpOrderItem.getNPrice());//正常价
                orderItem.setSalesPrice(erpOrderItem.getSPrice());//销售单价
                orderItem.setDisPrice(erpOrderItem.getDPrice());//折后价
                orderItem.setMoney(erpOrderItem.getMoney());//总金额
                orderItem.setBrand(erpOrderItem.getBrands());//品牌
                orderItem.setReArea(erpOrderItem.getMadeIn());//产地
//                orderItem.setRemark(erpOrderItem.getRemark());//备注
                orderItem.setProductCategoryId(erpOrderItem.getPCategoryId());

                //根据商品id查询对应库位信息
                List<ErpProductStockArea> erpProductStockAreaList = erpProductStockAreaServiceImplWrap.queryStoreArea(erpOrderItem.getProduct());
                String remark ="";
                if(erpProductStockAreaList.size() > 0){
                for (ErpProductStockArea erpProductStockArea : erpProductStockAreaList){
                      remark += erpProductStockArea.getStockAreaName()+"<br>";
                }
                    remark = remark.substring(0, remark.length() - 4);
                    orderItem.setRemark(remark);
                }

                //从erp中获取电商单号
                String sn = erpSaleOrder.getZqpSn();
                //测试环境
//                String s = HttpRequest.httpRequest("https://www.jxcdemo.com/app/api/order/view.jhtml?sn="+sn);
                //正式环境
                String s = HttpRequest.httpRequest("https://www.jinxiaochao.com/app/api/order/view.jhtml?sn="+sn);
                JSONObject obj = JSON.parseObject(s);
                JSONObject message = obj.getJSONObject("message");//通过key去获取value
                String  type = message.getString("type");//通过key去获取value

                String memo="";
                if (type.equals("success")){
                    JSONObject data = obj.getJSONObject("data");//通过key去获取value
                     memo = data.getString("memo");
                }
                ecErpOrderVo.setMemo(memo);


                orderItemList.add(orderItem);
//                orderItemList.sort((o1, o2) -> (o1.getProductCategoryId() - o2.getProductCategoryId()));


            }
//            for (OrderItem orderItem : orderItemList) {
//                orderItem.setProductCategoryId(CategoryUtil.getBigcategory(orderItem.getProductCategoryId()));
//            }
//            Collections.sort(orderItemList, Comparator.comparingInt(OrderItem::getProductCategoryId));
            //排序
            Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

            orderItemList.sort((o1,o2) -> {

                if (o1.getRemark() == o2.getRemark()) {
                    return 0;
                } else if (o1.getRemark() == null) {
                    return 1;
                } else if (o2.getRemark() == null) {
                    return -1;
                }
                //比较的基本原则，拿最小长度的字符串进行比较，若全部相等，则长字符串往后排
                int len1 = o1.getRemark().length();
                int len2 = o2.getRemark().length();


                int len = (len1 - len2) <= 0 ? len1 : len2;
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < len; i++) {
                    String s1 = o1.getRemark().substring(i, i + 1);
                    String s2 = o2.getRemark().substring(i, i + 1);
                    if (isNumeric(s1) && isNumeric(s2)){
                        //取出所有的数字
                        sb1.append(s1);
                        sb2.append(s2);
                        //取数字时，不比较
                        continue;
                    }

                    if (sb1.length() != 0 && sb2.length() != 0){
                        if (!isNumeric(s1) && !isNumeric(s2)){
                            int value1 = Integer.valueOf(sb1.toString());
                            int value2 = Integer.valueOf(sb2.toString());
                            return value1 - value2;
                        } else if (isNumeric(s1)) {
                            return 1;
                        } else if (isNumeric(s2)) {
                            return -1;
                        }
                    }
                    int result = CHINA_COMPARE.compare(s1, s2);
                    if (result != 0) {
                        return result;
                    }
                }
                //这一步：是为了防止以下情况：第10  第20，正好以数字结尾，且字符串长度相等
                if (len1 == len2 && sb1.length() != 0 && sb2.length() != 0) {
                    int value1 = Integer.valueOf(sb1.toString());
                    int value2 = Integer.valueOf(sb2.toString());
                    return value1 - value2;
                }
                //若前面都相等，则直接比较字符串的长度，长的排后面，短的排前面
                return Integer.compare(len1, len2);
            });

            ecErpOrderVo.setOrderItemList(orderItemList);
            return ecErpOrderVo;
        }
        return null;
    }

    //判断是否是数字
    private static boolean isNumeric(String s) {
        return Character.isDigit(s.charAt(0));
    }


    /**
     * 订单管理详情
     */
    @RequestMapping(value = "/detail")
    public Object detail(Integer ecErpOrderId) {
        return PREFIX + "cangPeiOrderDetail.html";
    }

    /**
     * 待出库订单
     */
    @RequestMapping("")
    public String cangpei() {
        return PREFIX + "cangpei_orderList.html";
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper();
        System.out.println();
        wrapper.orderBy("sale_date", false);
        Page<ErpSaleOrder> erpSaleOrderPage = erpSaleOrderSeriverImpWrap.selectPage(page, wrapper);

        List<EcErpOrderVo> erpOrderVos = transOrder(erpSaleOrderPage.getRecords(), 3);


        //暂时注销电商数据


        long st = System.currentTimeMillis();
        List<EcErpOrderVo> ecOrderVos = transOrderEc(2, pageNumber, pageSize);
        long ed = System.currentTimeMillis();
        System.out.println("：" + (ed - st));

        //非空判断
        if (ecOrderVos != null & ecOrderVos.size() > 0) {
            //得到电商订单
            List ecOrderVoEcSnList = new ArrayList();
            for (EcErpOrderVo ecErpOrderVo : ecOrderVos) {
                ecOrderVoEcSnList.add(ecErpOrderVo.getEcNo());
            }
            wrapper = new EntityWrapper();
            wrapper.in("zqp_sn", ecOrderVoEcSnList);
            List<ErpSaleOrder> deList = erpSaleOrderSeriverImpWrap.selectList(wrapper);
            //删除重复电商订单

            for (int i = ecOrderVos.size() - 1; i >= 0; i--) {
                for (ErpSaleOrder erpSaleOrder : deList) {
                    if (erpSaleOrder.getZqpSn().equals(ecOrderVos.get(i).getEcNo())) {
                        ecOrderVos.remove(i);
                        break;
                    }
                }
                continue;
            }
            //合并数据
            erpOrderVos.addAll(ecOrderVos);
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", erpSaleOrderPage.getTotal());
        map.put("rows", erpOrderVos);
        return map;
    }

    /**
     * 主页各类型订单数量
     */
    @RequestMapping("/orderNumber")
    @ResponseBody
    public OrderNumberVo orderNumber(Date startTime, Date endTime) {
        OrderNumberVo orderNumberVo = new OrderNumberVo();
        Integer depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();
        //全部订单
        System.out.println("查看全部订单开始");
        int allSize = erpSaleOrderSeriverImpWrap.selectCount(null);
        orderNumberVo.setAllNumber(allSize);
        //已关闭订单
        Wrapper ygbWrapper = new EntityWrapper<ExOrder>().eq("if_close", true);
        orderNumberVo.setCloseOrderNumber(ecErpOrderService.selectCount(ygbWrapper));
        //已出库订单
        Wrapper yckWrapper = new EntityWrapper<ExOrder>().eq("if_out_store", true);
        orderNumberVo.setOutStoreNumber( ecErpOrderService.selectCount(yckWrapper));
        //待出库订单
        if (startTime != null && endTime != null) {
            orderNumberVo.setNotOutStoreNumber(dck(startTime, endTime).size());
        }
        //已审核
        Wrapper yshWrapper = new EntityWrapper();
        orderNumberVo.setExamNumber(iExOrderExamService.selectCount(yshWrapper));
        //未审核
        Wrapper dshWrapper = new EntityWrapper<ExOrder>().eq("exam_dep_id", depId);
        orderNumberVo.setNotExamNumber(exOrderService.selectCount(dshWrapper));
        return orderNumberVo;
    }

    /**
     * 已关闭订单
     */
    @RequestMapping("/ygb")
    @ResponseBody
    public Object ygb(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper<ExOrder>().eq("if_close", true);
        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, wrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setOrderStateInfo("本订单关闭");
            ecErpOrderVo.setDetailsFlag(1);
            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 待关闭订单
     */
    @RequestMapping("/dgb")
    @ResponseBody
    public List<EcErpOrderVo> dgb(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper dgbWrapper = new EntityWrapper<ExOrder>().eq("if_close", false);
        List<EcErpOrder> list = ecErpOrderService.selectList(dgbWrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : list) {
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setOrderStateInfo("本订单关闭");
            ecErpOrderVos.add(ecErpOrderVo);
        }
        return ecErpOrderVos;
    }

    /**
     * 待出库订单
     */
    @RequestMapping("/dck")
    @ResponseBody
    public List<EcErpOrderVo> dck(Date startTime, Date endTime) {
        Wrapper wrapper = new EntityWrapper();
        long start = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        wrapper.between("sale_date", startTime, endTime);
        wrapper.orderBy("sn", false);
        List<ErpSaleOrder> allErpList = erpSaleOrderSeriverImpWrap.selectList(wrapper);
        System.out.println("待出库erp订单：" + (System.currentTimeMillis() - start));
        //查询erp已出库订单
        List<String> erpListSn = new ArrayList<>();
        for (ErpSaleOrder erpSaleOrder : allErpList) {
            erpListSn.add(erpSaleOrder.getSn());
        }
        wrapper = new EntityWrapper();
        wrapper.in("sale_no", erpListSn);
        List<EcErpOrder> deErpList = ecErpOrderService.selectList(wrapper);
        //删除重复erp订单
        System.out.println(allErpList.size());
        for (int i = allErpList.size() - 1; i >= 0; i--) {
            for (EcErpOrder ecErpOrder : deErpList) {
                if (allErpList.get(i).getSn().equals(ecErpOrder.getSaleNo())) {
                    allErpList.remove(i);
                    break;
                }
            }
        }

        List<EcErpOrderVo> ecErpOrderVoList = transOrder(allErpList, 3);
        for (EcErpOrderVo ecErpOrderVo : ecErpOrderVoList) {
            ecErpOrderVo.setConfirmOutButton(true);
            ecErpOrderVo.setCloseOrderButton(true);
        }

//        电商数据暂时注销
//        //电商订单列表
//        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
//        start = System.currentTimeMillis();
//        List<EcStoepVo> list = ecSelectByTime(startTime, endTime);
//        System.out.println("待出库电商订单：" + (System.currentTimeMillis() - start));
//        for (EcStoepVo ecStoepVo : list) {
//            EcErpOrderVo ecErpOrderVo = new EcErpOrderVo();
//            ecErpOrderVo.setId(ecStoepVo.getId());
//            ecErpOrderVo.setEcNo(ecStoepVo.getEcNo());//电商单号
//            ecErpOrderVo.setSaleNo(""); //erp单号
//            ecErpOrderVo.setCreatedate(ecStoepVo.getCreatedate());//销售时间
//            ecErpOrderVo.setAmount(ecStoepVo.getMoney());//总金额
//            ecErpOrderVo.setCustomerName(ecStoepVo.getCustomerName()); //客户名称
//            ecErpOrderVo.setDataSource(1); //订单来源
//            ecErpOrderVo.setDetailsFlag(2); //查询详情标志
//            ecErpOrderVos.add(ecErpOrderVo);
//        }


//        //电商订单erp去重
//        List<String> ecListSn = new ArrayList<>();
//        for (EcErpOrder ecErpOrder : ecErpOrderVos) {
//            erpListSn.add(ecErpOrder.getEcNo());
//        }
//        wrapper = new EntityWrapper();
//        wrapper.in("zqp_sn", ecListSn);
//        List<ErpSaleOrder> deEcToErpList = erpSaleOrderSeriverImpWrap.selectList(wrapper);
//
//        for (int i = ecErpOrderVos.size() - 1; i >= 0; i--) {
//            for (ErpSaleOrder erpSaleOrder : deEcToErpList) {
//                if (ecErpOrderVos.get(i).getEcNo().equals(erpSaleOrder.getZqpSn())) {
//                    ecErpOrderVos.remove(i);
//                    break;
//                }
//            }
//        }
//        //电商订单本地库去重
//        ecListSn = new ArrayList<>();
//        for (EcErpOrder ecErpOrder : ecErpOrderVos) {
//            erpListSn.add(ecErpOrder.getEcNo());
//        }
//
//        wrapper = new EntityWrapper();
//        wrapper.in("ec_no", ecListSn);
//        List<EcErpOrder> deEcToLocalList = ecErpOrderService.selectList(wrapper);
//
//        for (int i = ecErpOrderVos.size() - 1; i >= 0; i--) {
//            for (EcErpOrder ecErpOrder : deEcToLocalList) {
//                if (ecErpOrderVos.get(i).getEcNo().equals(ecErpOrder.getEcNo())) {
//                    ecErpOrderVos.remove(i);
//                    break;
//                }
//            }
//        }
//
//        for (EcErpOrderVo ecErpOrderVo : ecErpOrderVos) {
//            ecErpOrderVo.setConfirmOutButton(true);
//            ecErpOrderVo.setCloseOrderButton(true);
//        }
//        ecErpOrderVoList.addAll(ecErpOrderVos);

        return ecErpOrderVoList;
    }

    /**
     * 已出库订单
     */
    @RequestMapping("/yck")
    @ResponseBody
    public Object yck(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper<ExOrder>().eq("if_out_store", true)
                .orderBy("enter_system_time", false);
        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, wrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setOrderStateInfo("订单已出库");
            ecErpOrderVo.setDetailsFlag(1);
            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 转换数据
     */
    public List<EcErpOrderVo> transOrder(List<ErpSaleOrder> erpSaleOrderList, Integer detailsFlag) {
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (ErpSaleOrder erpSaleOrder : erpSaleOrderList) {
            EcErpOrderVo ecErpOrderVo = new EcErpOrderVo();
            ecErpOrderVo.setId(erpSaleOrder.getId());
            ecErpOrderVo.setEcNo(erpSaleOrder.getZqpSn());//电商单号
            ecErpOrderVo.setSaleNo(erpSaleOrder.getSn()); //erp单号
            ecErpOrderVo.setCreatedate(erpSaleOrder.getSaleDate());//销售时间
            ecErpOrderVo.setTotalAmount(erpSaleOrder.getTotalPrice());//总金额
            ecErpOrderVo.setCustomerName(erpSaleOrder.getCName()); //客户名称
            ecErpOrderVo.setDataSource(2); //订单来源
            ecErpOrderVo.setDetailsFlag(detailsFlag);
            ecErpOrderVos.add(ecErpOrderVo);
        }
        return ecErpOrderVos;
    }

    public List<EcErpOrderVo> transOrderEc(Integer detailsFlag, int pageNumber, int pageSize) {
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        List<EcStoepVo> list = ecSelectPage(pageNumber, pageSize);
        for (EcStoepVo ecStoepVo : list) {
            EcErpOrderVo ecErpOrderVo = new EcErpOrderVo();
            ecErpOrderVo.setId(ecStoepVo.getId());
            ecErpOrderVo.setEcNo(ecStoepVo.getEcNo());//电商单号
            ecErpOrderVo.setSaleNo(""); //erp单号
            ecErpOrderVo.setCreatedate(ecStoepVo.getCreatedate());//销售时间
            ecErpOrderVo.setTotalAmount(ecStoepVo.getMoney());//总金额
            ecErpOrderVo.setCustomerName(ecStoepVo.getCustomerName()); //客户名称
            ecErpOrderVo.setDataSource(1); //订单来源
            ecErpOrderVo.setDetailsFlag(detailsFlag);
            ecErpOrderVos.add(ecErpOrderVo);
        }
        return ecErpOrderVos;
    }

    /**
     * 关闭订单
     */
    @RequestMapping("/closeOrder")
    @ResponseBody
    public Tip closeOrder(Long ecErpOrderId) throws ParseException {

        ErpSaleOrder erpSaleOrder = erpSaleOrderSeriverImpWrap.selectById(ecErpOrderId);
        EcErpOrder ecErpOrder = new EcErpOrder();
        if (erpSaleOrder != null) {
            transferOrder(ecErpOrder, erpSaleOrder);//初始化数据
            ecErpOrder.setIfCheck(false);//是否审核
            ecErpOrder.setIfClose(true);//是否关闭
            ecErpOrder.setIfOutStore(false);//是否出库
            ecErpOrderService.insert(ecErpOrder);
            //查询订单项,同步插入订单项
            List<ErpOrderItem> erpOrderItemList = erpOrderItemServiceImplWrap.selectByOrderId(ecErpOrderId);
            insertItemList(erpOrderItemList, ecErpOrder.getId());
            return SUCCESS_TIP;
        } else {
            StoepVo stoepVo = getView(ecErpOrderId);
            transferOrder2(ecErpOrder, stoepVo);
            ecErpOrder.setIfCheck(false);//是否审核
            ecErpOrder.setIfClose(true); //是否关闭
            ecErpOrder.setIfOutStore(false);//是否出库
            ecErpOrderService.insert(ecErpOrder);
            List<OrderItem> orderItemList = stoepVo.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                orderItem.setExErpOrderId(ecErpOrder.getId());
                iOrderItemService.insert(orderItem);
            }
            return SUCCESS_TIP;
        }
    }



//    /**
//     * 确认出库
//     */
//    @RequestMapping("/confirmOutStoreTTTTT")
//    @ResponseBody
//    public Tip confirmOutStoreTTTTT(long saleOrderId,long ecErpOrderId)  {
//        List<ErpOrderItem> erpOrderItemList = erpOrderItemServiceImplWrap.selectByOrderId(saleOrderId);
//        insertItemList(erpOrderItemList, ecErpOrderId);
//        return null;
//    }





    /**
     * 确认出库
     */
    @RequestMapping("/confirmOutStore")
    @ResponseBody
    public Tip confirmOutStore(EcErpOrder ecErpOrder) throws ParseException {
        long ecErpOrderId = ecErpOrder.getId();
        ErpSaleOrder erpSaleOrder = erpSaleOrderSeriverImpWrap.selectById(ecErpOrder.getId());
//        long st = System.currentTimeMillis();
        if (erpSaleOrder != null) {
            transferOrder(ecErpOrder, erpSaleOrder);//初始化数据
            ecErpOrder.setIfCheck(false);//是否核销
            ecErpOrder.setIfClose(false); //是否关闭
            ecErpOrder.setExOrderSign(false);//异常订单标识符
            ecErpOrder.setIfOutStore(true);//是否出库
            ecErpOrderService.insert(ecErpOrder);
            //查询订单项,同步插入订单项
            List<ErpOrderItem> erpOrderItemList = erpOrderItemServiceImplWrap.selectByOrderId(ecErpOrderId);
            insertItemList(erpOrderItemList, ecErpOrder.getId());
//            long ed = System.currentTimeMillis();
//            System.out.println("erp出库所用时间：" + (ed - st));
            return SUCCESS_TIP;
        } else {
            StoepVo stoepVo = getView(ecErpOrderId);
            transferOrder2(ecErpOrder, stoepVo);
            ecErpOrder.setIfCheck(false);//是否审核
            ecErpOrder.setIfClose(false); //是否关闭
            ecErpOrder.setExOrderSign(false);//设置异常标识
            ecErpOrder.setIfOutStore(true);//是否出库
            ecErpOrderService.insert(ecErpOrder);
            List<OrderItem> orderItemList = stoepVo.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                orderItem.setExErpOrderId(ecErpOrder.getId());
                iOrderItemService.insert(orderItem);
            }
            return SUCCESS_TIP;
        }
    }

    /**
     * 确认出库(批量)
     */
    @PostMapping("/confirmOutStoreBatch")
    @ResponseBody
    public Tip confirmOutStoreBatch(@RequestBody List<EcErpOrder> ecErpOrderRoList) throws ParseException {
        //遍历循环分成两组一组，一组走erp
        List<EcErpOrder> erpList = new ArrayList();
        List<EcErpOrder> ecList = new ArrayList();
        List<Long> erpIdList = new ArrayList<>();//erpId集合
        for (EcErpOrder ecErpOrder : ecErpOrderRoList) {
            if (ecErpOrder.getDataSource() == 1) {
                ecList.add(ecErpOrder);
            } else {
                erpIdList.add(ecErpOrder.getId());
                erpList.add(ecErpOrder);
            }
        }

        //出库人，出库时间
        Date outTime = ecErpOrderRoList.get(0).getOutStoreTime();
        String outPerson = ecErpOrderRoList.get(0).getOutStorePer();

        //erp出库
        if (erpList.size() > 0) {
            Wrapper wrapper = new EntityWrapper();
            wrapper.in("id", erpIdList);
            //查询全部erp订单
            List<ErpSaleOrder> erpSaleOrderList = erpSaleOrderSeriverImpWrap.selectList(wrapper);
            for (ErpSaleOrder erpSaleOrder : erpSaleOrderList) {
                EcErpOrder ecErpOrder = new EcErpOrder();
                ecErpOrder.setOutStoreTime(outTime);
                ecErpOrder.setOutStorePer(outPerson);
                transferOrder(ecErpOrder, erpSaleOrder);
                ecErpOrder.setIfCheck(false);//是否审核
                ecErpOrder.setIfClose(false); //是否关闭
                ecErpOrder.setExOrderSign(false);//设置异常标识
                ecErpOrder.setIfOutStore(true);//是否出库
                //查询订单项,同步插入订单项
                List<ErpOrderItem> erpOrderItemList = erpOrderItemServiceImplWrap.selectByOrderId(erpSaleOrder.getId());
                //批量插入
                ecErpOrderService.insert(ecErpOrder);
                insertItemList(erpOrderItemList, ecErpOrder.getId());
            }
            return SUCCESS_TIP;
        }
        //电商出库
        if (ecList.size() > 0) {
            for (EcErpOrder ecErpOrder : ecList) {
                StoepVo stoepVo = getView(ecErpOrder.getId());
                transferOrder2(ecErpOrder, stoepVo);
                ecErpOrder.setIfCheck(false);//是否审核
                ecErpOrder.setIfClose(false); //是否关闭
                ecErpOrder.setExOrderSign(false);//设置异常标识
                ecErpOrder.setIfOutStore(true);//是否出库
                ecErpOrderService.insert(ecErpOrder);
                List<OrderItem> orderItemList = stoepVo.getOrderItemList();
                for (OrderItem orderItem : orderItemList) {
                    orderItem.setExErpOrderId(ecErpOrder.getId());
                    iOrderItemService.insert(orderItem);
                }
            }

            return SUCCESS_TIP;
        }
        return SUCCESS_TIP;
    }


    public boolean insertItemList(List<ErpOrderItem> erpOrderItemList, Long ecErpOrderId) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (ErpOrderItem erpOrderItem : erpOrderItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setExErpOrderId(ecErpOrderId);//订单号
            orderItem.setProductName(erpOrderItem.getPName());//商品名称
            orderItem.setSn(erpOrderItem.getBarCode());//二维码
            orderItem.setLib(erpOrderItem.getLibrary());//库区
//            orderItem.setKuwei(erpOrderItem.getKuwei());//库位
            orderItem.setUnit(erpOrderItem.getUnit());//单位
            orderItem.setModel(erpOrderItem.getSpec());//规格型号
            orderItem.setAmount(erpOrderItem.getNum());//数量
            orderItem.setUnitE(erpOrderItem.getUnitE());//换算数量
            orderItem.setNoValency(erpOrderItem.getNPrice());//正常价
            orderItem.setSalesPrice(erpOrderItem.getSPrice());//销售单价
            orderItem.setDisPrice(erpOrderItem.getDPrice());//折后价
            orderItem.setMoney(erpOrderItem.getMoney());//总金额
            orderItem.setBrand(erpOrderItem.getBrands());//品牌
            orderItem.setReArea(erpOrderItem.getMadeIn());//产地
            orderItem.setRemark(erpOrderItem.getRemark());//备注
            orderItem.setProductCategoryId(erpOrderItem.getPCategoryId());
            orderItemList.add(orderItem);//添加订单
        }
        //批量插入
        iOrderItemService.insertBatch(orderItemList);
        return true;
    }

    public EcErpOrder transferOrder(EcErpOrder ecErpOrder, ErpSaleOrder erpSaleOrder) {
        ecErpOrder.setDataSource(2);//数据来源erp
        ecErpOrder.setEcNo(erpSaleOrder.getZqpSn());//电商单号
        ecErpOrder.setSaleNo(erpSaleOrder.getSn()); //erp单号
        ecErpOrder.setCreatedate(erpSaleOrder.getSaleDate());//销售时间
        ecErpOrder.setCustomerName(erpSaleOrder.getCName()); //客户名称
        ecErpOrder.setDeliveryAddress(erpSaleOrder.getRAddress());//收货地址
        ecErpOrder.setTMan(erpSaleOrder.getTMan());//收货人
        ecErpOrder.setPayType(erpSaleOrder.getPMethod());//付款方式
//        ecErpOrder.setOutStoreTime(new Date());//设置出库时间
//        ecErpOrder.setOpenBillStore(erpSaleOrder.getOperator());//开单门店
        ecErpOrder.setSaleDate(erpSaleOrder.getSaleDate());//销售时间
//        ecErpOrder.setPayMode(erpSaleOrder.getPMethod());//结算方式
//        ecErpOrder.setLib(erpSaleOrder.getDLibrary());//库区
//        ecErpOrder.setTotalQnty(erpSaleOrder.get);//总数量
//        ecErpOrder.setDiscountAmount();//优惠金额
        if (erpSaleOrder.getDPrice() != null) {
            ecErpOrder.setActualAmount(erpSaleOrder.getDPrice());//实际付款金额
            ecErpOrder.setDiscountAmount(erpSaleOrder.getTPrice().subtract(erpSaleOrder.getDPrice()));
        } else {
            ecErpOrder.setActualAmount(erpSaleOrder.getTPrice());//实际付款金额
            ecErpOrder.setDiscountAmount(new BigDecimal(0));
        }
        ecErpOrder.setTotalAmount(erpSaleOrder.getTPrice());//总金额
        ecErpOrder.setTPrice(erpSaleOrder.getTPrice());//优惠前金额(加运费)
        ecErpOrder.setDPrice(erpSaleOrder.getDPrice());//优惠后金额(加运费)
        ecErpOrder.setTCost(erpSaleOrder.getTCost()); //运费
        ecErpOrder.setTNo(erpSaleOrder.getTNo());//承运单号
        ecErpOrder.setTMan(erpSaleOrder.getTMan());//收货人
        ecErpOrder.setPMethod(erpSaleOrder.getPMethod());//收款方式
        ecErpOrder.setOrderState(erpSaleOrder.getOrderStatus());//订单状态
        ecErpOrder.setStockStatus(erpSaleOrder.getStockStatus());//出库状态
        ecErpOrder.setVerifyStatus(erpSaleOrder.getVerifyStatus());//审核状态
        ecErpOrder.setDiscountP(erpSaleOrder.getDiscountP());//折扣百分比
        return ecErpOrder;
    }

    public EcErpOrder transferOrder2(EcErpOrder ecErpOrder, StoepVo stoepVo) {
        ecErpOrder.setDataSource(1);//数据来源電商
        ecErpOrder.setEcNo(String.valueOf(stoepVo.getSn()));//电商单号
        ecErpOrder.setCreatedate(stoepVo.getCreatedate());//销售时间
        ecErpOrder.setTotalAmount(stoepVo.getTotalPrice());//总金额
        ecErpOrder.setCustomerName(stoepVo.getCName()); //客户名称
        ecErpOrder.setDeliveryAddress(stoepVo.getRAddress());//收货地址
        ecErpOrder.setTMan(stoepVo.getTMan());//收货人
        ecErpOrder.setPMethod(stoepVo.getPayType());//收款方式
        ecErpOrder.setOutStoreTime(new Date());//设置出库时间
        ecErpOrder.setOpenBillStore(stoepVo.getOpenBillStore());//开单门店
        ecErpOrder.setSaleDate(stoepVo.getCreatedate());//销售时间
        ecErpOrder.setPayMode(stoepVo.getPMethod());//结算方式
        ecErpOrder.setLib(stoepVo.getLib());//库区
        ecErpOrder.setTotalQnty(stoepVo.getTotalQnty());//总数量
        ecErpOrder.setActualAmount(stoepVo.getMoney());//实际付款金额
        ecErpOrder.setTMan(stoepVo.getTMan());//收货人
        ecErpOrder.setPMethod(stoepVo.getPMethod());//收款方式

//        ecErpOrder.setDiscountAmount();//优惠金额
        /*
        ecErpOrder.setTPrice(erpSaleOrder.getTPrice());//优惠前金额
        ecErpOrder.setDiscountAmount(erpSaleOrder.getTPrice().subtract(erpSaleOrder.getDPrice()));
        ecErpOrder.setDPrice(erpSaleOrder.getDPrice());//优惠后金额
        ecErpOrder.setTCost(erpSaleOrder.getTCost()); //运费
        ecErpOrder.setTNo(erpSaleOrder.getTNo());//承运单号

       */
        ecErpOrder.setOrderState(stoepVo.getOrderStatus());//订单状态
        ecErpOrder.setStockStatus(stoepVo.getStockStatus());//出库状态
        ecErpOrder.setVerifyStatus(stoepVo.getVerifyStatus());//审核状态
        return ecErpOrder;
    }


    public List<EcStoepVo> ecSelectPage(int pageNumber, int pageSize) {
        long start = System.currentTimeMillis();
        String st = HttpRequest.sendGet(url + "/bcs/member/trade/list.jhtml",
                "account=13389035585&token=" +
                        Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}")
                        + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize
        );
        return ecJsonToOrderList(st);
    }

    public List<EcStoepVo> ecSelectByTime(Date beginDate, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String start = sdf.format(beginDate);
        String end = sdf.format(endTime);

        long milli1 = System.currentTimeMillis();
        System.out.println();
        String st = HttpRequest.sendGet(url + "/bcs/member/trade/list.jhtml",
                "account=13389035585&token=" +
                        Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}")
                        + "&pageNumber=" + 1 + "&pageSize=" + Integer.MAX_VALUE
                        + "&beginDate=" + start + "&endTime=" + end
        );
        long milli2 = System.currentTimeMillis();
        System.out.println(milli2 - milli1);
        System.out.println(beginDate);
        System.out.println(endTime);
        System.out.println(beginDate.getTime());
        return ecJsonToOrderList(st);

    }


    public List<EcStoepVo> ecJsonToOrderList(String st) {
        JSONObject json = JSONObject.parseObject(st);
        long ed = System.currentTimeMillis();
        String data = json.getString("data");
        JSONObject data1 = JSONObject.parseObject(data);
        JSONArray json2 = data1.getJSONArray("list");
        List<EcStoepVo> ecStoepVoList = new ArrayList<EcStoepVo>();
        for (int i = 0; i < json2.size(); i++) {
            JSONObject list = (JSONObject) json2.get(i);
            EcStoepVo ecStoepVo = new EcStoepVo();
            ecStoepVo.setId(Long.valueOf(list.getString("id")));//id
            ecStoepVo.setEcNo(list.getString("sn"));//电商单号
            ecStoepVo.setSaleNo("");//销售单号
            ecStoepVo.setCreatedate(list.getDate("create_date"));//订单创建日期
            ecStoepVo.setAmount((BigDecimal) list.get("amount"));//金额
            ecStoepVo.setPaymentMethodName(list.getString("paymentMethodName"));//支付方式名称
            List<Map<String, OrderItems>> map = (List<Map<String, OrderItems>>) list.get("orderItems");
            Map<String, OrderItems> map2 = (Map<String, OrderItems>) map.get(0).get("product");
            Map<String, OrderItems> map3 = (Map<String, OrderItems>) map2.get("tenantInfoModel");
            ecStoepVo.setDataSource(1);//来源
            ecStoepVo.setCustomerName(list.getString("consignee"));//客户名称
            ecStoepVo.setDeliveryAddress(list.getString("address"));//收货地址
            ecStoepVo.setLib(String.valueOf(map2.get("area")));//库区
            ecStoepVo.setTotalQnty((Integer) list.get("quantity"));//总数量
            ecStoepVo.setQuantity(Integer.valueOf(String.valueOf(map.get(0).get("quantity"))));//数量
            ecStoepVo.setDiscountAmount((BigDecimal) list.get("discount"));//优惠金额
            ecStoepVo.settMan(list.getString("consignee"));//收货人
            ecStoepVo.setProductName(String.valueOf(map.get(0).get("fullName")));//商品名称
            ecStoepVo.setSn(String.valueOf(map2.get("pn")));//二维码
            ecStoepVo.setUnit(String.valueOf(map.get(0).get("unit")));//单位
            ecStoepVo.setModel(String.valueOf(map2.get("spec")));//规格型号
            // ecStoepVo.setNoValency((BigDecimal) list.get("price"));//正常价
//            ecStoepVo.setSalesPrice(BigDecimal.valueOf(Long.parseLong(String.valueOf(map2.get("price")))));//销售单价
            ecStoepVo.setMoney((BigDecimal) list.get("amount"));//总金额
            ecStoepVo.setBrand(String.valueOf(map2.get("brands")));//品牌
            ecStoepVo.setReArea(String.valueOf(map2.get("madeIn")));//产地
            ecStoepVo.setRemark(String.valueOf(map2.get("memo")));//备注
            ecStoepVoList.add(ecStoepVo);
        }
        return ecStoepVoList;
    }


    public StoepVo getView(Long id) {

        long stj = System.currentTimeMillis();
        String st = HttpRequest.sendGet(url + "/bcs/member/trade/view.jhtml", "account=13389035585&token=" +
                Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}") + "&id=" + id
        );
        JSONObject json = JSONObject.parseObject(st);
        long ed = System.currentTimeMillis();

        System.out.println("=============真正调取时间：" + (ed - stj));
        JSONObject json1 = (JSONObject) json.get("data");
        Map<String, Object> map = json1.getInnerMap();
        List<Map<String, OrderItem>> orderItemsList = (List<Map<String, OrderItem>>) map.get("orderItems");
        Map<String, OrderItem> tenant = (Map<String, OrderItem>) map.get("tenant");
        Map<String, OrderItem> paymentMethod = (Map<String, OrderItem>) map.get("paymentMethod");
        Map<String, OrderItem> receiverModel = (Map<String, OrderItem>) map.get("receiverModel");
        Map<String, OrderItem> member = (Map<String, OrderItem>) map.get("member");
        Map<String, OrderItem> tenan = (Map<String, OrderItem>) member.get("tenant");
        StoepVo stoepVo = new StoepVo();
        stoepVo.setId(Long.valueOf(String.valueOf(map.get("id"))));
        stoepVo.setOpenBillStore(String.valueOf(tenant.get("shortName")));//开单门店
        stoepVo.setRAddress(String.valueOf(receiverModel.get("address")));//地址
        stoepVo.setCName(String.valueOf(tenan.get("shortName")));//客户名称
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(String.valueOf(map.get("create_date")));
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stoepVo.setCreatedate(date);//创建时间
        stoepVo.setPayType(String.valueOf(map.get("paymentMethodName")));//付款方式
        stoepVo.setMoney((BigDecimal) map.get("totalPrice"));//总金额
        stoepVo.setActualAmount((BigDecimal) map.get("payAmount"));//实际付款金额
        stoepVo.setTotalQnty((Integer) map.get("quantity"));//总数量
        stoepVo.setSn(String.valueOf(map.get("sn")));//订单号
        stoepVo.setTMan(String.valueOf(receiverModel.get("consignee")));//收货人
        //    stoepVoList.setProductName(String.valueOf(product.get("fullName")));//商品名称


        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < orderItemsList.size(); i++) {
            OrderItem orderItem = new OrderItem();
            Map<String, OrderItem> product = (Map<String, OrderItem>) orderItemsList.get(i).get("product");
            Map<String, OrderItem> tenantInfoModel = (Map<String, OrderItem>) product.get("tenantInfoModel");
            Map<String, OrderItem> productCategory = (Map<String, OrderItem>) product.get("productCategory");
            orderItem.setUnit(String.valueOf(orderItemsList.get(i).get("unit")));
            orderItem.setProductName(String.valueOf(product.get("fullName")));
            orderItem.setModel(String.valueOf(product.get("spec")));//规格
            orderItem.setBrand(String.valueOf(product.get("brands")));//品牌
            orderItem.setReArea(String.valueOf(product.get("madeIn")));//库区
            orderItem.setNoValency(new BigDecimal(String.valueOf(product.get("marketPrice"))));//正常价
            orderItem.setSalesPrice(new BigDecimal(String.valueOf(product.get("price"))));
            orderItem.setAmount(new Integer(String.valueOf(orderItemsList.get(i).get("quantity"))));
            orderItem.setUnitE((new Integer(String.valueOf(orderItemsList.get(i).get("proCount")))));
            orderItem.setMoney(new BigDecimal(String.valueOf(orderItemsList.get(i).get("price"))));
            orderItem.setSn(String.valueOf(product.get("pn")));//条形码
            orderItem.setProductCategoryId(Integer.valueOf((String.valueOf(productCategory.get("id")))));
            orderItemList.add(orderItem);


            /*Collections.sort(orderItemList, (o1, o2) -> {
                Collator collator = Collator.getInstance(Locale.get);
                return collator     .compare(String.valueOf(o1.getProductCategoryId()), String.valueOf(o2.getProductCategoryId()));
            });*/
            orderItemList.sort((o1, o2) -> (o1.getProductCategoryId() - o2.getProductCategoryId()));
        }


        stoepVo.setOrderItemList(orderItemList);
        //   stoepVoList.setModel(String.valueOf(product.get("spec")));//规格
        //  stoepVoList.setBrand(String.valueOf(product.get("brands")));//品牌
        // stoepVoList.setLib(String.valueOf(tenantInfoModel.get("area")));//库区

        return stoepVo;
    }

//    public static void main(String[] args) throws ParseException {
//        String st = HttpRequest.sendGet(url+"/bcs/member/trade/view.jhtml", "account=13389035585&token=" +
//                Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}") + "&id=77639"
//        );
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date beginDate = sdf.parse("2018-11-15");
//        Date endDate = sdf.parse("2018-11-24");
//
//        System.out.println(beginDate + "===========" + endDate);
//
//        String st = HttpRequest.sendGet(url + "/bcs/member/trade/list.jhtml",
//                "account=13389035585&token=" +
//                        Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}")
//                        + "&beginDate=2018-11-15&endDate=2018-11-24"
//        );
//        System.out.println(st);
//        JSONObject json = JSONObject.parseObject(st);
//        System.out.println(json);
//      /*  String data = json.getString("data");
//        JSONObject data1 = JSONObject.parseObject(data);
//        JSONArray json2 = data1.getJSONArray("list");
//*/
//    }


    /**
     * 测试接口速度
     *
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
//        url = "http://www.jinxiaochao.com";
//        long start = System.currentTimeMillis();
//        String st = HttpRequest.sendGet(url + "/bcs/member/trade/list.jhtml",
//                "account=13389035585&token=" +
//                        Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}")
//                        + "&pageNumber=1&pageSize=10"
//        );
//        CangpeiController cangpeiController = new CangpeiController();
//        System.out.println(123);
//        System.out.println(cangpeiController.ecJsonToOrderList(st).size());
//        long end = System.currentTimeMillis();
//        System.out.println(st);
//        System.out.println("共用时间：" + (end - start));

        String s = HttpClientProxy.doGet("https://www.jxcdemo.com/app/api/order/view.jhtml?sn=2019080520510");
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(s);
        System.out.println("------------------------");
        String message = jsonObject.getString("message");
        JSONObject jsonObject1 = JSON.parseObject(message);
        String  type = jsonObject1.getString("type");
        System.out.println(type);





    }
}
