<template>
  <div>
    <Modal v-model="filter.advanced.show"
           width="800"
           class-name="vertical-center-modal"
           @on-ok="this.onAdvancedSearch"
           :title="filter.advanced.title">
      <Form>
        <Row style="margin-bottom: 10px;">
          <Col span="3"
               style="padding-right: 10px">
          <Select v-model="filter.advanced.currentLogic"
                  placeholder="选择逻辑">
            <Option v-for="sItem in filter.advanced.logics"
                    :value="sItem.value"
                    :key="sItem.value">{{ sItem.label }}</Option>
          </Select>
          </Col>
          <Col span="4"
               style="padding-right: 10px">
          <Select v-model="filter.advanced.currentCriteriaIndex"
                  @on-change="this.onCriteriaChange"
                  placeholder="选择字段">
            <Option v-for="(sItem, index) in filter.advanced.criterias"
                    :value="index"
                    :key="index">{{ sItem.title }}</Option>
          </Select>
          </Col>
          <Col span="4"
               style="padding-right: 10px">
          <Select v-model="filter.advanced.currentCriteriaOpIndex"
                  placeholder="选择运算符">
            <Option v-for="(op, index) in filter.advanced.currentCriteriaOps"
                    :value="index"
                    :key="index">{{ op.label }}</Option>
          </Select>
          </Col>
          <Col span="7"
               v-if="
              filter.advanced.currentCriteria &&
                filter.advanced.currentCriteria.dataType === 'string'
            "
               style="padding-right: 10px">
          <Input :placeholder="filter.advanced.currentCriteria.placeholder"
                 v-model="filter.advanced.currentCriteria.value"
                 clearable
                 :maxlength="
                filter.advanced.currentCriteria.maxlength
                  ? filter.advanced.currentCriteria.maxlength
                  : -1
              " />
          </Col>
          <Col span="7"
               v-if="
              filter.advanced.currentCriteria &&
                filter.advanced.currentCriteria.dataType === 'number'
            "
               style="padding-right: 10px">
          <Input type="number"
                 :placeholder="filter.advanced.currentCriteria.placeholder"
                 v-model="filter.advanced.currentCriteria.value"
                 clearable
                 style="width:210px;" />
          </Col>
          <Col span="7"
               v-if="
              filter.advanced.currentCriteria &&
                filter.advanced.currentCriteria.dataType === 'date'
            "
               style="padding-right: 10px">
          <DatePicker :options="filter.dateOptions1"
                      :placeholder="filter.advanced.currentCriteria.placeholder"
                      v-model="filter.advanced.currentCriteria.value"
                      placement="bottom-start"
                      format="yyyy-MM-dd"
                      type="date"
                      style="width:210px;" />
          </Col>
          <Col span="7"
               v-if="
              filter.advanced.currentCriteria &&
                filter.advanced.currentCriteria.dataType === 'datetime'
            "
               style="padding-right: 10px">
          <DatePicker :options="filter.dateOptions1"
                      :placeholder="filter.advanced.currentCriteria.placeholder"
                      v-model="filter.advanced.currentCriteria.value"
                      placement="bottom-start"
                      format="yyyy-MM-dd HH:mm:ss"
                      type="datetime"
                      style="width:210px;" />
          </Col>
          <Col :span="
              filter.advanced.currentCriteria &&
              filter.advanced.currentCriteria.dataType == 'select'
                ? '13'
                : '6'
            ">
          <div style="float:right;">
            <Button type="primary"
                    icon="md-add"
                    @click="onAddCriteriaItem()">添加</Button>
            <Button type="warning"
                    icon="md-close"
                    style="margin-left:10px;"
                    @click="onDeleteAllCriteriaItem()">全清</Button>
          </div>
          </Col>
        </Row>
        <Table border
               highlightRow
               :columns="filter.advanced.columns"
               :data="filter.advanced.tempItems"></Table>
      </Form>
    </Modal>
    <div class="card-title">{{ dataMeta.title }}</div>
    <Divider />
    <Button v-if="dataMeta && dataMeta.opButtons"
            v-for="(item, index) in dataMeta.opButtons"
            v-bind:key="index"
            :type="item.type"
            :icon="item.icon"
            :style="item.style ? item.style : 'margin:0px 10px 10px 0px;'"
            @click="item.click">{{ item.text }}</Button>
    <Button type="info"
            icon="md-refresh"
            style="margin:0px 10px 10px 0px;"
            @click="onRefresh()">刷新</Button>
    <Button v-if="dataMeta && dataMeta.downloadByExcelInfoApi"
            icon="ios-download-outline"
            style="margin:0px 10px 10px 0px;"
            @click="onDownloadExcel">导出</Button>
    <page-table ref="pageTable" v-if="dataMeta.columns"
                :columns="dataMeta.columns"
                :data="this.tableMeta.data"
                :total="this.tableMeta.total"
                :current="this.tableMeta.currentPage"
                :pageSize="this.tableMeta.pageSize"
                :showLoadding="this.tableMeta.loadding"
                :highlightRow="highlightRow"
                :rowClassName="rowClassName"
                :isAdvancedSearch="
        dataMeta !== null &&
          dataMeta.advancedFilter &&
          dataMeta.advancedFilter.length > 0
      "
                @on-search="this.onSearch"
                @on-page-change="this.onPageChange"
                @on-page-size-change="onPageSizeChange"
                @on-advanced-search="this.onOpenAdvancedSearch"
                show-elevator show-sizer>
      <template slot="opration">
        <div v-if="dataMeta !== null && dataMeta.fastFilter !== null && dataMeta.fastFilter.length > 0"
             v-for="(item, index) in dataMeta.fastFilter"
             v-bind:key="index"
             style="float:left">
          <Input v-if="item.dataType === 'string'"
                 :placeholder="item.placeholder"
                 v-model="item.value"
                 clearable
                 enter-button
                 :maxlength="item.maxlength ? item.maxlength : -1"
                 @on-clear="onRefresh(1)"
                 @on-keydown="onKeydown"
                 :style="
              item.width
                ? 'width:' + item.width + ' ;margin:0px 10px 10px 0px;'
                : 'width: 200px;margin:0px 10px 10px 0px;'
            " />
          <Input v-if="item.dataType === 'number'"
                 type="number"
                 :placeholder="item.placeholder"
                 v-model="item.value"
                 clearable
                 enter-button
                 @on-clear="onRefresh(1)"
                 @on-keydown="onKeydown"
                 :style="
              item.width
                ? 'width:' + item.width + ' ;margin:0px 10px 10px 0px;'
                : 'width: 200px;margin:0px 10px 10px 0px;'
            " />
          <Select v-if="item.dataType === 'select'"
                  v-model="item.value"
                  @on-change="onRefresh(1)"
                  :placeholder="item.placeholder"
                  :style="
              item.width
                ? 'width:' + item.width + ' ;margin:0px 10px 10px 0px;'
                : 'width: 120px;margin:0px 10px 10px 0px;'
            ">
            <Option v-for="(sItem, index) in item.ops"
                    :value="sItem.value"
                    :key="index">{{ sItem.label }}</Option>
          </Select>
          <DatePicker v-if="item.dataType === 'date'"
                      :options="filter.dateOptions2"
                      :placeholder="item.placeholder"
                      v-model="item.value"
                      format="yyyy-MM-dd"
                      type="daterange"
                      placement="bottom-start"
                      @on-ok="onRefresh(1)"
                      :style="
              item.width
                ? 'width:' +
                  item.width +
                  ';font-size: 12px;margin:0px 10px 10px 0px;'
                : 'width: 250px; font-size: 12px; margin:0px 10px 10px 0px;'
            " />
          <DatePicker v-if="item.dataType === 'datetime'"
                      :options="filter.dateOptions2"
                      :placeholder="item.placeholder"
                      placement="bottom-start"
                      v-model="item.value"
                      @on-ok="onRefresh(1)"
                      format="yyyy-MM-dd HH:mm:ss"
                      type="datetimerange"
                      :style="
              item.width
                ? 'width:' +
                  item.width +
                  ';font-size: 12px;margin:0px 10px 10px 0px;'
                : 'width: 280px; font-size: 12px; margin:0px 10px 10px 0px;'
            " />
        </div>
      </template>
    </page-table>
  </div>
