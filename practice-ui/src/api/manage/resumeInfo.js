import request from '@/utils/request'

// 查询简历信息列表
export function listResumeInfo(query) {
  return request({
    url: '/manage/resumeInfo/list',
    method: 'get',
    params: query
  })
}

// 查询简历信息详细
export function getResumeInfo(resumeId) {
  return request({
    url: '/manage/resumeInfo/' + resumeId,
    method: 'get'
  })
}

// 新增简历信息
export function addResumeInfo(data) {
  return request({
    url: '/manage/resumeInfo',
    method: 'post',
    data: data
  })
}

// 修改简历信息
export function updateResumeInfo(data) {
  return request({
    url: '/manage/resumeInfo',
    method: 'put',
    data: data
  })
}

// 删除简历信息
export function delResumeInfo(resumeId) {
  return request({
    url: '/manage/resumeInfo/' + resumeId,
    method: 'delete'
  })
}
