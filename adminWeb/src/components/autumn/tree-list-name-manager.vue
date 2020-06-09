<template>
  <Card>
    <Modal :title="formModal.title"
           v-model="formModal.show"
           class-name="vertical-center-modal"
           :mask-closable="false"
           width="600"
           :footer-hide="true"
           :closable="false">
      <Form :ref="formModal.formName"
            :model="formModal.formData"
            :rules="formModal.ruleValidate"
            :label-width="130">
        <Tabs v-model="formModal.selectTabValue">
          <TabPane label="基本信息"
                   icon="md-information-circle"
                   name="default">
            <FormItem :label="this.managerName + '名称：'"
                      prop="name">
              <Input v-model="formModal.formData.name"
                     placeholder="请输入名称" />
            </FormItem>
            <FormItem label="排序："
                      prop="sortId">
              <InputNumber :min="1"
                           v-model="formModal.formData.sortId"
                           style="width:150px"></InputNumber>
            </FormItem>
            <FormItem label="状态：">
              <i-switch v-model="treeStatus"
                        size="large">
                <span slot="open">启用</span>
                <span slot="close">禁用</span>
              </i-switch>
            </FormItem>
            <FormItem label="备注："
                      prop="remarks">
              <Input v-model="formModal.formData.remarks"
                     type="textarea"
                     placeholder="请输入备注信息" />
            </FormItem>
          </TabPane>
          <TabPane label="审计信息"
                   icon="ios-clock"
                   name="audInfo">
            <Form :ref="formModal.formName"
                  :model="formModal.formData"
                  :label-width="100">
              <FormItem label="创建人："
                        prop="createdUserName">
                <Input v-model="formModal.formData.createdUserName"
                       readonly />
              </FormItem>
              <FormItem label="创建时间："
                        prop="gmtCreate">
                <Input v-model="formModal.formData.gmtCreate"
                       readonly />
              </FormItem>
              <FormItem label="最后修改人："
                        prop="modifiedUserName">
                <Input v-model="formModal.formData.modifiedUserName"
                       readonly />
              </FormItem>
              <FormItem label="最后修改时间："
                        prop="gmtModified">
                <Input v-model="formModal.formData.gmtModified"
                       readonly />
              </FormItem>
            </Form>
          </TabPane>
        </Tabs>
        <FormItem>
          <Button type="primary"
                  icon="md-checkmark-circle"
                  @click="save()">保存</Button>
          <Button @click="resetForm()"
                  icon="md-close"
                  style="margin-left: 10px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <div class="card-title">{{this.managerName}}管理</div>
    <Divider />
    <Button type="primary"
            icon="md-add"
            @click="onCreate()">添加</Button>
    <Button type="info"
            icon="md-refresh"
            @click="onRefresh()"
            style="margin-left: 10px">刷新</Button>
    <Button v-if="importInfo!==null" icon="md-cloud-upload"
            type='primary'
            @click="onImport()"
            style="margin-left: 10px">Excel导入</Button>
    <Button icon="ios-download-outline"
            @click="onExport()"
            style="margin-left: 10px">导出</Button>
    <TreeList ref="treeList"
              :titleName="this.managerName + '名称'"
              :queryChildrenApi="this.queryChildrenApi"
              :columns="treeColumns"
              :tableButtons="this.tableButtons"
              @on-parent-change="this.onParentChange">
    </TreeList>
    <ExcelImport ref="import" v-if="importInfo!==null"/>
  </Card>
