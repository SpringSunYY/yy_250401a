package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
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
import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.model.vo.companyInfo.CompanyInfoVo;
import com.lz.manage.model.dto.companyInfo.CompanyInfoQuery;
import com.lz.manage.model.dto.companyInfo.CompanyInfoInsert;
import com.lz.manage.model.dto.companyInfo.CompanyInfoEdit;
import com.lz.manage.service.ICompanyInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 公司信息Controller
 *
 * @author YY
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/manage/companyInfo")
public class CompanyInfoController extends BaseController
{
    @Resource
    private ICompanyInfoService companyInfoService;

    /**
     * 查询公司信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:companyInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompanyInfoQuery companyInfoQuery)
    {
        CompanyInfo companyInfo = CompanyInfoQuery.queryToObj(companyInfoQuery);
        startPage();
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        List<CompanyInfoVo> listVo= list.stream().map(CompanyInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出公司信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:companyInfo:export')")
    @Log(title = "公司信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompanyInfoQuery companyInfoQuery)
    {
        CompanyInfo companyInfo = CompanyInfoQuery.queryToObj(companyInfoQuery);
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        ExcelUtil<CompanyInfo> util = new ExcelUtil<CompanyInfo>(CompanyInfo.class);
        util.exportExcel(response, list, "公司信息数据");
    }

    /**
     * 获取公司信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:companyInfo:query')")
    @GetMapping(value = "/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId)
    {
        CompanyInfo companyInfo = companyInfoService.selectCompanyInfoByCompanyId(companyId);
        return success(CompanyInfoVo.objToVo(companyInfo));
    }

    /**
     * 新增公司信息
     */
    @PreAuthorize("@ss.hasPermi('manage:companyInfo:add')")
    @Log(title = "公司信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompanyInfoInsert companyInfoInsert)
    {
        CompanyInfo companyInfo = CompanyInfoInsert.insertToObj(companyInfoInsert);
        return toAjax(companyInfoService.insertCompanyInfo(companyInfo));
    }

    /**
     * 修改公司信息
     */
    @PreAuthorize("@ss.hasPermi('manage:companyInfo:edit')")
    @Log(title = "公司信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompanyInfoEdit companyInfoEdit)
    {
        CompanyInfo companyInfo = CompanyInfoEdit.editToObj(companyInfoEdit);
        return toAjax(companyInfoService.updateCompanyInfo(companyInfo));
    }

    /**
     * 删除公司信息
     */
    @PreAuthorize("@ss.hasPermi('manage:companyInfo:remove')")
    @Log(title = "公司信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{companyIds}")
    public AjaxResult remove(@PathVariable Long[] companyIds)
    {
        return toAjax(companyInfoService.deleteCompanyInfoByCompanyIds(companyIds));
    }
}
