package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.PostApplyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 岗位申请Mapper接口
 * 
 * @author YY
 * @date 2025-04-04
 */
public interface PostApplyInfoMapper extends BaseMapper<PostApplyInfo>
{
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
     * 删除岗位申请
     * 
     * @param applyId 岗位申请主键
     * @return 结果
     */
    public int deletePostApplyInfoByApplyId(Long applyId);

    /**
     * 批量删除岗位申请
     * 
     * @param applyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePostApplyInfoByApplyIds(Long[] applyIds);
}
