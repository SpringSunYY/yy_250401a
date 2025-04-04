package com.lz.manage.model.dto.workReportInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.WorkReportInfo;
/**
 * 工作报告Query对象 tb_work_report_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class WorkReportInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 报告编号 */
    private Long id;

    /** 报告类型 */
    private Long reportType;

    /** 报告日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportDate;

    /** 评分 */
    private Long score;

    /** 评语 */
    private String comment;

    /** 创建人 */
    private Long userId;

    /** 班级 */
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param workReportInfoQuery 查询对象
     * @return WorkReportInfo
     */
    public static WorkReportInfo queryToObj(WorkReportInfoQuery workReportInfoQuery) {
        if (workReportInfoQuery == null) {
            return null;
        }
        WorkReportInfo workReportInfo = new WorkReportInfo();
        BeanUtils.copyProperties(workReportInfoQuery, workReportInfo);
        return workReportInfo;
    }
}
