import request from '@/utils/request'

// 查询月度考核列表
export function listMonthlyAssessmentInfo(query) {
  return request({
    url: '/manage/monthlyAssessmentInfo/list',
    method: 'get',
    params: query
  })
}

// 查询月度考核详细
export function getMonthlyAssessmentInfo(id) {
  return request({
    url: '/manage/monthlyAssessmentInfo/' + id,
    method: 'get'
  })
}

// 新增月度考核
export function addMonthlyAssessmentInfo(data) {
  return request({
    url: '/manage/monthlyAssessmentInfo',
    method: 'post',
    data: data
  })
}

// 修改月度考核
export function updateMonthlyAssessmentInfo(data) {
  return request({
    url: '/manage/monthlyAssessmentInfo',
    method: 'put',
    data: data
  })
}

// 删除月度考核
export function delMonthlyAssessmentInfo(id) {
  return request({
    url: '/manage/monthlyAssessmentInfo/' + id,
    method: 'delete'
  })
}
