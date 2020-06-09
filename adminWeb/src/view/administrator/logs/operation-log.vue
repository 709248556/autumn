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
            <Row>
              <Col span="10">
              <FormItem label="操作时间："
                        prop="gmtCreate">
                <span class="span-underline">{{formModal.formData.gmtCreate}}</span>
              </FormItem>
              </Col>
              <Col span="10">
              <FormItem label="操作人："
                        prop="userName">
                <span class="span-underline">{{formModal.formData.userName}}</span>
              </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="10">
              <FormItem label="模块名称："
                        prop="moduleName">
                <span class="span-underline">{{formModal.formData.moduleName}}</span>
              </FormItem>
              </Col>
              <Col span="10">
              <FormItem label="操作名称："
                        prop="operationName">
                <span class="span-underline">{{formModal.formData.operationName}}</span>
              </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="7">
              <FormItem label="客户端IP："
                        prop="clientIpAddress">
                <span class="span-underline">{{formModal.formData.clientIpAddress}}</span>
              </FormItem>
              </Col>
              <Col span="7">
              <FormItem label="客户端名称："
                        prop="clientName">
                <span class="span-underline">{{formModal.formData.clientName}}</span>
              </FormItem>
              </Col>
              <Col span="7">
              <FormItem label="客户端版本："
                        prop="clientVersion">
                <span class="span-underline">{{formModal.formData.clientVersion}}</span>
              </FormItem>
              </Col>
            </Row>
            <Row>
              <Col span="10">
              <FormItem label="浏览器平台："
                        prop="browserPlatform">
                <span class="span-underline">{{formModal.formData.browserPlatform}}</span>
              </FormItem>
              </Col>
              <Col span="10">
              <FormItem label="浏览器名称："
                        prop="browserName">
                <span class="span-underline">{{formModal.formData.browserName}}</span>
              </FormItem>
              </Col>
            </Row>
            <FormItem label="浏览器信息："
                      prop="browserInfo">
              <span class="span-underline">{{formModal.formData.browserInfo}}</span>
            </FormItem>
          </Form>
        </TabPane>
        <TabPane label="操作日志"
                 icon="ios-apps-outline"
                 name="else">
          <Form :model="formModal.formData"
                :label-width="100">
            <span class="span-underline">{{formModal.formData.logDetails}}</span>
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
import { deleteAll, downloadByExcelInfo, queryByPageList } from '@/api/administrator/logs/operation-log'
export default {
  name: 'sys_log_operation',
  components: {
    PageTableManager
  },
  data () {
    return {
      dataMeta: {
        title: '操作日志管理',
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
            placeholder: '输入精确用户名称',
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
            key: 'moduleName',
            title: '模块名称',
            placeholder: '请输入模块名称',
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
            key: 'operationName',
            title: '操作名称',
            placeholder: '请输入操作名称',
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
            key: 'operationName',
            title: 'Ip地址',
            placeholder: '请输入id地址',
            dataType: 'string',
            value: '',
            op: 1,
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
          }
        ],
        columns: [
          { title: '行号', key: '_rowIndex', width: 70, align: 'right' },
          { title: '操作时间', key: 'gmtCreate', width: 150 },
          { title: '模块名称', key: 'moduleName', width: 140 },
          { title: '操作名称', key: 'operationName', width: 150 },
          { title: '操作人', key: 'userName', width: 100 },
          { title: '操作详情', key: 'logDetails', width: 400, ellipsis: true },
          { title: '客户端IP', key: 'clientIpAddress', width: 140 },
          { title: '客户端名称', key: 'clientName', width: 100 },
          { title: '客户端版本', key: 'clientVersion', width: 100 },
          { title: '浏览器平台', key: 'browserPlatform', width: 100 },
          { title: '浏览器名称', key: 'browserName', width: 150 },
          { title: '浏览器信息', key: 'browserInfo', minWidth: 400 },
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
        title: '清除所有日志？',
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
