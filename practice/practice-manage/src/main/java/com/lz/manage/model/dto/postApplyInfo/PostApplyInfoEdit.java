package com.lz.manage.model.dto.postApplyInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PostApplyInfo;
/**
 * 岗位申请Vo对象 tb_post_apply_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class PostApplyInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 申请编号 */
    private Long applyId;

    /** 公司编号 */
    private Long companyId;

    /** 简历编号 */
    private Long resumeId;

    /** 岗位编号 */
    private Long postId;

    /** 状态（0待审核 1同意 2拒绝） */
    private Long applyStatus;

    /** 创建人 */
    private Long userId;

    /** 班级 */
    private Long deptId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param postApplyInfoEdit 编辑对象
     * @return PostApplyInfo
     */
    public static PostApplyInfo editToObj(PostApplyInfoEdit postApplyInfoEdit) {
        if (postApplyInfoEdit == null) {
            return null;
        }
        PostApplyInfo postApplyInfo = new PostApplyInfo();
        BeanUtils.copyProperties(postApplyInfoEdit, postApplyInfo);
        return postApplyInfo;
    }
}
