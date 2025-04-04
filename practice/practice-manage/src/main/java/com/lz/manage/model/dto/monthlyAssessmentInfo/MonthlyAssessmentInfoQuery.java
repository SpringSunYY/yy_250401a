package com.lz.manage.model.dto.monthlyAssessmentInfo;

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
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
/**
 * 月度考核Query对象 tb_monthly_assessment_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class MonthlyAssessmentInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 考核编号 */
    private Long id;

    /** 考核年份 */
    private Long assessYear;

    /** 考核月份 */
    private Long assessMonth;

    /** 考核得分 */
    private Long score;

    /** 评语 */
    private String comments;

    /** 创建人 */
    private String createBy;

    /** 用户 */
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
     * @param monthlyAssessmentInfoQuery 查询对象
     * @return MonthlyAssessmentInfo
     */
    public static MonthlyAssessmentInfo queryToObj(MonthlyAssessmentInfoQuery monthlyAssessmentInfoQuery) {
        if (monthlyAssessmentInfoQuery == null) {
            return null;
        }
        MonthlyAssessmentInfo monthlyAssessmentInfo = new MonthlyAssessmentInfo();
        BeanUtils.copyProperties(monthlyAssessmentInfoQuery, monthlyAssessmentInfo);
        return monthlyAssessmentInfo;
    }
}
