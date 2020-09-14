package com.stylefeng.guns.modular.orderItem.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.OrderItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author curry
 * @since 2018-10-23
 */
public interface IOrderItemService extends IService<OrderItem> {
    void insertOrderItem(OrderItem orderItem);
    String  readExcelFile(MultipartFile file);
    List<OrderItem> selectBySn(Long exErpOrderId);
}
