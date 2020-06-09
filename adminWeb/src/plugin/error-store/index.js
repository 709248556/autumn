import store from '@/store'
import config from '@/config/index'
export default {
  install (Vue, options) {
    if (options.developmentOff && config.isDev) return
    Vue.config.errorHandler = (error, vm, mes) => {
      let info = {
        type: 'script',
        code: 0,
        mes: error.message,
        url: window.location.href
      }
      Vue.nextTick(() => {
        store.dispatch('addErrorLog', info)
      })
    }
  }
}
