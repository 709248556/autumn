<template>
  <Card>
    <div class="card-title">帮助文档管理</div>
    <Divider />
    <div style="height:40px">
      <Button type="primary"
              icon="md-add"
              @click="onCreateFile()">创建文件</Button>
      <Button icon="md-add"
              style="margin:0 10px;"
              @click="onCreateFolder()">新建文件夹</Button>
      <ButtonGroup>
        <Button v-if="showUnSelectAll"
                @click="handleSelectAll(false)">取消选择</Button>
        <Button v-if="showToolbar"
                icon='md-fastforward'
                @click="onToolbarGenerateHtml()">生成文档</Button>
        <Button v-if="showToolbar"
                icon='md-create'
                @click="onToolbarEdit()">编辑</Button>
        <Button v-if="showToolbar"
                icon='md-trash'
                @click="onToolbarDelete()">删除</Button>

      </ButtonGroup>
    </div>
    <Modal title="创建文件夹"
           width="700"
           class-name="vertical-center-modal"
           v-model="formModal.show"
           :footer-hide="true">
      <Form :ref="formModal.formName"
            :model="formModal.formData"
            :rules="formModal.ruleValidate"
            :label-width="100">
        <FormItem label="文件夹名称"
                  prop="name">
          <Input v-model="formModal.formData.name"
                 placeholder="请输入文件夹名称"
                 style="width:260px"></Input>
        </FormItem>
        <FormItem label="友好名字"
                  prop="friendlyName">
          <Input v-model="formModal.formData.friendlyName"
                 placeholder="请输入友好名字"
                 style="width:260px"></Input>
        </FormItem>
        <FormItem label="状态"
                  prop="title">
          <RadioGroup v-model="formModal.formData.status">
            <Radio label="1">启用</Radio>
            <Radio label="2">禁用</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="排序"
                  prop="sortId">
          <InputNumber v-model="formModal.formData.sortId"
                       placeholder="请输入排序"
                       :min="1"></InputNumber>
        </FormItem>
        <FormItem label="备注"
                  prop="remarks">
          <Input v-model="formModal.formData.remarks"
                 placeholder="请输入备注"
                 type="textarea"
                 style="width:260px"></Input>
        </FormItem>
        <FormItem>
          <Button type="primary"
                  @click="onSaveFolder()">保存</Button>
          <Button @click="onCancelForm()"
                  style="margin-left: 8px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <div style="margin-top:10px">
      <Breadcrumb separator="/"
                  style="margin:10px 0">
        <BreadcrumbItem v-for="item in breadcrumb"
                        :key="item.id">
          <router-link @click.native="onNative(item)"
                       to="">{{item.name}}</router-link>
        </BreadcrumbItem>
      </Breadcrumb>
      <Table ref="selection"
             border
             highlight-row
             :columns="columns"
             :data="dataItems"
             @on-select="onSelect"
             @on-select-cancel="onSelectCancel"
             @on-select-all="onSelectAll"></Table>
    </div>
  </Card>
