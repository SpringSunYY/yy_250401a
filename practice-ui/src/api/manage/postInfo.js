import request from '@/utils/request'

// 查询岗位信息列表
export function listPostInfo(query) {
  return request({
    url: '/manage/postInfo/list',
    method: 'get',
    params: query
  })
}

// 查询岗位信息详细
export function getPostInfo(postId) {
  return request({
    url: '/manage/postInfo/' + postId,
    method: 'get'
  })
}

// 新增岗位信息
export function addPostInfo(data) {
  return request({
    url: '/manage/postInfo',
    method: 'post',
    data: data
  })
}

// 修改岗位信息
export function updatePostInfo(data) {
  return request({
    url: '/manage/postInfo',
    method: 'put',
    data: data
  })
}

// 删除岗位信息
export function delPostInfo(postId) {
  return request({
    url: '/manage/postInfo/' + postId,
    method: 'delete'
  })
}
