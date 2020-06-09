import HttpRequest from '@/libs/axios'
import host from '@/config/host'
import router from '../router'
import iview from 'iview'
import fileDownload from 'js-file-download'
import { uuid } from '@/libs/util'

const axios = new HttpRequest(host())

class ApiHttpRequest {
  //  Api 结果处理
  resultHandle (promise, resolve, reject) {
    promise
      .then(res => {
        if (res && res.data) {
          let data = res.data
          if (data.success) {
            resolve(data.result)
          } else {
            let errorMsg = data.error
            iview.Message.error(errorMsg.message)
            //  用户未登录
            if (errorMsg.code === -1) {
              // 登录跳转
              router.replace({
                path: '/login'
              })
              return
            }
            reject(errorMsg)
          }
        } else {
          // let errorMsg = '网络出错'
          // iview.Message.error(errorMsg)
          // reject(errorMsg)
        }
      })
      .catch(err => {
        reject(err)
      })
  }

  // 下载文件
  downloadHandle (promise, resolve, reject) {
    promise.then(res => {
      if (res.status === 200) {
        resolve(res)
      } else {
        iview.Message.error('下载文件失败!')
        reject(errorMsg)
      }
    })
  }

  // 基于 Api 方式调用 Post
  post (url, params = {}) {
    params = params || {}
    return new Promise((resolve, reject) => {
      this.resultHandle(
        axios.request({
          method: 'post',
          url,
          data: JSON.stringify(params),
          headers: {
            'Content-Type': 'application/json'
          }
        }),
        resolve,
        reject
      )
    })
  }

  // 基于 Api 方式调用 Get
  get (url, params = {}) {
    params = params || {}
    return new Promise((resolve, reject) => {
      this.resultHandle(
        axios.request({
          method: 'get',
          url,
          params,
          headers: {
            'Content-Type': 'application/json'
          }
        }),
        resolve,
        reject
      )
    })
  }

  // 基于 Api 方式调用 Put
  put (url, params = {}) {
    params = params || {}
    return new Promise((resolve, reject) => {
      this.resultHandle(
        axios.request({
          method: 'put',
          url,
          data: JSON.stringify(params),
          headers: {
            'Content-Type': 'application/json'
          }
        }),
        resolve,
        reject
      )
    })
  }

  // 基于 Api 方式调用 delete
  delete (url, params = {}) {
    params = params || {}
    return new Promise((resolve, reject) => {
      this.resultHandle(
        axios.request({
          method: 'delete',
          url,
          data: JSON.stringify(params),
          headers: {
            'Content-Type': 'application/json'
          }
        }),
        resolve,
        reject
      )
    })
  }

  // 基于 Api 方式调用 Result
  request (options = {}) {
    return new Promise((resolve, reject) => {
      this.resultHandle(axios.request(options), resolve, reject)
    })
  }

  // 下载Excel文件
  downloadExcel (options = {}) {
    new Promise((resolve, reject) => {
      this.downloadHandle(axios.request(options), resolve, reject)
    }).then(res => {
      fileDownload(res.data, uuid() + '.xlsx')
    })
  }

  // 下载Word文件
  downloadWord (options = {}) {
    new Promise((resolve, reject) => {
      this.downloadHandle(axios.request(options), resolve, reject)
    }).then(res => {
      fileDownload(res.data, uuid() + '.docx')
    })
  }
}
const api = new ApiHttpRequest()
export default api
