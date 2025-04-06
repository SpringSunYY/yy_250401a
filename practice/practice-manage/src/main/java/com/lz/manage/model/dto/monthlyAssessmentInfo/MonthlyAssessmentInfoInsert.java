package com.lz.manage.model.dto.monthlyAssessmentInfo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 月度考核Vo对象 tb_monthly_assessment_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class MonthlyAssessmentInfoInsert implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 考核编号
     */
    private Long id;

    /**
     * 公司编号
     */
    @NotNull(message = "公司编号不能为空")
    private Long companyId;

    /**
     * 考核年份
     */
    @NotNull(message = "考核年份不能为空")
    private Long assessYear;

    /**
     * 考核月份
     */
    @NotNull(message = "考核月份不能为空")
    @Min(value = 1, message = "考核月份不能小于1")
    @Max(value = 1, message = "考核月份不能大于12")
    private Long assessMonth;

    /**
     * 考核得分
     */
    @NotNull(message = "考核得分不能为空")
    @Min(value = 0, message = "考核得分不能小于0")
    @Max(value = 100, message = "考核得分不能大于100")
    private Long score;

    /**
     * 评语
     */
    private String comments;

    /**
     * 用户
     */
    @NotNull(message = "用户不能为空")
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param monthlyAssessmentInfoInsert 插入对象
     * @return MonthlyAssessmentInfoInsert
     */
    public static MonthlyAssessmentInfo insertToObj(MonthlyAssessmentInfoInsert monthlyAssessmentInfoInsert) {
        if (monthlyAssessmentInfoInsert == null) {
            return null;
        }
        MonthlyAssessmentInfo monthlyAssessmentInfo = new MonthlyAssessmentInfo();
        BeanUtils.copyProperties(monthlyAssessmentInfoInsert, monthlyAssessmentInfo);
        return monthlyAssessmentInfo;
    }
}
