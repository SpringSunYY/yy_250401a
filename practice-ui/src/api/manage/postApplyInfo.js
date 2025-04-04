import request from '@/utils/request'

// 查询岗位申请列表
export function listPostApplyInfo(query) {
  return request({
    url: '/manage/postApplyInfo/list',
    method: 'get',
    params: query
  })
}

// 查询岗位申请详细
export function getPostApplyInfo(applyId) {
  return request({
    url: '/manage/postApplyInfo/' + applyId,
    method: 'get'
  })
}

// 新增岗位申请
export function addPostApplyInfo(data) {
  return request({
    url: '/manage/postApplyInfo',
    method: 'post',
    data: data
  })
}

// 修改岗位申请
export function updatePostApplyInfo(data) {
  return request({
    url: '/manage/postApplyInfo',
    method: 'put',
    data: data
  })
}

// 删除岗位申请
export function delPostApplyInfo(applyId) {
  return request({
    url: '/manage/postApplyInfo/' + applyId,
    method: 'delete'
  })
}
