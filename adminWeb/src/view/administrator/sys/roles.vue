
<template>
  <Card>
    <Modal :title="roleFormModal.title"
           v-model="roleFormModal.show"
           class-name="vertical-center-modal"
           :mask-closable="false"
           width="600"
           :footer-hide="true"
           :closable="false">
      <Form :ref="roleFormModal.formName"
            :model="roleFormModal.formData"
            :rules="roleFormModal.ruleValidate"
            :label-width="100">
        <Tabs value="roleInfo">
          <TabPane label="角色信息"
                   name="roleInfo">
            <FormItem label="角色名称："
                      prop="name">
              <Input v-model="roleFormModal.formData.name"
                     placeholder="请输入角色名字"
                     style="width:100%"></Input>
            </FormItem>
            <!-- <FormItem label="关联用户">
              <Select v-model="roleFormModal.selectedUsers"
                      filterable
                      remote
                      :remote-method="loadUsers"
                      :loading="roleFormModal.loading"
                      @on-change="onSelectedUser"
                      placeholder="输入用户名后自动搜索"
                      style="width:100%">
                <Option v-for="item in roleFormModal.queryUsers"
                        :value="item.id"
                        :key="item.id">{{item.userName}}</Option>
              </Select>
            </FormItem>
            -->
            <FormItem label="用户列表："
                      prop="users">
              <Tag v-for="item in roleFormModal.formData.users"
                   :key="item.id"
                   :name="item.userName">{{item.userName}}</Tag>
            </FormItem>
            <FormItem label="排序："
                      prop="sortId">
              <InputNumber :min="0"
                           v-model="roleFormModal.formData.sortId"
                           style="width:100%"></InputNumber>
            </FormItem>
            <FormItem label="默认：">
              <i-switch v-model="roleFormModal.formData.isDefault"
                        size="large">
                <span slot="open">是</span>
                <span slot="close">否</span>
              </i-switch>
            </FormItem>
            <FormItem label="状态：">
              <i-switch v-model="roleStatus"
                        size="large">
                <span slot="open">启用</span>
                <span slot="close">禁用</span>
              </i-switch>
            </FormItem>
            <FormItem label="备注："
                      prop="summary">
              <Input v-model="roleFormModal.formData.summary"
                     type="textarea"
                     :autosize="{minRows: 2,maxRows: 5}"
                     placeholder="请输入备注..."
                     style="width:100%"></Input>
            </FormItem>
            <FormItem style="padding-top:20px;">
              <Button type="primary"
                      @click="saveRole()">保存</Button>
              <Button @click="resetForm()"
                      style="margin-left: 8px">取消</Button>
            </FormItem>
          </TabPane>
        </Tabs>
      </Form>
    </Modal>
    <AuthorizeTree ref="authTree"
                   title="角色授权"
                   :authorizeQueryApi="this.authorizeModal.authorizeQueryApi"
                   :authorizeSaveApi="this.authorizeModal.authorizeSaveApi" />
    <PageTableManager ref="ptm"
                      :dataMeta="this.dataMeta" />
  </Card>
