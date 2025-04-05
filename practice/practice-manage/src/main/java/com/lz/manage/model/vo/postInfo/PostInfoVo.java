package com.lz.manage.model.vo.postInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.PostInfo;
/**
 * 岗位信息Vo对象 tb_post_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class PostInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 岗位编号 */
    @Excel(name = "岗位编号")
    private Long postId;

    /** 公司编号 */
    @Excel(name = "公司编号")
    private String companyName;
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
    private String userName;
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


     /**
     * 对象转封装类
     *
     * @param postInfo PostInfo实体对象
     * @return PostInfoVo
     */
    public static PostInfoVo objToVo(PostInfo postInfo) {
        if (postInfo == null) {
            return null;
        }
        PostInfoVo postInfoVo = new PostInfoVo();
        BeanUtils.copyProperties(postInfo, postInfoVo);
        return postInfoVo;
    }
}
