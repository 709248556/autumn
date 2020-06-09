/**
 * 系统配置/应用配置
 */
import api from '@/libs/api.http'
import getHost from '@/config/host'

const baseUrl = '/sys/info'

// 上传附件的url
export const uploadFileUrl = getHost() + baseUrl + '/upload/file'

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

// 查询帮助访问文档
export const queryHelpDocumentVisit = () => {
  return api.request({
    url: baseUrl + '/query/help/document',
    method: 'post'
  })
}
