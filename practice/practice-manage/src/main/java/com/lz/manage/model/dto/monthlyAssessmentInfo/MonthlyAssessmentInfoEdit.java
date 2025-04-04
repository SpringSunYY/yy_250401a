package com.lz.manage.model.dto.monthlyAssessmentInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
/**
 * 月度考核Vo对象 tb_monthly_assessment_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class MonthlyAssessmentInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 考核编号 */
    private Long id;

    /** 公司编号 */
    private Long companyId;

    /** 考核年份 */
    private Long assessYear;

    /** 考核月份 */
    private Long assessMonth;

    /** 考核得分 */
    private Long score;

    /** 评语 */
    private String comments;

    /** 用户 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param monthlyAssessmentInfoEdit 编辑对象
     * @return MonthlyAssessmentInfo
     */
    public static MonthlyAssessmentInfo editToObj(MonthlyAssessmentInfoEdit monthlyAssessmentInfoEdit) {
        if (monthlyAssessmentInfoEdit == null) {
            return null;
        }
        MonthlyAssessmentInfo monthlyAssessmentInfo = new MonthlyAssessmentInfo();
        BeanUtils.copyProperties(monthlyAssessmentInfoEdit, monthlyAssessmentInfo);
        return monthlyAssessmentInfo;
    }
}
