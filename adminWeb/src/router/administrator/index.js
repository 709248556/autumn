/**
 * 系统管理
 */
import Main from '@/components/main'

export default {
  path: '/administrator',
  name: 'administrator',
  meta: {
    icon: 'md-settings',
    title: '系统管理'
  },
  component: Main,
  children: [
    {
      path: 'sys_log_login',
      name: 'sys_log_login',
      meta: {
        icon: 'md-list-box',
        title: '登录日志'
      },
      component: () => import('@/view/administrator/logs/login-log.vue')
    },
    {
      path: 'sys_log_operation',
      name: 'sys_log_operation',
      meta: {
        icon: 'md-list-box',
        title: '操作日志'
      },
      component: () => import('@/view/administrator/logs/operation-log.vue')
    },
    {
      path: 'commonDic',
      name: 'commonDic',
      meta: {
        icon: 'ios-map',
        title: '公共字典'
      },
      component: () => import('@/view/administrator/commonDic.vue')
    },
    {
      path: 'resources-modules',
      name: 'resources_modules',
      meta: {
        icon: 'md-paper',
        title: '资源模块'
      },
      component: () => import('@/view/administrator/sys/modules.vue')
    },
    {
      path: 'url-request-permission-mapping',
      name: 'url-request-permission-mapping',
      meta: {
        icon: 'md-paper',
        title: 'url请求权限'
      },
      component: () => import('@/view/administrator/sys/url-request-permission-mapping.vue')
    },
    {
      path: 'roles',
      name: 'roles',
      meta: {
        icon: 'md-people',
        title: '角色管理'
      },
      component: () => import('@/view/administrator/sys/roles.vue')
    },
    {
      path: 'users',
      name: 'users',
      meta: {
        icon: 'md-contacts',
        title: '用户管理'
      },
      component: () => import('@/view/administrator/sys/users.vue')
    },
    {
      path: 'areaInfoManager',
      name: 'areaInfoManager',
      meta: {
        title: '行政区管理'
      },
      component: () => import('@/view/administrator/areaInfo.vue')
    },
    {
      path: 'app-configre',
      name: 'app_onfigre',
      meta: {
        title: '应用配置'
      },
      component: () => import('@/view/administrator/sys/app-configre.vue')
    },
    {
      path: 'help-center',
      name: 'help_center',
      meta: {
        title: '帮助文档'
      },
      component: () => import('@/view/administrator/sys/help-center.vue')
    },
    {
      path: 'help-center-edit',
      name: 'help_center_edit',
      meta: {
        title: '帮助文档详情'
      },
      component: () => import('@/view/administrator/sys/help-center-edit.vue')
    },
    {
      path: 'business-agreement',
      name: 'business_agreement',
      meta: {
        title: '用户协议'
      },
      component: () => import('@/view/administrator/sys/business-agreement.vue')
    },
    {
      path: 'business-agreement-edit',
      name: 'business_agreement_edit',
      meta: {
        title: '用户协议详情'
      },
      component: () =>
        import('@/view/administrator/sys/business-agreement-edit.vue')
    }
  ]
}
