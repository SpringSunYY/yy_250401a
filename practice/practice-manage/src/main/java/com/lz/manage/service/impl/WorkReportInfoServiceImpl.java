package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.WorkReportInfoMapper;
import com.lz.manage.model.domain.WorkReportInfo;
import com.lz.manage.model.dto.workReportInfo.WorkReportInfoQuery;
import com.lz.manage.model.vo.workReportInfo.WorkReportInfoVo;
import com.lz.manage.service.IWorkReportInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工作报告Service业务层处理
 *
 * @author YY
 * @date 2025-04-04
 */
@Service
public class WorkReportInfoServiceImpl extends ServiceImpl<WorkReportInfoMapper, WorkReportInfo> implements IWorkReportInfoService {
    @Resource
    private WorkReportInfoMapper workReportInfoMapper;
    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;


    //region mybatis代码
    /**
     * 查询工作报告
     *
     * @param id 工作报告主键
     * @return 工作报告
     */
    @Override
    public WorkReportInfo selectWorkReportInfoById(Long id) {
        return workReportInfoMapper.selectWorkReportInfoById(id);
    }

    /**
     * 查询工作报告列表
     *
     * @param workReportInfo 工作报告
     * @return 工作报告
     */
    @Override
    public List<WorkReportInfo> selectWorkReportInfoList(WorkReportInfo workReportInfo) {
        List<WorkReportInfo> workReportInfos = workReportInfoMapper.selectWorkReportInfoList(workReportInfo);
        for (WorkReportInfo info : workReportInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
        }
        return workReportInfos;
    }

    /**
     * 新增工作报告
     *
     * @param workReportInfo 工作报告
     * @return 结果
     */
    @Override
    public int insertWorkReportInfo(WorkReportInfo workReportInfo) {
        workReportInfo.setDeptId(SecurityUtils.getDeptId());
        workReportInfo.setUserId(SecurityUtils.getUserId());
        workReportInfo.setCreateTime(DateUtils.getNowDate());
        return workReportInfoMapper.insertWorkReportInfo(workReportInfo);
    }

    /**
     * 修改工作报告
     *
     * @param workReportInfo 工作报告
     * @return 结果
     */
    @Override
    public int updateWorkReportInfo(WorkReportInfo workReportInfo) {
        workReportInfo.setUpdateBy(SecurityUtils.getUsername());
        workReportInfo.setUpdateTime(DateUtils.getNowDate());
        return workReportInfoMapper.updateWorkReportInfo(workReportInfo);
    }

    /**
     * 批量删除工作报告
     *
     * @param ids 需要删除的工作报告主键
     * @return 结果
     */
    @Override
    public int deleteWorkReportInfoByIds(Long[] ids) {
        return workReportInfoMapper.deleteWorkReportInfoByIds(ids);
    }

    /**
     * 删除工作报告信息
     *
     * @param id 工作报告主键
     * @return 结果
     */
    @Override
    public int deleteWorkReportInfoById(Long id) {
        return workReportInfoMapper.deleteWorkReportInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<WorkReportInfo> getQueryWrapper(WorkReportInfoQuery workReportInfoQuery) {
        QueryWrapper<WorkReportInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = workReportInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = workReportInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long reportType = workReportInfoQuery.getReportType();
        queryWrapper.eq(StringUtils.isNotNull(reportType), "report_type", reportType);

        Date reportDate = workReportInfoQuery.getReportDate();
        queryWrapper.eq(StringUtils.isNotNull(reportDate), "report_date", reportDate);

        Long score = workReportInfoQuery.getScore();
        queryWrapper.eq(StringUtils.isNotNull(score), "score", score);

        String comment = workReportInfoQuery.getComment();
        queryWrapper.eq(StringUtils.isNotEmpty(comment), "comment", comment);

        Long userId = workReportInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = workReportInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = workReportInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = workReportInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = workReportInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<WorkReportInfoVo> convertVoList(List<WorkReportInfo> workReportInfoList) {
        if (StringUtils.isEmpty(workReportInfoList)) {
            return Collections.emptyList();
        }
        return workReportInfoList.stream().map(WorkReportInfoVo::objToVo).collect(Collectors.toList());
    }
}
