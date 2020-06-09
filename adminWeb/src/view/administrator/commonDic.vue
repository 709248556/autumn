<template>
  <Card style="height: 100%">
    <Modal v-model="formModal.modalShow"
           class-name="vertical-center-modal"
           :mask-closable="false"
           width="700"
           :title="formModal.modalTitle">
      <Tabs value="baseInfo">
        <TabPane label="基本信息"
                 icon="ios-home"
                 name="baseInfo">
          <Form :ref="formModal.formName"
                :model="formModal.formData"
                :rules="formModal.ruleValidate"
                :label-width="80">
            <FormItem label="名称"
                      prop="name">
              <Input v-model="formModal.formData.name"
                     :maxlength="50"
                     placeholder="请输入名称" />
            </FormItem>
            <FormItem label="排序"
                      prop="sortId">
              <InputNumber :min="1"
                           v-model="formModal.formData.sortId"
                           style="width:260px"></InputNumber>
            </FormItem>
            <FormItem label="状态">
              <i-switch v-model="dicStatus"
                        size="large">
                <span slot="open">启用</span>
                <span slot="close">禁用</span>
              </i-switch>
            </FormItem>
            <FormItem label="备注"
                      prop="remarks">
              <Input v-model="formModal.formData.remarks"
                     type="textarea"
                     :autosize="{minRows: 2,maxRows: 5}" />
            </FormItem>
          </Form>
        </TabPane>
        <TabPane label="附件信息"
                 icon="md-document"
                 name="images">
          <FileUploadTable :uploadFileUrl="uploadFilesUrl" :uploadFiles="formModal.formData.uploadFiles" accept=""/>
        </TabPane>
        <TabPane label="审计信息"
                 icon="ios-clock"
                 name="audInfo">
          <Form :ref="formModal.formName"
                :model="formModal.formData"
                :label-width="100">
            <FormItem label="创建人"
                      prop="createdUserName">
              <Input v-model="formModal.formData.createdUserName"
                     readonly />
            </FormItem>
            <FormItem label="创建时间"
                      prop="gmtCreate">
              <Input v-model="formModal.formData.gmtCreate"
                     readonly />
            </FormItem>
            <FormItem label="最后修改人"
                      prop="modifiedUserName">
              <Input v-model="formModal.formData.modifiedUserName"
                     readonly />
            </FormItem>
            <FormItem label="最后修改时间"
                      prop="gmtModified">
              <Input v-model="formModal.formData.gmtModified"
                     readonly />
            </FormItem>
          </Form>
        </TabPane>
      </Tabs>
      <div slot="footer">
        <Button type="text"
                @click="resetForm()"
                style="margin-left: 8px">取消</Button>
        <Button type="primary"
                @click="save()">保存</Button>
      </div>
    </Modal>
    <Layout style="height: 100%; background: white;">
      <Sider width="200"
             breakpoint="md"
             :style="{height: menuHeight + 'px', overflow: 'auto'}">
        <Menu theme="light"
              ref="asideMenu"
              :active-name="activeDictionaryType"
              width="200"
              style="background: white;"
              @on-select="onSelect">
          <MenuGroup title="字典类型"
                     style="font-weight: bold;background: white;">
            <MenuItem v-for="item in menuDic"
                      :key="item.value"
                      style="font-weight: 500;background: white;"
                      :name="item.value"> {{item.name}}
            </MenuItem>
          </MenuGroup>
        </Menu>
      </Sider>
      <Content class="dic-content">
        <div class="card-title">{{activeDictionaryTypeName}}</div>
        <Divider />
        <div class="btn-group">
          <Button type="primary"
                  icon="md-add"
                  @click="addHandle">添加</Button>
          <Button icon="md-download"
                  style="margin-left:5px;"
                  @click="download()">导出</Button>
        </div>
        <page-table :columns="tableMeta.columns"
                    :data="tableMeta.data"
                    highlight-row
                    :total="tableMeta.total"
                    :pageSize="tableMeta.pageSize"
                    :current="tableMeta.page"
                    :showLoadding="tableMeta.loadding"
                    @on-search="onSearch"
                    @on-page-change="onPageChange">
          <div slot="opration"
               class="opration">
            状态：
            <Select v-model="selectStatus"
                    @on-change="changeHandle"
                    style="width:200px">
              <Option :value="Number(0)"
                      key="0">所有</Option>
              <Option :value="Number(1)"
                      key="1">启用</Option>
              <Option :value="Number(2)"
                      key="2">停用</Option>
            </Select>
          </div>
        </page-table>
      </Content>
    </Layout>
  </Card>
