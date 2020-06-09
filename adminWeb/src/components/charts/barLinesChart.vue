<template>
  <div :id="chartId"></div>
</template>
<script>
// 引入 ECharts 主模块
import echarts from 'echarts'

export default {
  name: 'bar-line',
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
  methods: {
    setMax (type) {
      let arr = []
      let half = 0
      half = this.options.data.length / 2
      this.options.data.forEach((item, index) => {
        if (index < half && type === 1) {
          arr = arr.concat(item.data)
        } else if (index >= half && type === 2) {
          arr = arr.concat(item.data)
        }
      })
      return Math.ceil(Math.max.apply(null, arr) / 10) * 10
    },
    setAvg (type) {
      let arr = []
      let half = 0
      half = this.options.data.length / 2
      this.options.data.forEach((item, index) => {
        if (index < half && type === 1) {
          arr = arr.concat(item.data)
        } else if (index >= half && type === 2) {
          arr = arr.concat(item.data)
        }
      })
      return Math.ceil(Math.max.apply(null, arr) / 10)
    },
    setSeries () {
      let series = []
      let half = this.options.data.length / 2
      if (half > 0) {
        this.options.data.forEach((item, index) => {
          if (index < half) {
            series.push({
              name: item.name,
              type: 'bar',
              data: item.data
            })
          } else {
            series.push({
              name: item.name,
              type: 'line',
              yAxisIndex: 1,
              data: item.data
            })
          }
        })
      }
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
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          right: '20',
          top: 'center',
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          right: '200',
          data: this.options.lables,
          textStyle: {
            color: labelColor
          }
        },
        xAxis: [
          {
            type: 'category',
            data: this.options.category,
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: this.options.axisLabel[0] + '/' + this.options.units[0],
            min: 0,
            max: this.setMax(1),
            interval: this.setAvg(1),
            axisLabel: {
              formatter: '{value} ' + this.options.units[0]
            }
          },
          {
            type: 'value',
            name: this.options.axisLabel[1] + '/' + this.options.units[1],
            min: 0,
            max: this.setMax(2),
            interval: this.setAvg(2),
            axisLabel: {
              formatter: '{value} ' + this.options.units[1]
            }
          }
        ],
        series: this.setSeries()
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
