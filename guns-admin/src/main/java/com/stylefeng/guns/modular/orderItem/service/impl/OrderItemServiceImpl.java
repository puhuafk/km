package com.stylefeng.guns.modular.orderItem.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.system.dao.OrderItemMapper;
import com.stylefeng.guns.modular.system.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author curry
 * @since 2018-10-23
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public String readExcelFile(MultipartFile file) {
        String result = "";
        String orderItem =readExcelFile(file);
            if(orderItem !=null && orderItem.isEmpty()){
                result = "上传成功" ;
            }else{
                result = "上传失败";
            }

        return result;
    }
    @Override
    public void insertOrderItem(OrderItem orderItem) {
    }

    @Override
    public List<OrderItem> selectBySn(Long exErpOrderId) {
        List<OrderItem> orderItem = orderItemMapper.selectBySn(exErpOrderId);
        return orderItem;
    }
}
