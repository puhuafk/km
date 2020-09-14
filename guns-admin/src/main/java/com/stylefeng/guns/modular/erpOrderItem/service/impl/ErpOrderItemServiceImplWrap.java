package com.stylefeng.guns.modular.erpOrderItem.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.common.constant.DatasourceEnum;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.erpOrderItem.service.IErpOrderItemService;
import com.stylefeng.guns.modular.system.model.ErpOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ErpOrderItemServiceImplWrap {

    @Autowired
    private IErpOrderItemService erpOrderItemService;

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public ErpOrderItem selectById(Long erpOrderItemId) {
        return erpOrderItemService.selectById(erpOrderItemId);
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public ErpOrderItem selectOne(Wrapper wrapper) {
        return erpOrderItemService.selectOne(wrapper);
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public List<ErpOrderItem> selectByOrderId(Long orderId) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("o_id", orderId);
        return erpOrderItemService.selectList(wrapper);
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public List<ErpOrderItem> selectList(Wrapper wrapper) {
        return erpOrderItemService.selectList(wrapper);
    }

}