</template>
<script>
import Action from '_c/action'
import TreeList from '@/components/autumn/tree-list'
import ExcelImport from '@/components/autumn/excel-import-manager'
export default {
  name: 'tree-list-name-manager',
  components: {
    Action,
    TreeList,
    ExcelImport
  },
  props: {
    managerName: {
      type: String,
      default: ''
    },
    queryChildrenApi: {
      type: Function
    },
    queryByIdApi: {
      type: Function
    },
    addApi: {
      type: Function
    },
    updateApi: {
      type: Function
    },
    deleteApi: {
      type: Function
    },
    downloadByExcelInfoApi: {
      type: Function
    },
    importInfo: {
      type: Object,
      default: null
      // 对象结构
      // {
      //    uploadFileUrl, //上传文件Url
      //    downloadTemplateApi, // 下载模板Api
      //    importTitle, // 导入标题
      //    refreshApi 刷新回调
      // }
    }
  },
  data () {
    return {
      currentParentItem: null,
      treeColumns: [
        { title: '完整名称', key: 'fullName', minWidth: 160 },
        { title: '排序', key: 'sortId', minWidth: 80 },
        { title: '级别', key: 'level', minWidth: 80 },
        {
          title: '状态',
          key: 'status',
          width: 80,
          render: (h, params) => {
            return h('div', [
              h('Icon', {
                props: {
                  size: 20,
                  type: params.row.status ? (params.row.status === 1 ? 'md-checkmark' : 'md-close') : ''
                }
              }, '')
            ])
          }
        },
        { title: '备注', key: 'remarks', minWidth: 160 },
        { title: '创建人', key: 'createdUserName', width: 100 },
        { title: '创建时间', key: 'gmtCreate', width: 155 },
        { title: '修改人', key: 'modifiedUserName', width: 100 },
        { title: '修改时间', key: 'gmtModified', width: 155 }
      ],
      formModal: {
        selectTabValue: 'default',
        show: false,
        isUpdate: false,
        formName: 'academicExchange',
        title: '添加' + this.managerName,
        formData: {
          id: null,
          name: '',
          sortId: 1,
          status: 1,
          parentId: null,
          remark: ''
        },
        ruleValidate: {
          name: [{
            required: true,
            message: '名称必填',
            trigger: 'blur'
          }]
        }
      }
    }
  },
  computed: {
    treeStatus: {
      get () {
        let status = this.formModal.formData.status === 1
        return status
      },
      set (newVal) {
        if (newVal) {
          this.formModal.formData.status = 1
        } else {
          this.formModal.formData.status = 2
        }
      }
    }
  },
  methods: {
    onParentChange (item) {
      this.currentParentItem = item
    },
    tableButtons (h, params) {
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
                this.onUpdate(params)
              }
            }
          },
          '修改'
        ),
        h(
          'Button',
          {
            props: {
              type: 'error',
              size: 'small',
              icon: 'md-trash'
            },
            on: {
              click: () => {
                this.onDelete(params)
              }
            }
          },
          '删除'
        )
      ])
    },
    onRefresh () {
      this.$refs.treeList.onLoadData()
    },
    onCreate () {
      this.formModal.isUpdate = false
      this.formModal.show = true
      this.formModal.title = '添加' + this.managerName
      this.formModal.selectTabValue = 'default'
      this.formModal.formData = {}
      this.formModal.formData.sortId = this.$refs.treeList.getDataItemLength() + 1
      this.formModal.formData.status = 1
    },
    onImport () {
      if (this.importInfo != null) {
        this.$refs.import.openImport(
          this.importInfo.uploadFileUrl,
          this.importInfo.downloadTemplateApi,
          this.importInfo.importTitle,
          this.importInfo.refreshApi)
      }
    },
    onExport () {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>确定要导出数据到Excel文件吗？</p>',
        onOk: () => {
          this.$Message.success('正在生成Excel文件，请稍候...')
          this.downloadByExcelInfoApi({}).then(result => {
            this.$Message.success('生成Excel成功，正在下载...')
            window.location.href = result.accessUrlPath
          })
        },
        onCancel: () => { }
      })
    },
    onDelete (params) {
      this.$Modal.confirm({
        title: '删除提示',
        content: '<p>确定要删除吗？</p>',
        onOk: () => {
          this.deleteApi({ id: params.row.id }).then(result => {
            this.$Message.success('删除成功!')
            this.onRefresh()
          })
        },
        onCancel: () => { }
      })
    },
    onUpdate (params) {
      this.formModal.title = '修改' + this.managerName
      this.formModal.selectTabValue = 'default'
      this.formModal.isUpdate = true
      this.queryByIdApi({ id: params.row.id }).then(res => {
        this.formModal.formData = res
        this.formModal.show = true
      })
    },
    save () {
      let that = this
      that.$refs[that.formModal.formName].validate(valid => {
        if (valid) {
          let input = that.formModal.formData
          if (that.formModal.isUpdate) {
            this.updateApi(input).then(result => {
              that.$Message.success('修改成功!')
              that.resetForm()
              that.onRefresh()
            })
          } else {
            if (that.currentParentItem) {
              input.parentId = that.currentParentItem.id
            } else {
              input.parentId = null
            }
            this.addApi(input).then(result => {
              that.$Message.success('添加成功!')
              that.resetForm()
              that.onRefresh()
            })
          }
        } else {
          that.$Message.error('请检查必输项后继续!')
          that.formModal.show = true
          that.formModal.isUpdate = false
        }
      })
    },
    resetForm () {
      this.formModal.show = false
      this.$refs[this.formModal.formName].resetFields()
    }
  },
  mounted () {

  }
}
</script>
