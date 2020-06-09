import axios from 'axios'
import store from '@/store'
import iView from 'iview'
import router from '../router'

// 最大响应时间
axios.defaults.timeout = 120000
// 每次请求默认
axios.defaults.withCredentials = true
// import { Spin } from 'iview'
const addErrorLog = errorInfo => {
  const {
    statusText,
    status,
    request: { responseURL }
  } = errorInfo
  let info = {
    type: 'ajax',
    code: status,
    mes: statusText,
    url: responseURL
  }
  if (!responseURL.includes('save_error_logger')) {
    store.dispatch('addErrorLog', info)
  }
}

const showError = error => {
  if (error) {
    iView.Message.error(error)
  }
}

const showInfo = error => {
  if (error) {
    iView.Message.info(error)
  }
}

class HttpRequest {
  constructor (baseUrl = baseURL) {
    this.baseUrl = baseUrl
    this.queue = {}
  }
  getInsideConfig () {
    const config = {
      baseURL: this.baseUrl,
      headers: {
        //
        authorization: ''
      }
    }
    return config
  }
  destroy (url) {
    delete this.queue[url]
    if (!Object.keys(this.queue).length) {
      // Spin.hide()
    }
  }
  interceptors (instance, url) {
    // 请求拦截
    instance.interceptors.request.use(
      config => {
        // 添加全局的loading...
        if (!Object.keys(this.queue).length) {
          // Spin.show() // 不建议开启，因为界面不友好
        }
        this.queue[url] = true
        return config
      },
      error => {
        return Promise.reject(error)
      }
    )
    // 响应拦截
    instance.interceptors.response.use(
      res => {
        this.destroy(url)
        const { data, status, headers } = res
        if (data) {
          if (data.success) {
            return { data, status, headers }
            // return { data: data.result, status, headers }
          } else {
            if (data.error) {
              let errorInfo = data.error
              //  用户未登录
              if (errorInfo.code === -1) {
                showInfo('用户未登录，将自动转向登录页面。')
                // 登录跳转
                router.replace({
                  path: '/login'
                })
                return
              }
              showError(errorInfo.message)
              return Promise.reject(errorInfo)
            } else {
              let errorInfo = {
                message: '未知错误'
              }
              showError(errorInfo.message)
              return Promise.reject(errorInfo)
            }
          }
        }
        return { data, status, headers }
      },
      error => {
        this.destroy(url)
        let errorInfo = {
          message: '未知错误'
        }
        addErrorLog(error)
        if (error.response) {
          errorInfo = error.response
          if (error.response.message) {
            showError(error.response.message)
            errorInfo.message = error.response.message
          } else if (error.response.status) {
            showError('响应状态出错:' + error.response.status)
          } else {
            showError('未知响应错误')
          }
        } else if (error.request) {
          errorInfo = error.request
          showError('网络出错')
        } else {
          showError(errorInfo.message)
        }
        return Promise.reject(errorInfo)
      }
    )
  }
  request (options) {
    const instance = axios.create()
    options = Object.assign(this.getInsideConfig(), options)
    this.interceptors(instance, options.url)
    return instance(options)
  }
}
export default HttpRequest
