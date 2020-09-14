package com.stylefeng.guns.modular.common;

import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.vo.EcErpOrderVo;

import java.util.Map;

public class FlowUtil {

    /**
     * 通过异常流程id,得到流程map
     * @param flowId
     * @return
     */
    public static Map<Integer,Integer> getFlowMap(Integer flowId){
        switch (flowId) {
            case 1:
                return ConstCheck.redPacket;
            case 2:
                return ConstCheck.returnSingle;
            case 3:
            case 4:
                return ConstCheck.returnAll;
        }
        return null;
    }

    /**
     * 通过异常流程id,得到异常流程名称
     * @param flowId
     * @return
     */
    public static String getFlowName(Integer flowId){
        switch (flowId) {
            case 1:
                return "减红包";
            case 2:
                return "退单品";
            case 3:
                return "商家拒收";
            case 4:
                return "商家退货";
        }
        return null;
    }


    /**
     * 初始化订单按钮
     * @param ecErpOrderVo
     * @param exOrder
     * @return
     */
    public static EcErpOrderVo initOrderButton(EcErpOrderVo ecErpOrderVo, ExOrder exOrder) {

        if(exOrder.getExType().equals(ConstCheck.exOrderExType.JHB)){
            ecErpOrderVo.setRedPacketButton(true);
        }

        if(exOrder.getExType().equals(ConstCheck.exOrderExType.TDP)){
            ecErpOrderVo.setReturnSingleButton(true);
        }

        if(exOrder.getExType().equals(ConstCheck.exOrderExType.TD)){
            ecErpOrderVo.setReturnAllButton(true);
        }

        return ecErpOrderVo;
    }



}
