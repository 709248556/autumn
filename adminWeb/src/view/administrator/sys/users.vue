<template>
  <Card>
    <Modal :title="resetPasswordModal.title"
           v-model="resetPasswordModal.show"
           class-name="vertical-center-modal"
           :mask-closable="false"
           width="500"
           :footer-hide="true"
           :closable="false">
      <Form :ref="resetPasswordModal.formName"
            :model="resetPasswordModal.formData"
            :rules="resetPasswordModal.ruleValidate"
            :label-width="110">
          <FormItem label="用户账号："
                      prop="userName">
              <label>{{resetPasswordModal.formData.userName}} </label>
          </FormItem>
           <FormItem label="用户名称："
                      prop="realName">
              <label>{{resetPasswordModal.formData.realName}} </label>
          </FormItem>
          <FormItem label="新密码："
                      prop="password">
              <Input v-model="resetPasswordModal.formData.password"
                    :maxlength=50
                    type="password"
                     placeholder="请输入新密码"></Input>
          </FormItem>
          <FormItem label="确认密码："
                      prop="confirmPassword">
              <Input v-model="resetPasswordModal.formData.confirmPassword"
                     type="password"
                     :maxlength=50
                     placeholder="请再输入一次密码"></Input>
          </FormItem>
       <FormItem>
          <Button type="primary"
                  @click="onResetPassword()">确定</Button>
          <Button @click="closeResetPassword()"
                  style="margin-left: 10px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <Modal :title="userFormModal.title"
           v-model="userFormModal.show"
           class-name="vertical-center-modal"
           :mask-closable="false"
           width="700"
           :footer-hide="true"
           :closable="false">
      <Form :ref="userFormModal.formName"
            :model="userFormModal.formData"
            :rules="userFormModal.ruleValidate"
            :label-width="90">
        <Tabs value="userInfo">
          <TabPane label="账号信息"
                   name="userInfo">
            <FormItem label="用户账号："
                      prop="userName">
              <Input v-model="userFormModal.formData.userName"
                     placeholder="请输入账号"
                     style="width:260px"></Input>
            </FormItem>
            <FormItem label="用户名称："
                      prop="realName">
              <Input v-model="userFormModal.formData.realName"
                     placeholder="请输入用户名称"
                     style="width:260px"></Input>
            </FormItem>
            <FormItem label="默认密码："
                      v-if="!userFormModal.isUpdate"
                      :maxlength=50
                      prop="password">
              <Input v-model="userFormModal.formData.password"
                     type="password"
                     placeholder="请输入默认密码"
                     style="width:260px"></Input>
            </FormItem>
            <FormItem label="手机号："
                      prop="phoneNumber">
              <Input v-model="userFormModal.formData.phoneNumber"
                     placeholder="请输入手机号"
                     style="width:260px"></Input>
            </FormItem>
            <FormItem label="角色："
                      prop="roles">
              <Select v-model="userFormModal.selectRoles"
                      multiple
                      style="width:260px">
                <Option v-for="item in roles"
                        :value="item.id"
                        :key="item.id">{{ item.name }}
                </Option>
              </Select>
            </FormItem>
            <FormItem label="状态："
                      prop="status">
              <RadioGroup v-model="userFormModal.formData.status">
                <Radio label="1">正常</Radio>
                <Radio label="2">锁定</Radio>
                <Radio label="3">过期</Radio>
                <Radio label="4">未激活</Radio>
              </RadioGroup>
            </FormItem>
          </TabPane>
          <TabPane label="用户信息"
                   name="roleInfo">
            <FormItem label="出生日期："
                      prop="birthday">
              <DatePicker type="date"
                          placeholder="请选择出生日期"
                          placement="right-start"
                          v-model="userFormModal.formData.birthday"
                          style="width: 260px"></DatePicker>
            </FormItem>

            <FormItem label="昵称："
                      prop="nickName">
              <Input v-model="userFormModal.formData.nickName"
                     placeholder="请输入昵称"
                     style="width:260px"></Input>
            </FormItem>
            <FormItem label="性别："
                      prop="sex">
              <RadioGroup v-model="userFormModal.formData.sex">
                <Radio label="男"></Radio>
                <Radio label="女"></Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="邮箱地址："
                      prop="emailAddress">
              <Input v-model="userFormModal.formData.emailAddress"
                     placeholder="请输入邮箱地址"
                     style="width:260px"></Input>
            </FormItem>
          </TabPane>
        </Tabs>
        <FormItem>
          <Button type="primary"
                  @click="saveUser()">保存</Button>
          <Button @click="resetForm()"
                  style="margin-left: 8px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <AuthorizeTree ref="authTree"
                   title="用户授权"
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
  createUser,
  queryUserById,
  deleteUserById,
  updateUser,
  queryUserByPageList,
  authorizeByModulePermissionTree,
  authorize,
  resetPassword,
  downloadByExcelInfo
} from '@/api/administrator/sys/users'
import { queryByList } from '@/api/administrator/sys/roles'

