package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.model.enums.MCommonStatus;
import com.lz.manage.service.ICompanyInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PostInfoMapper;
import com.lz.manage.model.domain.PostInfo;
import com.lz.manage.service.IPostInfoService;
import com.lz.manage.model.dto.postInfo.PostInfoQuery;
import com.lz.manage.model.vo.postInfo.PostInfoVo;

/**
 * 岗位信息Service业务层处理
 *
 * @author YY
 * @date 2025-04-04
 */
@Service
public class PostInfoServiceImpl extends ServiceImpl<PostInfoMapper, PostInfo> implements IPostInfoService {
    @Resource
    private PostInfoMapper postInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ICompanyInfoService companyInfoService;

    //region mybatis代码

    /**
     * 查询岗位信息
     *
     * @param postId 岗位信息主键
     * @return 岗位信息
     */
    @Override
    public PostInfo selectPostInfoByPostId(Long postId) {
        return postInfoMapper.selectPostInfoByPostId(postId);
    }

    /**
     * 查询岗位信息列表
     *
     * @param postInfo 岗位信息
     * @return 岗位信息
     */
    @Override
    public List<PostInfo> selectPostInfoList(PostInfo postInfo) {
        List<PostInfo> postInfos = postInfoMapper.selectPostInfoList(postInfo);
        for (PostInfo info : postInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            CompanyInfo companyInfo = companyInfoService.selectCompanyInfoByCompanyId(info.getCompanyId());
            if (StringUtils.isNotNull(companyInfo)) {
                info.setCompanyName(companyInfo.getCompanyName());
            }
        }
        return postInfos;
    }

    /**
     * 新增岗位信息
     *
     * @param postInfo 岗位信息
     * @return 结果
     */
    @Override
    public int insertPostInfo(PostInfo postInfo) {
        postInfo.setPostStatus(Long.valueOf(MCommonStatus.COMMON_STATUS_0.getValue()));
        postInfo.setUserId(SecurityUtils.getUserId());
        postInfo.setCreateTime(DateUtils.getNowDate());
        return postInfoMapper.insertPostInfo(postInfo);
    }

    /**
     * 修改岗位信息
     *
     * @param postInfo 岗位信息
     * @return 结果
     */
    @Override
    public int updatePostInfo(PostInfo postInfo) {
        //查询数据库内容
        PostInfo postInfoDb = postInfoMapper.selectPostInfoByPostId(postInfo.getPostId());
        if (StringUtils.isNull(postInfoDb)) {
            throw new ServiceException("数据为空");
        }
        if (!postInfoDb.getPostStatus().equals(postInfo.getPostStatus()) && !SecurityUtils.hasPermi("manage:postInfo:audit")) {
            throw new ServiceException("权限不足");
        }
        postInfo.setUpdateTime(DateUtils.getNowDate());
        return postInfoMapper.updatePostInfo(postInfo);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位信息主键
     * @return 结果
     */
    @Override
    public int deletePostInfoByPostIds(Long[] postIds) {
        return postInfoMapper.deletePostInfoByPostIds(postIds);
    }

    /**
     * 删除岗位信息信息
     *
     * @param postId 岗位信息主键
     * @return 结果
     */
    @Override
    public int deletePostInfoByPostId(Long postId) {
        return postInfoMapper.deletePostInfoByPostId(postId);
    }

    //endregion
    @Override
    public QueryWrapper<PostInfo> getQueryWrapper(PostInfoQuery postInfoQuery) {
        QueryWrapper<PostInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = postInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long postId = postInfoQuery.getPostId();
        queryWrapper.eq(StringUtils.isNotNull(postId), "post_id", postId);

        String postName = postInfoQuery.getPostName();
        queryWrapper.like(StringUtils.isNotEmpty(postName), "post_name", postName);

        Long postType = postInfoQuery.getPostType();
        queryWrapper.eq(StringUtils.isNotNull(postType), "post_type", postType);

        Long userId = postInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = postInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = postInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = postInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<PostInfoVo> convertVoList(List<PostInfo> postInfoList) {
        if (StringUtils.isEmpty(postInfoList)) {
            return Collections.emptyList();
        }
        return postInfoList.stream().map(PostInfoVo::objToVo).collect(Collectors.toList());
    }

}
