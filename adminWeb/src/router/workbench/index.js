/**
 * 医疗机构配置相关路由
 */
import Main from '@/components/main'

import statisticsAnalysis from './statisticsAnalysis'

export default (() => {
  let routers = {
    path: '/medicals',
    name: 'medicals',
    meta: {
      icon: 'md-settings',
      title: '配置管理'
    },
    component: Main,
    children: [
      ...statisticsAnalysis
    ]
  }
  return routers
})()
