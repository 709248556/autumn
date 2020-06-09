<template>
  <div>
    <Spin v-if="showLoadding"
          fix
          size="large">
      <Icon type="ios-loading"
            size="22"
            class="spin-icon-load"></Icon>
      <div>正在加载... ，请稍后
      </div>
    </Spin>
    <Button v-if="isAdvancedSearch"
            type="primary"
            icon="ios-arrow-down"
            style="height:32px;width:35px;float:right;margin:1px 0px 18px;"
            @click="onAdvancedSearch()"></Button>
    <Input v-if="isSearch"
           search
           placeholder="输入关键字回车搜索..."
           class="search-input"
           v-model="keyword"
           enter-button
           @on-keydown="searchChange"
           @on-search="searchChange"
           :style="isAdvancedSearch ? 'width:300px;float:right;margin:0px -10px 18px;':'width:300px;float:right;margin:0px 0px 18px;'" />
    <slot name="opration"></slot>
    <div style="clear:both;"></div>
    <Table border
           :columns="columns"
           :highlight-row="highlightRow"
           :height="height"
           no-data-text="无数据"
           @on-current-change="onCurrentChange"
           @on-selection-change="onChangeSelect"
           @on-select="onSelected"
           @on-select-cancel="onSelectCancel"
           @on-select-all="onSelectAll"
           @on-select-all-cancel="onSelectAllCancel"
           :rowClassName="rowClassName"
           :data="data"></Table>
    <Page :total="total"
          :current="current"
          :pageSize="pageSize"
          :page-size-opts="pageSizeOpts"
          @on-change="onChangePage"
          @on-page-size-change="onPageSizeChange"
          show-total
          :show-elevator="showElevator"
          :show-sizer="showSizer"
          style="margin:18px 0;" />
  </div>
</template>
<script>
export default {
  name: 'PageTable',
  props: {
    columns: {
      type: Array,
      default () {
        return []
      }
    },
    data: {
      type: Array,
      default () {
        return []
      }
    },
    total: {
      type: Number,
      default () {
        return 0
      }
    },
    showElevator: {
      type: Boolean,
      default () {
        return false
      }
    },
    showSizer: {
      type: Boolean,
      default () {
        return false
      }
    },
    pageSize: {
      type: Number,
      default () {
        return 10
      }
    },
    pageSizeOpts: {
      type: Array,
      default () {
        return [10, 20, 30, 40, 50]
      }
    },
    current: {
      type: Number,
      default () {
        return 1
      }
    },
    showLoadding: {
      type: Boolean,
      default () {
        return false
      }
    },
    isSearch: {
      type: Boolean,
      default () {
        return true
      }
    },
    isAdvancedSearch: {
      type: Boolean,
      default () {
        return false
      }
    },
    highlightRow: {
      type: Boolean,
      default () {
        return false
      }
    },
    rowClassName: {
      type: Function,
      default: function (row, index) {
        return ''
      }
    },
    height: {
      type: String,
      default () {
        return ''
      }
    }
  },
  data () {
    return {
      keyword: ''
    }
  },
  methods: {
    searchChange (evn) {
      if (evn === this.keyword) {
        this.$emit('on-search', this.keyword)
        return
      }
      if (evn && evn.key === 'Enter') {
        this.$emit('on-search', this.keyword)
      }
    },
    onChangePage (page) {
      this.$emit('on-page-change', page, this.keyword)
    },
    onPageSizeChange (size) {
      this.$emit('on-page-size-change', size)
    },
    onChangeSelect (selection) {
      this.$emit('on-select-change', selection)
    },
    onCurrentChange (currentRow, oldCurrentRow) {
      this.$emit('on-current-change', currentRow, oldCurrentRow)
    },
    onSelected (selection, row) {
      this.$emit('on-select', selection, row)
    },
    onSelectCancel (selection, row) {
      this.$emit('on-select-cancel', selection, row)
    },
    onSelectAll (selection) {
      this.$emit('on-select-all', selection)
    },
    onSelectAllCancel (selection) {
      this.$emit('on-select-all-cancel', selection)
    },
    onAdvancedSearch () {
      this.$emit('on-advanced-search', this.keyword)
    }
  }
}
</script>
<style>
.spin-icon-load {
  animation: ani-spin 1s linear infinite;
}
@keyframes ani-spin {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
.ivu-table .table-info-row td {
  background-color: #2db7f5;
  color: #fff;
}
.ivu-table .table-error-row td {
  background-color: #ff6600;
  color: #fff;
}
.ivu-table .table-message-row td {
  background-color: #187;
  color: #fff;
}
.ivu-table td.table-info-column {
  background-color: #2db7f5;
  color: #fff;
}
.ivu-table .table-info-cell-name {
  background-color: #2db7f5;
  color: #fff;
}
.ivu-table .table-info-cell-age {
  background-color: #ff6600;
  color: #fff;
}
.ivu-table .table-info-cell-address {
  background-color: #187;
  color: #fff;
}
</style>
