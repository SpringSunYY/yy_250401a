package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.model.domain.PostInfo;
import com.lz.manage.model.domain.ResumeInfo;
import com.lz.manage.model.enums.MCommonStatus;
import com.lz.manage.service.ICompanyInfoService;
import com.lz.manage.service.IPostInfoService;
import com.lz.manage.service.IResumeInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PostApplyInfoMapper;
import com.lz.manage.model.domain.PostApplyInfo;
import com.lz.manage.service.IPostApplyInfoService;
import com.lz.manage.model.dto.postApplyInfo.PostApplyInfoQuery;
import com.lz.manage.model.vo.postApplyInfo.PostApplyInfoVo;

/**
 * 岗位申请Service业务层处理
 *
 * @author YY
 * @date 2025-04-04
 */
@Service
public class PostApplyInfoServiceImpl extends ServiceImpl<PostApplyInfoMapper, PostApplyInfo> implements IPostApplyInfoService {
    @Resource
    private PostApplyInfoMapper postApplyInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ICompanyInfoService companyInfoService;

    @Resource
    private IPostInfoService postInfoService;

    @Resource
    private IResumeInfoService resumeInfoService;
    //region mybatis代码

    /**
     * 查询岗位申请
     *
     * @param applyId 岗位申请主键
     * @return 岗位申请
     */
    @Override
    public PostApplyInfo selectPostApplyInfoByApplyId(Long applyId) {
        return postApplyInfoMapper.selectPostApplyInfoByApplyId(applyId);
    }

    /**
     * 查询岗位申请列表
     *
     * @param postApplyInfo 岗位申请
     * @return 岗位申请
     */
    @Override
    public List<PostApplyInfo> selectPostApplyInfoList(PostApplyInfo postApplyInfo) {
        List<PostApplyInfo> postApplyInfos = postApplyInfoMapper.selectPostApplyInfoList(postApplyInfo);
        for (PostApplyInfo info : postApplyInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            CompanyInfo companyInfo = companyInfoService.selectCompanyInfoByCompanyId(info.getCompanyId());
            if (StringUtils.isNotNull(companyInfo)) {
                info.setCompanyName(companyInfo.getCompanyName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            PostInfo postInfo = postInfoService.selectPostInfoByPostId(info.getPostId());
            if (StringUtils.isNotNull(postInfo)) {
                info.setPostName(postInfo.getPostName());
            }
            ResumeInfo resumeInfo = resumeInfoService.selectResumeInfoByResumeId(info.getResumeId());
            if (StringUtils.isNotNull(resumeInfo)) {
                info.setResumeName(resumeInfo.getFullName());
            }
        }
        return postApplyInfos;
    }

    /**
     * 新增岗位申请
     *
     * @param postApplyInfo 岗位申请
     * @return 结果
     */
    @Override
    public int insertPostApplyInfo(PostApplyInfo postApplyInfo) {
        //判断用户是否已经申请过
        List<PostApplyInfo> list = this.list(new LambdaQueryWrapper<>(PostApplyInfo.class)
                .eq(PostApplyInfo::getUserId, postApplyInfo.getUserId())
                .eq(PostApplyInfo::getPostId, postApplyInfo.getPostId()));
        if (StringUtils.isNotEmpty(list)) {
            throw new ServiceException("用户已经申请过");
        }
        //判断岗位是否存在且正常
        PostInfo postInfo = postInfoService.selectPostInfoByPostId(postApplyInfo.getPostId());
        if (StringUtils.isNull(postInfo)
                || !postInfo.getPostStatus().equals(Long.parseLong(MCommonStatus.COMMON_STATUS_1.getValue()))) {
            throw new ServiceException("岗位不存在或状态不正常");
        }
        //判断该公司是否存在且正常
        CompanyInfo companyInfo = companyInfoService.selectCompanyInfoByCompanyId(postInfo.getCompanyId());
        if (StringUtils.isNull(companyInfo)
                || !companyInfo.getCompanyStatus().equals(Long.parseLong(MCommonStatus.COMMON_STATUS_1.getValue()))) {
            throw new ServiceException("该公司不存在或状态不正常");
        }
        postApplyInfo.setCompanyId(companyInfo.getCompanyId());
        postApplyInfo.setApplyStatus(Long.parseLong(MCommonStatus.COMMON_STATUS_0.getValue()));
        postApplyInfo.setUserId(SecurityUtils.getUserId());
        postApplyInfo.setDeptId(SecurityUtils.getDeptId());
        postApplyInfo.setCreateTime(DateUtils.getNowDate());
        return postApplyInfoMapper.insertPostApplyInfo(postApplyInfo);
    }

    /**
     * 修改岗位申请
     *
     * @param postApplyInfo 岗位申请
     * @return 结果
     */
    @Override
    public int updatePostApplyInfo(PostApplyInfo postApplyInfo) {
        //判断岗位是否存在
        PostInfo postInfo = postInfoService.selectPostInfoByPostId(postApplyInfo.getPostId());
        if (StringUtils.isNull(postInfo)) {
            throw new ServiceException("岗位不存在");
        }
        if (!postInfo.getPostStatus().equals(postApplyInfo.getApplyStatus()) && !SecurityUtils.hasPermi("manage:postApplyInfo:audit")) {
            throw new ServiceException("权限不足");
        }
        postApplyInfo.setUpdateBy(SecurityUtils.getUsername());
        postApplyInfo.setUpdateTime(DateUtils.getNowDate());
        return postApplyInfoMapper.updatePostApplyInfo(postApplyInfo);
    }

    /**
     * 批量删除岗位申请
     *
     * @param applyIds 需要删除的岗位申请主键
     * @return 结果
     */
    @Override
    public int deletePostApplyInfoByApplyIds(Long[] applyIds) {
        return postApplyInfoMapper.deletePostApplyInfoByApplyIds(applyIds);
    }

    /**
     * 删除岗位申请信息
     *
     * @param applyId 岗位申请主键
     * @return 结果
     */
    @Override
    public int deletePostApplyInfoByApplyId(Long applyId) {
        return postApplyInfoMapper.deletePostApplyInfoByApplyId(applyId);
    }

    //endregion
    @Override
    public QueryWrapper<PostApplyInfo> getQueryWrapper(PostApplyInfoQuery postApplyInfoQuery) {
        QueryWrapper<PostApplyInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = postApplyInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long applyId = postApplyInfoQuery.getApplyId();
        queryWrapper.eq(StringUtils.isNotNull(applyId), "apply_id", applyId);

        Long applyStatus = postApplyInfoQuery.getApplyStatus();
        queryWrapper.eq(StringUtils.isNotNull(applyStatus), "apply_status", applyStatus);

        Long userId = postApplyInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = postApplyInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = postApplyInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = postApplyInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = postApplyInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<PostApplyInfoVo> convertVoList(List<PostApplyInfo> postApplyInfoList) {
        if (StringUtils.isEmpty(postApplyInfoList)) {
            return Collections.emptyList();
        }
        return postApplyInfoList.stream().map(PostApplyInfoVo::objToVo).collect(Collectors.toList());
    }

}
