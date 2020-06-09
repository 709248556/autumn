<template>
  <Card>
    <div class="card-title">模块管理</div>
    <Divider />
    <div style="margin-bottom:20px;">
      <Select v-model="formModal.currentResourcesType"
              style="width:260px"
              @on-change="onLoadData()">
        <Option v-for="item in formModal.resourcesTypes"
                :value="item.id"
                :key="item.id">{{ item.name }}</Option>
      </Select>
      <Button type="primary"
              icon="md-add"
              style="margin-left:10px;"
              @click="onOpenModal()">添加</Button>
      <Button type="primary"
              icon="md-refresh"
              style="margin-left:10px;"
              @click="onLoadData()">刷新</Button>
    </div>
    <div>
      <Breadcrumb separator="/"
                  style="margin:10px 0">
        <BreadcrumbItem v-for="item in breadcrumb"
                        :key="item.id">
          <router-link @click.native="onNative(item)"
                       to="">{{item.name}}</router-link>
        </BreadcrumbItem>
      </Breadcrumb>
      <Table border
             highlight-row
             :columns="treeTableMeta.columns"
             :data="dataItems"></Table>
    </div>
    <Modal :title="formModal.title"
           v-model="formModal.show"
           class-name="vertical-center-modal"
           :mask-closable="false"
           :width="formModal.width"
           :footer-hide="true"
           :closable="false">
      <Form :ref="formModal.formName"
            :model="formModal.formData"
            :rules="formModal.ruleValidate"
            :label-width="100">

        <Tabs v-model="formModal.tabName">
          <TabPane label="模块信息"
                   name="main">
            <FormItem label="资源id："
                      prop="id">
              <Input v-model="formModal.formData.id"
                     v-bind:readonly="showPermissionTab"
                     placeholder="请输入模块ID"></Input>
            </FormItem>
            <FormItem label="模块名称："
                      prop="name">
              <Input v-model="formModal.formData.name"
                     placeholder="请输入模块名称"></Input>
            </FormItem>
            <FormItem label="显示名称："
                      prop="customName">
              <Input v-model="formModal.formData.customName"
                     placeholder="请输入模块显示名称"></Input>
            </FormItem>
            <FormItem label="排序："
                      prop="sortId">
              <InputNumber :min="1"
                           v-model="formModal.formData.sortId"
                           style="width:100px"></InputNumber>
            </FormItem>
            <FormItem label="备注："
                      prop="summary">
              <Input v-model="formModal.formData.summary"
                     type="textarea"
                     :autosize="{minRows: 2,maxRows: 5}"
                     placeholder="请输入备注..."></Input>
            </FormItem>
          </TabPane>
          <TabPane label="配置项"
                   name="secend">
            <FormItem label="路由标识："
                      prop="identification">
              <Input v-model="formModal.formData.identification"
                     placeholder="请输入对应路由标识"></Input>
            </FormItem>
            <FormItem label="图标："
                      prop="icon">
              <Input v-model="formModal.formData.icon"
                     placeholder="请输入图标"
                     :icon="formModal.formData.icon"
                     style="width:225px"></Input>
              <Button type="primary"
                      shape="circle"
                      icon="ios-search"
                      size="small"
                      style="margin-left:10px;"
                      @click="iconSelectorShow=true"></Button>
            </FormItem>
            <FormItem label="URL："
                      prop="url">
              <Input v-model="formModal.formData.url"
                     placeholder="请输入模块URL"></Input>
            </FormItem>
            <FormItem label="权限URL："
                      prop="permissionUrl">
              <Input v-model="formModal.formData.permissionUrl"
                     placeholder="请输入权限URL,多个Url采英文分号或回车换行"
                     type="textarea"
                     :autosize="{minRows: 3,maxRows: 6}"></Input>
            </FormItem>
            <Row>
              <Col span="5">
              <FormItem label="是否菜单：">
                <i-switch v-model="formModal.formData.isMenu"
                          size="large">
                  <span slot="open">是</span>
                  <span slot="close">否</span>
                </i-switch>
              </FormItem>
              </Col>
              <Col span="5">
              <FormItem label="系统模块：">
                <i-switch v-model="formModal.formData.isSysModule"
                          size="large">
                  <span slot="open">是</span>
                  <span slot="close">否</span>
                </i-switch>
              </FormItem>
              </Col>
              <Col span="5">
              <FormItem label="需要授权：">
                <i-switch v-model="formModal.formData.isAuthorize"
                          size="large">
                  <span slot="open">是</span>
                  <span slot="close">否</span>
                </i-switch>
              </FormItem>
              </Col>
            </Row>
          </TabPane>
          <TabPane v-if="showPermissionTab"
                   label="模块权限"
                   name="permission">
            <Modal :title="formModalPermission.title"
                   v-model="formModalPermission.show"
                   class-name="vertical-center-modal"
                   :mask-closable="false"
                   width="600"
                   :footer-hide="true"
                   :closable="false">
              <Form :ref="formModalPermission.formName"
                    :model="formModalPermission.formData"
                    :rules="formModalPermission.ruleValidate"
                    :label-width="100">
                <FormItem label="名称"
                          prop="name">
                  <Input v-model="formModalPermission.formData.name"
                         placeholder="请输入权限名称"></Input>
                </FormItem>
                <FormItem label="友好名称"
                          prop="friendlyName">
                  <Input v-model="formModalPermission.formData.friendlyName"
                         placeholder="请输入友好名称"></Input>
                </FormItem>
                <FormItem label="权限URL"
                          prop="permissionUrl">
                  <Input v-model="formModalPermission.formData.permissionUrl"
                         placeholder="请输入权限URL,多个Url采英文分号或回车换行"
                         type="textarea"
                         :autosize="{minRows: 3,maxRows: 6}"></Input>
                </FormItem>
                <FormItem label="排序"
                          prop="sortId">
                  <InputNumber :min="1"
                               v-model="formModalPermission.formData.sortId"></InputNumber>
                </FormItem>
                <FormItem label="备注"
                          prop="summary">
                  <Input v-model="formModalPermission.formData.summary"
                         type="textarea"
                         :autosize="{minRows: 2,maxRows: 5}"
                         placeholder="请输入备注..."></Input>
                </FormItem>
                <FormItem>
                  <Button type="primary"
                          @click="savePermission()">保存</Button>
                  <Button @click="resetFormPermission()"
                          style="margin-left: 8px">取消</Button>
                </FormItem>
              </Form>
            </Modal>
            <page-table :columns="tableMeta.columns"
                        :data="tableMeta.data"
                        :total="tableMeta.total"
                        :pageSize="tableMeta.pageSize"
                        :current="tableMeta.page"
                        :showLoadding="tableMeta.loadding"
                        @on-search="onSearchPermission"
                        height="300"
                        @on-page-change="onPageChange">
              <template slot="opration">
                <Button type="primary"
                        icon="md-add"
                        @click="formModalPermission.show=true;formModalPermission.formData.sortId=tableMeta.total+1;formModalPermission.isUpdate=false;formModalPermission.title='添加权限'">添加</Button>
              </template>
            </page-table>
          </TabPane>
        </Tabs>
        <FormItem>
          <Button type="primary"
                  @click="save()">保存</Button>
          <Button @click="resetForm()"
                  style="margin-left: 8px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <Modal title="图标选择"
           v-model="iconSelectorShow"
           class-name="vertical-center-modal"
           :mask-closable="false"
           width="804"
           :footer-hide="true"
           :scrollable="true">
      <icon-selector @on-select="onSelectIcon"></icon-selector>
    </Modal>

  </Card>
