import api from '@/libs/api.http'

const baseUrl = '/sys/log/login'

export const deleteAll = () => {
  return api.request({
    url: baseUrl + '/delete/all',
    method: 'post'
  })
}

export const downloadByExcelInfo = queryFiletr => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
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
