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
          unit: '',
          category: [],
          data: []
        }
      }
    }
  },
  data () {
    return {
      myChart: null,
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
      }
    }
  },
  methods: {
    setSeries (data) {
      let series = []
      let _this = this
      data.forEach(function (item, index) {
        if (index === 0) {
          series.push({
            name: item.name,
            type: 'bar',
            barGap: 0,
            label: _this.labelOption,
            data: item.data
          })
        } else {
          series.push({
            name: item.name,
            type: 'bar',
            label: _this.labelOption,
            data: item.data
          })
        }
      })
      return series
    },
    buildOptions () {
      let labelColor = this.options.labelColor ? this.options.labelColor : '#333'
      let unit = this.options.unit ? this.options.unit : ''
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
          x: 'right',
          data: this.options.labels,
          textStyle: {
            color: labelColor
          }
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          right: '20',
          top: 'center',
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar', 'stack', 'tiled'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        calculable: true,
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: this.options.category,
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
            name: '单位/' + unit,
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
