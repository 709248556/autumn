/**
 * 站点-论坛管理-论坛分类
 */
import api from '@/libs/api.http'

const baseUrl = '/sys/help/document'

// 添加目录
export const addDirectory = input => {
  return api.request({
    url: baseUrl + '/add/directory',
    method: 'post',
    data: input
  })
}

// 添加文件
export const addFile = input => {
  return api.request({
    url: baseUrl + '/add/file',
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

// 导出excel
export const generateHtml = input => {
  return api.request({
    url: baseUrl + '/generate/html',
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

// 查询目录或文件
export const queryChildren = input => {
  return api.request({
    url: baseUrl + '/query/children',
    method: 'post',
    data: input
  })
}

// 修改目录
export const updateDirectory = input => {
  return api.request({
    url: baseUrl + '/update/directory',
    method: 'post',
    data: input
  })
}

// 修改文件
export const updateFile = input => {
  return api.request({
    url: baseUrl + '/update/file',
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