export default {
  name: 'users',
  components: {
    Action,
    PageTableManager,
    AuthorizeTree
  },
  data () {
    return {
      resetPasswordModal: {
        title: '重置密码',
        show: false,
        formName: 'resetPasswordForm',
        formData: {
          id: null,
          userName: '',
          realName: '',
          password: '',
          confirmPassword: ''
        },
        ruleValidate: {
          password: [
            { required: true, message: '新密码必填', trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: '确认密码必填', trigger: 'blur' }
          ]
        }
      },
      userFormModal: {
        title: '添加用户',
        show: false,
        isUpdate: false,
        formName: 'userForm',
        formData: {
          id: 0,
          birthday: '',
          emailAddress: '',
          headPortraitPath: '',
          nickName: '',
          phoneNumber: '',
          realName: '',
          roles: [],
          sex: '',
          userName: '',
          password: '',
          status: '1'
        },
        selectRoles: [],
        ruleValidate: {
          userName: [{ required: true, message: '账号必填', trigger: 'blur' }],
          realName: [
            { required: true, message: '用户名称必填', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '默认密码必填', trigger: 'blur' }
          ],
          emailAddress: [
            {
              required: false,
              type: 'email',
              message: '请输入正确的邮箱地址',
              trigger: 'blur'
            }
          ],
          phoneNumber: [
            {
              required: false,
              pattern: /^1[3|4|5|7|8][0-9]\d{4,8}$/,
              message: '请输入正确的手机号',
              trigger: 'blur'
            }
          ]
        },
        showPermissionTree: false,
        permissionTree: [],
        authorizePermissions: []
      },
      dataMeta: {
        title: '用户管理',
        queryPageApi: queryUserByPageList,
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
          { title: '用户账号', key: 'userName', sortable: true, minWidth: 120 },
          { title: '用户名称', key: 'realName', minWidth: 120 },
          { title: '状态', key: 'statusName', width: 70 },
          { title: '性别', key: 'sex', width: 70 },
          { title: '昵称', key: 'nickName', width: 80 },
          {
            title: '手机激活',
            width: 90,
            key: 'isActivatePhone',
            render: (h, params) => {
              return h('div', [
                h(
                  'Icon',
                  {
                    props: {
                      size: 20,
                      type: params.row.isActivatePhone
                        ? 'md-checkmark'
                        : 'md-remove'
                    }
                  },
                  ''
                )
              ])
            }
          },
          { title: '手机号', key: 'phoneNumber', width: 120 },
          {
            title: '邮件激活',
            width: 90,
            key: 'isActivateEmail',
            render: (h, params) => {
              return h('div', [
                h(
                  'Icon',
                  {
                    props: {
                      size: 20,
                      type: params.row.isActivateEmail
                        ? 'md-checkmark'
                        : 'md-remove'
                    }
                  },
                  ''
                )
              ])
            }
          },
          { title: '邮件地址', key: 'emailAddress', width: 120 },
          { title: '生日', key: 'birthday', width: 120 },
          {
            title: '操作',
            key: 'action',
            width: 160,
            fixed: 'right',
            align: 'left',
            render: (h, params) => {
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
                        this.updateUser(params)
                      }
                    }
                  },
                  '修改'
                ),
                h(Action, {
                  props: {
                    value: [
                      { key: 'authorize', name: '授权', icon: 'md-hammer' },
                      { key: 'resetPassword', name: '重置密码', icon: 'md-key' },
                      { key: 'delete', name: '删除', icon: 'md-trash' }
                    ]
                  },
                  on: {
                    click: key => {
                      switch (key) {
                        case 'delete':
                          this.deleteUser(params)
                          break
                        case 'resetPassword':
                          this.openResetPassword(params)
                          break
                        case 'authorize':
                          this.onAuthorize(params)
                          break
                      }
                    }
                  }
                })
              ])
            }
          }
        ]
      },
      roles: [],
      authorizeModal: {
        authorizeQueryApi: authorizeByModulePermissionTree,
        authorizeSaveApi: authorize
      }
    }
  },
  methods: {
    onRefresh () {
      this.$refs.ptm.onRefresh()
    },
    onOpenModal () {
      this.userFormModal.isUpdate = false
      this.resetForm()
      this.userFormModal.show = true
      this.userFormModal.formData.status = '1'
      this.userFormModal.title = '添加用户'
    },
    saveUser () {
      this.$refs[this.userFormModal.formName].validate(valid => {
        if (valid) {
          this.userFormModal.formData.roles = []
          for (var i = 0; i < this.userFormModal.selectRoles.length; i++) {
            this.userFormModal.formData.roles.push({
              roleId: this.userFormModal.selectRoles[i]
            })
          }
          if (this.userFormModal.isUpdate) {
            updateUser(this.userFormModal.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.onRefresh()
            })
          } else {
            createUser(this.userFormModal.formData).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.onRefresh()
            })
          }
        } else {
          this.$Message.error('请检查表达后继续!')
          this.userFormModal.show = true
          this.userFormModal.isUpdate = false
        }
      })
    },
    updateUser (params) {
      queryUserById(params.row.id).then(res => {
        this.userFormModal.formData = res
        this.userFormModal.formData.status = this.userFormModal.formData.status.toString()
        this.userFormModal.selectRoles = []
        for (var i = 0; i < this.userFormModal.formData.roles.length; i++) {
          this.userFormModal.selectRoles.push(
            this.userFormModal.formData.roles[i].roleId
          )
        }
        this.userFormModal.title = '修改用户信息'
        this.userFormModal.show = true
        this.userFormModal.isUpdate = true
      })
    },
    deleteUser (params) {
      this.$Modal.confirm({
        title: '删除用户？',
        content: '<p>确定要删除当前选中的用户吗？</p>',
        onOk: () => {
          deleteUserById(params.row.id).then(result => {
            this.$Message.success('删除成功!')
            this.onRefresh()
          })
        },
        onCancel: () => { }
      })
    },
    // 授权
    onAuthorize (params) {
      this.$refs.authTree.openAuthorize(params.row.id)
    },
    // 打开重置密码
    openResetPassword (params) {
      this.resetPasswordModal.formData.id = params.row.id
      this.resetPasswordModal.formData.userName = params.row.userName
      this.resetPasswordModal.formData.realName = params.row.realName
      this.resetPasswordModal.formData.password = ''
      this.resetPasswordModal.formData.confirmPassword = ''
      this.resetPasswordModal.show = true
    },
    closeResetPassword () {
      this.resetPasswordModal.formData.userName = ''
      this.resetPasswordModal.formData.realName = ''
      this.resetPasswordModal.formData.password = ''
      this.resetPasswordModal.formData.confirmPassword = ''
      this.resetPasswordModal.show = false
    },
    // 重置密码
    onResetPassword () {
      this.$refs[this.resetPasswordModal.formName].validate(valid => {
        if (valid) {
          if (this.resetPasswordModal.formData.password !== this.resetPasswordModal.formData.confirmPassword) {
            this.$Message.error('确认密码与新密码不相等.')
            return
          }
          let Input = {
            id: this.resetPasswordModal.formData.id,
            newPassword: this.resetPasswordModal.formData.confirmPassword
          }
          resetPassword(Input).then(result => {
            this.$Message.success('重置成功!')
            this.resetPasswordModal.show = false
          })
        } else {
          this.$Message.error('请检查必输项!')
        }
      })
    },
    resetForm () {
      this.$refs[this.userFormModal.formName].resetFields()
      this.userFormModal.selectRoles = []
      this.userFormModal.formData.roles = []
      this.userFormModal.show = false
    }
  },
  mounted () {
    queryByList({}).then(res => {
      this.roles = res
    })
  }
}
</script>