</template>
<script>
import Action from '_c/action'
import PageTable from '_c/page-table'
import IconSelector from '_c/icon-selector'
import {
  createPermission,
  deletePermissionById,
  queryByPageList,
  updatePermission,
  queryPermissionById,
  addDefaultEditPermission,
  addDefaultQueryPermission
} from '@/api/administrator/sys/resource-permissions'
import {
  queryResourcesTypeList,
  queryChildren,
  queryModuleById,
  createModule,
  deleteModuleById,
  updateModule
} from '@/api/administrator/sys/resource-modules'
export default {
  name: 'resources_modules',
  components: {
    Action,
    PageTable,
    IconSelector
  },
  data () {
    return {
      lastCurrentItem: null,
      currentItem: null,
      breadcrumb: [],
      dataItems: [],
      sourceItems: [],
      formModal: {
        width: 800,
        title: '添加模块',
        show: false,
        isUpdate: false,
        tabName: 'main',
        formName: 'moduleForm',
        formData: {
          customName: '',
          icon: '',
          id: '',
          identification: '',
          isAuthorize: false,
          isMenu: false,
          isSysModule: false,
          name: '',
          parentId: '',
          permissionUrl: '',
          resourcesType: '1',
          sortId: 1,
          summary: '',
          url: ''
        },
        currentResourcesType: 1,
        resourcesTypes: [],
        ruleValidate: {
          id: [
            { required: true, message: '模块ID字必填', trigger: 'blur' }
          ],
          customName: [
            { required: true, message: '自定义名称必填', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ]
        }
      },
      treeTableMeta: {
        columns: [
          {
            title: '模块名称',
            key: 'name',
            minWidth: 200,
            render: (h, params) => {
              let _this = this
              return h('div', {
                style: {
                  display: 'flex'
                }
              }, [h('Icon', {
                props: {
                  type: params.row.isReturnParent ? 'ios-undo' : params.row.icon,
                  size: 30
                },
                style: {
                  color: params.row.isReturnParent ? '#FFD659' : '',
                  display: params.row.isReturnParent ? 'block' : ''
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
          },
          { title: '排序', key: 'sortId', width: 80 },
          { title: '资源id', key: 'id', minWidth: 100 },
          {
            title: '系统',
            key: 'isSysModule',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.isSysModule ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          {
            title: '授权',
            key: 'isAuthorize',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.isAuthorize ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          {
            title: '菜单',
            key: 'isMenu',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.isMenu ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          { title: '路由标识', key: 'identification', minWidth: 100 },
          { title: 'Url', key: 'url', minWidth: 100 },
          {
            title: '权限URL',
            key: 'permissionUrl',
            minWidth: 300,
            render: (h, params) => {
              params.row.permissionUrl = params.row.permissionUrl.replace(';', '\n')
              return h('pre', params.row.permissionUrl)
            }
          },
          { title: '备注', key: 'summary', minWidth: 100 },
          {
            title: '操作',
            key: 'action',
            align: 'center',
            width: 165,
            fixed: 'right',
            render: (h, params) => {
              if (!params.row.isReturnParent) {
                let buttons = [
                  { key: 'delete', name: '删除', icon: 'md-trash' },
                  { key: 'defaultQuery', name: '添加默认查询权限', icon: 'md-key' },
                  { key: 'defaultEdit', name: '添加默认编辑权限', icon: 'md-key' }
                ]
                return h('div', [
                  h(
                    'Button',
                    {
                      props: {
                        type: 'primary',
                        size: 'small',
                        icon: 'md-create'
                      },
                      style: {
                        marginRight: '5px'
                      },
                      on: {
                        click: () => {
                          this.update(params)
                        }
                      }
                    },
                    '修改'
                  ),
                  h(Action, {
                    props: {
                      value: buttons
                    },
                    on: {
                      click: key => {
                        switch (key) {
                          case 'delete':
                            this.delete(params)
                            break
                          case 'defaultQuery':
                            this.createDefaultQuery(params)
                            break
                          case 'defaultEdit':
                            this.createDefaultEdit(params)
                            break
                        }
                      }
                    }
                  })
                ])
              }
            }
          }
        ]
      },
      formModalPermission: {
        title: '添加资源权限',
        show: false,
        isUpdate: false,
        formName: 'permissionsForm',
        modules: [],
        formData: {
          friendlyName: '',
          id: 0,
          name: '',
          permissionUrl: '',
          resourcesId: '',
          sortId: 1,
          summary: ''
        },
        ruleValidate: {
          friendlyName: [
            { required: true, message: '友好名称必填', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '权限名称必填', trigger: 'blur' }
          ],
          resourcesId: [
            { required: true, message: '资源标识必填', trigger: 'blur' }
          ]
        }
      },
      tableMeta: {
        columns: [
          { title: '权限名字', key: 'name', width: 90 },
          { title: '友好名称', key: 'friendlyName', width: 90 },
          { title: '排序', key: 'sortId', width: 70 },
          {
            title: '权限URL',
            key: 'permissionUrl',
            width: 300,
            render: (h, params) => {
              params.row.permissionUrl = params.row.permissionUrl.replace(';', '\n')
              return h('pre', params.row.permissionUrl)
            }
          },
          { title: '备注', key: 'summary', width: 200 },
          {
            title: '操作',
            key: 'action',
            width: 165,
            fixed: 'right',
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small',
                    icon: 'md-create'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.updatePermission(params)
                    }
                  }
                }, '修改'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small',
                    icon: 'md-trash'
                  },
                  on: {
                    click: () => {
                      this.deletePermission(params)
                    }
                  }
                }, '删除')
              ])
            }
          }
        ],
        data: [],
        total: 0,
        page: 1,
        pageSize: 10,
        loadding: false
      },
      showPermissionTab: false,
      currentResourceId: '',
      iconSelectorShow: false
    }
  },
  methods: {
    onOpenModal () {
      this.formModal.tabName = 'main'
      this.formModal.show = true
      this.formModal.isUpdate = false
      this.formModal.title = '添加模块'
      this.showPermissionTab = false
      this.currentResourceId = ''
      this.formModal.width = 800
      this.formModal.formData = {}
      this.formModal.formData.sortId = this.sourceItems.length + 1
      this.formModal.formData.isMenu = true
      this.formModal.formData.isAuthorize = true
      if (this.currentItem) {
        this.formModal.formData.id = this.currentItem.id + '_'
      }
    },
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
      if (this.formModal.resourcesTypes.length === 0) {
        this.sourceItems = []
        this.breadcrumb = []
        this.items.items = []
        return
      }
      let input = {
        resourcesType: this.formModal.currentResourcesType,
        parentId: this.currentItem ? this.currentItem.id : null
      }
      let _this = this
      queryChildren(input).then(res => {
        _this.sourceItems = res
        if (res && res[0] && res[0].parentId === '') {
          _this.currentItem = null
        }
        let items = []
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
        this.formModal.formData = this.formModal.formData || {}
        this.formModal.formData.resourcesType = this.formModal.currentResourcesType
      })
    },
    onPageChange (page, keyword) {
      this.loadPageData(this.currentResourceId, page, keyword)
    },
    save () {
      this.$refs[this.formModal.formName].validate((valid) => {
        if (valid) {
          this.formModal.formData.resourcesType = this.formModal.currentResourcesType
          if (this.currentItem) {
            this.formModal.formData.parentId = this.currentItem.id
          } else {
            this.formModal.formData.parentId = ''
          }
          if (this.formModal.isUpdate) {
            updateModule(this.formModal.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.onLoadData()
            })
          } else {
            createModule(this.formModal.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.onLoadData()
            })
          }
        } else {
          this.$Message.error('请检查必填项后继续!')
          this.formModal.show = true
          this.formModal.isUpdate = false
        }
      })
    },
    update (params) {
      let that = this
      queryModuleById(params.row.id).then(res => {
        that.formModal.tabName = 'main'
        that.formModal.formData = res
        that.formModal.formData.resourcesType = res.resourcesType + ''
        that.formModal.title = '修改模块信息'
        that.formModal.show = true
        that.formModal.isUpdate = true
        that.showPermissionTab = true
        that.formModal.width = 800
        that.currentResourceId = params.row.id
        that.loadPageData(params.row.id, 1)
      })
    },
    delete (params) {
      this.$Modal.confirm({
        title: '删除模块？',
        content: '<p>确定要删除当前选中的模块吗？</p>',
        onOk: () => {
          deleteModuleById(params.row.id).then(result => {
            this.$Message.success('删除成功!')
            this.onLoadData()
          })
        },
        onCancel: () => {
        }
      })
    },
    createDefaultQuery (params) {
      addDefaultQueryPermission(params.row.id).then(res => {
        this.$Message.success('添加成功!')
      })
    },
    createDefaultEdit (params) {
      addDefaultEditPermission(params.row.id).then(res => {
        this.$Message.success('添加成功!')
      })
    },
    resetForm () {
      this.$refs[this.formModal.formName].resetFields()
      this.formModal.show = false
      this.formModal.formData.resourcesType = this.formModal.currentResourcesType
    },
    loadPageData (resourceId, page, keyword) {
      page = page || 1
      resourceId = resourceId || 0
      let q = {
        currentPage: page,
        pageSize: 10
      }
      if (keyword) {
        q.searchKeyword = keyword
      }
      q.criterias = []
      q.criterias.push(
        {
          expression: 'resourcesId',
          op: '=',
          value: resourceId
        }
      )
      this.tableMeta.loadding = true
      queryByPageList(q).then(result => {
        this.tableMeta.data = result.items
        this.tableMeta.total = result.rowTotal
        this.tableMeta.pageSize = result.pageSize
        this.tableMeta.page = result.currentPage
        this.tableMeta.loadding = false
      })
    },
    loadResourcesTypes () {
      queryResourcesTypeList().then(result => {
        this.formModal.resourcesTypes = result
        if (this.formModal.resourcesTypes.length > 0) {
          this.formModal.currentResourcesType = this.formModal.resourcesTypes[0].id
          this.formModal.formData.resourcesType = this.formModal.currentResourcesType
          this.onLoadData()
        }
      })
    },
    onSearchPermission (keyword) {
      if (keyword) {
        this.loadPageData(this.currentResourceId, 1, keyword)
      } else {
        this.loadPageData(this.currentResourceId)
      }
    },
    savePermission () {
      this.$refs[this.formModalPermission.formName].validate((valid) => {
        if (valid) {
          this.formModalPermission.formData.resourcesId = this.formModal.formData.id
          if (this.formModalPermission.isUpdate) {
            updatePermission(this.formModalPermission.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetFormPermission()
              this.loadPageData(this.currentResourceId)
            })
          } else {
            createPermission(this.formModalPermission.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetFormPermission()
              this.loadPageData(this.currentResourceId)
            })
          }
        } else {
          this.$Message.error('请检查表达后继续!')
          this.formModalPermission.show = true
          this.formModalPermission.isUpdate = false
        }
      })
    },
    updatePermission (params) {
      queryPermissionById(params.row.id).then(res => {
        this.formModalPermission.formData = res
        this.formModalPermission.title = '修改权限信息'
        this.formModalPermission.show = true
        this.formModalPermission.isUpdate = true
      })
    },
    deletePermission (params) {
      this.$Modal.confirm({
        title: '删除权限？',
        content: '<p>确定要删除当前选中的权限吗？</p>',
        onOk: () => {
          deletePermissionById(params.row.id).then(result => {
            this.$Message.success('删除成功!')
            this.loadPageData(this.currentResourceId)
          })
        },
        onCancel: () => {
        }
      })
    },
    resetFormPermission () {
      this.$refs[this.formModalPermission.formName].resetFields()
      this.formModalPermission.show = false
    },
    onSelectIcon (type) {
      this.formModal.formData.icon = type.name
      this.iconSelectorShow = false
    }
  },
  mounted () {
    this.loadResourcesTypes()
  }
}
</script>
