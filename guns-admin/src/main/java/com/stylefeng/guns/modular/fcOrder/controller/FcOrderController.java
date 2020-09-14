package com.stylefeng.guns.modular.fcOrder.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.FcOrder;
import com.stylefeng.guns.modular.fcOrder.service.IFcOrderService;

/**
 * 电商后台订单（视图）管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-15 16:37:42
 */
@Controller
@RequestMapping("/fcOrder")
public class FcOrderController extends BaseController {

    private String PREFIX = "/fcOrder/fcOrder/";

    @Autowired
    private IFcOrderService fcOrderService;

    /**
     * 跳转到电商后台订单（视图）管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "fcOrder.html";
    }

    /**
     * 跳转到添加电商后台订单（视图）管理
     */
    @RequestMapping("/fcOrder_add")
    public String fcOrderAdd() {
        return PREFIX + "fcOrder_add.html";
    }

    /**
     * 跳转到修改电商后台订单（视图）管理
     */
    @RequestMapping("/fcOrder_update/{fcOrderId}")
    public String fcOrderUpdate(@PathVariable Integer fcOrderId, Model model) {
        FcOrder fcOrder = fcOrderService.selectById(fcOrderId);
        model.addAttribute("item",fcOrder);
        LogObjectHolder.me().set(fcOrder);
        return PREFIX + "fcOrder_edit.html";
    }

    /**
     * 获取电商后台订单（视图）管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return fcOrderService.selectList(null);
    }

    /**
     * 新增电商后台订单（视图）管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(FcOrder fcOrder) {
        fcOrderService.insert(fcOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除电商后台订单（视图）管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer fcOrderId) {
        fcOrderService.deleteById(fcOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改电商后台订单（视图）管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(FcOrder fcOrder) {
        fcOrderService.updateById(fcOrder);
        return SUCCESS_TIP;
    }

    /**
     * 电商后台订单（视图）管理详情
     */
    @RequestMapping(value = "/detail/{fcOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("fcOrderId") Integer fcOrderId) {
        return fcOrderService.selectById(fcOrderId);
    }
}
