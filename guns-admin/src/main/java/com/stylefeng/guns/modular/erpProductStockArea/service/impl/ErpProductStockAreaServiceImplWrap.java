package com.stylefeng.guns.modular.erpProductStockArea.service.impl;



import com.stylefeng.guns.core.common.constant.DatasourceEnum;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.erpProductStockArea.service.IErpProductStockAreaService;
import com.stylefeng.guns.modular.system.model.ErpProductStockArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author
 *
 */

@Service
public class ErpProductStockAreaServiceImplWrap {


    @Autowired
    private IErpProductStockAreaService erpProductStockAreaService;


    @DataSource(name = DatasourceEnum.DATA_SOURCE_ERP)
    @Transactional
    public List<ErpProductStockArea> queryStoreArea(Long productId) {

        return erpProductStockAreaService.queryStoreArea(productId);
    }






}
