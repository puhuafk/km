package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.ExOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 异常订单表
 Mapper 接口
 * </p>
 *
 * @author chengshuai
 * @since 2018-10-17
 */
public interface ExOrderMapper extends BaseMapper<ExOrder> {

    List<Map<String, Object>> exportDeExOrderList(@Param("ex_type")Integer ex_type,
                                                  @Param("exam_dep_id")Integer exam_dep_id,
                                                  @Param("startTime")Date startTime,
                                                  @Param("endTime")Date endTime,
                                                  @Param("content")String content);




    List<Map<String, Object>> exportAllExOrderList(@Param("ex_type")Integer ex_type3,
                                                    @Param("ex_type")Integer ex_type4,
                                                    @Param("ex_state")Integer ex_state,
                                                    @Param("exam_dep_id")Integer exam_dep_id,
                                                    @Param("startTime")Date startTime,
                                                    @Param("endTime")Date endTime,
                                                    @Param("content")String content);

    //退单品，拒收，退单，异常订单导出
//    List<Map<String, Object>> exportAllExOrderList(@Param("ex_type")Integer ex_type2,
//                                                   @Param("ex_type")Integer ex_type3,
//                                                   @Param("ex_type")Integer ex_type4,
//                                                   @Param("ex_state")Integer ex_state,
//                                                   @Param("exam_dep_id")Integer exam_dep_id,
//                                                   @Param("startTime")Date startTime,
//                                                   @Param("endTime")Date endTime,
//                                                   @Param("content")String content);


}
