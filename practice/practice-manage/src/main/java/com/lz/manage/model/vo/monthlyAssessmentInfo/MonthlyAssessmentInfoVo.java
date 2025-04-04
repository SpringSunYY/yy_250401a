package com.lz.manage.model.vo.monthlyAssessmentInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
/**
 * 月度考核Vo对象 tb_monthly_assessment_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class MonthlyAssessmentInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 考核编号 */
    @Excel(name = "考核编号")
    private Long id;

    /** 公司编号 */
    @Excel(name = "公司编号")
    private Long companyId;

    /** 考核年份 */
    @Excel(name = "考核年份")
    private Long assessYear;

    /** 考核月份 */
    @Excel(name = "考核月份")
    private Long assessMonth;

    /** 考核得分 */
    @Excel(name = "考核得分")
    private Long score;

    /** 评语 */
    @Excel(name = "评语")
    private String comments;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 用户 */
    @Excel(name = "用户")
    private Long userId;

    /** 班级 */
    @Excel(name = "班级")
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;


     /**
     * 对象转封装类
     *
     * @param monthlyAssessmentInfo MonthlyAssessmentInfo实体对象
     * @return MonthlyAssessmentInfoVo
     */
    public static MonthlyAssessmentInfoVo objToVo(MonthlyAssessmentInfo monthlyAssessmentInfo) {
        if (monthlyAssessmentInfo == null) {
            return null;
        }
        MonthlyAssessmentInfoVo monthlyAssessmentInfoVo = new MonthlyAssessmentInfoVo();
        BeanUtils.copyProperties(monthlyAssessmentInfo, monthlyAssessmentInfoVo);
        return monthlyAssessmentInfoVo;
    }
}
