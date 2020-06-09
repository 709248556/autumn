<style>
.icons {
  overflow: hidden;
  zoom: 1;
  height: 400px;
  overflow-y: scroll;
}
.icons-item {
  float: left;
  margin: 6px 6px 6px 0;
  width: 145px;
  text-align: center;
  list-style: none;
  cursor: pointer;
  height: 100px;
  color: #5c6b77;
  transition: all 0.2s ease;
  position: relative;
  padding-top: 10px;
}
.icons-item p {
  padding-top: 15px;
}
.icon-search {
  position: relative;
  margin: 20px auto 30px;
  text-align: center;
}
.icon-search input {
  width: 500px;
  box-sizing: border-box;
  border: 0;
  border-radius: 4px;
  background: #f5f5f5;
  text-align: center;
  font-size: 14px;
  outline: none;
  margin: 0 auto;
  padding: 8px 0;
}
.icon-search p {
  margin-top: 20px;
}
</style>

<template>
  <div>
    <Row type="flex">
      <Col span="24">
      <div class="icon-search">
        <input type="text"
               :placeholder="searchPlaceholder"
               @focus="changeHolder('focus')"
               @blur="changeHolder('blur')"
               v-model="search">
        <p>点击下面的图标按钮可以直接选中图标</p>
      </div>
      </Col>
    </Row>
    <div class="icons">
      <div class="icons-item"
           v-for="item in filterIcons"
           @click="onClickIcon(item)"
           :key="item.name">
        <i class="ivu-icon"
           :class="[`ivu-icon-${item.name}`]"
           style="font-size:32px"></i>
        <p>
          {{ item.name }}
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import Icons from './icon-names'
const searchPlaceholder = '输入英文关键词搜索，比如 success'

export default {
  name: 'IconSelector',
  data () {
    return {
      icons: Icons,
      searchPlaceholder: searchPlaceholder,
      search: ''
    }
  },
  computed: {
    filterIcons () {
      return this.icons.filter(icon => icon.tag.indexOf(this.search) > -1)
    }
  },
  methods: {
    onClickIcon (type) {
      this.$emit('on-select', type)
    },
    changeHolder (type) {
      if (type === 'focus') {
        this.searchPlaceholder = ''
      } else if (type === 'blur') {
        this.searchPlaceholder = searchPlaceholder
      }
    }
  }
}
</script>
