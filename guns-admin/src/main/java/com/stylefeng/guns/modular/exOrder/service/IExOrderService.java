package com.stylefeng.guns.modular.exOrder.service;

import com.stylefeng.guns.modular.system.model.ExOrder;
import com.baomidou.mybatisplus.service.IService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 异常订单表
 服务类
 * </p>
 *
 * @author chengshuai
 * @since 2018-10-17
 */
public interface IExOrderService extends IService<ExOrder> {


    List<Map<String,Object>> exportDeExOrderList(Integer ex_type,
                                                 Integer exam_dep_id,
                                                 Date startTime,
                                                 Date endTime,
                                                 String content);





    List<Map<String, Object>> exportAllExOrderList(Integer ex_type3,
                                                   Integer ex_type4,
                                                   Integer ex_state,
                                                   Integer exam_dep_id,
                                                   Date startTime,
                                                   Date endTime,
                                                   String content);



    //退单品，拒收，退单，异常订单导出
//    List<Map<String, Object>> exportAllExOrderList(Integer ex_type2,
//                                                   Integer ex_type3,
//                                                   Integer ex_type4,
//                                                   Integer ex_state,
//                                                   Integer exam_dep_id,
//                                                   Date startTime,
//                                                   Date endTime,
//                                                   String content);



}
