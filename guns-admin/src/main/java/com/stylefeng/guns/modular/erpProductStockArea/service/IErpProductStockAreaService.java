package com.stylefeng.guns.modular.erpProductStockArea.service;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.ErpProductStockArea;

import java.util.List;

/**
 *
 * @author
 *
 */
public interface IErpProductStockAreaService extends IService<ErpProductStockArea> {

    List<ErpProductStockArea> queryStoreArea(Long productId);


}
