<template>
  <div :id="chartId"></div>
</template>
<script>
// 引入 ECharts 主模块
import echarts from 'echarts'

export default {
  name: 'pie-chart',
  props: {
    chartId: {
      type: String,
      default: function () {
        return 'myChart'
      }
    },
    options: {
      type: Object,
      default: function () {
        return {
          title: '',
          type: [],
          lable: '',
          data: []
        }
      }
    }
  },
  data () {
    return {
      myChart: null
    }
  },
  watch: {
    options: {
      handler (newValue, oldValue) {
        this.myChart.setOption(this.buidOptions(), true)
      },
      deep: true
    }
  },
  methods: {
    buidOptions () {
      let labelColor = this.options.labelColor ? this.options.labelColor : '#333'
      return {
        title: {
          text: this.options.title,
          x: 'center',
          textStyle: {
            color: labelColor,
            fontSize: '24'
          }
        },
        color: ['#387ee7', '#39c3a7', '#aed04e', '#d24c26', '#5b96ee', '#a726d2', '#7bd226'],
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        calculable: true,
        series: [
          {
            name: this.options.lable,
            type: 'pie',
            roseType: 'area',
            radius: ['10%', '60%'],
            data: this.options.data
          }
        ]
      }
    }
  },
  mounted () {
    this.$nextTick(function () {
      let _this = this
      this.myChart = echarts.init(document.getElementById(this.chartId))
      this.myChart.setOption(this.buidOptions())
      window.onresize = function () {
        _this.myChart.resize()
      }
    })
  }
}
</script>
