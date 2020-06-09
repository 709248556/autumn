<template>
  <div ref="pieDom" class="charts chart-pie"></div>
</template>

<script>
import echarts from 'echarts'
import tdTheme from './theme.json'
import { on, off } from '@/libs/tools'
echarts.registerTheme('tdTheme', tdTheme)
export default {
  name: 'ChartPie',
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
      // let legend = value.map(_ => _.name)
      let option = {
        title: {
          text: this.text,
          subtext: this.subtext,
          x: 'center'
        },
        tooltip: {
          trigger: 'item',
          // formatter: '{a} <br/>{b} : {c} ({d}%)'，
          formatter: '{b} : {c} ({d}%)'
        },
        // legend: {
        //  orient: 'vertical',
        //  left: 'left',
        //  data: legend
        // },
        series: [
          {
            name: this.text,
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: value,
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
      this.dom = echarts.init(this.$refs.pieDom, 'tdTheme')
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
