/**
 * 搜索文档
 */
import api from '@/libs/api.http'
const baseUrl = '/sys/search/document'

// 异步重置数据
export const asyncResetData = input => {
  return api.request({
    url: baseUrl + '/asyncResetData',
    method: 'post',
    data: input
  })
}

// 创建索引
export const createIndex = input => {
  return api.request({
    url: baseUrl + '/createIndex',
    method: 'post',
    data: input
  })
}

// 删除索引
export const deleteIndex = input => {
  return api.request({
    url: baseUrl + '/deleteIndex',
    method: 'post',
    data: input
  })
}

// 文档索引类型
export const documentTypes = () => {
  return api.request({
    url: baseUrl + '/documentTypes',
    method: 'post'
  })
}

// 查询索引列表
export const queryIndexList = () => {
  return api.request({
    url: baseUrl + '/queryIndexList',
    method: 'post'
  })
}

// 重置数据
export const resetData = input => {
  return api.request({
    url: baseUrl + '/resetData',
    method: 'post',
    data: input
  })
}
