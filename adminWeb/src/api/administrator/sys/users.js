import api from '@/libs/api.http'

const baseUrl = '/sys/user'

export const createUser = user => {
  return api.request({
    url: baseUrl + '/add',
    data: user,
    method: 'post'
  })
}

export const deleteUserById = userId => {
  return api.request({
    url: baseUrl + '/delete',
    data: {
      id: userId
    },
    method: 'post'
  })
}

export const downloadByExcelInfo = querFilter => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
    data: querFilter,
    method: 'post'
  })
}

export const queryUserById = userId => {
  return api.request({
    url: baseUrl + '/query/single',
    data: {
      id: userId
    },
    method: 'post'
  })
}

export const queryByList = querFilter => {
  return api.request({
    url: baseUrl + '/query/list',
    data: querFilter,
    method: 'post'
  })
}

export const queryUserByPageList = querFilter => {
  return api.request({
    url: baseUrl + '/query/page',
    data: querFilter,
    method: 'post'
  })
}

export const updateUser = user => {
  return api.request({
    url: baseUrl + '/update',
    data: user,
    method: 'post'
  })
}

// 用户授权模块权限树
export const authorizeByModulePermissionTree = (id, resourcesType) => {
  return api.request({
    url: baseUrl + '/query/authorize/tree',
    data: {
      id: id,
      resourcesType: resourcesType || 1
    },
    method: 'post'
  })
}

// 用户授权
export const authorize = permission => {
  return api.request({
    url: baseUrl + '/authorize',
    data: permission,
    method: 'post'
  })
}

// 重置密码
export const resetPassword = queryFiletr => {
  return api.request({
    url: baseUrl + '/reset/password',
    data: queryFiletr,
    method: 'post'
  })
}
