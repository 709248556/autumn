export default {
  /**
   * @description 配置显示在浏览器标签的title
   */
  title: 'Autunm - 后台管理系统',
  /**
   * @description token在Cookie中存储的天数，默认1天
   */
  cookieExpires: 1,
  /**
   * @description 是否使用国际化，默认为false
   *              如果不使用，则需要在路由中给需要在菜单中展示的路由设置meta: {title: 'xxx'}
   *              用来在菜单中显示文字
   */
  useI18n: false,
  /**
   * 是否为开发
   */
  isDev: process.env.NODE_ENV === 'development',
  /**
   * @description api请求基础路径
   */
  baseUrl: {
    // 打包说明，如果需要将文件直接放到spring boot 下需要修改 /public/ueditor/ueditor.config.js 与 vue.config.js

    // dev: '/', // 直接打包到java项目下面
    // pro: '/' // 直接打包到java项目下面
    // dev: 'http://127.0.0.1:8001',
    // pro: 'http://127.0.0.1:8001'
    dev: 'http://127.0.0.1:8001',
    pro: 'http://127.0.0.1:8001'
  },
  /**
   * @description 默认打开的首页的路由name值，默认为home
   */
  homeName: 'dashboard',
  /**
   * @description 需要加载的插件
   */
  plugin: {
    'error-store': {
      showInHeader: true, // 设为false后不会在顶部显示错误日志徽标
      developmentOff: true // 设为true后在开发环境不会收集错误信息，方便开发中排查错误
    }
  }
}
