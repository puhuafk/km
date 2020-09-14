package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程表

 * </p>
 *
 * @author chengshuai
 * @since 2018-10-16
 */
@Getter
@Setter
@ToString
@TableName("ex_order_flow")
public class ExOrderFlow extends Model<ExOrderFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
    /**
     * 异常订单id
     */
    @TableField("ex_order_id")
    private String exOrderId;
    /**
     * 流程类型（1减红包、2退单品、3已出库退单）
     */
    @TableField("flow_type")
    private Integer flowType;
    /**
     * 流程1（部门id）
     */
    @TableField("sys_dept_id1")
    private Integer sysDeptId1;
    /**
     * 流程2（部门id）
     */
    @TableField("sys_dept_id2")
    private Integer sysDeptId2;
    /**
     * 流程3（部门id）
     */
    @TableField("sys_dept_id3")
    private Integer sysDeptId3;
    /**
     * 流程4（部门id）、以0结尾、查询到0本流程结束
     */
    @TableField("sys_dept_id4")
    private Integer sysDeptId4;
    /**
     * 流程5（部门id）
     */
    @TableField("sys_dept_id5")
    private Integer sysDeptId5;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人
     */
    private String operator;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
