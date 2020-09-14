package com.stylefeng.guns.modular.exOrderFlow.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.ExOrderFlow;
import com.stylefeng.guns.modular.exOrderFlow.service.IExOrderFlowService;

/**
 * 异常订单流程管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-16 16:02:44
 */
@Controller
@RequestMapping("/exOrderFlow")
public class ExOrderFlowController extends BaseController {

    private String PREFIX = "/exOrderFlow/exOrderFlow/";

    @Autowired
    private IExOrderFlowService exOrderFlowService;

    /**
     * 跳转到异常订单流程管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "exOrderFlow.html";
    }

    /**
     * 跳转到添加异常订单流程管理
     */
    @RequestMapping("/exOrderFlow_add")
    public String exOrderFlowAdd() {
        return PREFIX + "exOrderFlow_add.html";
    }

    /**
     * 跳转到修改异常订单流程管理
     */
    @RequestMapping("/exOrderFlow_update/{exOrderFlowId}")
    public String exOrderFlowUpdate(@PathVariable Integer exOrderFlowId, Model model) {
        ExOrderFlow exOrderFlow = exOrderFlowService.selectById(exOrderFlowId);
        model.addAttribute("item",exOrderFlow);
        LogObjectHolder.me().set(exOrderFlow);
        return PREFIX + "exOrderFlow_edit.html";
    }

    /**
     * 获取异常订单流程管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return exOrderFlowService.selectList(null);
    }

    /**
     * 新增异常订单流程管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExOrderFlow exOrderFlow) {
        exOrderFlowService.insert(exOrderFlow);
        return SUCCESS_TIP;
    }

    /**
     * 删除异常订单流程管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer exOrderFlowId) {
        exOrderFlowService.deleteById(exOrderFlowId);
        return SUCCESS_TIP;
    }

    /**
     * 修改异常订单流程管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExOrderFlow exOrderFlow) {
        exOrderFlowService.updateById(exOrderFlow);
        return SUCCESS_TIP;
    }

    /**
     * 异常订单流程管理详情
     */
    @RequestMapping(value = "/detail/{exOrderFlowId}")
    @ResponseBody
    public Object detail(@PathVariable("exOrderFlowId") Integer exOrderFlowId) {
        return exOrderFlowService.selectById(exOrderFlowId);
    }
}
