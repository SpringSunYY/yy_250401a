package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ResumeInfo;
import com.lz.manage.model.vo.resumeInfo.ResumeInfoVo;
import com.lz.manage.model.dto.resumeInfo.ResumeInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 简历信息Service接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface IResumeInfoService extends IService<ResumeInfo>
{
    //region mybatis代码
    /**
     * 查询简历信息
     * 
     * @param resumeId 简历信息主键
     * @return 简历信息
     */
    public ResumeInfo selectResumeInfoByResumeId(Long resumeId);

    /**
     * 查询简历信息列表
     * 
     * @param resumeInfo 简历信息
     * @return 简历信息集合
     */
    public List<ResumeInfo> selectResumeInfoList(ResumeInfo resumeInfo);

    /**
     * 新增简历信息
     * 
     * @param resumeInfo 简历信息
     * @return 结果
     */
    public int insertResumeInfo(ResumeInfo resumeInfo);

    /**
     * 修改简历信息
     * 
     * @param resumeInfo 简历信息
     * @return 结果
     */
    public int updateResumeInfo(ResumeInfo resumeInfo);

    /**
     * 批量删除简历信息
     * 
     * @param resumeIds 需要删除的简历信息主键集合
     * @return 结果
     */
    public int deleteResumeInfoByResumeIds(Long[] resumeIds);

    /**
     * 删除简历信息信息
     * 
     * @param resumeId 简历信息主键
     * @return 结果
     */
    public int deleteResumeInfoByResumeId(Long resumeId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param resumeInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ResumeInfo> getQueryWrapper(ResumeInfoQuery resumeInfoQuery);

    /**
     * 转换vo
     *
     * @param resumeInfoList ResumeInfo集合
     * @return ResumeInfoVO集合
     */
    List<ResumeInfoVo> convertVoList(List<ResumeInfo> resumeInfoList);
}
