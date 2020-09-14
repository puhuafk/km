package com.stylefeng.guns.modular.exOrderExam.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.common.FlowUtil;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.exOrderExam.service.IExOrderExamService;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.ro.OrderItemRo;
import com.stylefeng.guns.modular.system.model.*;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IExOrderItemService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.vo.ExOrderVo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 异常订单审核管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-16 16:17:23
 */
@Controller
@RequestMapping("/exOrderExam")
public class ExOrderExamController extends BaseController {

    private String PREFIX = "/exOrderExam/exOrderExam/";

    @Autowired
    private IExOrderExamService exOrderExamService;

    @Autowired
    private IExOrderService exOrderService;

    @Autowired
    private IEcErpOrderService ecErpOrderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Qualifier("deptServiceImpl")
    @Autowired
    private IDeptService iDeptService;

    @Qualifier("userServiceImpl")
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IExOrderItemService exOrderItemService;


    @RequestMapping("/selectExanList")
    @ResponseBody
        public ExOrderVo selectExanList(Integer exOrderId) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("ex_order_id", exOrderId);
        List list = new ArrayList();
        list.add("createtime");
        wrapper.orderAsc(list);
        List<ExOrderExam> exOrderExamList = exOrderExamService.selectList(wrapper);
        ExOrder exOrder = exOrderService.selectById(exOrderId);
        String flowName = FlowUtil.getFlowName(exOrder.getExType());


        ExOrderVo exOrderVo = ExOrderVo.adapt(exOrder);
        if (exOrder.getExType() == 1) {
            exOrderVo.setExOrderInfo(flowName + ":" + exOrder.getRedPackAmount() + "元");
        }
        if (exOrderVo.getDeleteOrderItems() != null && !exOrderVo.getDeleteOrderItems().equals("")) {
            List<OrderItemRo> orderItemVoList = (List<OrderItemRo>) JSONArray.toList(JSONArray.fromObject(exOrderVo.getDeleteOrderItems()), OrderItemRo.class);
            String exOrderInfo = flowName + "<br>";
            for (OrderItemRo orderItemRo : orderItemVoList) {
                exOrderInfo = exOrderInfo + orderItemRo.getProductName() + ":" + orderItemRo.getNum() + "<br>";
            }
            exOrderVo.setExOrderInfo(exOrderInfo);
        }
        if (exOrder.getExType() == 3) {
            exOrderVo.setExOrderInfo("商家拒收");
        }
        if (exOrder.getExType() == 4) {
            exOrderVo.setExOrderInfo("商家退货");
        }

