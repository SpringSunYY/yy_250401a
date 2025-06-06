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

import com.lz.manage.model.enums.MCommonStatus;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.CompanyInfoMapper;
import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.service.ICompanyInfoService;
import com.lz.manage.model.dto.companyInfo.CompanyInfoQuery;
import com.lz.manage.model.vo.companyInfo.CompanyInfoVo;

/**
 * 公司信息Service业务层处理
 *
 * @author YY
 * @date 2025-04-04
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements ICompanyInfoService {
    @Resource
    private CompanyInfoMapper companyInfoMapper;

    @Resource
    private ISysUserService userService;

    //region mybatis代码

    /**
     * 查询公司信息
     *
     * @param companyId 公司信息主键
     * @return 公司信息
     */
    @Override
    public CompanyInfo selectCompanyInfoByCompanyId(Long companyId) {
        return companyInfoMapper.selectCompanyInfoByCompanyId(companyId);
    }

    /**
     * 查询公司信息列表
     *
     * @param companyInfo 公司信息
     * @return 公司信息
     */
    @Override
    public List<CompanyInfo> selectCompanyInfoList(CompanyInfo companyInfo) {
        List<CompanyInfo> companyInfos = companyInfoMapper.selectCompanyInfoList(companyInfo);
        for (CompanyInfo info : companyInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            //脱敏
            if (StringUtils.isNotEmpty(info.getContactNumber())) {
                info.setContactNumber(StringUtils.hide(info.getContactNumber(), 3, info.getContactNumber().length() - 4));
            }
            if (StringUtils.isNotEmpty(info.getEmail())) {
                info.setEmail(StringUtils.hide(info.getEmail(), 0, 3));
            }
        }
        return companyInfos;
    }

    /**
     * 新增公司信息
     *
     * @param companyInfo 公司信息
     * @return 结果
     */
    @Override
    public int insertCompanyInfo(CompanyInfo companyInfo) {
        companyInfo.setCompanyStatus(Long.valueOf(MCommonStatus.COMMON_STATUS_0.getValue()));
        companyInfo.setUserId(SecurityUtils.getUserId());
        companyInfo.setCreateTime(DateUtils.getNowDate());
        return companyInfoMapper.insertCompanyInfo(companyInfo);
    }

    /**
     * 修改公司信息
     *
     * @param companyInfo 公司信息
     * @return 结果
     */
    @Override
    public int updateCompanyInfo(CompanyInfo companyInfo) {
        //查询数据库内容
        CompanyInfo companyInfoDb = companyInfoMapper.selectCompanyInfoByCompanyId(companyInfo.getCompanyId());
        if (StringUtils.isNull(companyInfoDb)) {
            throw new ServiceException("数据为空");
        }
        //判断状态是否不一样且用户是否有审核权限
        if (!companyInfoDb.getCompanyStatus().equals(companyInfo.getCompanyStatus()) && !SecurityUtils.hasPermi("manage:companyInfo:audit")) {
            throw new ServiceException("权限不足");
        }
        companyInfo.setUpdateBy(SecurityUtils.getUsername());
        companyInfo.setUpdateTime(DateUtils.getNowDate());
        return companyInfoMapper.updateCompanyInfo(companyInfo);
    }

    /**
     * 批量删除公司信息
     *
     * @param companyIds 需要删除的公司信息主键
     * @return 结果
     */
    @Override
    public int deleteCompanyInfoByCompanyIds(Long[] companyIds) {
        return companyInfoMapper.deleteCompanyInfoByCompanyIds(companyIds);
    }

    /**
     * 删除公司信息信息
     *
     * @param companyId 公司信息主键
     * @return 结果
     */
    @Override
    public int deleteCompanyInfoByCompanyId(Long companyId) {
        return companyInfoMapper.deleteCompanyInfoByCompanyId(companyId);
    }

    //endregion
    @Override
    public QueryWrapper<CompanyInfo> getQueryWrapper(CompanyInfoQuery companyInfoQuery) {
        QueryWrapper<CompanyInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = companyInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long companyId = companyInfoQuery.getCompanyId();
        queryWrapper.eq(StringUtils.isNotNull(companyId), "company_id", companyId);

        String companyName = companyInfoQuery.getCompanyName();
        queryWrapper.like(StringUtils.isNotEmpty(companyName), "company_name", companyName);

        Long companyStatus = companyInfoQuery.getCompanyStatus();
        queryWrapper.eq(StringUtils.isNotNull(companyStatus), "company_status", companyStatus);

        Long userId = companyInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = companyInfoQuery.getCreateTime();
        queryWrapper.eq(StringUtils.isNotNull(createTime), "create_time", createTime);

        String updateBy = companyInfoQuery.getUpdateBy();
        queryWrapper.eq(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = companyInfoQuery.getUpdateTime();
        queryWrapper.eq(StringUtils.isNotNull(updateTime), "update_time", updateTime);

        return queryWrapper;
    }

    @Override
    public List<CompanyInfoVo> convertVoList(List<CompanyInfo> companyInfoList) {
        if (StringUtils.isEmpty(companyInfoList)) {
            return Collections.emptyList();
        }
        return companyInfoList.stream().map(CompanyInfoVo::objToVo).collect(Collectors.toList());
    }

}