</template>
<style lang='less' scoped>
.dic-content {
  padding: 0 20px;
  background: white;
  .btn-group {
    margin-bottom: 20px;
  }
  .opration {
    float: left;
  }
}
</style>

<script>
import PageTable from '_c/page-table'
import FileUploadTable from '@/components/autumn/file-upload-table'
import {
  addDic,
  deleteById,
  downloadByExcelInfo,
  queryByDictionaryTypeList,
  queryByPageList,
  updateDic,
  queryById,
  updateFile
} from '@/api/administrator/commonDic'
export default {
  name: 'commonDic',
  components: {
    PageTable,
    FileUploadTable
  },
  data () {
    return {
      menuHeight: 0,
      menuDic: [],
      uploadFilesUrl: '',
      activeDictionaryType: 1,
      activeDictionaryTypeName: '',
      formModal: {
        formName: 'dicForm',
        modalShow: false,
        modalTitle: '添加',
        parentIds: [],
        isUpdate: false,
        formData: {
          dictionaryType: 0,
          id: 0,
          name: '',
          remarks: '',
          sortId: 1,
          status: 1,
          createdUserName: '',
          gmtCreate: null,
          modifiedUserName: '',
          gmtModified: null,
          uploadFileIds: [],
          uploadFiles: []
        },
        ruleValidate: {
          name: [{
            required: true,
            message: '名称必填',
            trigger: 'blur'
          }]
        }
      },
      selectStatus: 0,
      tableMeta: {
        columns: [
          { title: '名称', key: 'name', width: 350, minWidth: 200 },
          { title: '顺序', key: 'sortId', width: 80 },
          {
            title: '系统项',
            key: 'sysDictionary',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.sysDictionary ? 'md-checkmark' : 'md-remove'
                  }
                }, '')
              ])
            }
          },
          {
            title: '状态',
            key: 'status',
            width: 80,
            render: (h, params) => {
              return h('div', [
                h('Icon', {
                  props: {
                    size: 20,
                    type: params.row.status === 1 ? 'md-checkmark' : 'md-close'
                  }
                }, '')
              ])
            }
          },
          { title: '创建时间', key: 'gmtCreate', width: 160 },
          { title: '备注', key: 'remarks' },
          {
            title: '操作',
            key: 'action',
            fixed: 'right',
            align: 'center',
            width: 165,
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
                      this.modifyHandle(params)
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
                      this.deleteDic(params)
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
        pageSize: 20,
        loadding: false
      }
    }
  },
  computed: {
    dicStatus: {
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
    onSelect (value) {
      this.activeDictionaryType = value
      let items = this.menuDic
      for (var i = 0; i < items.length; i++) {
        let item = items[i]
        if (item.value === value) {
          this.activeDictionaryTypeName = item.name
          break
        }
      }
      this.tableMeta.page = 1
      this.loadPageData()
    },
    resetForm () {
      this.$refs[this.formModal.formName].resetFields()
      this.formModal.formData = {}
      this.formModal.modalShow = false
      this.formModal.formData.remarks = ''
      this.formModal.formData.sortId = this.tableMeta.total + 1
      this.formModal.formData.status = 1
      this.formModal.formData.uploadFiles = []
      this.formModal.formData.uploadFileIds = []
    },
    changeHandle (value) {
      this.loadPageData()
    },
    addHandle () {
      this.formModal.isUpdate = false
      this.resetForm()
      this.formModal.formData.dictionaryType = this.activeDictionaryType
      this.formModal.modalTitle = '添加 ' + this.activeDictionaryTypeName
      this.formModal.modalShow = true
    },
    modifyHandle (params) {
      queryById(
        params.row.id
      ).then(res => {
        this.formModal.isUpdate = true
        this.formModal.formData = Object.assign({}, res)
        this.formModal.modalTitle = '修改 ' + this.activeDictionaryTypeName
        this.formModal.modalShow = true
      })
    },
    deleteDic (param) {
      this.$Modal.confirm({
        title: '警告？',
        content: '<p>确定要删除当前选中' + this.activeDictionaryTypeName + '吗？</p>',
        onOk: () => {
          deleteById(param.row.id).then(result => {
            this.$Message.success('清除成功!')
            this.loadPageData()
          })
        },
        onCancel: () => {
        }
      })
    },
    save () {
      this.$refs[this.formModal.formName].validate((valid) => {
        if (valid) {
          let input = Object.assign({}, this.formModal.formData)
          input.uploadFileIds = input.uploadFiles.map(item => {
            return item.id
          })
          if (this.formModal.isUpdate) {
            updateDic(input).then(result => {
              this.$Message.success('保存成功!')
              this.resetForm()
              this.loadPageData()
            })
          } else {
            addDic(input).then(result => {
              this.$Message.success('添加成功!')
              this.resetForm()
              this.loadPageData()
            })
          }
        } else {
          this.$Message.error('请检查必填项后继续!')
          this.formModal.isUpdate = false
        }
      })
    },
    buildData () {
      let rules = []
      if (this.selectStatus !== 0) {
        rules.push({
          expression: 'status',
          op: '=',
          value: this.selectStatus
        })
      }
      return rules
    },
    loadPageData (keyword) {
      let q = {
        currentPage: this.tableMeta.page,
        pageSize: this.tableMeta.pageSize
      }
      if (keyword) {
        q.searchKeyword = keyword
      }
      let rules = this.buildData()
      if (rules.length) {
        q.criterias = rules
      }
      q.dictionaryType = this.activeDictionaryType
      this.tableMeta.loadding = true
      queryByPageList(q).then(result => {
        this.tableMeta.data = result.items
        this.tableMeta.total = result.rowTotal
        this.tableMeta.pageSize = result.pageSize
        this.tableMeta.page = result.currentPage
        this.tableMeta.loadding = false
      })
    },
    onSearch (keyword) {
      if (keyword) {
        this.loadPageData(keyword)
      } else {
        this.loadPageData()
      }
    },
    onPageChange (page) {
      this.tableMeta.page = page
      this.loadPageData()
    },
    download (keyword) {
      let q = {
      }
      if (keyword) {
        q.searchKeyword = keyword
      }
      let rules = this.buildData()
      if (rules.length) {
        q.criterias = rules
      }
      q.dictionaryType = this.activeDictionaryType
      downloadByExcelInfo(q).then(result => {
        this.$Message.success('生成Excel成功，正在下载...')
        window.location.href = result.accessUrlPath
      })
    }
  },
  mounted () {
    this.menuHeight = window.innerHeight - 175
    this.uploadFilesUrl = updateFile()
    queryByDictionaryTypeList().then(res => {
      this.menuDic = res
      if (res.length > 0) {
        this.activeDictionaryType = res[0].value
        this.activeDictionaryTypeName = res[0].name
        this.tableMeta.page = 1
        this.loadPageData()
        this.$nextTick(() => {
          this.$refs.asideMenu.updateActiveName()
        })
      }
    })
  }
}
</script>
