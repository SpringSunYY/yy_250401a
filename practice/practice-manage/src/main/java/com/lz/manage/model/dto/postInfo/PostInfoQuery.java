package com.lz.manage.model.dto.postInfo;

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
import com.lz.manage.model.domain.PostInfo;
/**
 * 岗位信息Query对象 tb_post_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class PostInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 岗位编号 */
    private Long postId;

    /** 岗位名称 */
    private String postName;

    private Long postStatus;

    /** 岗位类型 */
    private Long postType;

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
     * @param postInfoQuery 查询对象
     * @return PostInfo
     */
    public static PostInfo queryToObj(PostInfoQuery postInfoQuery) {
        if (postInfoQuery == null) {
            return null;
        }
        PostInfo postInfo = new PostInfo();
        BeanUtils.copyProperties(postInfoQuery, postInfo);
        return postInfo;
    }
}
