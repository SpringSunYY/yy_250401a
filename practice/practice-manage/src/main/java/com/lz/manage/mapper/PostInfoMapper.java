package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PostInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 岗位信息Mapper接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface PostInfoMapper extends BaseMapper<PostInfo>
{
    /**
     * 查询岗位信息
     * 
     * @param postId 岗位信息主键
     * @return 岗位信息
     */
    public PostInfo selectPostInfoByPostId(Long postId);

    /**
     * 查询岗位信息列表
     * 
     * @param postInfo 岗位信息
     * @return 岗位信息集合
     */
    public List<PostInfo> selectPostInfoList(PostInfo postInfo);

    /**
     * 新增岗位信息
     * 
     * @param postInfo 岗位信息
     * @return 结果
     */
    public int insertPostInfo(PostInfo postInfo);

    /**
     * 修改岗位信息
     * 
     * @param postInfo 岗位信息
     * @return 结果
     */
    public int updatePostInfo(PostInfo postInfo);

    /**
     * 删除岗位信息
     * 
     * @param postId 岗位信息主键
     * @return 结果
     */
    public int deletePostInfoByPostId(Long postId);

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePostInfoByPostIds(Long[] postIds);
}
