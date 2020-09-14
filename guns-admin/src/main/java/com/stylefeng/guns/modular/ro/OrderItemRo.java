package com.stylefeng.guns.modular.ro;

import com.stylefeng.guns.modular.system.model.OrderItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemRo {

    private String productName;

    private Integer id;

    private Integer num;

    private OrderItem orderItem;
}
