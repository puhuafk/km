package com.stylefeng.guns.modular.erpSaleOrder.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.DatasourceEnum;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.erpSaleOrder.service.IErpSaleOrderService;
import com.stylefeng.guns.modular.system.model.ErpSaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * erp订单管理控制器
 *
 * @author fengshuonan
 * @Date 2018-11-08 17:42:56
 */
@Controller
@RequestMapping("/erpSaleOrder")
public class ErpSaleOrderController extends BaseController {

    private String PREFIX = "/erpSaleOrder/erpSaleOrder/";

    @Autowired
    private IErpSaleOrderService erpSaleOrderService;



    /**
     * 跳转到erp订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "erpSaleOrder.html";
    }

    /**
     * 跳转到添加erp订单管理
     */
    @RequestMapping("/erpSaleOrder_add")
    public String erpSaleOrderAdd() {
        return PREFIX + "erpSaleOrder_add.html";
    }

    /**
     * 跳转到修改erp订单管理
     */
    @RequestMapping("/erpSaleOrder_update/{erpSaleOrderId}")
    public String erpSaleOrderUpdate(@PathVariable Integer erpSaleOrderId, Model model) {
        ErpSaleOrder erpSaleOrder = erpSaleOrderService.selectById(erpSaleOrderId);
        model.addAttribute("item",erpSaleOrder);
        LogObjectHolder.me().set(erpSaleOrder);
        return PREFIX + "erpSaleOrder_edit.html";
    }

    /**
     * 获取erp订单管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public List<ErpSaleOrder> list(String condition) {
        return erpSaleOrderService.selectList(null);
    }

    /**
     * 新增erp订单管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Object add(ErpSaleOrder erpSaleOrder) {
        erpSaleOrderService.insert(erpSaleOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除erp订单管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Object delete(@RequestParam Integer erpSaleOrderId) {

        System.out.println();
        erpSaleOrderService.deleteById(erpSaleOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改erp订单管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ErpSaleOrder erpSaleOrder) {
        erpSaleOrderService.updateById(erpSaleOrder);
        return SUCCESS_TIP;
    }

    /**
     * erp订单管理详情
     */
    @RequestMapping(value = "/detail/{erpSaleOrderId}")
    @ResponseBody
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Object detail(@PathVariable("erpSaleOrderId") Integer erpSaleOrderId) {
        return erpSaleOrderService.selectById(erpSaleOrderId);
    }
}
