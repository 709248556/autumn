/**
 * 站点-论坛管理-论坛分类
 */
import api from '@/libs/api.http'

const baseUrl = '/sys/agreement'

// 查询类型协议列表
export const agreementTypeList = () => {
  return api.request({
    url: baseUrl + '/types',
    method: 'post'
  })
}

// 添加
export const add = input => {
  return api.request({
    url: baseUrl + '/add',
    method: 'post',
    data: input
  })
}

// 修改
export const update = input => {
  return api.request({
    url: baseUrl + '/update',
    method: 'post',
    data: input
  })
}

// 根据id删除
export const deleteById = input => {
  return api.request({
    url: baseUrl + '/delete',
    method: 'post',
    data: input
  })
}

// 根据id获取
export const queryById = input => {
  return api.request({
    url: baseUrl + '/query/single',
    method: 'post',
    data: input
  })
}

// 更新状态
export const updateStatus = input => {
  return api.request({
    url: baseUrl + '/update/status',
    method: 'post',
    data: input
  })
}

// 分页查询
export const queryForPage = input => {
  return api.request({
    url: baseUrl + '/query/page',
    method: 'post',
    data: input
  })
}

// 下载文件信息
export const downloadByExcelInfo = input => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
    method: 'post',
    data: input
  })
}
