package com.stylefeng.guns.modular.ecErpOrder.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-22 11:58:02
 */
@Controller
@RequestMapping("/ecErpOrder")
public class EcErpOrderController extends BaseController {

    private String PREFIX = "/ecErpOrder/ecErpOrder/";

    @Autowired
    private IEcErpOrderService ecErpOrderService;

    /**
     * 跳转到订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "ecErpOrder.html";
    }

    /**
     * 跳转到添加订单管理
     */
//    @RequestMapping("/ecErpOrder_add")
//    public String ecErpOrderAdd() {
//
//        for (int i = 0; i < 10; i++) {
//            EcErpOrder ecErpOrder1 = new EcErpOrder();
//            ecErpOrder1.setTotalAmount(new BigDecimal((int) (300 + Math.random() * 600)));
//            ecErpOrder1.setCreatedate(new Date());
//            ecErpOrder1.setCustomerName(ConstCheck.bjxs[(int) (Math.random() * (ConstCheck.bjxs.length))]
//                    + ConstCheck.names[(int) (Math.random() * (ConstCheck.names.length))]+ConstCheck.names[(int) (Math.random() * (ConstCheck.names.length))]);
//            ecErpOrder1.setEcNo((2018101 + i) + "");
//            ecErpOrder1.setSaleNo("jxc" + (2018101 + i));
//            ecErpOrder1.setDeliveryAddress(ConstCheck.addresss[(int) (Math.random() * (ConstCheck.addresss.length))]);
//            ecErpOrder1.setDataSource(1);//数据来源
//            ecErpOrder1.setOutStoreTime(new Date());
//            ecErpOrder1.setIfCheck(false); //未核销
//            ecErpOrder1.setIfOutStore(true); //已经出库
//            ecErpOrderService.insert(ecErpOrder1);
//        }
//        return PREFIX + "ecErpOrder_add.html";
//    }

    /**
     * 跳转到修改订单管理
     */
    @RequestMapping("/ecErpOrder_update/{ecErpOrderId}")
    public String ecErpOrderUpdate(@PathVariable Integer ecErpOrderId, Model model) {
        EcErpOrder ecErpOrder = ecErpOrderService.selectById(ecErpOrderId);
        model.addAttribute("item", ecErpOrder);
        LogObjectHolder.me().set(ecErpOrder);
        return PREFIX + "ecErpOrder_edit.html";
    }

    /**
     * 获取订单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return ecErpOrderService.selectList(null);
    }

    /**
     * 新增订单管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(EcErpOrder ecErpOrder) {

        //ecErpOrderService.insert(ecErpOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除订单管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer ecErpOrderId) {
        ecErpOrderService.deleteById(ecErpOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改订单管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(EcErpOrder ecErpOrder) {
        ecErpOrderService.updateById(ecErpOrder);
        return SUCCESS_TIP;
    }


    /**
     * 订单管理详情
     */
    @RequestMapping(value = "/detail/{ecErpOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("ecErpOrderId") Integer ecErpOrderId) {
        return ecErpOrderService.selectById(ecErpOrderId);
    }


    public static void main(String[] args) {
        System.out.println((int) 0.9);
    }

}
