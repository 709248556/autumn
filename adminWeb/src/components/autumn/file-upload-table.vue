<template>
  <Card :bordered="false" :dis-hover="true">
    <Spin v-if="showLoadding"
          fix
          size="large">
      <Icon type="ios-loading"
            size="30"
            class="demo-spin-icon-load"></Icon>
      <div>{{uploadProgressMessage}}</div>
      <Progress :percent="progressPercentage" style="width:300px"></Progress>
    </Spin>
    <span v-if="supportUpload && showIdentification" style="float:left;margin:-12px 0px 15px -12px;font-size:12px">{{this.identificationTitle}}</span>
    <Select v-if="supportUpload && showIdentification" @on-change="onIdentificationChange"
                style="float:left;margin:-17px 0px 15px 10px;width:200px">
          <Option v-for="item in identificationItems"
                  :value="item.id"
                  :key="item.id">{{ item.name }}</Option>
    </Select>
    <Upload v-if="supportUpload" ref="upload"
                :action="uploadFileUrl"
                :accept="accept"
                :with-credentials="true"
                :before-upload="onUploadBefore"
                :on-progress ="onUploadProgress"
                :on-success="onUploadSuccess"
                :on-error="onUploadError"
                :on-exceeded-size="onUploadExceededSize"
                :show-upload-list="false"
                :max-size="maxSize"
                :style="showIdentification ? 'margin: -17px 0px 15px 250px;' : 'margin: -17px 0px 15px -15px;'">
            <Button type="primary" icon="ios-cloud-upload-outline">选择上传</Button>
    </Upload>
    <Button v-if="supportUpload" icon="md-trash" @click="onRemoveAllFile()"
           style="float:right;margin:-47px -15px 15px;">全部移除</Button>
    <slot name="opration"></slot>
    <div style="clear:both;"></div>
    <Table border
                :columns="columns"
                highlight-row
                :data="uploadFiles" style="margin: 0px -15px 0px -15px;"></Table>
  </Card>
