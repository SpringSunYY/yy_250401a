package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.model.vo.companyInfo.CompanyInfoVo;
import com.lz.manage.model.dto.companyInfo.CompanyInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 公司信息Service接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface ICompanyInfoService extends IService<CompanyInfo>
{
    //region mybatis代码
    /**
     * 查询公司信息
     * 
     * @param companyId 公司信息主键
     * @return 公司信息
     */
    public CompanyInfo selectCompanyInfoByCompanyId(Long companyId);

    /**
     * 查询公司信息列表
     * 
     * @param companyInfo 公司信息
     * @return 公司信息集合
     */
    public List<CompanyInfo> selectCompanyInfoList(CompanyInfo companyInfo);

    /**
     * 新增公司信息
     * 
     * @param companyInfo 公司信息
     * @return 结果
     */
    public int insertCompanyInfo(CompanyInfo companyInfo);

    /**
     * 修改公司信息
     * 
     * @param companyInfo 公司信息
     * @return 结果
     */
    public int updateCompanyInfo(CompanyInfo companyInfo);

    /**
     * 批量删除公司信息
     * 
     * @param companyIds 需要删除的公司信息主键集合
     * @return 结果
     */
    public int deleteCompanyInfoByCompanyIds(Long[] companyIds);

    /**
     * 删除公司信息信息
     * 
     * @param companyId 公司信息主键
     * @return 结果
     */
    public int deleteCompanyInfoByCompanyId(Long companyId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param companyInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CompanyInfo> getQueryWrapper(CompanyInfoQuery companyInfoQuery);

    /**
     * 转换vo
     *
     * @param companyInfoList CompanyInfo集合
     * @return CompanyInfoVO集合
     */
    List<CompanyInfoVo> convertVoList(List<CompanyInfo> companyInfoList);
}
