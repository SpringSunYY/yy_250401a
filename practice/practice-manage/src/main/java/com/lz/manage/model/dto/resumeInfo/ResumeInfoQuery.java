package com.lz.manage.model.dto.resumeInfo;

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
import com.lz.manage.model.domain.ResumeInfo;
/**
 * 简历信息Query对象 tb_resume_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class ResumeInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 简历编号 */
    private Long resumeId;

    /** 姓名 */
    private String fullName;

    /** 性别（0女 1男） */
    private Long gender;

    /** 创建人 */
    private Long userId;

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
     * @param resumeInfoQuery 查询对象
     * @return ResumeInfo
     */
    public static ResumeInfo queryToObj(ResumeInfoQuery resumeInfoQuery) {
        if (resumeInfoQuery == null) {
            return null;
        }
        ResumeInfo resumeInfo = new ResumeInfo();
        BeanUtils.copyProperties(resumeInfoQuery, resumeInfo);
        return resumeInfo;
    }
}
