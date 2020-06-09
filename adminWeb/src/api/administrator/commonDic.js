import api from '@/libs/api.http'

import getHost from '@/config/host'
const domainUrl = getHost()

const baseUrl = '/common/dictionary'

export const addDic = queryFiletr => {
  return api.request({
    data: queryFiletr,
    url: baseUrl + '/add',
    method: 'post'
  })
}

export const chooseByList = queryFiletr => {
  return api.request({
    url: baseUrl + '/query/list/choose',
    data: queryFiletr,
    method: 'post'
  })
}

export const deleteById = rowId => {
  return api.request({
    url: baseUrl + '/delete',
    data: {
      id: rowId
    },
    method: 'post'
  })
}

export const downloadByExcelInfo = (queryFiletr = {}) => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
    data: queryFiletr,
    method: 'post'
  })
}

export const queryByDictionaryTypeList = () => {
  return api.request({
    url: baseUrl + '/types',
    method: 'post'
  })
}

export const queryById = rowId => {
  return api.request({
    url: baseUrl + '/query/single',
    data: {
      id: rowId
    },
    method: 'post'
  })
}

export const queryByList = queryFiletr => {
  return api.request({
    url: baseUrl + '/query/list',
    data: queryFiletr,
    method: 'post'
  })
}

export const queryByPageList = queryFiletr => {
  return api.request({
    url: baseUrl + '/query/page',
    data: queryFiletr,
    method: 'post'
  })
}

export const updateDic = queryFiletr => {
  return api.request({
    url: baseUrl + '/update',
    data: queryFiletr,
    method: 'post'
  })
}

// 上传附件url
export const updateFile = () => {
  return domainUrl + baseUrl + '/upload/file'
}
