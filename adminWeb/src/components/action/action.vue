<template>
  <Dropdown placement="bottom-start"
            :transfer="true"
            @on-click="click">
    <Button :type="type"
            :size="size">
      {{title}}
      <Icon type="ios-arrow-down"></Icon>
    </Button>
    <DropdownMenu slot="list">
      <DropdownItem v-for="item in value"
                    :name="item.key"
                    :key="item.key">
        <Icon v-if="item.icon"
              :type="item.icon"></Icon>
        {{item.name}}
      </DropdownItem>
    </DropdownMenu>
  </Dropdown>
</template>

<script>
export default {
  name: 'Action',
  props: {
    value: {
      type: Array,
      default () {
        return []
      }
    },
    title: {
      type: String,
      default () {
        return '更多'
      }
    },
    type: {
      type: String,
      default () {
        return 'primary'
      }
    },
    size: {
      type: String,
      default () {
        return 'small'
      }
    },
    attr: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  methods: {
    click (key) {
      this.$emit('click', key, this.attr)
    }
  }
}
</script>
