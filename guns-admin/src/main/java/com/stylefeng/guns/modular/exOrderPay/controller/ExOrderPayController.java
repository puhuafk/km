package com.stylefeng.guns.modular.exOrderPay.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.ExOrderPay;
import com.stylefeng.guns.modular.exOrderPay.service.IExOrderPayService;

/**
 * 异常订单首付款管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-16 16:04:17
 */
@Controller
@RequestMapping("/exOrderPay")
public class ExOrderPayController extends BaseController {

    private String PREFIX = "/exOrderPay/exOrderPay/";

    @Autowired
    private IExOrderPayService exOrderPayService;

    /**
     * 跳转到异常订单首付款管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "exOrderPay.html";
    }

    /**
     * 跳转到添加异常订单首付款管理
     */
    @RequestMapping("/exOrderPay_add")
    public String exOrderPayAdd() {
        return PREFIX + "exOrderPay_add.html";
    }

    /**
     * 跳转到修改异常订单首付款管理
     */
    @RequestMapping("/exOrderPay_update/{exOrderPayId}")
    public String exOrderPayUpdate(@PathVariable Integer exOrderPayId, Model model) {
        ExOrderPay exOrderPay = exOrderPayService.selectById(exOrderPayId);
        model.addAttribute("item",exOrderPay);
        LogObjectHolder.me().set(exOrderPay);
        return PREFIX + "exOrderPay_edit.html";
    }

    /**
     * 获取异常订单首付款管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return exOrderPayService.selectList(null);
    }

    /**
     * 新增异常订单首付款管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExOrderPay exOrderPay) {
        exOrderPayService.insert(exOrderPay);
        return SUCCESS_TIP;
    }

    /**
     * 删除异常订单首付款管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer exOrderPayId) {
        exOrderPayService.deleteById(exOrderPayId);
        return SUCCESS_TIP;
    }

    /**
     * 修改异常订单首付款管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExOrderPay exOrderPay) {
        exOrderPayService.updateById(exOrderPay);
        return SUCCESS_TIP;
    }

    /**
     * 异常订单首付款管理详情
     */
    @RequestMapping(value = "/detail/{exOrderPayId}")
    @ResponseBody
    public Object detail(@PathVariable("exOrderPayId") Integer exOrderPayId) {
        return exOrderPayService.selectById(exOrderPayId);
    }
}
