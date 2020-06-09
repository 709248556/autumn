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
      labelOption: {
        normal: {
          show: true,
          position: 'insideBottom',
          distance: 10,
          align: 'left',
          verticalAlign: 'middle',
          rotate: '90',
          formatter: '{c}  {name|{a}}',
          fontSize: 16,
          rich: {
            name: {
              textBorderColor: '#fff'
            }
          }
        }
      },
      myChart: null
    }
  },
  methods: {
    setSeries (data) {
      let series = []
      let _this = this
      data.forEach(function (item, index) {
        series.push({
          name: item.name,
          type: 'bar',
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
          x: 'center',
          textStyle: {
            color: labelColor,
            fontSize: '24'
          }
        },
        color: ['#387ee7', '#39c3a7', '#aed04e', '#d24c26', '#5b96ee', '#a726d2', '#7bd226'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: this.options.labels,
          textStyle: {
            color: labelColor
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
            type: 'value',
            boundaryGap: [0, 0.01],
            axisLine: {
              lineStyle: {
                color: labelColor
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'category',
            data: this.options.category,
            axisLine: {
              lineStyle: {
                color: labelColor
              }
            }
          }
        ],
        series: this.setSeries(this.options.data)
      }
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