</template>
<script>
import Action from '_c/action'
import { queryChildren, addDirectory, updateDirectory, queryById, deleteById, generateHtml, updateStatus } from '@/api/administrator/sys/help-center'
export default {
  name: 'help_conter',
  components: {
    Action
  },
  data () {
    return {
      showUnSelectAll: false,
      showToolbar: false,
      lastCurrentItem: null,
      currentItem: null,
      breadcrumb: [],
      dataItems: [],
      sourceItems: [],
      selection: [],
      columns: [
        {
          type: 'selection',
          width: 50,
          align: 'center'
        },
        {
          title: '文件名',
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
                  type: params.row.isReturnParent ? 'ios-undo' : (params.row.directory ? 'md-folder' : 'ios-document-outline'),
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
                attrs: {
                  dataId: params.row.id
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
        {
          title: '友好名称',
          minWidth: 160,
          key: 'friendlyName'
        },
        {
          title: '文件编号',
          minWidth: 150,
          key: 'id'
        },
        {
          title: '子级数量',
          minWidth: 90,
          key: 'childrenCount'
        },
        {
          title: '状态',
          minWidth: 60,
          key: 'statusName'
        },
        {
          title: '访问路径',
          minWidth: 300,
          key: 'accessPath'
        },
        {
          title: '大小',
          minWidth: 80,
          key: 'fileFriendlyLength'
        },
        {
          title: '修改日期',
          minWidth: 150,
          key: 'gmtCreate'
        },
        {
          title: '操作',
          key: 'action',
          align: 'center',
          minWidth: 160,
          fixed: 'right',
          render: (h, params) => {
            let buttons = []
            if (!params.row.directory) {
              // 查看html
              if (params.row.fileLength > 0) {
                buttons.push({ key: 'viewHtml', name: '查看文档', icon: 'md-eye' })
              }
              buttons.push({ key: 'generateHtml', name: '生成文档', icon: 'md-fastforward' })
              buttons.push({ key: 'updateStatus', name: params.row.status === 1 ? '禁用' : '启用', icon: params.row.status ? 'md-pause' : 'md-play' })
            }
            if (params.row.name !== '返回上级') {
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
                      if (params.row.directory) {
                        this.onUpdateFolder(params.row)
                      } else {
                        this.onUpdate(params.row)
                      }
                    }
                  }
                }, '编辑'),
                h(Action, {
                  props: {
                    value: [
                      { key: 'delete', name: '删除', icon: 'md-trash' },
                      ...buttons
                    ]
                  },
                  on: {
                    click: (key) => {
                      switch (key) {
                        case 'delete':
                          this.onDelete(params.row)
                          break
                        case 'viewHtml':
                          window.open(params.row.accessPath, '_blank')
                          break
                        case 'generateHtml':
                          this.onGenerateHtml(params.row)
                          break
                        case 'updateStatus':
                          this.onUpdateStatus(params.row)
                          break
                      }
                    }
                  }
                })
              ])
            }
          }
        }
      ],
      data: [],
      formModal: {
        show: false,
        isUpdate: false,
        formName: 'CreateFolder',
        formData: {
          id: 0,
          name: '',
          friendlyName: '',
          parentId: null,
          remarks: '',
          sortId: 1,
          status: '1'
        },
        ruleValidate: {
          name: [
            { required: true, message: '文件夹名称必填', trigger: 'blur' }
          ],
          friendlyName: [
            { required: true, message: '友好名称必填', trigger: 'blur' }
          ]
        }
      }
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
        if (params.row.directory) {
          // 文件夹 打开
          this.currentItem = params.row
          this.onLoadData()
        } else {
          // 文件 打开编辑页面
          this.$router.push({
            name: 'help_center_edit',
            query: {
              isUpdate: true,
              id: params.row.id,
              parentId: params.row.id
            }
          })
        }
      }
    },
    onLoadData () {
      this.showUnSelectAll = false
      this.showToolbar = false
      let input = {
        parentId: this.currentItem ? this.currentItem.id : null,
        status: null
      }
      let _this = this
      queryChildren(input).then(res => {
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
      })
    },
    handleSelectAll (status) {
      this.$refs.selection.selectAll(status)
      this.showUnSelectAll = false
    },
    onSelect (selection, row) {
      if (row.name === '返回上级') {
        return
      }
      this.showToolbar = selection.length === 1
      this.showUnSelectAll = selection.length > 1
      this.selection = selection
    },
    onSelectCancel (selection, row) {
      this.showToolbar = selection.length === 1
      this.showUnSelectAll = selection.length > 1
      this.selection = selection
    },
    onSelectAll (selection) {
      this.showUnSelectAll = true
      this.selection = selection
    },
    onToolbarEdit () {
      if (this.selection.length !== 1) {
        return
      }
      let row = this.selection[0]
      if (row.directory) {
        this.onUpdateFolder(row)
      } else {
        this.onUpdate(row)
      }
    },
    onToolbarDelete () {
      if (this.selection.length !== 1) {
        return
      }
      let row = this.selection[0]
      this.onDelete(row)
    },
    onToolbarGenerateHtml () {
      if (this.selection.length !== 1) {
        return
      }
      let row = this.selection[0]
      this.onGenerateHtml(row)
    },
    // 文件夹
    onCreateFolder () {
      this.formModal.isUpdate = false
      this.formModal.show = true
      this.formModal.formData.sortId = this.sourceItems.length + 1
    },
    onUpdateFolder (row) {
      this.formModal.isUpdate = true
      queryById({
        id: row.id
      }).then(res => {
        this.formModal.formData = res
        this.formModal.formData.status = res.status + ''
        this.formModal.show = true
      })
    },
    onSaveFolder () {
      this.$refs[this.formModal.formName].validate((valid) => {
        if (!valid) {
          this.$Message.error('请检查表单项!')
        }
        let input = Object.assign({}, this.formModal.formData)
        if (this.currentItem) {
          input.parentId = this.currentItem.id
        }
        if (this.formModal.isUpdate) {
          updateDirectory(input).then(res => {
            this.$Message.success('更新成功!')
            this.onLoadData()
            this.onCancelForm()
          })
        } else {
          addDirectory(input).then(res => {
            this.$Message.success('添加成功!')
            this.onLoadData()
            this.onCancelForm()
          })
        }
      })
    },
    onCancelForm () {
      this.$refs[this.formModal.formName].resetFields()
      this.formModal.show = false
    },
    // 删除
    onDelete (row) {
      let input = { id: row.id }
      this.$Modal.confirm({
        title: '删除提示',
        content: `<p>${row.directory ? '确定要删除当前目录吗？' : '确定要删除当前文件吗？'} </p>`,
        onOk: () => {
          deleteById(input).then(result => {
            this.$Message.success('删除成功!')
            this.onLoadData()
          })
        },
        onCancel: () => { }
      })
    },
    onUpdateStatus (row) {
      let input = {
        id: row.id,
        status: row.status === 1 ? 2 : 1
      }
      let text = row.status === 1 ? '禁用' : '启用'
      this.$Modal.confirm({
        title: '提示信息',
        content: `<p>确定要${text}当前信息吗？</p>`,
        onOk: () => {
          updateStatus(input).then(result => {
            this.$Message.success(`${text}成功!`)
            this.onLoadData()
          })
        },
        onCancel: () => { }
      })
    },
    onCreateFile () {
      this.$router.push({
        name: 'help_center_edit',
        query: {
          parentId: this.currentItem ? this.currentItem.id : null,
          sortId: this.sourceItems.length + 1
        }
      })
    },
    onGenerateHtml (row) {
      this.$Modal.confirm({
        title: '提示消息',
        content: `<p>${row.directory ? '确定要生成当前目录吗？' : '确定要生成当前文件吗？'} </p>`,
        onOk: () => {
          let input = {
            id: row.id,
            resetGenerate: true
          }
          generateHtml(input).then(result => {
            this.$Message.success('生成成功!')
            this.onLoadData()
          })
        },
        onCancel: () => { }
      })
    },
    onUpdate (row) {
      this.$router.push({
        name: 'help_center_edit',
        query: {
          isUpdate: true,
          id: row.id,
          parentId: row.id
        }
      })
    }
  },
  mounted () {
    this.onLoadData()
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      if (vm.$route.query.refresh) {
        vm.onLoadData()
      }
    })
  }
}
</script>
