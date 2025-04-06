package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.CompanyInfoMapper;
import com.lz.manage.model.domain.CompanyInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
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
import com.lz.manage.model.domain.PostInfo;
import com.lz.manage.model.vo.postInfo.PostInfoVo;
import com.lz.manage.model.dto.postInfo.PostInfoQuery;
import com.lz.manage.model.dto.postInfo.PostInfoInsert;
import com.lz.manage.model.dto.postInfo.PostInfoEdit;
import com.lz.manage.service.IPostInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 岗位信息Controller
 *
 * @author YY
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/manage/postInfo")
public class PostInfoController extends BaseController
{
    @Resource
    private IPostInfoService postInfoService;

    @Resource
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询岗位信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:postInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PostInfoQuery postInfoQuery)
    {
        PostInfo postInfo = getPostInfo(postInfoQuery);
        if (StringUtils.isNull(postInfo)) {
            return new TableDataInfo();
        }
        startPage();
        List<PostInfo> list = postInfoService.selectPostInfoList(postInfo);
        List<PostInfoVo> listVo= list.stream().map(PostInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出岗位信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:postInfo:export')")
    @Log(title = "岗位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PostInfoQuery postInfoQuery)
    {
        PostInfo postInfo = getPostInfo(postInfoQuery);
        if (StringUtils.isNull(postInfo)) {
            return;
        }
        List<PostInfo> list = postInfoService.selectPostInfoList(postInfo);
        ExcelUtil<PostInfo> util = new ExcelUtil<PostInfo>(PostInfo.class);
        util.exportExcel(response, list, "岗位信息数据");
    }

    private PostInfo getPostInfo(PostInfoQuery postInfoQuery) {
        PostInfo postInfo = PostInfoQuery.queryToObj(postInfoQuery);
        //查看全部或者查看公司
        if (!SecurityUtils.hasPermi("manage:all")) {
            if (!SecurityUtils.hasPermi("manage:postInfo:company")){
                CompanyInfo companyInfo = new CompanyInfo();
                companyInfo.setUserId(SecurityUtils.getUserId());
                List<CompanyInfo> companyInfos = companyInfoMapper.selectCompanyInfoList(companyInfo);
                //获取所有的公司id
                List<Long> companyIds = companyInfos.stream().map(CompanyInfo::getCompanyId).collect(Collectors.toList());
                if (StringUtils.isEmpty(companyInfos)) {
                    return null;
                }
                postInfo.setCompanyIds(companyIds);
            }
        }
        return postInfo;
    }

    /**
     * 获取岗位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:postInfo:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable("postId") Long postId)
    {
        PostInfo postInfo = postInfoService.selectPostInfoByPostId(postId);
        return success(PostInfoVo.objToVo(postInfo));
    }

    /**
     * 新增岗位信息
     */
    @PreAuthorize("@ss.hasPermi('manage:postInfo:add')")
    @Log(title = "岗位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated PostInfoInsert postInfoInsert)
    {
        PostInfo postInfo = PostInfoInsert.insertToObj(postInfoInsert);
        return toAjax(postInfoService.insertPostInfo(postInfo));
    }

    /**
     * 修改岗位信息
     */
    @PreAuthorize("@ss.hasPermi('manage:postInfo:edit')")
    @Log(title = "岗位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PostInfoEdit postInfoEdit)
    {
        PostInfo postInfo = PostInfoEdit.editToObj(postInfoEdit);
        return toAjax(postInfoService.updatePostInfo(postInfo));
    }

    /**
     * 删除岗位信息
     */
    @PreAuthorize("@ss.hasPermi('manage:postInfo:remove')")
    @Log(title = "岗位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(postInfoService.deletePostInfoByPostIds(postIds));
    }
}
