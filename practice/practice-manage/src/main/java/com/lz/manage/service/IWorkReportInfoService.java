package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.WorkReportInfo;
import com.lz.manage.model.vo.workReportInfo.WorkReportInfoVo;
import com.lz.manage.model.dto.workReportInfo.WorkReportInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 工作报告Service接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface IWorkReportInfoService extends IService<WorkReportInfo>
{
    //region mybatis代码
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
     * 批量删除工作报告
     * 
     * @param ids 需要删除的工作报告主键集合
     * @return 结果
     */
    public int deleteWorkReportInfoByIds(Long[] ids);

    /**
     * 删除工作报告信息
     * 
     * @param id 工作报告主键
     * @return 结果
     */
    public int deleteWorkReportInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param workReportInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<WorkReportInfo> getQueryWrapper(WorkReportInfoQuery workReportInfoQuery);

    /**
     * 转换vo
     *
     * @param workReportInfoList WorkReportInfo集合
     * @return WorkReportInfoVO集合
     */
    List<WorkReportInfoVo> convertVoList(List<WorkReportInfo> workReportInfoList);
}
