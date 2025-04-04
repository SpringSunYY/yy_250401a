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
import com.lz.manage.model.domain.ResumeInfo;
import com.lz.manage.model.vo.resumeInfo.ResumeInfoVo;
import com.lz.manage.model.dto.resumeInfo.ResumeInfoQuery;
import com.lz.manage.model.dto.resumeInfo.ResumeInfoInsert;
import com.lz.manage.model.dto.resumeInfo.ResumeInfoEdit;
import com.lz.manage.service.IResumeInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 简历信息Controller
 *
 * @author YY
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/manage/resumeInfo")
public class ResumeInfoController extends BaseController
{
    @Resource
    private IResumeInfoService resumeInfoService;

    /**
     * 查询简历信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:resumeInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResumeInfoQuery resumeInfoQuery)
    {
        ResumeInfo resumeInfo = ResumeInfoQuery.queryToObj(resumeInfoQuery);
        startPage();
        List<ResumeInfo> list = resumeInfoService.selectResumeInfoList(resumeInfo);
        List<ResumeInfoVo> listVo= list.stream().map(ResumeInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出简历信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:resumeInfo:export')")
    @Log(title = "简历信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResumeInfoQuery resumeInfoQuery)
    {
        ResumeInfo resumeInfo = ResumeInfoQuery.queryToObj(resumeInfoQuery);
        List<ResumeInfo> list = resumeInfoService.selectResumeInfoList(resumeInfo);
        ExcelUtil<ResumeInfo> util = new ExcelUtil<ResumeInfo>(ResumeInfo.class);
        util.exportExcel(response, list, "简历信息数据");
    }

    /**
     * 获取简历信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:resumeInfo:query')")
    @GetMapping(value = "/{resumeId}")
    public AjaxResult getInfo(@PathVariable("resumeId") Long resumeId)
    {
        ResumeInfo resumeInfo = resumeInfoService.selectResumeInfoByResumeId(resumeId);
        return success(ResumeInfoVo.objToVo(resumeInfo));
    }

    /**
     * 新增简历信息
     */
    @PreAuthorize("@ss.hasPermi('manage:resumeInfo:add')")
    @Log(title = "简历信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResumeInfoInsert resumeInfoInsert)
    {
        ResumeInfo resumeInfo = ResumeInfoInsert.insertToObj(resumeInfoInsert);
        return toAjax(resumeInfoService.insertResumeInfo(resumeInfo));
    }

    /**
     * 修改简历信息
     */
    @PreAuthorize("@ss.hasPermi('manage:resumeInfo:edit')")
    @Log(title = "简历信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResumeInfoEdit resumeInfoEdit)
    {
        ResumeInfo resumeInfo = ResumeInfoEdit.editToObj(resumeInfoEdit);
        return toAjax(resumeInfoService.updateResumeInfo(resumeInfo));
    }

    /**
     * 删除简历信息
     */
    @PreAuthorize("@ss.hasPermi('manage:resumeInfo:remove')")
    @Log(title = "简历信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{resumeIds}")
    public AjaxResult remove(@PathVariable Long[] resumeIds)
    {
        return toAjax(resumeInfoService.deleteResumeInfoByResumeIds(resumeIds));
    }
}
