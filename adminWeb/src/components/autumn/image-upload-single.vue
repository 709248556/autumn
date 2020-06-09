<template>
  <div>
    <div class="image-upload" :style="imageDivStyle">
    <Spin v-if="showLoadding"
          fix
          size="large">
      <Icon type="ios-loading"
            size="30"
            class="demo-spin-icon-load"></Icon>
    </Spin>
    <template v-if="showImageUrl">
        <img :src="showImageUrl">
        <div class="image-upload-cover">
          <Icon type="ios-eye-outline"
                size="40"
                @click.native="onPreviewImage()"></Icon>
          <Icon type="ios-trash-outline"
                size="40"
                @click.native="onClearImage()"></Icon>
        </div>
    </template>
    <Upload :action="uploadImageUrl"
            :with-credentials="true"
            :on-success="onUploadSuccess"
            :show-upload-list="false"
            :max-size="maxSize"
            type="drag"
            :on-progress ="onUploadProgress"
            :on-format-error="handleFormatError"
            :on-exceeded-size="handleMaxSize"
            accept="image/gif,image/jpeg,image/png"
            :style="uploadStyle">
      <div :style="uploadDivStyle">
          <Icon type="ios-camera" size="80"></Icon>
        </div>
    </Upload>
    </div>
    <Modal title="预览图片"
           :width="800"
            class-name="vertical-center-modal"
            v-model="previewShow">
        <img :src="showImageUrl"
            v-if="previewShow"
            style="width: 100%">
    </Modal>
  </div>
</template>
<script>
export default {
  props: {
    uploadImageUrl: {
      type: String,
      default () {
        return ''
      }
    },
    // 最大限制，单位KB
    maxSize: {
      type: Number,
      default () {
        return null
      }
    },
    showImageSize: {
      type: Number,
      default () {
        return 120
      }
    },
    showImageUrl: {
      type: String,
      default () {
        return ''
      }
    }
  },
  data () {
    return {
      previewShow: false,
      showLoadding: false,
      uploadProgressMessage: '正在上传'
    }
  },
  computed: {
    imageDivStyle () {
      return 'width:' + this.showImageSize + 'px;height:' + (this.showImageSize + 4) + 'px;'
    },
    uploadStyle () {
      return 'display: inline-block;width:' + this.showImageSize + 'px;'
    },
    uploadDivStyle () {
      return 'width:' + this.showImageSize + 'px;height:' + this.showImageSize + 'px;line-height: ' + this.showImageSize + 'px;'
    }
  },
  methods: {
    onUploadSuccess (response, file, fileList) {
      this.showLoadding = false
      if (response.success) {
        this.$Message.success('上传成功')
        this.$emit('on-set-upload-image', response.result)
      } else {
        this.$emit('on-clear-upload-image')
        let message = response.error ? response.error.message : '上传失败'
        this.$Message.error(message)
      }
    },
    onUploadProgress (event, file, fileList) {
      if (!this.showLoadding) {
        this.showLoadding = true
      }
    },
    handleFormatError (file) {
      this.showLoadding = false
      this.$Notice.warning({
        title: '文件格式不正确',
        desc: '当前上传的文件 ' + file.name + ' 格式不正确, 请选择图片文件上传。'
      })
    },
    handleMaxSize (file) {
      this.showLoadding = false
      this.$Notice.warning({
        title: '超出文件大小限制',
        desc: '当前文件  ' + file.name + ' 大小超出限制。'
      })
    },
    onPreviewImage () {
      this.previewShow = true
    },
    onClearImage () {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>确定要移除吗？</p>',
        onOk: () => {
          this.$emit('on-clear-upload-image')
        },
        onCancel: () => { }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.image-upload {
  display: inline-block;
  width: 200px;
  height: 204px;
  text-align: center;
  line-height: 200px;
  border: 0px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  margin-right: 0px;
}
.image-upload img {
  width: 100%;
  height: 100%;
}
.image-upload-cover {
  display: none;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
}
.image-upload:hover .image-upload-cover {
  display: block;
}
.image-upload-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}
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
