package com.lz.manage.model.dto.postInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.PostInfo;
/**
 * 岗位信息Vo对象 tb_post_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class PostInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 岗位编号 */
    private Long postId;

    /** 公司编号 */
    private Long companyId;

    /** 岗位名称 */
    private String postName;

    /** 岗位类型 */
    private Long postType;

    /** 岗位描述 */
    private String postDesc;

    /** 岗位描述 */
    private String postRequired;

    /** 状态（0待审核 1同意 2拒绝） */
    private Long postStatus;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param postInfoEdit 编辑对象
     * @return PostInfo
     */
    public static PostInfo editToObj(PostInfoEdit postInfoEdit) {
        if (postInfoEdit == null) {
            return null;
        }
        PostInfo postInfo = new PostInfo();
        BeanUtils.copyProperties(postInfoEdit, postInfo);
        return postInfo;
    }
}
