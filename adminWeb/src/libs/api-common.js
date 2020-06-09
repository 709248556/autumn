/**
 * Cascader 数据格式转换
 * @param {Array} items 数据
 */
export const mapCascaderData = items => {
  return mapConvertCascaderData(items, item => {
    return {
      value: item.id,
      label: item.name
    }
  })
}

/**
 * Cascader 数据格式转换
 * @param {Array} items 数据
 * @param {function} converter 转换方法
 */
export const mapConvertCascaderData = (items, converter) => {
  items = items || []
  return items.map(item => {
    var p = converter(item)
    if (item.childrenCount > 0) {
      p.children = []
      p.loading = false
    }
    return p
  })
}

/**
 * 请求 Cascader 数据
 * @param {*} api 请求接口定义 function
 * @param {*} data 请求参数
 * @param {*} callback 请求成功后的回调方法
 */
export const requestCascaderData = (api, data, callback, converter) => {
  if (!api) {
    console.log(
      '执行 requestCascaderData(api) 失败，当前传入的 api 参数：' + api
    )
    return
  }
  api(data)
    .then(result => {
      // let data = mapCascaderData(result)
      let data = converter
        ? mapConvertCascaderData(result, converter)
        : mapCascaderData(result)
      // console.log(data)
      callback && callback(data)
    })
    .catch(err => {
      console.log(err)
    })
}
