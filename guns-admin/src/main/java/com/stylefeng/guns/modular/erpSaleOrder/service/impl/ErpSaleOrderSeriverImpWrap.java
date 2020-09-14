package com.stylefeng.guns.modular.erpSaleOrder.service.impl;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.common.constant.DatasourceEnum;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.erpSaleOrder.service.IErpSaleOrderService;
import com.stylefeng.guns.modular.system.model.ErpSaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ErpSaleOrderSeriverImpWrap {

    @Autowired
    private IErpSaleOrderService erpSaleOrderService;


    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public List<ErpSaleOrder> selectList(Wrapper wrapper) {
        List<ErpSaleOrder> erpSaleOrders = erpSaleOrderService.selectList(wrapper);
        return erpSaleOrders;
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Page<ErpSaleOrder> selectPage(Page page) {
        Page<ErpSaleOrder> erpSaleOrderPage = erpSaleOrderService.selectPage(page);
        return erpSaleOrderPage;
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Page<ErpSaleOrder> selectPage(Page page,Wrapper wrapper) {
        Page<ErpSaleOrder> erpSaleOrderPage = erpSaleOrderService.selectPage(page,wrapper);
        return erpSaleOrderPage;
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public ErpSaleOrder selectById(Long erpSaleOrderId) {
        ErpSaleOrder erpSaleOrde = erpSaleOrderService.selectById(erpSaleOrderId);
        return erpSaleOrde;
    }

    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public Integer selectCount(Wrapper wrapper) {
        return erpSaleOrderService.selectCount(wrapper);
    }







}
