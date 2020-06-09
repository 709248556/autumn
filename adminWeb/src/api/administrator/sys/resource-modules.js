import api from '@/libs/api.http'

const baseUrl = '/sys/res/module'

export const createModule = modules => {
  return api.request({
    url: baseUrl + '/add',
    data: modules,
    method: 'post'
  })
}

export const createDefaultSystemModule = () => {
  return api.request({
    url: baseUrl + '/add/default',
    method: 'post'
  })
}

export const deleteModuleById = moduleId => {
  return api.request({
    url: baseUrl + '/delete',
    data: {
      id: moduleId
    },
    method: 'post'
  })
}

export const downloadByExcelInfo = queryFilter => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
    data: queryFilter,
    method: 'post'
  })
}

export const queryByAllTree = () => {
  return api.request({
    url: baseUrl + '/query/tree/all',
    method: 'post'
  })
}

export const queryUserByMenuTree = () => {
  return api.request({
    url: '/auth/query/user/menu/tree',
    method: 'post'
  })
}

export const queryModuleById = moduleId => {
  return api.request({
    url: baseUrl + '/query/single',
    data: {
      id: moduleId
    },
    method: 'post'
  })
}

export const queryModuleByList = queryFilter => {
  return api.request({
    url: baseUrl + '/query/list',
    data: queryFilter,
    method: 'post'
  })
}

export const queryByMenuTree = resourcesType => {
  return api.request({
    url: baseUrl + '/query/tree/menu',
    data: {
      resourcesType: resourcesType
    },
    method: 'post'
  })
}

export const queryByPageList = queryFilter => {
  return api.request({
    url: baseUrl + '/query/page',
    data: queryFilter,
    method: 'post'
  })
}

export const queryResourcesTypeList = () => {
  return api.request({
    url: baseUrl + '/query/list/type',
    method: 'post'
  })
}

export const queryByTree = resourcesType => {
  return api.request({
    url: baseUrl + '/query/tree',
    data: {
      resourcesType
    },
    method: 'post'
  })
}

export const queryChildren = input => {
  return api.request({
    url: baseUrl + '/query/children',
    data: input,
    method: 'post'
  })
}

export const updateModule = modules => {
  return api.request({
    url: baseUrl + '/update',
    data: modules,
    method: 'post'
  })
}
