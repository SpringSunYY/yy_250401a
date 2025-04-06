package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 工作报告对象 tb_work_report_info
 *
 * @author YY
 * @date 2025-04-04
 */
@TableName("tb_work_report_info")
@Data
public class WorkReportInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 报告编号
     */
    @Excel(name = "报告编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 报告类型
     */
    @Excel(name = "报告类型")
    private Long reportType;

    /**
     * 报告日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报告日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportDate;

    /**
     * 工作内容描述
     */
    @Excel(name = "工作内容描述")
    private String content;

    /**
     * 未来计划
     */
    @Excel(name = "未来计划")
    private String plan;

    /**
     * 遇到问题
     */
    @Excel(name = "遇到问题")
    private String problems;

    /**
     * 评分
     */
    @Excel(name = "评分")
    private Long score;

    /**
     * 评语
     */
    @Excel(name = "评语")
    private String comment;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /**
     * 班级
     */
    @Excel(name = "班级")
    @TableField(exist = false)
    private String deptName;
    private Long deptId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
