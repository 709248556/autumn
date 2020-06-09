<!-- 基于 autumn 的树接口列表 -->
<template>
  <div>
    <Spin fix  v-if="this.loadding">
                <Icon type="ios-loading"
                      size=30
                      class="modal-con-spin-icon-load"></Icon>
                <div>正在加载，请稍后...</div>
    </Spin>
    <Breadcrumb separator="/"
                style="margin:10px 0">
      <BreadcrumbItem v-for="item in breadcrumb"
                      :key="item.id">
        <router-link @click.native="onNative(item)"
                     to="">{{item.name}}</router-link>
      </BreadcrumbItem>
    </Breadcrumb>
    <div>
    <Table border
           highlight-row
           :columns="treeColumns"
           no-data-text="无数据"
           :data="dataItems">
    </Table>
     </div>
  </div>
</template>
<script>
import Action from '_c/action'
export default {
  name: 'autumn-tree-list',
  components: {
    Action
  },
  model: {
    // 通过v-model 传过来的父级变更参数
    prop: 'item',
    event: 'on-parent-change'
  },
  props: {
    queryChildrenApi: {
      type: Function
    },
    titleName: {
      type: String,
      default: '名称'
    },
    columns: {
      type: Array,
      default: null
    },
    tableButtons: {
      type: Function,
      default: null
    }
  },
  data () {
    return {
      lastCurrentItem: null,
      currentItem: null,
      loadding: false,
      breadcrumb: [],
      dataItems: [],
      sourceItems: [],
      treeColumns: [
        {
          title: '序号',
          key: '_rowIndex',
          width: 65,
          align: 'right'
        },
        {
          title: this.titleName,
          key: 'name',
          minWidth: 160,
          render: (h, params) => {
            let _this = this
            return h('div', {
              style: {
                display: 'flex'
              }
            }, [
              h('Icon', {
                props: {
                  type: params.row.isReturnParent ? 'ios-undo' : (params.row.childrenCount > 0 ? 'md-folder' : 'ios-card-outline'),
                  size: 30
                },
                style: {
                  color: '#FFD659',
                  display: 'block'
                }
              }),
              h('a', {
                style: {
                  marginLeft: '10px',
                  fontSize: '12px',
                  display: 'block',
                  height: '30px',
                  lineHeight: '30px'
                },
                on: {
                  click () {
                    _this.onClickItem(params)
                  }
                }
              }, params.row.name)
            ])
          }
        }
      ]
    }
  },
  methods: {
    onNative (item) {
      let oldBreadcrumb = this.breadcrumb
      this.breadcrumb = []
      for (var i = 0; i < oldBreadcrumb.length - 1; i++) {
        if (!oldBreadcrumb[i].id || oldBreadcrumb[i].id !== item.id) {
          this.breadcrumb.push(oldBreadcrumb[i])
        } else {
          break
        }
      }
      if (item.id !== null) {
        this.breadcrumb.push(item)
      }
      this.lastCurrentItem = item
      this.currentItem = item
      this.onLoadData()
    },
    onClickItem (params) {
      if (params.row.isReturnParent) {
        this.currentItem = this.breadcrumb[this.breadcrumb.length - 2]
        this.onNative(this.currentItem)
      } else {
        this.currentItem = params.row
        this.onLoadData()
      }
    },
    onLoadData () {
      let input = {
        parentId: this.currentItem ? this.currentItem.id : null,
        status: null
      }
      if (this.currentItem === null || this.currentItem.isReturnParent) {
        this.$emit('on-parent-change', null)
      } else {
        this.$emit('on-parent-change', this.currentItem)
      }
      let _this = this
      _this.loadding = true
      this.queryChildrenApi(input).then(res => {
        if (res) {
          for (let i = 0; i < res.length; i++) {
            res[i]._rowIndex = i + 1
          }
        }
        _this.sourceItems = res
        let items = []
        if (res && res[0] && res[0].parentId === null) {
          _this.currentItem = null
        }
        if (_this.currentItem) {
          if (!_this.lastCurrentItem || _this.lastCurrentItem.id !== _this.currentItem.id) {
            if (!_this.currentItem.isReturnParent) {
              _this.breadcrumb.push(_this.currentItem)
            } else {
              let oldBreadcrumb = _this.breadcrumb
              _this.breadcrumb = []
              for (var i = 0; i < oldBreadcrumb.length - 1; i++) {
                _this.breadcrumb.push(oldBreadcrumb[i])
              }
            }
          }
          items.push({
            id: _this.currentItem.parentId,
            isReturnParent: true,
            name: '返回上级'
          })
        } else {
          _this.breadcrumb = []
          _this.breadcrumb.push({
            id: null,
            name: '根级'
          })
        }
        items.push(...res)
        _this.dataItems = items
        _this.lastCurrentItem = _this.currentItem
        _this.loadding = false
      }).catch(() => {
        _this.loadding = false
      })
    },
    // 获取数据项目长度
    getDataItemLength () {
      if (this.sourceItems) {
        return this.sourceItems.length
      } else {
        return 0
      }
    },
    // 获取数据项目集合
    getDataItems () {
      if (this.sourceItems) {
        return this.sourceItems
      } else {
        return []
      }
    },
    init () {
      if (this.columns) {
        this.treeColumns.push(...this.columns)
      }
      if (this.tableButtons) {
        var ops = {
          title: '操作',
          key: 'action',
          align: 'center',
          minWidth: 170,
          maxWidth: 170,
          fixed: 'right',
          render: (h, params) => {
            if (!params.row.isReturnParent) {
              return this.tableButtons(h, params)
            }
          }
        }
        this.treeColumns.push(ops)
      }
    }
  },
  mounted () {
    this.init()
    this.onLoadData()
  }
}
</script>
<style>
  .modal-con-spin-icon-load {
    animation: ani-demo-spin 1s linear infinite;
  }
  .modal-form .ivu-upload {
    width: 133px;
  }
</style>
