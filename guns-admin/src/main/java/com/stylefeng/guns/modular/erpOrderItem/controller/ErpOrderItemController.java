package com.stylefeng.guns.modular.erpOrderItem.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.DatasourceEnum;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.erpOrderItem.service.IErpOrderItemService;
import com.stylefeng.guns.modular.system.model.ErpOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ERP订单Item控制器
 *
 * @author fengshuonan
 * @Date 2018-11-08 17:41:05
 */
@Controller
@RequestMapping("/erpOrderItem")
public class ErpOrderItemController extends BaseController {

    private String PREFIX = "/erpOrderItem/erpOrderItem/";

    @Autowired
    private IErpOrderItemService erpOrderItemService;


    /**
     * 跳转到ERP订单Item首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "erpOrderItem.html";
    }

    /**
     * 跳转到添加ERP订单Item
     */
    @RequestMapping("/erpOrderItem_add")
    public String erpOrderItemAdd() {
        return PREFIX + "erpOrderItem_add.html";
    }

    /**
     * 跳转到修改ERP订单Item
     */
    @RequestMapping("/erpOrderItem_update/{erpOrderItemId}")
    public String erpOrderItemUpdate(@PathVariable Integer erpOrderItemId, Model model) {
        ErpOrderItem erpOrderItem = erpOrderItemService.selectById(erpOrderItemId);
        model.addAttribute("item", erpOrderItem);
        LogObjectHolder.me().set(erpOrderItem);
        return PREFIX + "erpOrderItem_edit.html";
    }

    /**
     * 获取ERP订单Item列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Object list(String condition) {
        return erpOrderItemService.selectList(null);
    }

    /**
     * 新增ERP订单Item
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Object add(ErpOrderItem erpOrderItem) {
        erpOrderItemService.insert(erpOrderItem);
        return SUCCESS_TIP;
    }

    /**
     * 删除ERP订单Item
     */
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer erpOrderItemId) {
        erpOrderItemService.deleteById(erpOrderItemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改ERP订单Item
     */
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ErpOrderItem erpOrderItem) {
        erpOrderItemService.updateById(erpOrderItem);
        return SUCCESS_TIP;
    }

    /**
     * ERP订单Item详情
     */
    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    @RequestMapping(value = "/detail/{erpOrderItemId}")
    @ResponseBody
    public Object detail(@PathVariable("erpOrderItemId") Integer erpOrderItemId) {
        return erpOrderItemService.selectById(erpOrderItemId);
    }

}
