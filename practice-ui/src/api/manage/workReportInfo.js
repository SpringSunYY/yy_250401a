import request from '@/utils/request'

// 查询工作报告列表
export function listWorkReportInfo(query) {
  return request({
    url: '/manage/workReportInfo/list',
    method: 'get',
    params: query
  })
}

// 查询工作报告详细
export function getWorkReportInfo(id) {
  return request({
    url: '/manage/workReportInfo/' + id,
    method: 'get'
  })
}

// 新增工作报告
export function addWorkReportInfo(data) {
  return request({
    url: '/manage/workReportInfo',
    method: 'post',
    data: data
  })
}

// 修改工作报告
export function updateWorkReportInfo(data) {
  return request({
    url: '/manage/workReportInfo',
    method: 'put',
    data: data
  })
}

// 删除工作报告
export function delWorkReportInfo(id) {
  return request({
    url: '/manage/workReportInfo/' + id,
    method: 'delete'
  })
}
