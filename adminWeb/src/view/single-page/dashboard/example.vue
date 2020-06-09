<template>
  <div ref="dom"></div>
</template>

<script>
import echarts from 'echarts'
import { on, off } from '@/libs/tools'
export default {
  name: 'serviceRequests',
  props: {
    text: String,
    value: Object
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
    setChart () {
      let option = {
        title: {
          text: this.text,
          textStyle: {
            color: '#516b91'
          },
          x: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        grid: {
          x: 60,
          x2: 60
        },
        xAxis: this.value.xAxis,
        yAxis: this.value.yAxis,
        series: this.value.series
      }
      this.dom = echarts.init(this.$refs.dom)
      this.dom.setOption(option)
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.setChart(this.value)
      on(window, 'resize', this.resize)
    })
  },
  beforeDestroy () {
    off(window, 'resize', this.resize)
  }
}
</script>
