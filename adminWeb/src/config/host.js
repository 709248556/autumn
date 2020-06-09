import config from './index'
/**
 * 获取当前 host 名称
 */
export default () => {
  let host = (config.isDev ? config.baseUrl.dev : config.baseUrl.pro).trim()
  if (host === '' || host === '/') {
    return location.origin
  }
  return host
}
