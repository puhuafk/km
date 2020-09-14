package com.stylefeng.guns.modular.erpProductStockArea.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.erpProductStockArea.service.IErpProductStockAreaService;

import com.stylefeng.guns.modular.system.dao.ErpProductStockAreaMapper;
import com.stylefeng.guns.modular.system.model.ErpProductStockArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*
* @author
*
*/
@Service
public class ErpProductStockAreaServiceImpl extends ServiceImpl<ErpProductStockAreaMapper, ErpProductStockArea> implements IErpProductStockAreaService {


    @Autowired
    ErpProductStockAreaMapper erpProductStockAreaMapper;

    @Override
    public List<ErpProductStockArea> queryStoreArea(Long productId){
       List<ErpProductStockArea> list = erpProductStockAreaMapper.queryStoreArea(productId);

        return list;
    }
}
