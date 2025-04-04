package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 月度考核Mapper接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface MonthlyAssessmentInfoMapper extends BaseMapper<MonthlyAssessmentInfo>
{
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
     * 删除月度考核
     * 
     * @param id 月度考核主键
     * @return 结果
     */
    public int deleteMonthlyAssessmentInfoById(Long id);

    /**
     * 批量删除月度考核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMonthlyAssessmentInfoByIds(Long[] ids);
}
