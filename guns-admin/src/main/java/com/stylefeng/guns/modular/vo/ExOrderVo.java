package com.stylefeng.guns.modular.vo;

import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.system.model.ExOrderExam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@ToString
@Getter
@Setter
public class ExOrderVo extends ExOrder {

    private List<ExOrderExam> exOrderExamList;//审核信息集合

    private String exOrderInfo;

    public static ExOrderVo adapt(ExOrder exOrder) {
        ExOrderVo vo = new ExOrderVo();
        BeanUtils.copyProperties(exOrder, vo);
        return vo;
    }

}
