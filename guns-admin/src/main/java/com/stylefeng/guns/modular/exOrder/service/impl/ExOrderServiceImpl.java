package com.stylefeng.guns.modular.exOrder.service.impl;

import com.stylefeng.guns.modular.system.dao.DictMapper;
import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.system.dao.ExOrderMapper;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 异常订单表
 服务实现类
 * </p>
 *
 * @author chengshuai
 * @since 2018-10-17
 */
@Service
public class ExOrderServiceImpl extends ServiceImpl<ExOrderMapper, ExOrder> implements IExOrderService {

    @Autowired
    ExOrderMapper exOrderMapper;

    //退单品导出
    @Override
    public List<Map<String, Object>> exportDeExOrderList(Integer ex_type,
                                                         Integer exam_dep_id,
                                                         Date startTime,
                                                         Date endTime,
                                                         String content) {
        return exOrderMapper.exportDeExOrderList( ex_type, exam_dep_id, startTime, endTime, content);

    }


    //退整单导出
    @Override
    public List<Map<String, Object>> exportAllExOrderList(Integer ex_type2,
                                                          Integer ex_type3,
                                                          Integer ex_state,
                                                          Integer exam_dep_id,
                                                          Date startTime,
                                                          Date endTime,
                                                          String content) {


        return exOrderMapper.exportAllExOrderList(ex_type2,
                                                  ex_type3,
                                                  ex_state,
                                                  exam_dep_id,
                                                  startTime,
                                                  endTime,
                                                  content);
    }

//    @Override
//    public List<Map<String, Object>> exportAllExOrderList(Integer ex_type2,
//                                                          Integer ex_type3,
//                                                          Integer ex_type4,
//                                                          Integer ex_state,
//                                                          Integer exam_dep_id,
//                                                          Date startTime,
//                                                          Date endTime,
//                                                          String content) {
//        return exOrderMapper.exportAllExOrderList(ex_type2, ex_type3, ex_type4, ex_state,exam_dep_id,startTime, endTime, content);
//    }
}
