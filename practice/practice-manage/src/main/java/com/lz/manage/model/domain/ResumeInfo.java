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
 * 简历信息对象 tb_resume_info
 *
 * @author YY
 * @date 2025-04-04
 */
@TableName("tb_resume_info")
@Data
public class ResumeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 简历编号
     */
    @Excel(name = "简历编号")
    @TableId(value = "resume_id", type = IdType.ASSIGN_ID)
    private Long resumeId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String fullName;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phone;

    /**
     * 电子邮箱
     */
    @Excel(name = "电子邮箱")
    private String email;

    /**
     * 性别（0女 1男）
     */
    @Excel(name = "性别", readConverterExp = "0=女,1=男")
    private Long gender;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 求职意向
     */
    @Excel(name = "求职意向")
    private String jobIntention;

    /**
     * 教育经历
     */
    @Excel(name = "教育经历")
    private String education;

    /**
     * 工作经历
     */
    @Excel(name = "工作经历")
    private String workExperience;

    /**
     * 自我介绍
     */
    @Excel(name = "自我介绍")
    private String introduction;

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