        exOrderVo.setExOrderExamList(exOrderExamList);
        return exOrderVo;
    }

    /**
     * 跳转到异常订单审核管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "exOrderExam.html";
    }

    /**
     * 跳转到添加异常订单审核管理
     */
    @RequestMapping("/exOrderExam_add")
    public String exOrderExamAdd() {
        return PREFIX + "exOrderExam_add.html";
    }

    /**
     * 跳转到修改异常订单审核管理
     */
    @RequestMapping("/exOrderExam_update/{exOrderExamId}")
    public String exOrderExamUpdate(@PathVariable Integer exOrderExamId, Model model) {
        ExOrderExam exOrderExam = exOrderExamService.selectById(exOrderExamId);
        model.addAttribute("item", exOrderExam);
        LogObjectHolder.me().set(exOrderExam);
        return PREFIX + "exOrderExam_edit.html";
    }

    /**
     * 获取异常订单审核管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return exOrderExamService.selectList(null);
    }

    /**
     * 新增异常订单审核管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExOrderExam exOrderExam) {
        exOrderExamService.insert(exOrderExam);
        return SUCCESS_TIP;
    }

    /**
     * 删除异常订单审核管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer exOrderExamId) {
        exOrderExamService.deleteById(exOrderExamId);
        return SUCCESS_TIP;
    }

    /**
     * 修改异常订单审核管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExOrderExam exOrderExam) {
        exOrderExamService.updateById(exOrderExam);
        return SUCCESS_TIP;
    }

    /**
     * 异常订单审核管理详情
     */
    @RequestMapping(value = "/detail/{exOrderExamId}")
    @ResponseBody
    public Object detail(@PathVariable("exOrderExamId") Integer exOrderExamId) {
        return exOrderExamService.selectById(exOrderExamId);
    }

    /**
     * 通过异常订单id查询审核信息
     */
    @RequestMapping(value = "/checkList/{exOrderId}")
    @ResponseBody
    public List<ExOrderExam> checkListByExId(@PathVariable("exOrderId") Integer exOrderId) {
        List orderClomList = new ArrayList();
        orderClomList.add("createtime");
        Wrapper Wrapper = new EntityWrapper<ExOrder>().eq("ex_order_id", exOrderId).orderAsc(orderClomList);
        return exOrderExamService.selectList(Wrapper);
    }

    /**
     * 财务发起异常订单
     */
    @Transactional
    @PostMapping(value = "/initExOrder")
    @ResponseBody
    public Object initExOrder(ExOrder exOrder, Long ecErpOrderId, String orderItemListJson, String opinion) {
        //更新订单异常状态
        EcErpOrder ecErpOrder = new EcErpOrder();
        ecErpOrder.setId(ecErpOrderId);
        ecErpOrder.setExOrderSign(true);

        //设置退单标识符，退单品，退单，退单标识符都设置成true
        if (exOrder.getExType() != 1) {
            ecErpOrder.setReturnGoodsFlag(true);
        }
        //初始化异常订单
        exOrder.setEcErpOrderId(ecErpOrderId);
        Map<Integer, Integer> flowMap = FlowUtil.getFlowMap(exOrder.getExType());
        Integer depId = flowMap.get(2);
        exOrder.setStepSign(2);
        exOrder.setFlowId(exOrder.getExType());
        exOrder.setExamDepId(depId);
        //初始化异常订单审核信息
        Integer currentDepId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();
        Integer currentSysUserId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getId();

        if (orderItemListJson != null && !orderItemListJson.equals("")) {
            exOrder.setDeleteOrderItems(orderItemListJson);
            List<OrderItemRo> orderItemRoList = JSONArray.toList(JSONArray.fromObject(exOrder.getDeleteOrderItems()), OrderItemRo.class);
            //设置待退单品数目
            for (OrderItemRo orderItemRo : orderItemRoList) {
                OrderItem orderItem = orderItemService.selectById(orderItemRo.getId());
                //更新退货数量
                if (orderItem.getForReturnNum() != null) {
                    orderItem.setForReturnNum(orderItem.getForReturnNum() + orderItemRo.getNum());
                } else {
                    orderItem.setForReturnNum(orderItemRo.getNum());
                }
                orderItemService.updateById(orderItem);
            }
        }

        //退单品处理
        exOrderService.insert(exOrder);
        ExOrderExam exOrderExam = new ExOrderExam();
        exOrderExam.setExOrderId(exOrder.getId());
        exOrderExam.setOpinion(opinion);
        exOrderExam.setSysDeptId(currentDepId);
        exOrderExam.setCreateDeptName(iDeptService.selectById(currentDepId).getSimplename());//部门名称
        exOrderExam.setSysUserId(currentSysUserId);
        exOrderExam.setOperator(iUserService.selectById(currentSysUserId).getName());
        exOrderExam.setIfPass(true);
        exOrderExam.setSysUserId(currentSysUserId);
        exOrderExam.setCreatetime(new Date());
        exOrderExam.setIfLaunch(true);
        exOrderExamService.insert(exOrderExam);
        ecErpOrderService.updateById(ecErpOrder);
        return SUCCESS_TIP;
    }


    /**
     * 批量审核
     *
     * @param exOrderExamList
     * @return
     */
    @Transactional
    @PostMapping(value = "/checkExOrderBatch")
    @ResponseBody
    public Object checkExOrderBatch(@RequestBody List<ExOrderExam> exOrderExamList) {
        for (ExOrderExam exOrderExam : exOrderExamList) {
            checkExOrder(exOrderExam.getExOrderId(), exOrderExam.getOpinion(), true);
        }
        return SUCCESS_TIP;
    }


    @Transactional
    @RequestMapping(value = "/cancelCheck")
    @ResponseBody
    public Object cancelCheck(Integer exOrderId) {
        ExOrder exOrder = exOrderService.selectById(exOrderId);
        exOrder.setExamDepId(0);
        //审核流程结束，更新订单异常状态
        if (exOrder.getExamDepId() == 0) {
            exOrder.setExState(false);
            exOrderService.updateById(exOrder);
            //同步取消异常订单标识符
            Wrapper wrapper = new EntityWrapper();
            EcErpOrder ecErpOrder = new EcErpOrder();
            ecErpOrder.setId(exOrder.getEcErpOrderId());
            wrapper.eq("ec_erp_order_id", exOrder.getEcErpOrderId()).eq("ex_state", true);

            if (exOrderService.selectList(wrapper).size() == 0) {
                ecErpOrder.setExOrderSign(false);
                ecErpOrderService.updateById(ecErpOrder);
            }
        }
        return SUCCESS_TIP;
    }


    /**
     * 部门审核异常订单
     *
     * @param exOrderId 异常信息id
     * @param opinion   审核意见
     * @param ifPass    审核是否同意
     * @return
     */
    @Transactional
    @RequestMapping(value = "/checkExOrder")
    @ResponseBody
    public Object checkExOrder(Integer exOrderId, String opinion, Boolean ifPass) {
        Integer currentDepId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();
        Integer currentSysUserId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getId();
        ExOrder exOrder = exOrderService.selectById(exOrderId);
        //更新审核部门
        Map<Integer, Integer> flowMap = FlowUtil.getFlowMap(exOrder.getFlowId());
        if (ifPass) {
            exOrder.setIfReject(false);
            exOrder.setStepSign(exOrder.getStepSign() + 1);
        } else {
            exOrder.setIfReject(true);
            exOrder.setStepSign(exOrder.getStepSign() - 1);
        }
        Integer depId = flowMap.get(exOrder.getStepSign());
        exOrder.setExamDepId(depId);
        ExOrderExam exOrderExam = new ExOrderExam();
        exOrderExam.setOpinion(opinion); //审核意见
        exOrderExam.setExOrderId(exOrderId);
        exOrderExam.setSysDeptId(currentDepId);
        exOrderExam.setSysUserId(currentSysUserId);
        exOrderExam.setIfPass(ifPass);
        exOrderExam.setSysDeptId(currentDepId);
        exOrderExam.setCreateDeptName(iDeptService.selectById(currentDepId).getSimplename());//部门名称
        exOrderExam.setSysUserId(currentSysUserId);
        exOrderExam.setOperator(iUserService.selectById(currentSysUserId).getName());
        exOrderExam.setCreatetime(new Date());
        exOrderExam.setIfLaunch(false);
        exOrderService.updateById(exOrder);
        exOrderExamService.insert(exOrderExam);
        //审核流程结束，更新订单异常状态
        if (exOrder.getExamDepId() == 0) {
            exOrder.setExState(false);
            exOrderService.updateById(exOrder);
            //同步取消异常订单标识符
            Wrapper wrapper = new EntityWrapper();
            EcErpOrder ecErpOrder = new EcErpOrder();
            ecErpOrder.setId(exOrder.getEcErpOrderId());
            wrapper.eq("ec_erp_order_id", exOrder.getEcErpOrderId()).eq("ex_state", true);

            if (exOrderService.selectList(wrapper).size() == 0) {
                ecErpOrder.setExOrderSign(false);
            }


            if (exOrder.getExType() == 1) {
                if (ecErpOrder.getReturnRedMoney() != null) {
                    ecErpOrder.setReturnRedMoney(ecErpOrder.getReturnRedMoney().add(exOrder.getRedPackAmount()));
                } else {
                    ecErpOrder.setReturnRedMoney(exOrder.getRedPackAmount());
                }
                ecErpOrder.setReturnRedType(exOrder.getRefundType());//设置退红包方式
            }

            if (exOrder.getExType() == 2) {
                List<OrderItemRo> orderItemRoList = (List<OrderItemRo>) JSONArray.toList(JSONArray.fromObject(exOrder.getDeleteOrderItems()), OrderItemRo.class);

                //创建中间表
                ArrayList<ExOrderItem> arrayList = new ArrayList();
                ExOrderItem exOrderItem = new ExOrderItem();
                for (OrderItemRo orderItemRo:orderItemRoList){
                    exOrderItem.setExOrderId(exOrder.getId());
                    exOrderItem.setReturnNumber(orderItemRo.getNum());
                    exOrderItem.setOrderItemId(orderItemRo.getId());
                    arrayList.add(exOrderItem);
                }

                exOrderItemService.insertBatch(arrayList);

                for (OrderItemRo orderItemRo : orderItemRoList) {
                    OrderItem orderItem = orderItemService.selectById(orderItemRo.getId());
                    //更新退货数量
                    if (orderItem.getReturnNum() != null) {
                        orderItem.setReturnNum(orderItem.getReturnNum() + orderItemRo.getNum());
                    } else {
                        orderItem.setReturnNum(orderItemRo.getNum());
                    }

                    if(orderItem.getForReturnNum()!=null){
                        orderItem.setForReturnNum(orderItem.getForReturnNum() - orderItemRo.getNum());
                    }
                    orderItem.setIfReturn(true);
                    orderItemService.updateById(orderItem);
                }
                if (ecErpOrder.getRefundMoney() != null) {
                    ecErpOrder.setRefundMoney(ecErpOrder.getRefundMoney().add(exOrder.getRefundMoney())); //设置退款金额
                } else {
                    ecErpOrder.setRefundMoney(exOrder.getRefundMoney()); //设置退款金额
                }
                ecErpOrder.setReturnRedType(exOrder.getRefundType());//设置退红包方式
            }

            if (exOrder.getExType() == 3 || exOrder.getExType() == 4) {
                wrapper = new EntityWrapper();
                wrapper.eq("ec_erp_order_id", exOrder.getEcErpOrderId());
                List<OrderItem> orderItemList = orderItemService.selectList(wrapper);
                for (OrderItem orderItem : orderItemList) {
                    orderItem.setReturnNum(orderItem.getAmount());
                    orderItemService.updateById(orderItem);
                }
                ecErpOrder.setRefundMoney(ecErpOrder.getActualAmount()); //设置退款金额
                ecErpOrder.setReturnSingleType(exOrder.getRefundType());//设置退货方式
            }

            ecErpOrderService.updateById(ecErpOrder);

        }
        return SUCCESS_TIP;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap();
        map.put(new Integer(1), new Integer(1));

        System.out.println(map.get(0));
    }


}
