package com.lz.manage.model.vo.companyInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CompanyInfo;
/**
 * 公司信息Vo对象 tb_company_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class CompanyInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 公司编号 */
    @Excel(name = "公司编号")
    private Long companyId;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String companyName;

    /** 公司状态（0待审核 1同意 2拒绝） */
    @Excel(name = "公司状态", readConverterExp = "0=待审核,1=同意,2=拒绝")
    private Long companyStatus;

    /** 公司地址 */
    @Excel(name = "公司地址")
    private String address;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 公司邮箱 */
    @Excel(name = "公司邮箱")
    private String email;

    /** 公司简介 */
    @Excel(name = "公司简介")
    private String companyDesc;

    /** 公司图片 */
    @Excel(name = "公司图片")
    private String companyImage;

    /** 创建人 */
    @Excel(name = "创建人")
    private String userName;
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;


     /**
     * 对象转封装类
     *
     * @param companyInfo CompanyInfo实体对象
     * @return CompanyInfoVo
     */
    public static CompanyInfoVo objToVo(CompanyInfo companyInfo) {
        if (companyInfo == null) {
            return null;
        }
        CompanyInfoVo companyInfoVo = new CompanyInfoVo();
        BeanUtils.copyProperties(companyInfo, companyInfoVo);
        return companyInfoVo;
    }
}
