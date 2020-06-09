/**
 * 系统配置/订单配置
 */
import api from '@/libs/api.http'

const baseUrl = '/sys/config/order'

// 查询

export const query = () => {
  return api.request({
    url: baseUrl + '/query',
    method: 'post'
  })
}

// 保存
export const save = input => {
  return api.request({
    url: baseUrl + '/save',
    method: 'post',
    data: input
  })
}

// 查询订单变量集合
export const queryOrderVariables = () => {
  return api.request({
    url: baseUrl + '/orderVariables',
    method: 'post'
  })
}

// 查询报告变量集合
export const qureyReportVariables = () => {
  return api.request({
    url: baseUrl + '/reportVariables',
    method: 'post'
  })
}

// 短信模板步骤列表
export const smsTemplateStepList = () => {
  return api.request({
    url: baseUrl + '/smsTemplateStepList',
    method: 'post'
  })
}
