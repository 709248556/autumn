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
          category: [],
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
        this.myChart.setOption(this.buildOptions(), true)
      },
      deep: true
    }
  },

  methods: {
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
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          data: this.options.category,
          textStyle: {
            color: labelColor
          }
        },
        series: [
          {
            name: this.options.lable,
            type: 'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
            label: {
              normal: {
                position: 'inner'
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: this.options.data
          },
          {
            name: this.options.lable,
            type: 'pie',
            radius: ['40%', '55%'],
            label: {
              normal: {
                formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                backgroundColor: '#eee',
                borderColor: '#aaa',
                borderWidth: 1,
                borderRadius: 4,
                rich: {
                  a: {
                    color: '#999',
                    lineHeight: 22,
                    align: 'center'
                  },
                  hr: {
                    borderColor: '#aaa',
                    width: '100%',
                    borderWidth: 0.5,
                    height: 0
                  },
                  b: {
                    fontSize: 16,
                    lineHeight: 33
                  },
                  per: {
                    color: '#eee',
                    backgroundColor: '#334455',
                    padding: [2, 4],
                    borderRadius: 2
                  }
                }
              }
            },
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
      this.myChart.setOption(this.buildOptions())
      window.onresize = function () {
        _this.myChart.resize()
      }
    })
  }
}
</script>
