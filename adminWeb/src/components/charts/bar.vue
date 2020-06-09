<template>
  <div ref="dom" class="charts chart-bar"></div>
</template>

<script>
import echarts from 'echarts'
import tdTheme from './theme.json'
import { on, off } from '@/libs/tools'
echarts.registerTheme('tdTheme', tdTheme)
export default {
  name: 'ChartBar',
  props: {
    value: Array,
    text: String,
    subtext: String
  },
  data () {
    return {
      dom: null
    }
  },
  watch: {
    value: {
      handler (newValue, oldValue) {
        this.setChart(newValue)
      },
      deep: true
    }
  },
  methods: {
    resize () {
      this.dom.resize()
    },
    setChart (value) {
      let xAxisData =
        (value.length &&
          value.map(item => {
            return item.name
          })) ||
        []
      let seriesData =
        (value.length &&
          value.map(item => {
            return item.value
          })) ||
        []
      let option = {
        title: {
          text: this.text,
          subtext: this.subtext,
          x: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: xAxisData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: seriesData,
            type: 'bar'
          }
        ]
      }
      this.dom = echarts.init(this.$refs.dom, 'tdTheme')
      this.dom.setOption(option, true)
      on(window, 'resize', this.resize)
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.setChart(this.value)
    })
  },
  beforeDestroy () {
    off(window, 'resize', this.resize)
  }
}
</script>
