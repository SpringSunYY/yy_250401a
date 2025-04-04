package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.WorkReportInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 工作报告Mapper接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface WorkReportInfoMapper extends BaseMapper<WorkReportInfo>
{
    /**
     * 查询工作报告
     * 
     * @param id 工作报告主键
     * @return 工作报告
     */
    public WorkReportInfo selectWorkReportInfoById(Long id);

    /**
     * 查询工作报告列表
     * 
     * @param workReportInfo 工作报告
     * @return 工作报告集合
     */
    public List<WorkReportInfo> selectWorkReportInfoList(WorkReportInfo workReportInfo);

    /**
     * 新增工作报告
     * 
     * @param workReportInfo 工作报告
     * @return 结果
     */
    public int insertWorkReportInfo(WorkReportInfo workReportInfo);

    /**
     * 修改工作报告
     * 
     * @param workReportInfo 工作报告
     * @return 结果
     */
    public int updateWorkReportInfo(WorkReportInfo workReportInfo);

    /**
     * 删除工作报告
     * 
     * @param id 工作报告主键
     * @return 结果
     */
    public int deleteWorkReportInfoById(Long id);

    /**
     * 批量删除工作报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkReportInfoByIds(Long[] ids);
}
