<template>
  <div>
    <Modal v-model="authorizeModal.show"
           class-name="vertical-center-modal"
           :title="this.title"
           :width="authorizeModal.width"
           :mask-closable="false"
           :closable="true"
           :footer-hide="true">
      <header>
        <Button @click="onSelectedAll(true)"
                icon="md-checkmark">全选</Button>
        <Button @click="onSelectedAll(false)"
                style="margin-left: 10px"
                icon="md-checkbox-outline">全部取消</Button>
        <Button @click="onNotSelected()"
                style="margin-left: 10px"
                icon="md-checkmark-circle-outline">反选</Button>
        <Button @click="onExpandAll(true)"
                icon="ios-add"
                style="margin-left: 10px">展开</Button>
        <Button @click="onExpandAll(false)"
                icon="ios-remove"
                style="margin-left: 10px">收缩</Button>
        <div style="float:right;">
          <Button type="primary"
                  icon="md-key"
                  @click="onSaveAuthorize()">保存</Button>
          <Button @click="onClose()"
                  icon="md-close"
                  style="margin-left: 10px">取消</Button>
        </div>
      </header>
      <Divider style="margin-top:15px" />
      <div style="max-height:500px;overflow: auto;margin-top:-20px">
        <Tree :data="authorizeModal.data"
              show-checkbox
              :render="renderContent"
              @on-check-change="onCheckChange"></Tree>
        <Spin fix
              v-if="authorizeModal.loading">
          <Icon type="ios-loading"
                size=30
                class="modal-con-spin-icon-load"></Icon>
          <div>{{authorizeModal.loadingText}}</div>
        </Spin>
      </div>
    </Modal>
  </div>
</template>
<script>
import TreeItem from './tree-item.vue'
import { mapTree } from '@/libs/util'
export default {
  name: 'AuthorizeTree',
  components: {
    TreeItem
  },
  props: {
    title: {
      type: String,
      default () {
        return '授权'
      }
    },
    authorizeQueryApi: {
      type: Function
    },
    authorizeSaveApi: {
      type: Function
    }
  },
  data () {
    return {
      authorizeModal: {
        show: false,
        width: 800,
        loading: false,
        loadingText: '正在处理,请稍后...',
        authorizeId: 0,
        data: []
      }
    }
  },
  methods: {
    openAuthorize (id) {
      this.authorizeModal.data = []
      this.authorizeModal.authorizeId = id
      this.authorizeModal.loading = true
      this.authorizeModal.loadingText = '正在加载权限信息，请稍后...'
      this.authorizeModal.show = true
      this.authorizeQueryApi(id)
        .then(res => {
          mapTree(res, (node, parent) => {
            node.expand = false
            node.title = node.customName
            node.operationPermissions = node.operationPermissions || []
            node.operationPermissions.forEach(item => {
              item.isPermission = true
              item.checked = item.isGranted
              item.resourcesId = node.resourcesId
              item.parent = node
            })
            if (node.children === null || node.children.length === 0) {
              node.checked = node.isGranted
            }
          })
          this.authorizeModal.data = res
          this.authorizeModal.loading = false
        })
        .catch(res => {
          this.authorizeModal.loading = false
          this.authorizeModal.show = false
        })
    },
    onSaveAuthorize () {
      let input = {
        id: this.authorizeModal.authorizeId,
        permissions: []
      }
      let _this = this
      mapTree(this.authorizeModal.data, (node, parent) => {
        let isGranted = _this.getIsGranted(node)
        if (isGranted) {
          input.permissions.push(
            {
              isGranted: isGranted,
              permissionName: '',
              resourcesId: node.resourcesId
            }
          )
        }
        if (node.operationPermissions !== null || node.operationPermissions.length > 0) {
          node.operationPermissions.forEach(item => {
            if (item.checked) {
              input.permissions.push(
                {
                  isGranted: item.checked,
                  permissionName: item.permissionName,
                  resourcesId: node.resourcesId
                }
              )
            }
          })
        }
      })
      this.authorizeModal.loading = true
      this.authorizeModal.loadingText = '正在保存权限信息，请稍后...'
      this.authorizeSaveApi(input).then(res => {
        this.$Message.success('授权成功!')
        this.onClose()
      }).catch(res => {
        this.authorizeModal.loading = false
      })
    },
    // 获取是否授权
    getIsGranted (node) {
      if (node.checked) {
        return true
      }
      if (node.operationPermissions && node.operationPermissions.length > 0) {
        let checkedItems = node.operationPermissions.filter(item => item.checked)
        if (checkedItems.length > 0) {
          return true
        }
      }
      if (node.children && node.children.length > 0) {
        for (let i = 0; i < node.children.length; i++) {
          let result = this.getIsGranted(node.children[i])
          if (result) {
            return true
          }
        }
      }
      return false
    },
    onCheckChange (selected, node) {
      node.operationPermissions.forEach(item => {
        item.checked = node.checked
      })
      if (node.callback) {
        node.callback()
      }
    },
    onClose () {
      this.authorizeModal.data = []
      this.authorizeModal.authorizeId = null
      this.authorizeModal.loading = false
      this.authorizeModal.loadingText = ''
      this.authorizeModal.show = false
    },
    onSelectedAll (isSelect) {
      mapTree(this.authorizeModal.data, (node, parent) => {
        node.checked = isSelect
        node.operationPermissions.forEach(item => {
          item.checked = isSelect
        })
        if (node.callback) {
          node.callback()
        }
      })
    },
    onNotSelected () {
      mapTree(this.authorizeModal.data, (node, parent) => {
        if (node.children === null || node.children.length === 0) {
          node.checked = !node.checked
        } else {
          if (node.checked) {
            node.checked = false
          }
        }
        node.operationPermissions.forEach(item => {
          item.checked = !item.checked
        })
        if (node.callback) {
          node.callback()
        }
      })
    },
    onExpandAll (isExpand) {
      mapTree(this.authorizeModal.data, (node, parent) => {
        node.expand = isExpand
      })
    },
    renderContent (h, { root, node, data }) {
      return h('span',
        {
          style: {
            display: 'inline-block',
            width: '60%'
          }
        },
        [
          h('span',
            [
              h('Icon',
                {
                  props: {
                    type: data.icon ? data.icon : 'md-folder',
                    size: 20
                  },
                  style: {
                    marginRight: '8px'
                  }
                }
              ),
              h('span', data.customName)
            ]
          ),
          h('span',
            {
              style: {
                display: 'inline-block',
                float: 'right',
                marginRight: '10px'
              }
            },
            [
              h(TreeItem,
                {
                  props: {
                    treeNode: data
                  }
                })
            ])
        ]
      )
    }
  }
}
</script>
<style>
.table-toolbar {
  margin-left: 10px;
}
.modal-form .ivu-modal-footer {
  text-align: left;
}
.modal-con-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
</style>
