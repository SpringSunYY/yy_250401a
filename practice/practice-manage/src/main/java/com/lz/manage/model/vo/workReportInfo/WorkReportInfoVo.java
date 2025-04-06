package com.lz.manage.model.vo.workReportInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.WorkReportInfo;

/**
 * 工作报告Vo对象 tb_work_report_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class WorkReportInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 报告编号
     */
    @Excel(name = "报告编号")
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
    private String userName;
    private Long userId;

    /**
     * 班级
     */
    @Excel(name = "班级")
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
     * 对象转封装类
     *
     * @param workReportInfo WorkReportInfo实体对象
     * @return WorkReportInfoVo
     */
    public static WorkReportInfoVo objToVo(WorkReportInfo workReportInfo) {
        if (workReportInfo == null) {
            return null;
        }
        WorkReportInfoVo workReportInfoVo = new WorkReportInfoVo();
        BeanUtils.copyProperties(workReportInfo, workReportInfoVo);
        return workReportInfoVo;
    }
}
