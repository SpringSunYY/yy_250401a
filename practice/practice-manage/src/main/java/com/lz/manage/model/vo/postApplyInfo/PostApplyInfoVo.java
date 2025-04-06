package com.lz.manage.model.vo.postApplyInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PostApplyInfo;

/**
 * 岗位申请Vo对象 tb_post_apply_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class PostApplyInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请编号
     */
    @Excel(name = "申请编号")
    private Long applyId;

    /**
     * 公司编号
     */
    @Excel(name = "公司编号")
    private String companyName;
    private Long companyId;

    /**
     * 简历编号
     */
    @Excel(name = "简历编号")
    private String resumeName;
    private Long resumeId;

    /**
     * 岗位编号
     */
    @Excel(name = "岗位编号")
    private String postName;
    private Long postId;

    /**
     * 状态（0待审核 1同意 2拒绝）
     */
    @Excel(name = "状态", readConverterExp = "0=待审核,1=同意,2=拒绝")
    private Long applyStatus;

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
     * @param postApplyInfo PostApplyInfo实体对象
     * @return PostApplyInfoVo
     */
    public static PostApplyInfoVo objToVo(PostApplyInfo postApplyInfo) {
        if (postApplyInfo == null) {
            return null;
        }
        PostApplyInfoVo postApplyInfoVo = new PostApplyInfoVo();
        BeanUtils.copyProperties(postApplyInfo, postApplyInfoVo);
        return postApplyInfoVo;
    }
}
