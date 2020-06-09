<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="less">
.size {
  width: 100%;
  height: 100%;
  overflow: auto;
}
html,
body {
  .size;
  overflow: hidden;
  margin: 0;
  padding: 0;
}
#app {
  .size;
}
.table-toolbar {
  margin-left: 10px;
}
.modal-form .ivu-modal-footer {
  text-align: left;
}
.modal-con-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
.modal-form .ivu-upload {
  width: 133px;
}

.vue-hide,
.vue-hide input,
div.vue-hide {
  visibility: hidden;
  width: 0;
  height: 0;
}
</style>
