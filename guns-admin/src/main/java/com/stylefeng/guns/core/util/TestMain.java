package com.stylefeng.guns.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.erpOrderItem.service.IErpOrderItemService;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.model.OrderItem;
import com.stylefeng.guns.modular.vo.EcStoepVo;
import com.stylefeng.guns.modular.vo.OrderItems;
import com.stylefeng.guns.modular.vo.StoepVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {
    @Autowired
    private IErpOrderItemService erpOrderItemService;
    @Autowired
    private IEcErpOrderService ecErpOrderService;

    public static void main(String[] args) throws ParseException {
        String st = HttpRequest.sendGet("http://www.jinxiaochao.com" + "/bcs/member/trade/view.jhtml", "account=13389035585&token=" +
                Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}") + "&id=11088"
        );
        JSONObject json = JSONObject.parseObject(st);
        JSONObject json1 = (JSONObject) json.get("data");
        System.out.println(json1);
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
            orderItem.setProductCategoryId(Integer.valueOf(String.valueOf(productCategory.get("grade"))));

System.out.println(orderItem.getProductCategoryId());
            orderItemList.add(orderItem);
            orderItemList.sort((o1,o2)-> (o1.getProductCategoryId()-o2.getProductCategoryId()));
            System.out.println(orderItemList);
        }


        stoepVo.setOrderItemList(orderItemList);
        }


    public static List<EcStoepVo> geturl(Integer pageNumber, Integer pageSize) {
        String st = HttpRequest.sendGet("http://www.jxcdemo.com/bcs/member/trade/list.jhtml",
                "account=13389035585&token=" +
                        Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}")

        );
        JSONObject json = JSONObject.parseObject(st);

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
            ecStoepVo.setCustomerName(String.valueOf(map3.get("shortName")));//客户名称
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
        return  ecStoepVoList;
    }

    public static StoepVo getView(Long id) throws ParseException {
        String st = HttpRequest.sendGet("http://www.jxcdemo.com/bcs/member/trade/view.jhtml", "account=13389035585&token=" +
                Base64Util.encode("{\"token\":\"" + "16d44bda455c02faf92c35bc23bec38a" + "\",\"timestamp\":\"" + System.currentTimeMillis() + "\"}") + "&id=" + id
        );
        JSONObject json = JSONObject.parseObject(st);
        JSONObject json1 = (JSONObject) json.get("data");
        Map<String, Object> map = json1.getInnerMap();
        List<Map<String, OrderItem>> orderItemsList = (List<Map<String, OrderItem>>) map.get("orderItems");
        Map<String, OrderItem> tenant = (Map<String, OrderItem>) map.get("tenant");
        Map<String, OrderItem> paymentMethod = (Map<String, OrderItem>) map.get("paymentMethod");
        Map<String, OrderItem> receiverModel = (Map<String, OrderItem>) map.get("receiverModel");
        Map<String, OrderItem> member = (Map<String, OrderItem>) map.get("member");
        Map<String, OrderItem> tenan = (Map<String, OrderItem>) member.get("tenant");
        StoepVo stoepVo=new StoepVo();
        stoepVo.setId(Long.valueOf(String.valueOf(map.get("id"))));
        stoepVo.setOpenBillStore(String.valueOf(tenant.get("shortName")));//开单门店
        stoepVo.setRAddress(String.valueOf(receiverModel.get("address")));//地址
        stoepVo.setCName(String.valueOf(tenan.get("shortName")));//客户名称
        SimpleDateFormat format =new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Long time=new Long(String.valueOf(map.get("create_date")));
        String d = format.format(time);
        Date date=format.parse(d);
        stoepVo.setCreatedate(date);//创建时间
        stoepVo.setPayType(String.valueOf(map.get("paymentMethodName")));//付款方式
        stoepVo.setMoney((BigDecimal) map.get("totalPrice"));//总金额
        stoepVo.setActualAmount((BigDecimal) map.get("earnestAmount"));//实际付款金额
        stoepVo.setTotalQnty((Integer) map.get("quantity"));//总数量
        stoepVo.setSn(String.valueOf(map.get("sn")));//订单号
        stoepVo.setTMan(String.valueOf(receiverModel.get("consignee")));//收货人
        //    stoepVoList.setProductName(String.valueOf(product.get("fullName")));//商品名称


        List<OrderItem> orderItemList = new ArrayList<>();

        for(int i=0;i<orderItemsList.size();i++){
            OrderItem orderItem = new OrderItem();
            Map<String, OrderItem> product = (Map<String, OrderItem>) orderItemsList.get(i).get("product");
            Map<String, OrderItem> tenantInfoModel  = (Map<String, OrderItem>) product.get("tenantInfoModel");
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
            orderItemList.add(orderItem);
        }


        stoepVo.setOrderItemList(orderItemList);
        //   stoepVoList.setModel(String.valueOf(product.get("spec")));//规格
        //  stoepVoList.setBrand(String.valueOf(product.get("brands")));//品牌
        // stoepVoList.setLib(String.valueOf(tenantInfoModel.get("area")));//库区

        return stoepVo;
    }

}
