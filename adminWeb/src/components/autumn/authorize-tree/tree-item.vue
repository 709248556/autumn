<template>
  <CheckboxGroup style="width:200px"
                 v-model="selectedItems"
                 @on-change="onChageItems">
    <Checkbox v-for="item in this.treeNode.operationPermissions"
              :label="item.friendlyName"
              v-model="item.checked"
              :key="item.resourcesId + ':' + item.permissionName"></Checkbox>
  </CheckboxGroup>
</template>
<script>
export default {
  name: 'TreeItem',
  props: {
    treeNode: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  data () {
    return {
      selectedItems: []
    }
  },
  methods: {
    onChageItems () {
      this.treeNode.operationPermissions.forEach(item => {
        if (this.selectedItems.indexOf(item.friendlyName) === -1) {
          item.checked = false
        } else {
          item.checked = true
          if (!item.parent.checked) {
            item.parent.checked = true
          }
        }
      })
      this.$emit('on-change', this.selectedItems)
    },
    onLoadSelectedItems () {
      this.selectedItems = []
      this.treeNode.operationPermissions = this.treeNode.operationPermissions || []
      this.treeNode.operationPermissions.forEach(item => {
        if (item.checked) {
          this.selectedItems.push(item.friendlyName)
        }
      })
    }
  },
  watch: {
    treeNode () {
      this.onLoadSelectedItems()
      this.onChageItems()
    }
  },
  mounted () {
    this.onLoadSelectedItems()
    let _this = this
    this.treeNode.callback = function () {
      _this.onLoadSelectedItems()
    }
  }
}
</script>
