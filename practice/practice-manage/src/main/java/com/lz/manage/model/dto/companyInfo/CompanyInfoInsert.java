package com.lz.manage.model.dto.companyInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CompanyInfo;
/**
 * 公司信息Vo对象 tb_company_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class CompanyInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 公司编号 */
    private Long companyId;

    /** 公司名称 */
    private String companyName;

    /** 公司状态（0待审核 1同意 2拒绝） */
    private Long companyStatus;

    /** 公司地址 */
    private String address;

    /** 联系电话 */
    private String contactNumber;

    /** 公司邮箱 */
    private String email;

    /** 公司简介 */
    private String companyDesc;

    /** 公司图片 */
    private String companyImage;

    /** 创建人 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param companyInfoInsert 插入对象
     * @return CompanyInfoInsert
     */
    public static CompanyInfo insertToObj(CompanyInfoInsert companyInfoInsert) {
        if (companyInfoInsert == null) {
            return null;
        }
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(companyInfoInsert, companyInfo);
        return companyInfo;
    }
}
