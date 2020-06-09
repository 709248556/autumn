<template>
  <Card>
    <PageTableManager ref="ptm"
                      :dataMeta="this.dataMeta" />
  </Card>
</template>
<script>
import PageTableManager from '@/components/autumn/page-table-manager'
import Action from '_c/action'
import {
  queryForPage,
  deleteById
} from '@/api/administrator/sys/business-agreement'
export default {
  name: 'business_agreement',
  components: {
    Action,
    PageTableManager
  },
  data () {
    return {
      dataMeta: {
        title: '用户协议管理',
        queryPageApi: queryForPage,
        downloadByExcelInfoApi: null,
        pageSize: 20,
        opButtons: [
          {
            type: 'primary',
            text: '添加',
            icon: 'md-add',
            click: () => {
              this.onCreate()
            }
          }
        ],
        fastFilter: [],
        advancedFilter: [],
        columns: [
          { title: '行号', key: '_rowIndex', width: 70, align: 'right' },
          { title: '协议名称', key: 'name', minWidth: 200 },
          { title: '协议类型', key: 'agreementTypeName', width: 120 },
          { title: '排序', key: 'sortId', width: 80 },
          {
            title: '默认',
            key: 'defaultAgreement',
            width: 90,
            render: (h, params) => {
              return h('div', [
                h(
                  'Icon',
                  {
                    props: {
                      size: 20,
                      type: params.row.defaultAgreement
                        ? 'md-checkmark'
                        : 'md-remove'
                    }
                  },
                  ''
                )
              ])
            }
          },
          {
            title: '状态',
            key: 'status',
            width: 90,
            render: (h, params) => {
              return h('div', [
                h(
                  'Icon',
                  {
                    props: {
                      size: 20,
                      type:
                        params.row.status === 1 ? 'md-checkmark' : 'md-remove'
                    }
                  },
                  ''
                )
              ])
            }
          },
          { title: '备注', key: 'summary', minWidth: 250 },
          {
            title: '操作',
            key: 'action',
            width: 170,
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
                      icon: 'md-create'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.onUpdate(params.row)
                      }
                    }
                  },
                  '修改'
                ),
                h(
                  'Button',
                  {
                    props: {
                      type: 'primary',
                      size: 'small',
                      icon: 'md-trash'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.onDelete(params.row)
                      }
                    }
                  },
                  '删除'
                )
              ])
            }
          }
        ]
      }
    }
  },
  methods: {
    onRefresh () {
      this.$refs.ptm.onRefresh()
    },
    onCreate () {
      this.$router.push({
        name: 'business_agreement_edit',
        query: {
          sortId: this.$refs.ptm.getRowTotal() + 1
        }
      })
    },
    onUpdate (row) {
      this.$router.push({
        name: 'business_agreement_edit',
        query: {
          isUpdate: true,
          id: row.id
        }
      })
    },
    // 删除
    onDelete (row) {
      let input = { id: row.id }
      this.$Modal.confirm({
        title: '删除提示',
        content: `<p>确定要删除当前所选的协议吗？ </p>`,
        onOk: () => {
          deleteById(input).then(result => {
            this.$Message.success('删除成功!')
            this.onRefresh()
          })
        },
        onCancel: () => { }
      })
    }
  },
  mounted () {
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      if (vm.$route.query.refresh) {
        vm.onRefresh()
      }
    })
  }
}
</script>
