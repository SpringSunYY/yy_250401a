package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.PostInfo;
import com.lz.manage.model.vo.postInfo.PostInfoVo;
import com.lz.manage.model.dto.postInfo.PostInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 岗位信息Service接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface IPostInfoService extends IService<PostInfo>
{
    //region mybatis代码
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
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的岗位信息主键集合
     * @return 结果
     */
    public int deletePostInfoByPostIds(Long[] postIds);

    /**
     * 删除岗位信息信息
     * 
     * @param postId 岗位信息主键
     * @return 结果
     */
    public int deletePostInfoByPostId(Long postId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param postInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PostInfo> getQueryWrapper(PostInfoQuery postInfoQuery);

    /**
     * 转换vo
     *
     * @param postInfoList PostInfo集合
     * @return PostInfoVO集合
     */
    List<PostInfoVo> convertVoList(List<PostInfo> postInfoList);
}