</template>
<script>
import PageTable from '_c/page-table'
import {
  formatDate,
  setTimeValue,
  getMonthBegin,
  getMonthEnd,
  getYearBegin,
  getYearEnd,
  getQuarterBegin,
  getQuarterEnd,
  getPreviousMonthBegin,
  getPreviousMonthEnd
} from '@/libs/common-tools'
export default {
  name: 'PageTableManager',
  components: {
    PageTable
  },
  props: {
    dataMeta: {
      type: Object,
      default () {
        return {
          title: '管理',
          queryPageApi: null,
          downloadByExcelInfoApi: null,
          opButtons: [
            {
              type: 'primary',
              text: '刷新',
              icon: 'md-refresh',
              click: () => {
                this.onRefresh()
              }
            }
          ],
          // 固定输入选项，作为查询选项条件
          fixedInputOption: {},
          // 固定输入条件
          fixedInputCriterias: [],
          fastFilter: [
            {
              key: 'id',
              placeholder: '请输入编号',
              width: '100px',
              maxlength: 30,
              dataType: 'string', // string date datetime number select
              op: '=',
              value: ''
            },
            {
              key: 'sex',
              placeholder: '请选择性别',
              width: '100px',
              dataType: 'select',
              op: '=',
              value: '',
              ops: [
                {
                  value: '1',
                  label: '男'
                },
                {
                  value: '1',
                  label: '女'
                }
              ]
            }
          ],
          advancedFilter: [
            {
              key: 'id',
              title: '编号',
              placeholder: '请输入编号',
              width: '100px',
              maxlength: 30,
              dataType: 'string', // string date datetime number select
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
                }
              ],
              value: ''
            },
            {
              key: 'sex',
              title: '性别',
              placeholder: '请选择性别',
              width: '100px',
              dataType: 'select',
              op: '=',
              value: '',
              ops: [
                {
                  value: '1',
                  label: '男'
                },
                {
                  value: '1',
                  label: '女'
                }
              ]
            }
          ],
          columns: []
        }
      }
    },
    highlightRow: {
      type: Boolean,
      default () {
        return true
      }
    },
    rowClassName: {
      type: Function,
      default: function (row, index) {
        return ''
      }
    }
  },
  data () {
    return {
      tableMeta: {
        loadding: false,
        data: [],
        total: 0,
        pageSize: 10,
        currentPage: 1
      },
      filter: {
        keyword: '',
        advanced: {
          show: false,
          title: '高级筛选',
          currentCriteria: null,
          currentCriteriaIndex: 0,
          currentCriteriaOpIndex: 0,
          currentCriteriaOps: [],
          currentCriteriaValue: null,
          currentLogic: 'and',
          logics: [
            {
              value: 'and',
              label: '并且'
            },
            {
              value: 'or',
              label: '或者'
            }
          ],
          criterias: null,
          items: [],
          tempItems: [],
          columns: [
            { title: '逻辑', key: 'logicName', width: 80 },
            { title: '字段', key: 'expressionName', width: 150 },
            { title: '运算符', key: 'opName', width: 120 },
            { title: '条件值', key: 'value', minWidth: 200 },
            {
              title: '操作',
              key: 'action',
              width: 95,
              align: 'left',
              fixed: 'right',
              render: (h, params) => {
                return h('div', [
                  h(
                    'Button',
                    {
                      props: {
                        type: 'warning',
                        size: 'small',
                        icon: 'md-trash'
                      },
                      on: {
                        click: () => {
                          this.onDeleteCriteriaItem(params)
                        }
                      }
                    },
                    '删除'
                  )
                ])
              }
            }
          ]
        },
        dateOptions1: {
          shortcuts: [
            {
              text: '今天',
              value () {
                const date = new Date()
                setTimeValue(date)
                return date
              }
            },
            {
              text: '昨天',
              value () {
                const date = new Date()
                setTimeValue(date, false)
                date.setTime(date.getTime() - 3600 * 1000 * 24)
                return date
              }
            },
            {
              text: '一周前',
              value () {
                const date = new Date()
                setTimeValue(date, false)
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
                return date
              }
            },
            {
              text: '本月初',
              value () {
                return getMonthBegin()
              }
            },
            {
              text: '本月底',
              value () {
                return getMonthEnd(true)
              }
            },
            {
              text: '本季初',
              value () {
                return getQuarterBegin()
              }
            },
            {
              text: '本季底',
              value () {
                return getQuarterEnd(true)
              }
            },
            {
              text: '本年初',
              value () {
                return getYearBegin()
              }
            },
            {
              text: '本年底',
              value () {
                return getYearEnd(true)
              }
            }
          ]
        },
        dateOptions2: {
          shortcuts: [
            {
              text: '最近一周',
              value () {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
                return [setTimeValue(start, false), setTimeValue(end, true)]
              }
            },
            {
              text: '最近一个月',
              value () {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
                return [setTimeValue(start, false), setTimeValue(end, true)]
              }
            },
            {
              text: '最近三个月',
              value () {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
                return [setTimeValue(start, false), setTimeValue(end, true)]
              }
            },
            {
              text: '本月',
              value () {
                return [getMonthBegin(), getMonthEnd(true)]
              }
            },
            {
              text: '上月',
              value () {
                return [getPreviousMonthBegin(), getPreviousMonthEnd(true)]
              }
            },
            {
              text: '本季度',
              value () {
                return [getQuarterBegin(), getQuarterEnd(true)]
              }
            },
            {
              text: '本年',
              value () {
                return [getYearBegin(), getYearEnd(true)]
              }
            }
          ]
        }
      }
    }
  },
  methods: {
    onKeydown (evn) {
      if (evn && evn.key === 'Enter') {
        this.onRefresh(1)
      }
    },
    onPageChange (page, keyword) {
      this.filter.keyword = keyword
      this.onRefresh(page)
    },
    onSearch (keyword) {
      this.filter.keyword = keyword
      this.onRefresh(1)
    },
    onPageSizeChange (size) {
      this.tableMeta.pageSize = size
      this.onRefresh(1)
    },
    onCalcIndex () {
      if (this.filter.advanced.criterias.length > 0) {
        if (
          this.filter.advanced.currentCriteriaIndex >
          this.filter.advanced.criterias.length - 1
        ) {
          this.filter.advanced.currentCriteriaIndex = 0
        }
        this.filter.advanced.currentCriteria = this.filter.advanced.criterias[this.filter.advanced.currentCriteriaIndex]
        this.filter.advanced.currentCriteriaOps = this.filter.advanced.currentCriteria.ops
      } else {
        this.filter.advanced.currentCriteria = {}
        this.filter.advanced.currentCriteriaOps = []
      }
      if (
        this.filter.advanced.currentCriteriaOpIndex >
        this.filter.advanced.currentCriteriaOps.length - 1
      ) {
        this.filter.advanced.currentCriteriaOpIndex = 0
      }
    },
    onCriteriaChange (value) {
      this.filter.advanced.currentCriteriaOpIndex = 0
      this.onCalcIndex()
    },
    onDeleteCriteriaItem (params) {
      this.filter.advanced.tempItems.splice(params.index, 1)
    },
    onDeleteAllCriteriaItem () {
      this.filter.advanced.tempItems = []
    },
    onAddCriteriaItem () {
      if (
        !(
          this.filter.advanced.currentCriteria &&
          this.filter.advanced.currentCriteria.key
        )
      ) {
        this.$Message.error('无法获取当前条件信息，请重新打开再试。')
        return
      }
      let op = this.filter.advanced.currentCriteriaOps[this.filter.advanced.currentCriteriaOpIndex]
      let value = null
      let opValue = op.value
      if (this.filter.advanced.currentCriteria.dataType === 'select') {
        if (!op.value) {
          this.$Message.error('请选择值。')
          return
        }
        value = op.value
        opValue = '='
      } else {
        if (!this.filter.advanced.currentCriteria.value) {
          this.$Message.error('请选择输入条件值。')
          return
        }
        if (
          this.filter.advanced.currentCriteria.dataType === 'string' &&
          this.filter.advanced.currentCriteria.value.trim() === ''
        ) {
          this.filter.advanced.currentCriteria.value = ''
          this.$Message.error('请选择输入条件值。')
          return
        }
        if (this.filter.advanced.currentCriteria.dataType === 'datetime') {
          value = formatDate(
            this.filter.advanced.currentCriteria.value,
            'yyyy-MM-dd hh:mm:ss'
          )
        } else if (this.filter.advanced.currentCriteria.dataType === 'date') {
          value = formatDate(
            this.filter.advanced.currentCriteria.value,
            'yyyy-MM-dd'
          )
        } else if (this.filter.advanced.currentCriteria.dataType === 'string') {
          value = this.filter.advanced.currentCriteria.value.trim()
        } else {
          value = this.filter.advanced.currentCriteria.value
        }
      }
      let item = {
        expression: this.filter.advanced.currentCriteria.key,
        expressionName: this.filter.advanced.currentCriteria.title,
        logic: this.filter.advanced.currentLogic,
        logicName: '',
        op: opValue,
        opName: op.label,
        value: value
      }
      if (!this.filter.advanced.currentLogic) {
        item.logic = 'and'
        item.logicName = '并且'
      } else {
        this.filter.advanced.logics.forEach(l => {
          if (l.value === this.filter.advanced.currentLogic) {
            item.logicName = l.label
          }
        })
      }
      for (let i = 0; i < this.filter.advanced.tempItems.length; i++) {
        let old = this.filter.advanced.tempItems[i]
        if (old.expression === item.expression && old.op === item.op) {
          this.$Message.error('相同的字段和相同的运算符不能重复。')
          return
        }
      }
      this.filter.advanced.tempItems.push(item)
    },
    onOpenAdvancedSearch () {
      if (
        this.filter.advanced.criterias === null ||
        this.filter.advanced.criterias.length === 0
      ) {
        this.filter.advanced.criterias = []
        if (this.dataMeta && this.dataMeta.advancedFilter) {
          this.dataMeta.advancedFilter.forEach(item => {
            this.filter.advanced.criterias.push(item)
          })
        }
      }
      this.onCalcIndex()
      this.filter.advanced.tempItems = []
      this.filter.advanced.items.forEach(item => {
        this.filter.advanced.tempItems.push(item)
      })
      this.filter.advanced.show = true
    },
    // 创建查询输入
    createQueryInput (page) {
      let q = {
        currentPage: page || this.tableMeta.currentPage,
        pageSize: this.tableMeta.pageSize
      }
      if (this.filter.keyword) {
        q.searchKeyword = this.filter.keyword
      }
      if (this.dataMeta && this.dataMeta.fixedInputOption) {
        const keys = Object.keys(this.dataMeta.fixedInputOption)
        keys.forEach(o => {
          q[o] = this.dataMeta.fixedInputOption[o]
        })
      }
      let criterias = []
      if (this.dataMeta && this.dataMeta.fixedInputCriterias) {
        this.dataMeta.fixedInputCriterias.forEach(o => {
          if (o.expression && o.op && o.value) {
            criterias.push(o)
          }
        })
      }
      if (
        this.dataMeta &&
        this.dataMeta.fastFilter &&
        this.dataMeta.fastFilter.length > 0
      ) {
        this.dataMeta.fastFilter.forEach(item => {
          if (item.dataType === 'date' || item.dataType === 'datetime') {
            if (item.value.length === 2) {
              if (item.value[0]) {
                criterias.push({
                  expression: item.key,
                  logic: 'and',
                  op: '>=',
                  value: formatDate(item.value[0], 'yyyy-MM-dd hh:mm:ss')
                })
              }
              if (item.value[1]) {
                criterias.push({
                  expression: item.key,
                  logic: 'and',
                  op: '<=',
                  value: formatDate(item.value[1], 'yyyy-MM-dd hh:mm:ss')
                })
              }
            }
          } else {
            if (item.value) {
              if (item.dataType === 'string') {
                if (item.value.trim() !== '') {
                  criterias.push({
                    expression: item.key,
                    logic: 'and',
                    op: item.op,
                    value: item.value.trim()
                  })
                } else {
                  item.value = ''
                }
              } else {
                criterias.push({
                  expression: item.key,
                  logic: 'and',
                  op: item.op,
                  value: item.value
                })
              }
            }
          }
        })
      }
      this.filter.advanced.items.forEach(item => {
        criterias.push(item)
      })
      q.criterias = criterias
      return q
    },
    onAdvancedSearch () {
      this.filter.advanced.items = []
      this.filter.advanced.tempItems.forEach(item => {
        this.filter.advanced.items.push(item)
      })
      this.onRefresh(1)
    },
    onRefresh (page) {
      if (this.dataMeta && this.dataMeta.queryPageApi) {
        this.$emit('on-refresh-before')
        let q = this.createQueryInput(page)
        this.tableMeta.loadding = true
        this.dataMeta
          .queryPageApi(q)
          .then(result => {
            this.tableMeta.data = result.items
            this.tableMeta.total = result.rowTotal
            this.tableMeta.pageSize = result.pageSize
            this.tableMeta.currentPage = result.currentPage
            let index = (result.currentPage - 1) * result.pageSize + 1
            this.tableMeta.data.forEach(item => {
              item._rowIndex = index
              index++
            })
            this.tableMeta.loadding = false
            this.$emit('on-refresh-after', true)
          })
          .catch(() => {
            this.tableMeta.loadding = false
            this.$emit('on-refresh-after', false)
          })
      }
    },
    // 获取数据项目长度
    getDataItemLength () {
      if (this.tableMeta.data) {
        return this.tableMeta.data.length
      } else {
        return 0
      }
    },
    // 获取行总数
    getRowTotal () {
      if (this.tableMeta.total) {
        return this.tableMeta.total
      } else {
        return 0
      }
    },
    onDownloadExcel () {
      if (this.dataMeta && this.dataMeta.downloadByExcelInfoApi) {
        this.$Modal.confirm({
          title: '提示!',
          content: '<p>是否确要定导出数据到Excel文件吗？</p>',
          onOk: () => {
            let q = this.createQueryInput()
            this.tableMeta.loadding = true
            this.$Message.success('正在生成Excel文件...')
            this.dataMeta
              .downloadByExcelInfoApi(q)
              .then(result => {
                this.tableMeta.loadding = false
                this.$Message.success('生成Excel成功，正在下载...')
                window.location.href = result.accessUrlPath
              })
              .catch(() => {
                this.tableMeta.loadding = false
              })
          },
          onCancel: () => { }
        })
      }
    }
  },
  mounted () {
    this.onRefresh()
  }
}
</script>