</template>
<script>
import Action from '_c/action'
import PageTableManager from '@/components/autumn/page-table-manager'
import { AuthorizeTree } from '_c/autumn/authorize-tree'
import {
  createRole,
  deleteRoleById,
  updateRole,
  queryRoleByPageList,
  queryRoleById,
  authorizeByModulePermissionTree,
  authorize,
  authorizeByAllPermission,
  downloadByExcelInfo
} from '@/api/administrator/sys/roles'
import { queryByList } from '@/api/administrator/sys/users'
export default {
  name: 'roles',
  components: {
    Action,
    PageTableManager,
    AuthorizeTree
  },
  data () {
    return {
      roleFormModal: {
        show: false,
        title: '添加角色',
        isUpdate: false,
        formName: 'roleForm',
        queryUsers: [],
        selectedUsers: 0,
        openLoading: false,
        formData: {
          id: 0,
          name: '',
          sortId: 1,
          summary: '',
          isDefault: false,
          status: 1,
          users: []
        },
        ruleValidate: {
          name: [
            { required: true, message: '角色名字必填', trigger: 'blur' }
          ]
        },
        showPermissionTree: false,
        permissionTree: [],
        authorizePermissions: []
      },
      dataMeta: {
        title: '角色管理',
        queryPageApi: queryRoleByPageList,
        downloadByExcelInfoApi: downloadByExcelInfo,
        pageSize: 20,
        opButtons: [
          {
            type: 'primary',
            text: '添加',
            icon: 'md-add',
            click: () => {
              this.onOpenModal()
            }
          }
        ],
        columns: [
          { title: '行号', key: '_rowIndex', width: 70, align: 'right' },
          { title: '角色名称', key: 'name', sortable: true, minWidth: 200 },
          { title: '排序', key: 'sortId', width: 120 },
          {
            title: '启用',
            key: 'status',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.status === 1 ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          {
            title: '默认',
            key: 'isDefault',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.isDefault ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          { title: '备注', key: 'summary', minWidth: 250 },
          {
            title: '操作',
            key: 'action',
            width: 160,
            fixed: 'right',
            align: 'left',
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
                      this.updateRole(params)
                    }
                  }
                }, '修改'),
                h(Action, {
                  props: {
                    value: [
                      { key: 'authorize', name: '授权', icon: 'md-hammer' },
                      { key: 'authorizeByAll', name: '授权全部权限', icon: 'md-hammer' },
                      { key: 'delete', name: '删除', icon: 'md-trash' }
                    ]
                  },
                  on: {
                    click: (key) => {
                      switch (key) {
                        case 'delete':
                          this.deleteRole(params)
                          break
                        case 'authorize':
                          this.onAuthorize(params)
                          break
                        case 'authorizeByAll':
                          this.onAuthorizeByAll(params)
                          break
                      }
                    }
                  }
                })
              ])
            }
          }
        ],
        data: [],
        total: 0,
        page: 1,
        rows: 10,
        keyword: '',
        loadding: false
      },
      authorizeModal: {
        authorizeQueryApi: authorizeByModulePermissionTree,
        authorizeSaveApi: authorize
      }
    }
  },
  computed: {
    roleStatus: {
      get () {
        let status = this.roleFormModal.formData.status === 1
        return status
      },
      set (newVal) {
        if (newVal) {
          this.roleFormModal.formData.status = 1
        } else {
          this.roleFormModal.formData.status = 2
        }
      }
    }
  },
  methods: {
    onRefresh () {
      this.$refs.ptm.onRefresh()
    },
    loadUsers (query) {
      if (query === '') {
        this.roleFormModal.queryUsers = []
        return
      }
      this.roleFormModal.openLoading = true
      queryByList({ searchKeyword: query }).then(res => {
        this.roleFormModal.openLoading = false
        this.roleFormModal.queryUsers = res
      })
    },
    onSelectedUser (userId) {
      let exists = this.roleFormModal.formData.users.filter((item) => item.userId === userId)
      if (exists.length > 0) {
        return
      }
      let selecteds = this.roleFormModal.queryUsers.filter((item) => item.id === userId)
      selecteds.forEach((item) => {
        this.roleFormModal.formData.users.push({
          userId: item.id,
          userName: item.userName
        })
      })
    },
    onSelectPermission (selected) {
      this.roleFormModal.authorizePermissions = selected
    },
    unSelectUser (event, name) {
      let length = this.roleFormModal.formData.users.length
      for (var i = 0; i < length; i++) {
        var item = this.roleFormModal.formData.users[i]
        if (item.userName === name) {
          this.roleFormModal.formData.users.splice(i, 1)
        }
      }
    },
    onOpenModal () {
      this.roleFormModal.isUpdate = false
      this.resetForm()
      this.roleFormModal.show = true
      this.roleFormModal.title = '添加角色'
      this.roleFormModal.formData.sortId = this.$refs.ptm.getRowTotal() + 1
      this.roleFormModal.formData.isDefault = false
      this.roleFormModal.formData.status = 1
    },
    updateRole (params) {
      queryRoleById(params.row.id).then(res => {
        this.roleFormModal.formData = res
        this.roleFormModal.title = '修改角色信息'
        this.roleFormModal.show = true
        this.roleFormModal.isUpdate = true
      })
    },
    deleteRole (params) {
      this.$Modal.confirm({
        title: '删除角色？',
        content: '<p>确定要删除当前选中的角色吗？</p>',
        onOk: () => {
          deleteRoleById(params.row.id).then(result => {
            this.$Message.success('删除成功!')
            this.onRefresh()
          })
        },
        onCancel: () => {
        }
      })
    },
    // 授权
    onAuthorize (params) {
      this.$refs.authTree.openAuthorize(params.row.id)
    },
    onAuthorizeByAll (params) {
      this.$Modal.confirm({
        title: '全部授权？',
        content: '<p>确定要对所选角色授全部权限吗？</p>',
        onOk: () => {
          authorizeByAllPermission(params.row.id).then(result => {
            this.$Message.success('授权成功!')
            this.onRefresh()
          })
        },
        onCancel: () => {
        }
      })
    },
    saveRole () {
      this.$refs[this.roleFormModal.formName].validate((valid) => {
        if (valid) {
          if (this.roleFormModal.isUpdate) {
            updateRole(this.roleFormModal.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.onRefresh()
            })
          } else {
            createRole(this.roleFormModal.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.onRefresh()
            })
          }
        } else {
          this.$Message.error('请检查表达后继续!')
          this.roleFormModal.show = true
          this.roleFormModal.isUpdate = false
        }
      })
    },
    resetForm () {
      if (!this.roleFormModal.isUpdate) {
        this.$refs[this.roleFormModal.formName].resetFields()
      }
      this.roleFormModal.show = false
      this.roleFormModal.selectedUsers = 0
    }
  },
  mounted () {

  }
}
</script>
