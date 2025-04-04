package com.lz.manage.model.dto.postApplyInfo;

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
import com.lz.manage.model.domain.PostApplyInfo;
/**
 * 岗位申请Query对象 tb_post_apply_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class PostApplyInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 申请编号 */
    private Long applyId;

    /** 状态（0待审核 1同意 2拒绝） */
    private Long applyStatus;

    /** 创建人 */
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
     * @param postApplyInfoQuery 查询对象
     * @return PostApplyInfo
     */
    public static PostApplyInfo queryToObj(PostApplyInfoQuery postApplyInfoQuery) {
        if (postApplyInfoQuery == null) {
            return null;
        }
        PostApplyInfo postApplyInfo = new PostApplyInfo();
        BeanUtils.copyProperties(postApplyInfoQuery, postApplyInfo);
        return postApplyInfo;
    }
}
