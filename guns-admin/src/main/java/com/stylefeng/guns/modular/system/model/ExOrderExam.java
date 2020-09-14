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
 * 异常订单审核表
 * </p>
 *
 * @author chengshuai
 * @since 2018-10-16
 */
@Getter
@Setter
@ToString
@TableName("ex_order_exam")
public class ExOrderExam extends Model<ExOrderExam> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;
    /**
     * 异常订单id
     */
    @TableField("ex_order_id")
    private Integer exOrderId;
    /**
     * 审核部门id
     */
    @TableField("sys_dept_id")
    private Integer sysDeptId;
    /**
     * 审核人id
     */
    @TableField("sys_user_id")
    private Integer sysUserId;
    /**
     * 审核意见
     */
    private String opinion;
    /**
     * 操作时间
     */
    @TableField("operate_time")
    private Date operateTime;
    /**
     * 创建人
     */
    private String operator;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 是否审核通过（0未通过，1通过）
     */
    @TableField("if_pass")
    private Boolean ifPass;

    /**
     * 是否为发起（1为发起审核信息）
     */
    @TableField("if_launch")
    private Boolean ifLaunch;

    /**
     * 是否为发起（1为发起审核信息）
     */
    @TableField("create_dept_name")
    private String createDeptName;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
