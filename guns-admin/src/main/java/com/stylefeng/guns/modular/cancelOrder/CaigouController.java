package com.stylefeng.guns.modular.cancelOrder;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.exOrderExam.service.IExOrderExamService;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.vo.EcErpOrderVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Controller
@RequestMapping("/cancelOrder/caigou")
public class CaigouController {


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

    /**
     * 跳转到采购订单管理首页
     */
    @RequestMapping("")
    public String caigou() {
        return PREFIX + "caigou_orderList.html";
    }

    /**
     * 跳转到采购订单管理首页
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object caigouList(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, null);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            //已出库显示核销按钮和其他三个异常操作
            if (ecErpOrder.getIfOutStore() != null && ecErpOrder.getIfOutStore()) {
            } else {
                ecErpOrderVo.setOrderStateInfo("待仓配出库");
            }

            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }



}
