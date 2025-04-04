package com.lz.manage.model.dto.resumeInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ResumeInfo;
/**
 * 简历信息Vo对象 tb_resume_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class ResumeInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 简历编号 */
    private Long resumeId;

    /** 姓名 */
    private String fullName;

    /** 联系电话 */
    private String phone;

    /** 电子邮箱 */
    private String email;

    /** 性别（0女 1男） */
    private Long gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    /** 地址 */
    private String address;

    /** 求职意向 */
    private String jobIntention;

    /** 教育经历 */
    private String education;

    /** 工作经历 */
    private String workExperience;

    /** 自我介绍 */
    private String introduction;

    /** 创建人 */
    private Long userId;

    /** 班级 */
    private Long deptId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param resumeInfoInsert 插入对象
     * @return ResumeInfoInsert
     */
    public static ResumeInfo insertToObj(ResumeInfoInsert resumeInfoInsert) {
        if (resumeInfoInsert == null) {
            return null;
        }
        ResumeInfo resumeInfo = new ResumeInfo();
        BeanUtils.copyProperties(resumeInfoInsert, resumeInfo);
        return resumeInfo;
    }
}
