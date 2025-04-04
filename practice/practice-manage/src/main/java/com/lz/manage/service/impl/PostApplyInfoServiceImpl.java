package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PostApplyInfoMapper;
import com.lz.manage.model.domain.PostApplyInfo;
import com.lz.manage.service.IPostApplyInfoService;
import com.lz.manage.model.dto.postApplyInfo.PostApplyInfoQuery;
import com.lz.manage.model.vo.postApplyInfo.PostApplyInfoVo;

/**
 * 岗位申请Service业务层处理
 * 
 * @author YY
 * @date 2025-04-04
 */
@Service
public class PostApplyInfoServiceImpl extends ServiceImpl<PostApplyInfoMapper, PostApplyInfo> implements IPostApplyInfoService
{
    @Resource
    private PostApplyInfoMapper postApplyInfoMapper;

    //region mybatis代码
    /**
     * 查询岗位申请
     * 
     * @param applyId 岗位申请主键
     * @return 岗位申请
     */
    @Override
    public PostApplyInfo selectPostApplyInfoByApplyId(Long applyId)
    {
        return postApplyInfoMapper.selectPostApplyInfoByApplyId(applyId);
    }

    /**
     * 查询岗位申请列表
     * 
     * @param postApplyInfo 岗位申请
     * @return 岗位申请
     */
    @Override
    public List<PostApplyInfo> selectPostApplyInfoList(PostApplyInfo postApplyInfo)
    {
        return postApplyInfoMapper.selectPostApplyInfoList(postApplyInfo);
    }

    /**
     * 新增岗位申请
     * 
     * @param postApplyInfo 岗位申请
     * @return 结果
     */
    @Override
    public int insertPostApplyInfo(PostApplyInfo postApplyInfo)
    {
        postApplyInfo.setCreateTime(DateUtils.getNowDate());
        return postApplyInfoMapper.insertPostApplyInfo(postApplyInfo);
    }

    /**
     * 修改岗位申请
     * 
     * @param postApplyInfo 岗位申请
     * @return 结果
     */
    @Override
    public int updatePostApplyInfo(PostApplyInfo postApplyInfo)
    {
        postApplyInfo.setUpdateTime(DateUtils.getNowDate());
        return postApplyInfoMapper.updatePostApplyInfo(postApplyInfo);
    }

    /**
     * 批量删除岗位申请
     * 
     * @param applyIds 需要删除的岗位申请主键
     * @return 结果
     */
    @Override
    public int deletePostApplyInfoByApplyIds(Long[] applyIds)
    {
        return postApplyInfoMapper.deletePostApplyInfoByApplyIds(applyIds);
    }

    /**
     * 删除岗位申请信息
     * 
     * @param applyId 岗位申请主键
     * @return 结果
     */
    @Override
    public int deletePostApplyInfoByApplyId(Long applyId)
    {
        return postApplyInfoMapper.deletePostApplyInfoByApplyId(applyId);
    }
    //endregion
    @Override
    public QueryWrapper<PostApplyInfo> getQueryWrapper(PostApplyInfoQuery postApplyInfoQuery){
        QueryWrapper<PostApplyInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = postApplyInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long applyId = postApplyInfoQuery.getApplyId();
        queryWrapper.eq( StringUtils.isNotNull(applyId),"apply_id",applyId);

        Long applyStatus = postApplyInfoQuery.getApplyStatus();
        queryWrapper.eq( StringUtils.isNotNull(applyStatus),"apply_status",applyStatus);

        Long userId = postApplyInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long deptId = postApplyInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        Date createTime = postApplyInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = postApplyInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = postApplyInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<PostApplyInfoVo> convertVoList(List<PostApplyInfo> postApplyInfoList) {
        if (StringUtils.isEmpty(postApplyInfoList)) {
            return Collections.emptyList();
        }
        return postApplyInfoList.stream().map(PostApplyInfoVo::objToVo).collect(Collectors.toList());
    }

}
