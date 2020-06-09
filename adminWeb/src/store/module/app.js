import {
  getBreadCrumbList,
  setTagNavListInLocalstorage,
  getTagNavListFromLocalstorage,
  getHomeRoute,
  getNextRoute,
  routeHasExist,
  routeEqual,
  getRouteTitleHandled,
  localSave,
  sessionSave
} from '@/libs/util'
import { queryUserByMenuTree } from '@/api/administrator/sys/resource-modules'
import {
  query,
  queryHelpDocumentVisit
} from '@/api/administrator/sys/app-configre'
import beforeClose from '@/router/before-close'
import router from '@/router'
import config from '@/config'
const { homeName } = config

const closePage = (state, route) => {
  const nextRoute = getNextRoute(state.tagNavList, route)
  state.tagNavList = state.tagNavList.filter(item => {
    return !routeEqual(item, route)
  })
  router.push(nextRoute)
}

export default {
  state: {
    breadCrumbList: [],
    tagNavList: [],
    homeRoute: {},
    errorList: [],
    menuList: [],
    hasReadErrorPage: false,
    systemInfo: {
      isDefault: true,
      systemName: 'Autumn 后台管理系统',
      systemKeepOnRecordNo: ''
    },
    helpDocumentVisit: null
  },
  getters: {
    errorCount: state => state.errorList.length
  },
  mutations: {
    setBreadCrumb (state, route) {
      state.breadCrumbList = getBreadCrumbList(route, state.homeRoute)
    },
    setHomeRoute (state, routes) {
      state.homeRoute = getHomeRoute(routes, homeName)
    },
    setTagNavList (state, list) {
      let tagList = []
      if (list) {
        tagList = [...list]
      } else tagList = getTagNavListFromLocalstorage() || []
      if (tagList[0] && tagList[0].name !== homeName) tagList.shift()
      let homeTagIndex = tagList.findIndex(item => item.name === homeName)
      if (homeTagIndex > 0) {
        let homeTag = tagList.splice(homeTagIndex, 1)[0]
        tagList.unshift(homeTag)
      }
      state.tagNavList = tagList
      setTagNavListInLocalstorage([...tagList])
    },
    closeTag (state, route) {
      let tag = state.tagNavList.filter(item => routeEqual(item, route))
      route = tag[0] ? tag[0] : null
      if (!route) return
      if (
        route.meta &&
        route.meta.beforeCloseName &&
        route.meta.beforeCloseName in beforeClose
      ) {
        new Promise(beforeClose[route.meta.beforeCloseName]).then(close => {
          if (close) {
            closePage(state, route)
          }
        })
      } else {
        closePage(state, route)
      }
    },
    addTag (state, { route, type = 'unshift' }) {
      let router = getRouteTitleHandled(route)
      if (!routeHasExist(state.tagNavList, router)) {
        if (type === 'push') state.tagNavList.push(router)
        else {
          if (router.name === homeName) state.tagNavList.unshift(router)
          else state.tagNavList.splice(1, 0, router)
        }
        setTagNavListInLocalstorage([...state.tagNavList])
      }
    },
    setLocal (state, lang) {
      localSave('local', lang)
      state.local = lang
    },
    addError (state, error) {
      state.errorList.push(error)
    },
    setMenus (state, menus) {
      sessionSave('menus', menus)
      state.menuList = menus
    },
    setHasReadErrorLoggerStatus (state, status = true) {
      state.hasReadErrorPage = status
    },
    setSystemInfo (state, systemInfo) {
      state.systemInfo = systemInfo
    },
    setHelpDocumentVisit (state, helpDocumentVisit) {
      state.helpDocumentVisit = helpDocumentVisit
    }
  },
  actions: {
    addErrorLog ({ commit, rootState }, info) {
      if (!window.location.href.includes('error_logger_page')) {
        commit('setHasReadErrorLoggerStatus', false)
      }
      const {
        user: { token, userId, userName }
      } = rootState
      let data = {
        ...info,
        time: Date.parse(new Date()),
        token,
        userId,
        userName
      }
      console.log(data)

      // saveErrorLogger(info).then(() => {
      //   commit('addError', data)
      // })
    },
    // 获取菜单
    getMenus ({ commit }) {
      return new Promise((resolve, reject) => {
        queryUserByMenuTree()
          .then(res => {
            var fun = (item, parent) => {
              let parentPaths = item.parentPaths || []
              if (parent != null) {
                let parentItem = {
                  id: parent.id,
                  name: parent.name,
                  parentId: parent.parentId,
                  resourcesType: parent.resourcesType,
                  sortId: parent.sortId,
                  isAuthorize: parent.isAuthorize,
                  isMenu: parent.isMenu,
                  identification: parent.identification,
                  isSysModule: parent.isSysModule,
                  permissionUrl: parent.permissionUrl,
                  url: parent.url,
                  icon: parent.icon,
                  summary: parent.summary
                }
                if (parent.parent != null) {
                  parentPaths.push(JSON.parse(JSON.stringify(parent.parent)))
                }
                parentPaths.push(parentItem)
                item.parent = JSON.parse(JSON.stringify(parentItem))
                if (parent.parent != null) {
                  item.parent.parent = JSON.parse(
                    JSON.stringify(parent.parent)
                  )
                } else {
                  item.parent.parent = null
                }
              } else {
                item.parent = null
              }
              item.parentPaths = parentPaths
              if (item.children != null) {
                item.children.forEach(element => {
                  fun(element, item)
                })
              }
            }
            let menus = res
            if (menus.length > 0) {
              menus.forEach(element => {
                fun(element, null)
              })
            }
            commit('setMenus', menus)
            resolve(menus)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 获取系统信息
    getSystemInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        query()
          .then(res => {
            commit('setSystemInfo', res)
            resolve(res)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 同步系统配置
    syncSystemInfo ({ commit }) {
      query().then(res => {
        commit('setSystemInfo', res)
      })
    },
    queryHelpDocumentVisit ({ commit }) {
      return new Promise((resolve, reject) => {
        queryHelpDocumentVisit()
          .then(res => {
            commit('setHelpDocumentVisit', res)
            resolve(res)
          })
          .catch(err => {
            reject(err)
          })
      })
    }
  }
}
