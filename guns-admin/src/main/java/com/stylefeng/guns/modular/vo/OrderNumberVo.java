package com.stylefeng.guns.modular.vo;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderNumberVo {

    private Integer allAllNumber =0; //所有订单，只有财务会用到

    private Integer AllNumber=0; //全部订单数量

    private Integer returnAllNumber=0; //已出库数量

    private Integer examNumber=0;//已审核数量

    private Integer initExamNumber=0;//待审核数量（已发起审核(未结束)）

    private Integer notExamNumber=0;//待审核数量

    private Integer checkNumber=0;//已核销数量

    private Integer notCheckNumber=0;//待核销数量

    private Integer outStoreNumber=0;//已出库数量

    private Integer notOutStoreNumber=0;//待出库数量

    private Integer closeOrderNumber=0;//已关闭订单数量


}
