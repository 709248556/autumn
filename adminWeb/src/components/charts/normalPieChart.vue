<template>
  <div :id="chartId"></div>
</template>
<script>
// 引入 ECharts 主模块
import echarts from 'echarts';

export default {
  name: 'normal-pie-chart',
  props: {
    chartId: {
      type: String,
      default: function () {
        return 'myChart';
      }
    },
    options: {
      type: Object,
      default: function () {
        return {
          title: '',
          category: [],
          pos: [],
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
      let labelColor = this.options.labelColor
        ? this.options.labelColor
        : '#333';
      let pos = this.options.pos ? this.options.pos : ['50%', '50%']
      let showLegend =
        this.options.showLegend === false ? this.options.showLegend : true
      return {
        title: {
          text: this.options.title,
          x: 'center',
          textStyle: {
            color: labelColor,
            fontSize: '24'
          }
        },
        color: [
          '#387ee7',
          '#39c3a7',
          '#aed04e',
          '#d24c26',
          '#5b96ee',
          '#a726d2',
          '#7bd226'
        ],
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          x: 'left',
          show: showLegend,
          data: this.options.category,
          textStyle: {
            color: labelColor
          }
        },
        series: [
          {
            name: this.options.lable,
            type: 'pie',
            center: pos,
            radius: [0, '60%'],
            data: this.options.data,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
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
      };
    })
  }
}
</script>
