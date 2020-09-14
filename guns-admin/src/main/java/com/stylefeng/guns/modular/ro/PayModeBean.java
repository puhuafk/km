package com.stylefeng.guns.modular.ro;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
public class PayModeBean {

    private String payType;

    private BigDecimal amoun;
}
