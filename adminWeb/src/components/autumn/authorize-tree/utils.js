/**
 * 处理树形结构数据
 * @param {*} trees 树
 * @param {*} callback 回调函数
 */
export const handlerTree = (trees, callback, parent) => {
  trees = trees || []
  parent = parent || null
  trees.forEach(item => {
    if (item && item.children) {
      handlerTree(item.children, callback, item)
    }
    callback && callback(item, parent)
  })
}
