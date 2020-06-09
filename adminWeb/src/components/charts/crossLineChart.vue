<template>
  <div :id="chartId"></div>
</template>
<script>
// 引入 ECharts 主模块
import echarts from 'echarts'

export default {
  name: 'crossline-chart',
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
          lables: [],
          unit: '',
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
        this.myChart.setOption(this.buildOptions(), true)
      },
      deep: true
    }
  },
  methods: {
    setSeries () {
      let series = []
      this.options.data.forEach((item, index) => {
        series.push({
          name: item.name,
          type: 'line',
          areaStyle: { normal: {} },
          stack: '总量',
          data: item.data
        })
      })
      return series
    },
    buildOptions () {
      let labelColor = this.options.labelColor ? this.options.labelColor : '#333'
      return {
        title: {
          text: this.options.title,
          x: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: this.options.lables,
          textStyle: {
            color: labelColor
          }
        },
        color: ['#387ee7', '#39c3a7', '#aed04e', '#d24c26', '#5b96ee', '#a726d2', '#7bd226'],
        grid: {
          left: '3%',
          right: '8%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.options.category,
          axisLine: {
            lineStyle: {
              color: labelColor
            }
          }
        },
        yAxis: {
          type: 'value',
          name: this.options.unit,
          nameGap: 7,
          axisLine: {
            lineStyle: {
              color: labelColor
            }
          }
        },
        series: this.setSeries()
      }
    }
  },
  mounted () {
    this.$nextTick(function () {
      let _this = this
      this.myChart = echarts.init(document.getElementById(this.chartId))
      this.myChart.setOption(this.buildOptions())
      window.onresize = function () {
        _this.myChart.resize()
      }
    })
  }
}
</script>
