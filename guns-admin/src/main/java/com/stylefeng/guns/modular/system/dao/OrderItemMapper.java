package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.system.model.OrderItem;
import com.stylefeng.guns.modular.vo.OrderItemSumVo;
import com.stylefeng.guns.modular.vo.OrderItemVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单详情表 Mapper 接口
 * </p>
 *
 * @author chengshuai
 * @since 2018-11-18
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    List<OrderItemVo> selectReportFormList(List<Long> orderIds);

    OrderItemSumVo selectReportForm(List<Long> orderIds);

    BigDecimal selectCheckMoney(List<Long> orderIds);
    void insertOrderItem(OrderItem orderItem);
    List<OrderItem> selectBySn(Long exErpOrderId);
}
