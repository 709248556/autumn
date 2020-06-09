import api from '@/libs/api.http'

const baseUrl = '/sys/role'

// 创建角色
export const createRole = role => {
  return api.request({
    url: baseUrl + '/add',
    data: role,
    method: 'post'
  })
}

// 根据id删除角色
export const deleteRoleById = roleId => {
  return api.request({
    url: baseUrl + '/delete',
    data: {
      id: roleId
    },
    method: 'post'
  })
}

// 下载Excel
export const downloadByExcelInfo = queryFilter => {
  return api.request({
    url: baseUrl + '/download/excel/Info',
    data: queryFilter,
    method: 'post'
  })
}

// 根据角色查询角色
export const queryRoleById = roleId => {
  return api.request({
    url: baseUrl + '/query/single',
    data: {
      id: roleId
    },
    method: 'post'
  })
}

// 查询角色列表
export const queryByList = queryFilter => {
  return api.request({
    url: baseUrl + '/query/list',
    data: queryFilter,
    method: 'post'
  })
}

// 分页角色列表
export const queryRoleByPageList = queryFilter => {
  return api.request({
    url: baseUrl + '/query/page',
    data: queryFilter,
    method: 'post'
  })
}

// 更新角色
export const updateRole = role => {
  return api.request({
    url: baseUrl + '/update',
    data: role,
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

// 角色授全部权限
export const authorizeByAllPermission = roleId => {
  return api.request({
    url: baseUrl + '/authorize/all',
    data: {
      id: roleId
    },
    method: 'post'
  })
}
