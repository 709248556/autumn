/**
 * 系统开关
 */
import api from '@/libs/api.http'
const baseUrl = '/sys/switch'

// 添加
export const add = () => {
  return api.request({
    url: baseUrl + '/add',
    method: 'post'
  })
}

// 清空
export const clear = input => {
  return api.request({
    url: baseUrl + '/clear',
    method: 'post',
    data: input
  })
}

// 查询
export const query = input => {
  return api.request({
    url: baseUrl + '/query',
    method: 'post',
    data: input
  })
}

// 更新
export const update = input => {
  return api.request({
    url: baseUrl + '/update',
    method: 'post',
    data: input
  })
}
