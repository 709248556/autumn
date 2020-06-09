<template>
  <Card>
    <div class="card-title">地址请求权限管理</div>
    <Divider />
    <Input placeholder="输入控制器名或说明，然后回车"
                 v-model="filter.controllerSearch"
                 clearable
                 enter-button
                 @on-clear="onFilter()"
                 @on-keydown="onKeydown"
                 style="margin:-15px 10px 10px 0px; width:250px"/>
    <Select v-model="filter.permissionVisit" @on-change="onFilter()" style="margin:-15px 10px 10px 0px; width:120px">
      <Option v-for="item in filter.permissionVisits" :value="item.value" :key="item.value">{{ item.label }}</Option>
    </Select>
    <Select v-model="filter.permissionVisitType" @on-change="onFilter()" style="margin:-15px 10px 10px 0px; width:120px">
      <Option v-for="item in filter.permissionVisitTypes" :value="item.value" :key="item.value">{{ item.label }}</Option>
    </Select>
    <Button type="info"
            icon="md-refresh"
            style="margin:-15px 10px 10px 0px;"
            @click="onLoadMappingGroups()">刷新</Button>
    <Button type="warning"
                    icon="ios-flash"
                    style="margin:-15px 10px 10px 0px;"
                    @click="onResetInterceptorPermission()">重置权限缓存</Button>
    <Table :loading="this.formData.loading"
            :columns="columns"
            :data="this.formData.mappingGroups">
    </Table>
  </Card>
