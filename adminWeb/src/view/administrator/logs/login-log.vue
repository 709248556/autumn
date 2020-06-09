<template>
  <Card>
    <Modal v-model="formModal.isShow"
           :width="formModal.width"
           class-name="vertical-center-modal"
           :title="formModal.title">
      <Tabs v-model="formModal.tabName">
        <TabPane label="基本信息"
                 icon="md-apps"
                 name="main">
          <Form :model="formModal.formData"
                :label-width="100">
            <FormItem label="登录时间："
                      prop="gmtCreate">
              <span class="span-underline">{{formModal.formData.gmtCreate}}</span>
            </FormItem>
            <FormItem label="用户名："
                      prop="userName">
              <span class="span-underline">{{formModal.formData.userName}}</span>
            </FormItem>
            <FormItem label="登录账号："
                      prop="userAccount">
              <span class="span-underline">{{formModal.formData.userAccount}}</span>
            </FormItem>
            <FormItem label="登录提供者："
                      prop="provider">
              <span class="span-underline">{{formModal.formData.provider}}</span>
            </FormItem>
            <FormItem label="登录提供者键："
                      prop="providerKey">
              <span class="span-underline">{{formModal.formData.providerKey}}</span>
            </FormItem>
            <FormItem label="登录状态："
                      prop="success">
              <Icon type="md-checkmark"
                    v-if="formModal.formData.success" />
              <Icon type="md-remove"
                    v-if="!formModal.formData.success" />
            </FormItem>
            <FormItem label="状态消息："
                      prop="statusMessage">
              <span class="span-underline">{{formModal.formData.statusMessage}}</span>
            </FormItem>
          </Form>
        </TabPane>
        <TabPane label="客户端信息"
                 icon="ios-apps-outline"
                 name="else">
          <Form :model="formModal.formData"
                :label-width="100">
            <FormItem label="客户端IP："
                      prop="clientIpAddress">
              <span class="span-underline">{{formModal.formData.clientIpAddress}}</span>
            </FormItem>
            <FormItem label="客户端名称："
                      prop="clientName">
              <span class="span-underline">{{formModal.formData.clientName}}</span>
            </FormItem>
            <FormItem label="客户端版本："
                      prop="clientVersion">
              <span class="span-underline">{{formModal.formData.clientVersion}}</span>
            </FormItem>
            <FormItem label="浏览器平台："
                      prop="browserPlatform">
              <span class="span-underline">{{formModal.formData.browserPlatform}}</span>
            </FormItem>
            <FormItem label="浏览器名称："
                      prop="browserName">
              <span class="span-underline">{{formModal.formData.browserName}}</span>
            </FormItem>
            <FormItem label="浏览器信息："
                      prop="browserInfo">
              <span class="span-underline">{{formModal.formData.browserInfo}}</span>
            </FormItem>
          </Form>
        </TabPane>
      </Tabs>
    </Modal>
    <PageTableManager ref="ptm"
                      :dataMeta="this.dataMeta" />
  </Card>