</template>
<script>
import Action from '_c/action'
export default {
  name: 'FileUploadTable',
  components: {
    Action
  },
  props: {
    // 是否支持上传
    supportUpload: {
      type: Boolean,
      default () {
        return true
      }
    },
    uploadFileUrl: {
      type: String,
      default () {
        return ''
      }
    },
    accept: {
      type: String,
      default () {
        return ''
      }
    },
    uploadFiles: {
      type: Array,
      default () {
        return []
      }
    },
    // 最大限制，单位KB
    maxSize: {
      type: Number,
      default () {
        return null
      }
    },
    // 显示列标题
    explainColumnTitle: {
      type: String,
      default () {
        return '说明'
      }
    },
    // 显示标识
    showIdentification: {
      type: Boolean,
      default () {
        return false
      }
    },
    // 必须上传标识
    mustIdentification: {
      type: Boolean,
      default () {
        return true
      }
    },
    // 标识标题
    identificationTitle: {
      type: String,
      default () {
        return '上传标识'
      }
    },
    // 标识项目集合
    identificationItems: {
      type: Array,
      default () {
        // 格式 {id:null,name:''}
        return []
      }
    }
  },
  data () {
    return {
      identificationCurrentItem: {
        id: null,
        name: ''
      },
      showLoadding: false,
      uploadProgressMessage: '正在上传，请稍后...',
      columns: [
        {
          title: '序号',
          key: 'index',
          width: 60,
          render: (h, params) => {
            return h('div', [
              h('span', params.index + 1)
            ])
          }
        },
        {
          title: '名称',
          key: 'fileFriendlyName',
          minWidth: 200,
          render: (h, params) => {
            return h('div', [
              h('span', params.row.fileFriendlyName + '.' + params.row.extensionName)
            ])
          }
        },
        {
          title: this.explainColumnTitle,
          key: 'uploadExplain',
          minWidth: 150
        },
        {
          title: '大小',
          key: 'fileFriendlyLength',
          width: 100
        },
        {
          title: '上传人',
          key: 'uploadUserName',
          width: 120
        },
        {
          title: '上传时间',
          key: 'uploadTime',
          width: 150
        },
        {
          title: '操作',
          key: 'action',
          width: 160,
          fixed: 'right',
          align: 'left',
          render: (h, params) => {
            let result = []
            let buttons = []
            if (params.row.extensionName.toLowerCase() === 'jpg' ||
              params.row.extensionName.toLowerCase() === 'jpeg' ||
              params.row.extensionName.toLowerCase() === 'png' ||
              params.row.extensionName.toLowerCase() === 'gif') {
              buttons.push({
                key: 'imageView',
                type: 'primary',
                size: 'small',
                icon: 'md-eye',
                name: '预览'
              })
            }
            buttons.push({
              key: 'download',
              type: 'primary',
              size: 'small',
              icon: 'md-download',
              name: '下载'
            })
            if (this.supportUpload) {
              result.push(
                h(
                  'Button',
                  {
                    props: {
                      type: 'error',
                      size: 'small',
                      icon: 'md-trash'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.onRemoveFile(params)
                      }
                    }
                  },
                  '移除'
                )
              )
            }
            result.push(
              h(Action, {
                props: {
                  value: buttons
                },
                on: {
                  click: key => {
                    switch (key) {
                      case 'imageView':
                        this.$Modal.info({
                          title: '预览',
                          width: '770px',
                          content: `<img src="${params.row.accessUrlPath}" style="width:640px;height:640px"/>`
                        })
                        break
                      case 'download':
                        window.open(params.row.accessUrlPath, '_blank')
                        break
                    }
                  }
                }
              })
            )
            return h('div', result)
          }
        }
      ]
    }
  },
  computed: {
    uploadList () {
      return this.$refs.upload ? this.$refs.upload.fileList : []
    },
    // 暂不支持方多文件，如果改为支持多文件会造成计算问题
    progressPercentage () {
      if (this.showLoadding) {
        if (this.uploadList.length === 0) {
          return 0
        }
        let value = this.uploadList[0].percentage
        if (value >= 100) {
          return 98
        }
        return Math.floor(value)
      } else {
        return 0
      }
    }
  },
  methods: {
    // 选择标识变更
    onIdentificationChange (value) {
      let items = this.identificationItems.filter(item => {
        return item.id === value
      })
      if (items != null && items.length > 0) {
        this.identificationCurrentItem.id = items[0].id
        this.identificationCurrentItem.name = items[0].name
      } else {
        this.identificationCurrentItem.id = null
        this.identificationCurrentItem.name = ''
      }
    },
    // 暂不支持方多文件，如果改为支持多文件会造成计算问题
    onUploadBefore (file) {
      if (this.showIdentification && this.mustIdentification) {
        if (!this.identificationCurrentItem.id) {
          this.$Message.error(
            '必须选择[' + this.identificationTitle + ']'
          )
          return false
        }
      }
      // let check = this.uploadList.length < 5
      // if (!check) {
      //  this.$Notice.warning({
      //    title: '最多只能上传 5 个文件。'
      //  })
      // }
      this.onClearUploadFile()
      return true
    },
    onClearUploadFile () {
      this.$refs.upload.clearFiles()
    },
    onUploadProgress (event, file, fileList) {
      if (!this.showLoadding) {
        this.showLoadding = true
      }
    },
    onUploadExceededSize (file, fileList) {
      this.showLoadding = false
      this.$Message.error('文件[' + file.name + ']太大，超过上传要求限制。')
    },
    onUploadSuccess (response, file, fileList) {
      this.showLoadding = false
      if (response.success) {
        this.$Message.success('上传成功')
        let item = response.result
        if (this.showIdentification && this.identificationCurrentItem.id) {
          item.identification = this.identificationCurrentItem.id
          item.uploadExplain = this.identificationCurrentItem.name
        }
        this.uploadFiles.push(item)
      } else {
        let message = response.error ? response.error.message : '未知错误'
        this.$Message.error('上传失败：' + message)
      }
      this.onClearUploadFile()
    },
    onUploadError (error, file, fileList) {
      this.showLoadding = false
      this.onClearUploadFile()
      let message = error.message || '检查网络是否正常，服务器是否限制上传大小。'
      this.$Message.error('上传失败：' + message)
    },
    onRemoveFile (params) {
      let item = this.uploadFiles[params.index]
      this.$Modal.confirm({
        title: '提示',
        content: '<p>确定要移除当前文件[' + item.fileFriendlyName + '.' + item.extensionName + ']吗？</p>',
        onOk: () => {
          this.uploadFiles.splice(params.index, 1)
        },
        onCancel: () => { }
      })
    },
    onRemoveAllFile () {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>确定要全部移除所有文件吗？</p>',
        onOk: () => {
          this.onClearUploadFile()
          if (this.uploadFiles.length > 0) {
            this.uploadFiles.splice(0, this.uploadFiles.length)
          }
        },
        onCancel: () => { }
      })
    }
  },
  mounted () {

  }
}
</script>
<style>
.demo-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
@keyframes ani-demo-spin {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
.demo-spin-col {
  height: 100px;
  position: relative;
  border: 1px solid #eee;
}
</style>
