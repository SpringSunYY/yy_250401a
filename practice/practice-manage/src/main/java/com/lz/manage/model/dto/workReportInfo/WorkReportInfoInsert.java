package com.lz.manage.model.dto.workReportInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.WorkReportInfo;
/**
 * 工作报告Vo对象 tb_work_report_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class WorkReportInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 报告编号 */
    private Long id;

    /** 报告类型 */
    private Long reportType;

    /** 报告日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportDate;

    /** 工作内容描述 */
    private String content;

    /** 未来计划 */
    private String plan;

    /** 遇到问题 */
    private String problems;

    /** 评分 */
    private Long score;

    /** 评语 */
    private String comment;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param workReportInfoInsert 插入对象
     * @return WorkReportInfoInsert
     */
    public static WorkReportInfo insertToObj(WorkReportInfoInsert workReportInfoInsert) {
        if (workReportInfoInsert == null) {
            return null;
        }
        WorkReportInfo workReportInfo = new WorkReportInfo();
        BeanUtils.copyProperties(workReportInfoInsert, workReportInfo);
        return workReportInfo;
    }
}
