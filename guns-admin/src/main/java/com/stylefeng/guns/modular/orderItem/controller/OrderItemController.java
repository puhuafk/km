package com.stylefeng.guns.modular.orderItem.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.system.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单详情控制器
 * @author fengshuonan
 * @Date 2018-10-23 17:19:41
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController extends BaseController {

    private String PREFIX = "/orderItem/orderItem/";

    @Autowired
    private IOrderItemService orderItemervice;

    /**
     * 跳转到订单详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "orderItem.html";
    }

    /**
     * 跳转到添加订单详情
     */
    @RequestMapping("/orderItem_add")
    public String orderItemAdd() {
        return PREFIX + "orderItem_add.html";
    }

    /**
     * 跳转到修改订单详情
     */
    @RequestMapping("/orderItem_update/{orderItemId}")
    public String orderItemUpdate(@PathVariable Integer orderItemId, Model model) {
        OrderItem orderItem = orderItemervice.selectById(orderItemId);
        model.addAttribute("item", orderItem);
        LogObjectHolder.me().set(orderItem);
        return PREFIX + "orderItem_edit.html";
    }

    /**
     * 获取订单详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return orderItemervice.selectList(null);
    }

    /**
     * 新增订单详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(OrderItem orderItem) {
        orderItemervice.insert(orderItem);
        return SUCCESS_TIP;
    }

    /**
     * 删除订单详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer orderItemId) {
        orderItemervice.deleteById(orderItemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改订单详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(OrderItem orderItem) {
        orderItemervice.updateById(orderItem);
        return SUCCESS_TIP;
    }

    /**
     * 订单详情详情
     */
    @RequestMapping(value = "/detail/{orderItemId}")
    @ResponseBody
    public Object detail(@PathVariable("orderItemId") Integer orderItemId) {
        return orderItemervice.selectById(orderItemId);
    }
}
