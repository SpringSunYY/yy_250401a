package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.SecurityUtils;
import com.lz.manage.mapper.CompanyInfoMapper;
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
import com.lz.manage.model.domain.WorkReportInfo;
import com.lz.manage.model.vo.workReportInfo.WorkReportInfoVo;
import com.lz.manage.model.dto.workReportInfo.WorkReportInfoQuery;
import com.lz.manage.model.dto.workReportInfo.WorkReportInfoInsert;
import com.lz.manage.model.dto.workReportInfo.WorkReportInfoEdit;
import com.lz.manage.service.IWorkReportInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 工作报告Controller
 *
 * @author YY
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/manage/workReportInfo")
public class WorkReportInfoController extends BaseController {
    @Resource
    private IWorkReportInfoService workReportInfoService;

    @Resource
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询工作报告列表
     */
    @PreAuthorize("@ss.hasPermi('manage:workReportInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkReportInfoQuery workReportInfoQuery) {
        WorkReportInfo workReportInfo = getWorkReportInfo(workReportInfoQuery);
        startPage();
        List<WorkReportInfo> list = workReportInfoService.selectWorkReportInfoList(workReportInfo);
        List<WorkReportInfoVo> listVo = list.stream().map(WorkReportInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出工作报告列表
     */
    @PreAuthorize("@ss.hasPermi('manage:workReportInfo:export')")
    @Log(title = "工作报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkReportInfoQuery workReportInfoQuery) {
        WorkReportInfo workReportInfo = getWorkReportInfo(workReportInfoQuery);
        List<WorkReportInfo> list = workReportInfoService.selectWorkReportInfoList(workReportInfo);
        ExcelUtil<WorkReportInfo> util = new ExcelUtil<WorkReportInfo>(WorkReportInfo.class);
        util.exportExcel(response, list, "工作报告数据");
    }

    private static WorkReportInfo getWorkReportInfo(WorkReportInfoQuery workReportInfoQuery) {
        WorkReportInfo workReportInfo = WorkReportInfoQuery.queryToObj(workReportInfoQuery);
        //查看全部
        if (!SecurityUtils.hasPermi("manage:all")) {
            //查看班级
            if (SecurityUtils.hasPermi("manage:workReportInfo:dept")) {
                workReportInfo.setDeptId(SecurityUtils.getDeptId());
            } else {
                //只能查看自己
                workReportInfo.setUserId(SecurityUtils.getUserId());
            }
        }
        return workReportInfo;
    }

    /**
     * 获取工作报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:workReportInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        WorkReportInfo workReportInfo = workReportInfoService.selectWorkReportInfoById(id);
        return success(WorkReportInfoVo.objToVo(workReportInfo));
    }

    /**
     * 新增工作报告
     */
    @PreAuthorize("@ss.hasPermi('manage:workReportInfo:add')")
    @Log(title = "工作报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkReportInfoInsert workReportInfoInsert) {
        WorkReportInfo workReportInfo = WorkReportInfoInsert.insertToObj(workReportInfoInsert);
        return toAjax(workReportInfoService.insertWorkReportInfo(workReportInfo));
    }

    /**
     * 修改工作报告
     */
    @PreAuthorize("@ss.hasPermi('manage:workReportInfo:edit')")
    @Log(title = "工作报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkReportInfoEdit workReportInfoEdit) {
        WorkReportInfo workReportInfo = WorkReportInfoEdit.editToObj(workReportInfoEdit);
        return toAjax(workReportInfoService.updateWorkReportInfo(workReportInfo));
    }

    /**
     * 删除工作报告
     */
    @PreAuthorize("@ss.hasPermi('manage:workReportInfo:remove')")
    @Log(title = "工作报告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workReportInfoService.deleteWorkReportInfoByIds(ids));
    }
}
