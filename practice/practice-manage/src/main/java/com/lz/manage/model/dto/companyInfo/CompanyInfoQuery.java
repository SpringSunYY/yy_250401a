package com.lz.manage.model.dto.companyInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.CompanyInfo;
/**
 * 公司信息Query对象 tb_company_info
 *
 * @author YY
 * @date 2025-04-04
 */
@Data
public class CompanyInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 公司编号 */
    private Long companyId;

    /** 公司名称 */
    private String companyName;

    /** 公司状态（0待审核 1同意 2拒绝） */
    private Long companyStatus;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param companyInfoQuery 查询对象
     * @return CompanyInfo
     */
    public static CompanyInfo queryToObj(CompanyInfoQuery companyInfoQuery) {
        if (companyInfoQuery == null) {
            return null;
        }
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(companyInfoQuery, companyInfo);
        return companyInfo;
    }
}
