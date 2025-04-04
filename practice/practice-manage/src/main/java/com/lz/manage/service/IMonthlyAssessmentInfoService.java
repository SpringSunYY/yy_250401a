package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
import com.lz.manage.model.vo.monthlyAssessmentInfo.MonthlyAssessmentInfoVo;
import com.lz.manage.model.dto.monthlyAssessmentInfo.MonthlyAssessmentInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 月度考核Service接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface IMonthlyAssessmentInfoService extends IService<MonthlyAssessmentInfo>
{
    //region mybatis代码
    /**
     * 查询月度考核
     * 
     * @param id 月度考核主键
     * @return 月度考核
     */
    public MonthlyAssessmentInfo selectMonthlyAssessmentInfoById(Long id);

    /**
     * 查询月度考核列表
     * 
     * @param monthlyAssessmentInfo 月度考核
     * @return 月度考核集合
     */
    public List<MonthlyAssessmentInfo> selectMonthlyAssessmentInfoList(MonthlyAssessmentInfo monthlyAssessmentInfo);

    /**
     * 新增月度考核
     * 
     * @param monthlyAssessmentInfo 月度考核
     * @return 结果
     */
    public int insertMonthlyAssessmentInfo(MonthlyAssessmentInfo monthlyAssessmentInfo);

    /**
     * 修改月度考核
     * 
     * @param monthlyAssessmentInfo 月度考核
     * @return 结果
     */
    public int updateMonthlyAssessmentInfo(MonthlyAssessmentInfo monthlyAssessmentInfo);

    /**
     * 批量删除月度考核
     * 
     * @param ids 需要删除的月度考核主键集合
     * @return 结果
     */
    public int deleteMonthlyAssessmentInfoByIds(Long[] ids);

    /**
     * 删除月度考核信息
     * 
     * @param id 月度考核主键
     * @return 结果
     */
    public int deleteMonthlyAssessmentInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param monthlyAssessmentInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<MonthlyAssessmentInfo> getQueryWrapper(MonthlyAssessmentInfoQuery monthlyAssessmentInfoQuery);

    /**
     * 转换vo
     *
     * @param monthlyAssessmentInfoList MonthlyAssessmentInfo集合
     * @return MonthlyAssessmentInfoVO集合
     */
    List<MonthlyAssessmentInfoVo> convertVoList(List<MonthlyAssessmentInfo> monthlyAssessmentInfoList);
}
