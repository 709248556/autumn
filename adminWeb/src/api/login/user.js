import axios from '@/libs/api.request'

export const login = data => {
  return axios.request({
    url: '/auth/login',
    data: data,
    method: 'post'
  })
}

export const getUserInfo = token => {
  return axios.request({
    url: '/auth/login/info',
    method: 'get'
  })
}

export const loginBySmsToken = data => {
  return axios.request({
    url: '/auth/sms/login',
    method: 'post',
    data: data
  })
}

export const updatePassword = data => {
  return axios.request({
    url: '/auth/update/password',
    method: 'post',
    data: data
  })
}
export const updatePasswordBySmsToken = data => {
  return axios.request({
    url: '/auth/sms/update/password',
    method: 'post',
    data: data
  })
}

export const logout = token => {
  return axios.request({
    url: '/auth/logout',
    method: 'get'
  })
}

export const loginRequest = token => {
  return axios.request({
    url: '/auth/login/request',
    method: 'get'
  })
}
