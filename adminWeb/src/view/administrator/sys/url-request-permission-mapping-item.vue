<template>
  <Table border
             :columns="columns"
             :data="this.mappings"></Table>
</template>
<script>
export default {
  name: 'url_request_permission_mapping_item',
  props: {
    mappings: Array
  },
  data () {
    return {
      columns: [
        {
          title: '序号',
          key: 'sortId',
          width: 80,
          sortable: true
        },
        {
          title: 'Api地址',
          key: 'url',
          minWidth: 200,
          sortable: true
        },
        {
          title: 'Api操作',
          key: 'operation',
          minWidth: 120,
          sortable: true
        },
        {
          title: '请求方法',
          key: 'methods',
          minWidth: 90,
          sortable: true
        },
        {
          title: '权限访问',
          key: 'permissionVisit',
          width: 105,
          sortable: true,
          render: (h, params) => {
            return h('div', [
              h('Icon', {
                props: {
                  size: 20,
                  type: params.row.permissionVisit ? 'md-checkmark' : 'md-remove'
                }
              }, '')
            ])
          }
        },
        {
          title: '权限要求',
          key: 'permissionVisitExplain',
          minWidth: 90,
          sortable: true
        },
        {
          title: '访问权限',
          key: 'permissionExplains',
          minWidth: 120,
          render: (h, params) => {
            params.row.permissionExplains = params.row.permissionExplains.replace(';', '\n')
            return h('pre', params.row.permissionExplains)
          }
        },
        {
          title: '权限模块',
          key: 'permissionVisitModuleNames',
          minWidth: 120,
          render: (h, params) => {
            params.row.permissionVisitModuleNames = params.row.permissionVisitModuleNames.replace(';', '\n')
            return h('pre', params.row.permissionVisitModuleNames)
          }
        },
        {
          title: 'Api说明',
          key: 'explain',
          minWidth: 150
        }
      ]
    }
  },
  methods: {

  },
  mounted () {

  }
}
</script>
