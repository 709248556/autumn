<template>
  <div class="header-bar">
    <div class="custom-content-con">
      <slot></slot>
    </div>
  </div>
</template>
<script>
import './header-bar.less'
export default {
  name: 'HeaderBar',
  methods: {
    handleCollpasedChange (state) {
      this.$emit('on-coll-change', state)
    }
  }
}
</script>
