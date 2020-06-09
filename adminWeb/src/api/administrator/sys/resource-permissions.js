import api from '@/libs/api.http'

const baseUrl = '/sys/res/permission'

export const createPermission = (permission) => {
  return api.request({
    url: baseUrl + '/add',
    data: permission,
    method: 'post'
  })
}

export const addDefaultEditPermission = (resourcesId) => {
  return api.request({
    url: baseUrl + '/add/default/edit',
    data: {
      resourcesId: resourcesId
    },
    method: 'post'
  })
}

export const addDefaultQueryPermission = (resourcesId) => {
  return api.request({
    url: baseUrl + '/add/default/query',
    data: {
      resourcesId: resourcesId
    },
    method: 'post'
  })
}

export const deletePermissionById = (resourcesId) => {
  return api.request({
    url: baseUrl + '/delete',
    data: {
      id: resourcesId
    },
    method: 'post'
  })
}

export const downloadByExcelInfo = (queryFilter) => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
    data: queryFilter,
    method: 'post'
  })
}

export const queryPermissionById = (resourcesId) => {
  return api.request({
    url: baseUrl + '/query/single',
    data: {
      id: resourcesId
    },
    method: 'post'
  })
}

export const queryByList = (queryFilter) => {
  return api.request({
    url: baseUrl + '/query/list',
    data: queryFilter,
    method: 'post'
  })
}

export const queryByPageList = (queryFilter) => {
  return api.request({
    url: baseUrl + '/query/page',
    data: queryFilter,
    method: 'post'
  })
}

export const updatePermission = (permission) => {
  return api.request({
    url: baseUrl + '/update',
    data: permission,
    method: 'post'
  })
}

// 查询Url权限映射组列表
export const queryUrlRequestPermissionMappingGroups = () => {
  return api.request({
    url: '/sys/res/url/query/mapping/groups',
    method: 'post'
  })
}

// 查询Url权限映射信息列表
export const queryUrlRequestPermissionMappingInfos = () => {
  return api.request({
    url: '/sys/res/url/query/mapping/infos',
    method: 'post'
  })
}

// 重置拦截权限
export const resetInterceptorPermission = () => {
  return api.request({
    url: '/sys/res/url/reset/interceptor/permission',
    method: 'post'
  })
}
