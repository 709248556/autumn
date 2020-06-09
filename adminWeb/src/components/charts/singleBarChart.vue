<template>
  <div :id="chartId"></div>
</template>
<script>
// 引入 ECharts 主模块
import echarts from 'echarts'

export default {
  name: 'bar-chart',
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
          labels: [],
          category: [],
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
        color: ['#f9a62d'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: this.options.category,
            axisTick: {
              alignWithLabel: true
            },
            axisLine: {
              lineStyle: {
                color: labelColor
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            axisLine: {
              lineStyle: {
                color: labelColor
              }
            }
          }
        ],
        series: [
          {
            name: this.options.label,
            type: 'bar',
            barWidth: '60%',
            barMaxWidth: '50',
            data: this.options.data
          }
        ]
      }
    }
  },
  mounted () {
    let _this = this
    this.$nextTick(function () {
      this.myChart = echarts.init(document.getElementById(this.chartId))
      this.myChart.setOption(this.buidOptions())
      window.onresize = function () {
        _this.myChart.resize()
      }
    })
  }
}
</script>
