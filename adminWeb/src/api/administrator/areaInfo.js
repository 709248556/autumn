/**
 * 行政区
 */
import api from '@/libs/api.http'
import getHost from '@/config/host'

const domainUrl = getHost()

const baseUrl = '/common/region'

// 添加
export const add = input => {
  return api.request({
    url: baseUrl + '/add',
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
export const downloadByExcel = input => {
  return api.request({
    url: baseUrl + '/download/excel/body',
    method: 'post',
    data: input
  })
}

// 导出excel并获取下载信息
export const downloadByExcelInfo = input => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
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

// 分页查询
export const queryForPage = input => {
  return api.request({
    url: baseUrl + '/query/page',
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

// 更新状态
export const updateStatus = input => {
  return api.request({
    url: baseUrl + '/update/status',
    method: 'post',
    data: input
  })
}

// 查询子级
export const queryChildren = input => {
  return api.request({
    url: baseUrl + '/query/children',
    method: 'post',
    data: input
  })
}

// 导入 Excel url
export const dataImportByExcelUrl = domainUrl + baseUrl + '/upload/import/excel'

// 下载Excel导入模板文件
export const downloadImportTemplateByExcel = () => {
  return api.request({
    url: baseUrl + '/download/import/template/body',
    method: 'post'
  })
}

// 下载到Excel导入模板文件信息
export const downloadImportTemplateByExcelInfo = () => {
  return api.request({
    url: baseUrl + '/download/import/template/Info',
    method: 'post'
  })
}
