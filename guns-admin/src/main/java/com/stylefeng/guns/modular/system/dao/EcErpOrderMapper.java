package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.model.ExOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 电商、erp订单表 Mapper 接口
 * </p>
 *
 * @author chengshuai
 * @since 2018-10-22
 */
public interface EcErpOrderMapper extends BaseMapper<EcErpOrder> {

    List<EcErpOrder> selectBySn(List<String> saleSnList);

    //已审核订单
    List<ExOrder> selectByDepIdysh(@Param("depId")Integer depId,
                                   @Param("content") String content,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime,
                                   @Param("index") Integer index,
                                   @Param("pageSize") Integer pageSize);




    //待审核订单
    List<EcErpOrder> selectByDepIddsh(Integer depId);

    //审核中订单
    List<EcErpOrder> selectByDepIdshz(Integer depId);
}