</template>
<script>
import PageTableManager from '@/components/autumn/page-table-manager'
import { deleteAll, downloadByExcelInfo, queryByPageList } from '@/api/administrator/logs/login-log'
export default {
  name: 'sys_log_login',
  components: {
    PageTableManager
  },
  data () {
    return {
      dataMeta: {
        title: '登录日志管理',
        queryPageApi: queryByPageList,
        downloadByExcelInfoApi: downloadByExcelInfo,
        pageSize: 20,
        opButtons: [
          {
            type: 'error',
            text: '清除日志',
            icon: 'md-trash',
            click: () => {
              this.deleteAll()
            }
          }
        ],
        fastFilter: [
          {
            key: 'gmtCreate',
            placeholder: '选择日志时间范围',
            width: '280px',
            dataType: 'datetime',
            op: '=',
            value: ''
          },
          {
            key: 'success',
            placeholder: '状态',
            width: '90px',
            dataType: 'select',
            op: '=',
            value: null,
            ops: [
              {
                value: '',
                label: '全部'
              },
              {
                value: '1',
                label: '成功'
              },
              {
                value: '0',
                label: '失败'
              }
            ]
          }
        ],
        advancedFilter: [
          {
            key: 'gmtCreate',
            title: '日志时间',
            placeholder: '选择日志时间',
            dataType: 'datetime',
            value: null,
            op: '=',
            ops: [
              {
                value: '=',
                label: '等于'
              },
              {
                value: '>',
                label: '大于'
              },
              {
                value: '>=',
                label: '大于或等于'
              },
              {
                value: '<',
                label: '小于'
              },
              {
                value: '<',
                label: '小于或等于'
              }
            ]
          },
          {
            key: 'userName',
            title: '用户名称',
            placeholder: '输入用户名称',
            dataType: 'string',
            value: '',
            op: '=',
            ops: [
              {
                value: '=',
                label: '等于'
              },
              {
                value: '!=',
                label: '不等于'
              },
              {
                value: 'like',
                label: '包含'
              }
            ]
          },
          {
            key: 'userAccount',
            title: '用户账号',
            placeholder: '请输入用户账号',
            dataType: 'string',
            value: '',
            op: '=',
            ops: [
              {
                value: '=',
                label: '等于'
              },
              {
                value: '!=',
                label: '不等于'
              },
              {
                value: 'like',
                label: '包含'
              }
            ]
          },
          {
            key: 'clientIpAddress',
            title: 'Ip地址',
            placeholder: '请输入id地址',
            dataType: 'string',
            value: '',
            op: '=',
            ops: [
              {
                value: '=',
                label: '等于'
              },
              {
                value: 'leftLike',
                label: '开头'
              },
              {
                value: 'rightlike',
                label: '结束'
              }
            ]
          },
          {
            key: 'success',
            title: '状态',
            placeholder: '请输入状态',
            dataType: 'select',
            value: '1',
            op: '=',
            ops: [
              {
                value: '1',
                label: '成功'
              },
              {
                value: '0',
                label: '失败'
              }
            ]
          }
        ],
        columns: [
          { title: '行号', key: '_rowIndex', width: 70, align: 'right' },
          { title: '登录时间', key: 'gmtCreate', width: 150 },
          { title: '用户名', key: 'userName', width: 140 },
          { title: '登录账号', key: 'userAccount', width: 140 },
          { title: '登录提供者', key: 'provider', width: 150 },
          { title: '登录提供者键', key: 'providerKey', width: 250 },
          {
            title: '状态',
            key: 'success',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.success ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          { title: '状态消息', key: 'statusMessage', width: 200 },
          { title: '客户端IP', key: 'clientIpAddress', width: 140 },
          { title: '客户端名称', key: 'clientName', width: 100 },
          { title: '客户端版本', key: 'clientVersion', width: 100 },
          { title: '浏览器平台', key: 'browserPlatform', width: 100 },
          { title: '浏览器名称', key: 'browserName', width: 150 },
          { title: '浏览器信息', key: 'browserInfo', minWidth: 400, ellipsis: true },
          {
            title: '操作',
            key: 'action',
            width: 95,
            fixed: 'right',
            align: 'center',
            render: (h, params) => {
              return h('div', [
                h(
                  'Button',
                  {
                    props: {
                      type: 'primary',
                      size: 'small',
                      icon: 'md-eye'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.onView(params.row)
                      }
                    }
                  },
                  '查看'
                )
              ])
            }
          }
        ]
      },
      formModal: {
        isShow: false,
        width: 800,
        title: '查看详情',
        tabName: 'main',
        formData: {}
      }
    }
  },
  methods: {
    onView (row) {
      this.formModal.tabName = 'main'
      this.formModal.formData = row
      this.formModal.isShow = true
    },
    deleteAll () {
      this.$Modal.confirm({
        title: '警告？',
        content: '<p>确定要清除所有日志吗？</p>',
        onOk: () => {
          deleteAll().then(result => {
            this.$Message.success('清除成功!')
            this.$refs.ptm.onRefresh()
          })
        },
        onCancel: () => {
        }
      })
    }
  },
  mounted () {

  }
}
</script>
<style>
.span-underline {
  text-decoration: underline;
  font-size: 14px;
}
</style>
