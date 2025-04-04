package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.MonthlyAssessmentInfoMapper;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
import com.lz.manage.service.IMonthlyAssessmentInfoService;
import com.lz.manage.model.dto.monthlyAssessmentInfo.MonthlyAssessmentInfoQuery;
import com.lz.manage.model.vo.monthlyAssessmentInfo.MonthlyAssessmentInfoVo;

/**
 * 月度考核Service业务层处理
 * 
 * @author YY
 * @date 2025-04-04
 */
@Service
public class MonthlyAssessmentInfoServiceImpl extends ServiceImpl<MonthlyAssessmentInfoMapper, MonthlyAssessmentInfo> implements IMonthlyAssessmentInfoService
{
    @Resource
    private MonthlyAssessmentInfoMapper monthlyAssessmentInfoMapper;

    //region mybatis代码
    /**
     * 查询月度考核
     * 
     * @param id 月度考核主键
     * @return 月度考核
     */
    @Override
    public MonthlyAssessmentInfo selectMonthlyAssessmentInfoById(Long id)
    {
        return monthlyAssessmentInfoMapper.selectMonthlyAssessmentInfoById(id);
    }

    /**
     * 查询月度考核列表
     * 
     * @param monthlyAssessmentInfo 月度考核
     * @return 月度考核
     */
    @Override
    public List<MonthlyAssessmentInfo> selectMonthlyAssessmentInfoList(MonthlyAssessmentInfo monthlyAssessmentInfo)
    {
        return monthlyAssessmentInfoMapper.selectMonthlyAssessmentInfoList(monthlyAssessmentInfo);
    }

    /**
     * 新增月度考核
     * 
     * @param monthlyAssessmentInfo 月度考核
     * @return 结果
     */
    @Override
    public int insertMonthlyAssessmentInfo(MonthlyAssessmentInfo monthlyAssessmentInfo)
    {
        monthlyAssessmentInfo.setCreateTime(DateUtils.getNowDate());
        return monthlyAssessmentInfoMapper.insertMonthlyAssessmentInfo(monthlyAssessmentInfo);
    }

    /**
     * 修改月度考核
     * 
     * @param monthlyAssessmentInfo 月度考核
     * @return 结果
     */
    @Override
    public int updateMonthlyAssessmentInfo(MonthlyAssessmentInfo monthlyAssessmentInfo)
    {
        monthlyAssessmentInfo.setUpdateTime(DateUtils.getNowDate());
        return monthlyAssessmentInfoMapper.updateMonthlyAssessmentInfo(monthlyAssessmentInfo);
    }

    /**
     * 批量删除月度考核
     * 
     * @param ids 需要删除的月度考核主键
     * @return 结果
     */
    @Override
    public int deleteMonthlyAssessmentInfoByIds(Long[] ids)
    {
        return monthlyAssessmentInfoMapper.deleteMonthlyAssessmentInfoByIds(ids);
    }

    /**
     * 删除月度考核信息
     * 
     * @param id 月度考核主键
     * @return 结果
     */
    @Override
    public int deleteMonthlyAssessmentInfoById(Long id)
    {
        return monthlyAssessmentInfoMapper.deleteMonthlyAssessmentInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<MonthlyAssessmentInfo> getQueryWrapper(MonthlyAssessmentInfoQuery monthlyAssessmentInfoQuery){
        QueryWrapper<MonthlyAssessmentInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = monthlyAssessmentInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = monthlyAssessmentInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long assessYear = monthlyAssessmentInfoQuery.getAssessYear();
        queryWrapper.eq( StringUtils.isNotNull(assessYear),"assess_year",assessYear);

        Long assessMonth = monthlyAssessmentInfoQuery.getAssessMonth();
        queryWrapper.eq( StringUtils.isNotNull(assessMonth),"assess_month",assessMonth);

        Long score = monthlyAssessmentInfoQuery.getScore();
        queryWrapper.eq( StringUtils.isNotNull(score),"score",score);

        String comments = monthlyAssessmentInfoQuery.getComments();
        queryWrapper.eq(StringUtils.isNotEmpty(comments) ,"comments",comments);

        String createBy = monthlyAssessmentInfoQuery.getCreateBy();
        queryWrapper.eq(StringUtils.isNotEmpty(createBy) ,"create_by",createBy);

        Long userId = monthlyAssessmentInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long deptId = monthlyAssessmentInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        Date createTime = monthlyAssessmentInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = monthlyAssessmentInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = monthlyAssessmentInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<MonthlyAssessmentInfoVo> convertVoList(List<MonthlyAssessmentInfo> monthlyAssessmentInfoList) {
        if (StringUtils.isEmpty(monthlyAssessmentInfoList)) {
            return Collections.emptyList();
        }
        return monthlyAssessmentInfoList.stream().map(MonthlyAssessmentInfoVo::objToVo).collect(Collectors.toList());
    }

}
