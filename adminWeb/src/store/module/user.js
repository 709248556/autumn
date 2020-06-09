import {
  login,
  logout,
  getUserInfo,
  getMessage,
  getContentByMsgId,
  hasRead,
  removeReaded,
  restoreTrash,
  getUnreadCount,
  updatePassword,
  updatePasswordBySmsToken,
  loginRequest
} from '@/api/login/user'
import {
  imageCaptcha,
  sendSms
} from '@/api/administrator/verification/verification'
import { sessionSave } from '@/libs/util'

export default {
  state: {
    userName: '',
    userId: '',
    avatorImgPath: '',
    access: '',
    userType: 0,
    hasGetInfo: false,
    unreadCount: 0,
    messageUnreadList: [],
    messageReadedList: [],
    messageTrashList: [],
    messageContentStore: {},
    userLoginInfo: null
  },
  mutations: {
    setAvator (state, avatorPath) {
      state.avatorImgPath = avatorPath
    },
    setUserId (state, id) {
      state.userId = id
    },
    setUserName (state, name) {
      state.userName = name
    },
    setOrganizeName (state, organizeName) {
      state.organizeName = organizeName
    },
    setAccess (state, access) {
      state.access = access
    },
    setUserType (state, userType) {
      state.userType = userType
    },
    setHasGetInfo (state, status) {
      state.hasGetInfo = status
    },
    setMessageCount (state, count) {
      state.unreadCount = count
    },
    setMessageUnreadList (state, list) {
      state.messageUnreadList = list
    },
    setMessageReadedList (state, list) {
      state.messageReadedList = list
    },
    setMessageTrashList (state, list) {
      state.messageTrashList = list
    },
    updateMessageContentStore (state, { msg_id, content }) {
      state.messageContentStore[msg_id] = content
    },
    moveMsg (state, { from, to, msg_id }) {
      const index = state[from].findIndex(_ => _.msg_id === msg_id)
      const msgItem = state[from].splice(index, 1)[0]
      msgItem.loading = false
      state[to].unshift(msgItem)
    },
    setUserLoginInfo (state, userLoginInfo) {
      state.userLoginInfo = userLoginInfo
    }
  },
  getters: {
    messageUnreadCount: state => state.messageUnreadList.length,
    messageReadedCount: state => state.messageReadedList.length,
    messageTrashCount: state => state.messageTrashList.length
  },
  actions: {
    // 登录
    handleLogin ({ commit }, { userName, password, imageCode }) {
      userName = userName.trim()
      return new Promise((resolve, reject) => {
        login({
          userName,
          password,
          imageCode
        })
          .then(res => {
            const data = res.data
            localStorage.setItem('_sid', data.result.sessionId)
            localStorage.setItem('_key', data.result.sessionKey)
            resolve(data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 更新密码
    handleUpdatePwd ({ commit }, data) {
      return new Promise((resolve, reject) => {
        updatePassword(data)
          .then(res => {
            const data = res.data
            resolve(data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 获取图形验证码
    handleImageCaptcha ({ commit }, data) {
      return new Promise((resolve, reject) => {
        imageCaptcha(data)
          .then(res => {
            resolve(res)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 发送手机验证码
    handleSendSms ({ commit }, data) {
      return new Promise((resolve, reject) => {
        sendSms(data)
          .then(res => {
            const data = res.data
            resolve(data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 登录请求，确认是否需要图形验证码和会话相息
    loginRequest ({ commit }, data) {
      return new Promise((resolve, reject) => {
        loginRequest(data)
          .then(res => {
            const data = res.data
            resolve(data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 用短信更新密码
    handleUpdatePwdSms ({ commit }, data) {
      return new Promise((resolve, reject) => {
        updatePasswordBySmsToken(data)
          .then(res => {
            const data = res.data
            resolve(data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    // 退出登录
    handleLogOut ({ state, commit }) {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            resolve()
          })
          .catch(err => {
            reject(err)
          })
        // 如果你的退出登录无需请求接口，则可以直接使用下面三行代码而无需使用logout调用接口
        // commit('setToken', '')
        sessionSave('subMenuList', [])
        sessionSave('menus', [])
        sessionSave('openNames', [])
        commit('setAccess', [])
        resolve()
      })
    },
    // 获取用户相关信息
    getUserInfo ({ state, commit }) {
      return new Promise((resolve, reject) => {
        try {
          getUserInfo(state.token)
            .then(res => {
              const data = res.data
              commit('setAvator', data.result.headPortraitPath)
              commit('setUserName', data.result.userName)
              commit('setUserId', data.result.userId)
              commit('setAccess', data.result.access)
              commit('setOrganizeName', data.result.organizeName)
              commit('setUserType', data.result.userType)
              commit('setHasGetInfo', true)
              commit('setUserLoginInfo', data.result)
              resolve(data.result)
            })
            .catch(err => {
              reject(err)
            })
        } catch (error) {
          reject(error)
        }
      })
    },
    // 此方法用来获取未读消息条数，接口只返回数值，不返回消息列表
    getUnreadMessageCount ({ state, commit }) {
      getUnreadCount().then(res => {
        const { data } = res
        commit('setMessageCount', data)
      })
    },
    // 获取消息列表，其中包含未读、已读、回收站三个列表
    getMessageList ({ state, commit }) {
      return new Promise((resolve, reject) => {
        getMessage()
          .then(res => {
            const { unread, readed, trash } = res.data
            commit(
              'setMessageUnreadList',
              unread.sort(
                (a, b) => new Date(b.create_time) - new Date(a.create_time)
              )
            )
            commit(
              'setMessageReadedList',
              readed
                .map(_ => {
                  _.loading = false
                  return _
                })
                .sort(
                  (a, b) => new Date(b.create_time) - new Date(a.create_time)
                )
            )
            commit(
              'setMessageTrashList',
              trash
                .map(_ => {
                  _.loading = false
                  return _
                })
                .sort(
                  (a, b) => new Date(b.create_time) - new Date(a.create_time)
                )
            )
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    // 根据当前点击的消息的id获取内容
    getContentByMsgId ({ state, commit }, { msg_id }) {
      return new Promise((resolve, reject) => {
        let contentItem = state.messageContentStore[msg_id]
        if (contentItem) {
          resolve(contentItem)
        } else {
          getContentByMsgId(msg_id).then(res => {
            const content = res.data
            commit('updateMessageContentStore', {
              msg_id,
              content
            })
            resolve(content)
          })
        }
      })
    },
    // 把一个未读消息标记为已读
    hasRead ({ state, commit }, { msg_id }) {
      return new Promise((resolve, reject) => {
        hasRead(msg_id)
          .then(() => {
            commit('moveMsg', {
              from: 'messageUnreadList',
              to: 'messageReadedList',
              msg_id
            })
            commit('setMessageCount', state.unreadCount - 1)
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    // 删除一个已读消息到回收站
    removeReaded ({ commit }, { msg_id }) {
      return new Promise((resolve, reject) => {
        removeReaded(msg_id)
          .then(() => {
            commit('moveMsg', {
              from: 'messageReadedList',
              to: 'messageTrashList',
              msg_id
            })
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    // 还原一个已删除消息到已读消息
    restoreTrash ({ commit }, { msg_id }) {
      return new Promise((resolve, reject) => {
        restoreTrash(msg_id)
          .then(() => {
            commit('moveMsg', {
              from: 'messageTrashList',
              to: 'messageReadedList',
              msg_id
            })
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  }
}
