package com.stylefeng.guns.modular.system.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.system.model.ErpProductStockArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 *
 */
public interface ErpProductStockAreaMapper extends BaseMapper<ErpProductStockArea> {


    List<ErpProductStockArea> queryStoreArea(@Param(value="productId")Long productId);
}
