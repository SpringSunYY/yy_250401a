package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CompanyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 公司信息Mapper接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo>
{
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
     * 删除公司信息
     * 
     * @param companyId 公司信息主键
     * @return 结果
     */
    public int deleteCompanyInfoByCompanyId(Long companyId);

    /**
     * 批量删除公司信息
     * 
     * @param companyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompanyInfoByCompanyIds(Long[] companyIds);
}
