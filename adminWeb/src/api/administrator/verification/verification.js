import api from '@/libs/api.http'
const baseUrl = '/captcha'

export const imageCaptcha = (params) => {
  return api.request({
    url: baseUrl + '/image/base64',
    method: 'get',
    params: params
  })
}

export const repeatSendSms = () => {
  return api.request({
    url: baseUrl + '/sms/send/repeat',
    method: 'post'
  })
}

export const sendSms = (filterData) => {
  return api.request({
    url: baseUrl + '/sms/send',
    method: 'post',
    data: filterData
  })
}