</template>
<script>
import Action from '_c/action'
import {
  queryUrlRequestPermissionMappingGroups,
  resetInterceptorPermission
} from '@/api/administrator/sys/resource-permissions'
import expandRow from './url-request-permission-mapping-item.vue'
export default {
  name: 'url_request_permission_mapping',
  components: {
    Action
  },
  data () {
    return {
      formData: {
        loading: false,
        loadingText: '正在加载...',
        sourceGroups: [],
        mappingGroups: []
      },
      filter: {
        controllerSearch: '',
        url: '',
        operation: '',
        methods: '',
        permissionVisit: 0,
        permissionVisitType: 0,
        permissionVisits: [
          {
            label: '全部',
            value: 0
          },
          {
            label: '需要授权',
            value: 1
          },
          {
            label: '不需要授权',
            value: -1
          }
        ],
        permissionVisitTypes: [
          {
            label: '全部',
            value: 0
          },
          {
            label: '已配置Url权限',
            value: 1
          },
          {
            label: '未配置Url权限',
            value: -1
          }
        ]
      },
      columns: [
        {
          type: 'expand',
          width: 50,
          render: (h, params) => {
            return h(expandRow, {
              props: {
                mappings: params.row.mappings
              }
            })
          }
        },
        {
          title: '序号',
          key: 'sortId',
          width: 125,
          sortable: true,
          filters: [],
          filterMultiple: false,
          filterMethod (value, row) {
            return row.sortId >= value.min && row.sortId <= value.max
          }
        },
        {
          title: '控制器',
          key: 'controllerName',
          width: 350,
          sortable: true,
          filters: [],
          filterMultiple: false,
          filterMethod (value, row) {
            return value === row.controllerName.substr(0, 1).toUpperCase()
          }
        },
        {
          title: 'Api数',
          key: 'apiCount',
          width: 135,
          sortable: true,
          filters: [],
          filterMultiple: false,
          filterMethod (value, row) {
            return row.apiCount >= value.min && row.apiCount <= value.max
          }
        },
        {
          title: '说明',
          key: 'controllerExplain',
          minWidth: 300,
          sortable: true
        }
      ]
    }
  },
  methods: {
    onKeydown (evn) {
      if (evn && evn.key === 'Enter') {
        this.onFilter()
      }
    },
    onFilter () {
      this.formData.mappingGroups = []
      let apiTotalCount = 0
      let apiMaxCount = 0
      this.columns[3].title = 'Api数'
      this.formData.sourceGroups.forEach(item => {
        let newitem = JSON.parse(JSON.stringify(item))
        newitem.mappings = newitem.mappings || []
        let isAdd = true
        if (this.filter.controllerSearch && this.filter.controllerSearch.trim() !== '') {
          if (newitem.controllerName == null) {
            newitem.controllerName = ''
          }
          if (newitem.controllerExplain == null) {
            newitem.controllerExplain = ''
          }
          isAdd = newitem.controllerName.toUpperCase().indexOf(this.filter.controllerSearch.toUpperCase().trim()) !== -1 ||
                  newitem.controllerExplain.toUpperCase().indexOf(this.filter.controllerSearch.toUpperCase().trim()) !== -1
        }
        if (isAdd) {
          if (this.filter.permissionVisit !== 0 || this.filter.permissionVisitType !== 0) {
            let newMappings = []
            newitem.mappings.forEach(pre => {
              let isAddPre = true
              if (this.filter.permissionVisit !== 0) {
                if (this.filter.permissionVisit === 1) {
                  if (!pre.permissionVisit) {
                    isAddPre = false
                  }
                } else {
                  if (pre.permissionVisit) {
                    isAddPre = false
                  }
                }
              }
              if (isAddPre && this.filter.permissionVisitType !== 0) {
                if (this.filter.permissionVisitType === 1) {
                  if (!pre.permissionExplains) {
                    isAddPre = false
                  }
                } else {
                  if (pre.permissionExplains) {
                    isAddPre = false
                  }
                }
              }
              if (isAddPre) {
                newMappings.push(pre)
              }
            })
            newitem.mappings = newMappings
          }
          if (newitem.mappings.length > 0) {
            newitem.apiCount = newitem.mappings.length
            if (newitem.apiCount > apiMaxCount) {
              apiMaxCount = newitem.apiCount
            }
            apiTotalCount += newitem.apiCount
            this.formData.mappingGroups.push(newitem)
          }
        }
      })
      this.calcControllerFilter()
      this.calcNumberRangeFilter(3, apiMaxCount)
      this.columns[3].title = 'Api数[' + apiTotalCount + ']'
    },
    onLoadMappingGroups () {
      this.formData.mappingGroups = []
      this.formData.sourceGroups = []
      this.formData.loading = true
      this.formData.loadingText = '正在加载...'
      this.columns[1].title = '序号'
      this.columns[3].title = 'Api数'
      queryUrlRequestPermissionMappingGroups().then(res => {
        for (let i = 0; i < res.length; i++) {
          let item = res[i]
          item.sortId = i + 1
          if (item.mappings) {
            for (let j = 0; j < item.mappings.length; j++) {
              let mapping = item.mappings[j]
              mapping.sortId = j + 1
            }
            item.apiCount = item.mappings.length
          }
        }
        this.formData.sourceGroups = res
        this.formData.loading = false
        this.columns[1].title = '序号[' + res.length + ']'
        this.calcNumberRangeFilter(1, res.length)
        this.onFilter()
      }).catch(res => {
        this.formData.loading = false
      })
    },
    // 计算数字范围过滤
    calcNumberRangeFilter (columnIndex, maxLength) {
      let column = this.columns[columnIndex]
      column.filters = []
      if (maxLength < 1) {
        return
      }
      let pageSize = 10
      let filterPages = Math.ceil(maxLength / pageSize)
      let min = 1
      let max = 19
      for (let p = 1; p <= filterPages; p++) {
        max = p * 10
        let label = min + ' - ' + max
        column.filters.push(
          {
            label: label,
            value: {
              min: min,
              max: max
            }
          }
        )
        min = max + 1
      }
    },
    // 计算控制器过滤
    calcControllerFilter () {
      let column = this.columns[2]
      column.filters = []
      let first = null
      var pattern = new RegExp('[A-Za-z]+')
      this.formData.mappingGroups.forEach(item => {
        let value = item.controllerName.substr(0, 1).toUpperCase()
        if (pattern.test(value)) {
          if (first === null || first !== value) {
            column.filters.push(
              {
                label: value,
                value: value
              }
            )
          }
          first = value
        }
      })
    },
    onResetInterceptorPermission () {
      this.$Modal.confirm({
        title: '提示',
        loading: true,
        content: '<p>确定要重置权限缓存吗？</p>',
        onOk: () => {
          this.formData.loading = true
          this.formData.loadingText = '正在重置...'
          resetInterceptorPermission().then(res => {
            this.$Message.success('重置权限缓存成功!')
          }).finally(() => {
            this.$Modal.remove()
            this.formData.loading = false
          })
        },
        onCancel: () => {
        }
      })
    }
  },
  mounted () {
    this.onLoadMappingGroups()
  }
}
</script>
<style>
.modal-con-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
</style>
