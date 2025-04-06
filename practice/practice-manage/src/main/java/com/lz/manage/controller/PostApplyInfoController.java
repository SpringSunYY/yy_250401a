package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.CompanyInfoMapper;
import com.lz.manage.model.domain.CompanyInfo;
import com.lz.manage.service.ICompanyInfoService;
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
import com.lz.manage.model.domain.PostApplyInfo;
import com.lz.manage.model.vo.postApplyInfo.PostApplyInfoVo;
import com.lz.manage.model.dto.postApplyInfo.PostApplyInfoQuery;
import com.lz.manage.model.dto.postApplyInfo.PostApplyInfoInsert;
import com.lz.manage.model.dto.postApplyInfo.PostApplyInfoEdit;
import com.lz.manage.service.IPostApplyInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 岗位申请Controller
 *
 * @author YY
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/manage/postApplyInfo")
public class PostApplyInfoController extends BaseController {
    @Resource
    private IPostApplyInfoService postApplyInfoService;

    @Resource
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询岗位申请列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:postApplyInfo:list,manage:postApplyInfo:query')")
    @GetMapping("/list")
    public TableDataInfo list(PostApplyInfoQuery postApplyInfoQuery) {
        PostApplyInfo postApplyInfo = getPostApplyInfo(postApplyInfoQuery);
        if (StringUtils.isNull(postApplyInfo)) {
            return new TableDataInfo();
        }
        startPage();
        List<PostApplyInfo> list = postApplyInfoService.selectPostApplyInfoList(postApplyInfo);
        List<PostApplyInfoVo> listVo = list.stream().map(PostApplyInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出岗位申请列表
     */
    @PreAuthorize("@ss.hasPermi('manage:postApplyInfo:export')")
    @Log(title = "岗位申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostApplyInfoQuery postApplyInfoQuery) {
        PostApplyInfo postApplyInfo = getPostApplyInfo(postApplyInfoQuery);
        if (StringUtils.isNull(postApplyInfo)) {
            return;
        }
        List<PostApplyInfo> list = postApplyInfoService.selectPostApplyInfoList(postApplyInfo);
        ExcelUtil<PostApplyInfo> util = new ExcelUtil<PostApplyInfo>(PostApplyInfo.class);
        util.exportExcel(response, list, "岗位申请数据");
    }

    private PostApplyInfo getPostApplyInfo(PostApplyInfoQuery postApplyInfoQuery) {
        PostApplyInfo postApplyInfo = PostApplyInfoQuery.queryToObj(postApplyInfoQuery);
        //如果没有查看全部
        if (!SecurityUtils.hasPermi("manage:all")) {
            //查看自己公司
            if (SecurityUtils.hasPermi("manage:postApplyInfo:company")) {
                CompanyInfo companyInfo = new CompanyInfo();
                companyInfo.setUserId(SecurityUtils.getUserId());
                List<CompanyInfo> companyInfos = companyInfoMapper.selectCompanyInfoList(companyInfo);
                //获取所有的公司id
                List<Long> companyIds = companyInfos.stream().map(CompanyInfo::getCompanyId).collect(Collectors.toList());
                if (StringUtils.isEmpty(companyIds)) {
                    return null;
                }
                postApplyInfo.setCompanyIds(companyIds);
            } else if (SecurityUtils.hasPermi("manage:postApplyInfo:dept")) {
                //查看自己班级
                postApplyInfo.setDeptId(SecurityUtils.getDeptId());
            } else {
                //只能查看自己
                postApplyInfo.setUserId(SecurityUtils.getUserId());
            }
        }
        return postApplyInfo;
    }

    /**
     * 获取岗位申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:postApplyInfo:query')")
    @GetMapping(value = "/{applyId}")
    public AjaxResult getInfo(@PathVariable("applyId") Long applyId) {
        PostApplyInfo postApplyInfo = postApplyInfoService.selectPostApplyInfoByApplyId(applyId);
        return success(PostApplyInfoVo.objToVo(postApplyInfo));
    }

    /**
     * 新增岗位申请
     */
    @PreAuthorize("@ss.hasPermi('manage:postApplyInfo:add')")
    @Log(title = "岗位申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PostApplyInfoInsert postApplyInfoInsert) {
        PostApplyInfo postApplyInfo = PostApplyInfoInsert.insertToObj(postApplyInfoInsert);
        return toAjax(postApplyInfoService.insertPostApplyInfo(postApplyInfo));
    }

    /**
     * 修改岗位申请
     */
    @PreAuthorize("@ss.hasPermi('manage:postApplyInfo:edit')")
    @Log(title = "岗位申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostApplyInfoEdit postApplyInfoEdit) {
        PostApplyInfo postApplyInfo = PostApplyInfoEdit.editToObj(postApplyInfoEdit);
        return toAjax(postApplyInfoService.updatePostApplyInfo(postApplyInfo));
    }

    /**
     * 删除岗位申请
     */
    @PreAuthorize("@ss.hasPermi('manage:postApplyInfo:remove')")
    @Log(title = "岗位申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public AjaxResult remove(@PathVariable Long[] applyIds) {
        return toAjax(postApplyInfoService.deletePostApplyInfoByApplyIds(applyIds));
    }
}
