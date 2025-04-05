package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ResumeInfoMapper;
import com.lz.manage.model.domain.ResumeInfo;
import com.lz.manage.service.IResumeInfoService;
import com.lz.manage.model.dto.resumeInfo.ResumeInfoQuery;
import com.lz.manage.model.vo.resumeInfo.ResumeInfoVo;

/**
 * 简历信息Service业务层处理
 *
 * @author YY
 * @date 2025-04-04
 */
@Service
public class ResumeInfoServiceImpl extends ServiceImpl<ResumeInfoMapper, ResumeInfo> implements IResumeInfoService {
    @Resource
    private ResumeInfoMapper resumeInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;
    //region mybatis代码

    /**
     * 查询简历信息
     *
     * @param resumeId 简历信息主键
     * @return 简历信息
     */
    @Override
    public ResumeInfo selectResumeInfoByResumeId(Long resumeId) {
        return resumeInfoMapper.selectResumeInfoByResumeId(resumeId);
    }

    /**
     * 查询简历信息列表
     *
     * @param resumeInfo 简历信息
     * @return 简历信息
     */
    @Override
    public List<ResumeInfo> selectResumeInfoList(ResumeInfo resumeInfo) {
        List<ResumeInfo> resumeInfos = resumeInfoMapper.selectResumeInfoList(resumeInfo);
        for (ResumeInfo info : resumeInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            //脱敏
            if (StringUtils.isNotEmpty(info.getPhone())) {
                info.setPhone(StringUtils.hide(info.getPhone(), 3, info.getPhone().length() - 4));
            }
            if (StringUtils.isNotEmpty(info.getEmail())) {
                info.setEmail(StringUtils.hide(info.getEmail(), 0, 3));
            }
        }
        return resumeInfos;
    }

    /**
     * 新增简历信息
     *
     * @param resumeInfo 简历信息
     * @return 结果
     */
    @Override
    public int insertResumeInfo(ResumeInfo resumeInfo) {
        resumeInfo.setUserId(SecurityUtils.getUserId());
        resumeInfo.setDeptId(SecurityUtils.getDeptId());
        resumeInfo.setCreateTime(DateUtils.getNowDate());
        return resumeInfoMapper.insertResumeInfo(resumeInfo);
    }

    /**
     * 修改简历信息
     *
     * @param resumeInfo 简历信息
     * @return 结果
     */
    @Override
    public int updateResumeInfo(ResumeInfo resumeInfo) {
        resumeInfo.setUpdateBy(SecurityUtils.getUsername());
        resumeInfo.setUpdateTime(DateUtils.getNowDate());
        return resumeInfoMapper.updateResumeInfo(resumeInfo);
    }

    /**
     * 批量删除简历信息
     *
     * @param resumeIds 需要删除的简历信息主键
     * @return 结果
     */
    @Override
    public int deleteResumeInfoByResumeIds(Long[] resumeIds) {
        return resumeInfoMapper.deleteResumeInfoByResumeIds(resumeIds);
    }

    /**
     * 删除简历信息信息
     *
     * @param resumeId 简历信息主键
     * @return 结果
     */
    @Override
    public int deleteResumeInfoByResumeId(Long resumeId) {
        return resumeInfoMapper.deleteResumeInfoByResumeId(resumeId);
    }

    //endregion
    @Override
    public QueryWrapper<ResumeInfo> getQueryWrapper(ResumeInfoQuery resumeInfoQuery) {
        QueryWrapper<ResumeInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = resumeInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long resumeId = resumeInfoQuery.getResumeId();
        queryWrapper.eq(StringUtils.isNotNull(resumeId), "resume_id", resumeId);

        String fullName = resumeInfoQuery.getFullName();
        queryWrapper.like(StringUtils.isNotEmpty(fullName), "full_name", fullName);

        Long gender = resumeInfoQuery.getGender();
        queryWrapper.eq(StringUtils.isNotNull(gender), "gender", gender);

        Long userId = resumeInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = resumeInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = resumeInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = resumeInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ResumeInfoVo> convertVoList(List<ResumeInfo> resumeInfoList) {
        if (StringUtils.isEmpty(resumeInfoList)) {
            return Collections.emptyList();
        }
        return resumeInfoList.stream().map(ResumeInfoVo::objToVo).collect(Collectors.toList());
    }

}
