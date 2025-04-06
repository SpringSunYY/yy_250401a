package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.CompanyInfoMapper;
import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.service.ICompanyInfoService;
import jdk.nashorn.api.scripting.ScriptUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.MonthlyAssessmentInfo;
import com.lz.manage.model.vo.monthlyAssessmentInfo.MonthlyAssessmentInfoVo;
import com.lz.manage.model.dto.monthlyAssessmentInfo.MonthlyAssessmentInfoQuery;
import com.lz.manage.model.dto.monthlyAssessmentInfo.MonthlyAssessmentInfoInsert;
import com.lz.manage.model.dto.monthlyAssessmentInfo.MonthlyAssessmentInfoEdit;
import com.lz.manage.service.IMonthlyAssessmentInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 月度考核Controller
 *
 * @author YY
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/manage/monthlyAssessmentInfo")
public class MonthlyAssessmentInfoController extends BaseController {
    @Resource
    private IMonthlyAssessmentInfoService monthlyAssessmentInfoService;

    @Resource
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询月度考核列表
     */
    @PreAuthorize("@ss.hasPermi('manage:monthlyAssessmentInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(MonthlyAssessmentInfoQuery monthlyAssessmentInfoQuery) {
        MonthlyAssessmentInfo monthlyAssessmentInfo = getMonthlyAssessmentInfo(monthlyAssessmentInfoQuery);
        if (StringUtils.isNull(monthlyAssessmentInfo)) {
            return new TableDataInfo();
        }
        return getTableDataInfo(monthlyAssessmentInfo);
    }

    private TableDataInfo getTableDataInfo(MonthlyAssessmentInfo monthlyAssessmentInfo) {
        startPage();
        List<MonthlyAssessmentInfo> list = monthlyAssessmentInfoService.selectMonthlyAssessmentInfoList(monthlyAssessmentInfo);
        List<MonthlyAssessmentInfoVo> listVo = list.stream().map(MonthlyAssessmentInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出月度考核列表
     */
    @PreAuthorize("@ss.hasPermi('manage:monthlyAssessmentInfo:export')")
    @Log(title = "月度考核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MonthlyAssessmentInfoQuery monthlyAssessmentInfoQuery) {
        MonthlyAssessmentInfo monthlyAssessmentInfo = getMonthlyAssessmentInfo(monthlyAssessmentInfoQuery);
        if (StringUtils.isNull(monthlyAssessmentInfo)) {
            return;
        }
        List<MonthlyAssessmentInfo> list = monthlyAssessmentInfoService.selectMonthlyAssessmentInfoList(monthlyAssessmentInfo);
        ExcelUtil<MonthlyAssessmentInfo> util = new ExcelUtil<MonthlyAssessmentInfo>(MonthlyAssessmentInfo.class);
        util.exportExcel(response, list, "月度考核数据");
    }

    private MonthlyAssessmentInfo getMonthlyAssessmentInfo(MonthlyAssessmentInfoQuery monthlyAssessmentInfoQuery) {
        MonthlyAssessmentInfo monthlyAssessmentInfo = MonthlyAssessmentInfoQuery.queryToObj(monthlyAssessmentInfoQuery);
        //是否有查看全部
        if (!SecurityUtils.hasPermi("manage:all")) {
            if (SecurityUtils.hasPermi("manage:monthlyAssessmentInfo:myCompany")) {
                //只能查看自己公司
                CompanyInfo companyInfo = new CompanyInfo();
                companyInfo.setUserId(SecurityUtils.getUserId());
                List<CompanyInfo> companyInfos = companyInfoMapper.selectCompanyInfoList(companyInfo);
                //获取所有的公司id
                List<Long> companyIds = companyInfos.stream().map(CompanyInfo::getCompanyId).collect(Collectors.toList());
                if (StringUtils.isEmpty(companyIds)) {
                    return null;
                }
                monthlyAssessmentInfo.setCompanyIds(companyIds);
            } else if (SecurityUtils.hasPermi("manage:monthlyAssessmentInfo:dept")) {
                //查看自己班级
                monthlyAssessmentInfo.setDeptId(SecurityUtils.getDeptId());
            } else {
                //只能查看自己
                monthlyAssessmentInfo.setUserId(SecurityUtils.getUserId());
            }
        }
        return monthlyAssessmentInfo;
    }

    /**
     * 获取月度考核详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:monthlyAssessmentInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        MonthlyAssessmentInfo monthlyAssessmentInfo = monthlyAssessmentInfoService.selectMonthlyAssessmentInfoById(id);
        return success(MonthlyAssessmentInfoVo.objToVo(monthlyAssessmentInfo));
    }

    /**
     * 新增月度考核
     */
    @PreAuthorize("@ss.hasPermi('manage:monthlyAssessmentInfo:add')")
    @Log(title = "月度考核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MonthlyAssessmentInfoInsert monthlyAssessmentInfoInsert) {
        MonthlyAssessmentInfo monthlyAssessmentInfo = MonthlyAssessmentInfoInsert.insertToObj(monthlyAssessmentInfoInsert);
        return toAjax(monthlyAssessmentInfoService.insertMonthlyAssessmentInfo(monthlyAssessmentInfo));
    }

    /**
     * 修改月度考核
     */
    @PreAuthorize("@ss.hasPermi('manage:monthlyAssessmentInfo:edit')")
    @Log(title = "月度考核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MonthlyAssessmentInfoEdit monthlyAssessmentInfoEdit) {
        MonthlyAssessmentInfo monthlyAssessmentInfo = MonthlyAssessmentInfoEdit.editToObj(monthlyAssessmentInfoEdit);
        return toAjax(monthlyAssessmentInfoService.updateMonthlyAssessmentInfo(monthlyAssessmentInfo));
    }

    /**
     * 删除月度考核
     */
    @PreAuthorize("@ss.hasPermi('manage:monthlyAssessmentInfo:remove')")
    @Log(title = "月度考核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(monthlyAssessmentInfoService.deleteMonthlyAssessmentInfoByIds(ids));
    }
}
