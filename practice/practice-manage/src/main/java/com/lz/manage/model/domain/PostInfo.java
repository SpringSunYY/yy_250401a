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
 * 岗位信息对象 tb_post_info
 *
 * @author YY
 * @date 2025-04-04
 */
@TableName("tb_post_info")
@Data
public class PostInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 岗位编号 */
    @Excel(name = "岗位编号")
    @TableId(value = "post_id", type = IdType.ASSIGN_ID)
    private Long postId;

    /** 公司编号 */
    @Excel(name = "公司编号")
    private Long companyId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 岗位类型 */
    @Excel(name = "岗位类型")
    private Long postType;

    /** 岗位描述 */
    @Excel(name = "岗位描述")
    private String postDesc;

    /** 岗位描述 */
    @Excel(name = "岗位描述")
    private String postRequired;

    /** 状态（0待审核 1同意 2拒绝） */
    @Excel(name = "状态", readConverterExp = "0=待审核,1=同意,2=拒绝")
    private Long postStatus;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long userId;

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

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
