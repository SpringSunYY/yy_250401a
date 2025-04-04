package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.PostApplyInfo;
import com.lz.manage.model.vo.postApplyInfo.PostApplyInfoVo;
import com.lz.manage.model.dto.postApplyInfo.PostApplyInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 岗位申请Service接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface IPostApplyInfoService extends IService<PostApplyInfo>
{
    //region mybatis代码
    /**
     * 查询岗位申请
     * 
     * @param applyId 岗位申请主键
     * @return 岗位申请
     */
    public PostApplyInfo selectPostApplyInfoByApplyId(Long applyId);

    /**
     * 查询岗位申请列表
     * 
     * @param postApplyInfo 岗位申请
     * @return 岗位申请集合
     */
    public List<PostApplyInfo> selectPostApplyInfoList(PostApplyInfo postApplyInfo);

    /**
     * 新增岗位申请
     * 
     * @param postApplyInfo 岗位申请
     * @return 结果
     */
    public int insertPostApplyInfo(PostApplyInfo postApplyInfo);

    /**
     * 修改岗位申请
     * 
     * @param postApplyInfo 岗位申请
     * @return 结果
     */
    public int updatePostApplyInfo(PostApplyInfo postApplyInfo);

    /**
     * 批量删除岗位申请
     * 
     * @param applyIds 需要删除的岗位申请主键集合
     * @return 结果
     */
    public int deletePostApplyInfoByApplyIds(Long[] applyIds);

    /**
     * 删除岗位申请信息
     * 
     * @param applyId 岗位申请主键
     * @return 结果
     */
    public int deletePostApplyInfoByApplyId(Long applyId);
    //endregion
    /**
     * 获取查询条件
     *
     * @param postApplyInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<PostApplyInfo> getQueryWrapper(PostApplyInfoQuery postApplyInfoQuery);

    /**
     * 转换vo
     *
     * @param postApplyInfoList PostApplyInfo集合
     * @return PostApplyInfoVO集合
     */
    List<PostApplyInfoVo> convertVoList(List<PostApplyInfo> postApplyInfoList);
}
