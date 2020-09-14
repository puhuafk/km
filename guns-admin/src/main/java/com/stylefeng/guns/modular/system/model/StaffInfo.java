package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 面试信息表
 * </p>
 *
 * @author curry
 * @since 2018-10-19
 *
 */
@TableName("staff_info")
@Getter
@Setter
@ToString
public class StaffInfo extends Model<StaffInfo> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 民族
     */
    private String nation;
    /**
     * 籍贯
     */
    @TableField("native_place")
    private String nativePlace;
    /**
     * 学历
     */
    private String education;
    /**
     * 婚姻情况
     */
    @TableField("ma_status")
    private String maStatus;
    /**
     * 生育状况
     */
    @TableField("fe_status")
    private String feStatus;
    /**
     * 工作情况
     */
    @TableField("work_status")
    private String workStatus;
    /**
     * 政治面貌
     */
    @TableField("po_status")
    private String poStatus;
    /**
     * 应聘岗位
     */
    private String position;
    /**
     * 薪资要求
     */
    private String pay;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 工作经历
     */
    @TableField("bu_history")
    private String buHistory;
    /**
     * 自我评价
     */
    @TableField("self_evaluation")
    private String selfEvaluation;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
