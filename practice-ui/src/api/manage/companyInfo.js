import request from '@/utils/request'

// 查询公司信息列表
export function listCompanyInfo(query) {
  return request({
    url: '/manage/companyInfo/list',
    method: 'get',
    params: query
  })
}

// 查询公司信息详细
export function getCompanyInfo(companyId) {
  return request({
    url: '/manage/companyInfo/' + companyId,
    method: 'get'
  })
}

// 新增公司信息
export function addCompanyInfo(data) {
  return request({
    url: '/manage/companyInfo',
    method: 'post',
    data: data
  })
}

// 修改公司信息
export function updateCompanyInfo(data) {
  return request({
    url: '/manage/companyInfo',
    method: 'put',
    data: data
  })
}

// 删除公司信息
export function delCompanyInfo(companyId) {
  return request({
    url: '/manage/companyInfo/' + companyId,
    method: 'delete'
  })
}
