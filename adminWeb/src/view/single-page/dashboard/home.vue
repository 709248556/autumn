<template>
  <div>
    <Row :gutter="20">
      <i-col :xs="12"
             :md="8"
             :lg="4"
             v-for="(infor, i) in inforCardData"
             :key="`link-${i}`"
             style="height: 120px;padding-bottom: 10px;">
        <infor-card shadow
                    :color="infor.color"
                    :icon="infor.icon"
                    :icon-size="36">
          <router-link :to="infor.routerLink">
            <div class="count-style">
              {{ infor.count }}
            </div>
            <p>{{ infor.title }}</p>
          </router-link>
        </infor-card>
      </i-col>
    </Row>
    <Row :gutter="20"
         style="margin-top: 10px;">
      <i-col :md="24"
             :lg="8"
             style="margin-bottom: 20px;">
        <Card shadow>
          <chart-pie style="height: 300px;"
                     :value="pieData"
                     text="所有订单类型"></chart-pie>
        </Card>
      </i-col>
      <i-col :md="24"
             :lg="16"
             style="margin-bottom: 20px;">
        <Card shadow>
          <chart-bar style="height: 300px;"
                     :value="barData"
                     text="本周订单量" />
        </Card>
      </i-col>
    </Row>
    <Row>
      <Card shadow>
        <example :value="monthOrderStatisticsOption"
                 text="本月订单"
                 style="height: 400px;" />
      </Card>
    </Row>
  </div>
</template>

<script>
import InforCard from '_c/info-card'
import CountTo from '_c/count-to'
import { ChartPie, ChartBar } from '_c/charts'
import Example from './example.vue'
export default {
  name: 'dashboard',
  components: {
    InforCard,
    CountTo,
    ChartPie,
    ChartBar,
    Example
  },
  data () {
    return {
      homeReportData: {},
      inforCardData: [],
      pieData: [],
      barData: [],
      monthOrderStatisticsOption: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '订单金额',
            min: 0,
            max: 250,
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '订单数量',
            min: 0,
            max: 50,
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: []
      }
    }
  },
  watch: {
    $route (to, from) {
      // if (from.name === 'dashboard') {
      //   this.onHomeReport()
      // }
    }
  },
  methods: {

  },
  mounted () {

  }
}
</script>

<style lang="less">
.count-style {
  font-size: 22px;
}
</style>
