package com.stylefeng.guns.modular.vo;

import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.system.model.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;


@Getter
@Setter
@ToString
public class EcErpOrderVo extends EcErpOrder {

    private String memo;


    //按钮控制
    private Boolean returnAllButton=false;//已出库退单

    private Boolean returnSingleButton=false;//退单品

    private Boolean redPacketButton=false;//退红包

    private Boolean examButton=false;//审核按钮

    private Boolean checkButton=false;//核销按钮

    private Boolean confirmOutButton=false;//确认出库

    private Boolean closeOrderButton=false;//关闭订单

    private Boolean examFlowButton=false;//查看审核流程

    private Boolean cancelCheckButton =false;//查看审核流程

    private Boolean repealCheckButton =false;//取消核销按钮

    private Integer detailsFlag=1;//查看审核流程

    private String nextDepName; //下个待审核部门名称

    private String orderStateInfo; //下个待审核部门名称

    private Integer exType;//下个待审核部门名称

    private Integer exOrderId;//异常订单id

    private List<OrderItem> orderItemList;//订单列表

    private List<ExOrder> exOrderList;

    private List<ExOrderVo> exOrderVoList;

    private List<StoepVo> stoepVos;

    public List<StoepVo> getStoepVos() {
        return stoepVos;
    }

    public void setStoepVos(List<StoepVo> stoepVos) {
        this.stoepVos = stoepVos;
    }

    public static EcErpOrderVo adapt(EcErpOrder ecErpOrder) {
        EcErpOrderVo vo = new EcErpOrderVo();
        BeanUtils.copyProperties(ecErpOrder, vo);
        return vo;
    }
}
