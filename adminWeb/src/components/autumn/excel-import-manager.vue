<template>
  <div>
    <Modal :title="formModal.title"
            v-model="formModal.show"
            class-name="vertical-center-modal"
            :width="formModal.width"
            :mask-closable="false"
            :footer-hide="true"
            :closable="false">
      <Form :ref="formModal.formName"
            :model="formModal.formData"
            :label-width="80">
        <Spin fix
                    v-if="formModal.loadding">
                <Icon type="ios-loading"
                      size=30
                      class="modal-con-spin-icon-load"></Icon>
                <div>上传并导入中，请稍后...</div>
        </Spin>
        <FormItem label="导入说明：">
          <div><span style="font-size:14px">{{formModal.importExplain}}</span></div>
        </FormItem>
        <FormItem label="Excel文件：">
              <div style="display:flex">
                <Upload :action="formModal.uploadFileUrl"
                        accept=".xlsx"
                        :with-credentials="true"
                        :before-upload="onUploadBefore"
                        :on-success="onUploadSuccess"
                        :on-error="onUploadError"
                        :show-upload-list="false"
                        style="margin-bottom: 20px;">
                  <Button type="primary" icon="ios-cloud-upload-outline">上传导入</Button>
                </Upload>
              </div>
              <span class="file-name">{{formModal.importFileName}}</span>
          </FormItem>
        <FormItem label="导入信息：">
          <div class="upload-Info"><span>{{formModal.importInfo}}</span></div>
          <br/>
          <span class="import-total">累计成功：{{formModal.importTotal}} 条记录</span>
        </FormItem>
        <FormItem>
          <Button type="success"
              v-if="formModal.importTemplateApi!==null"
              @click="downloadTemplate()"
              icon="md-download">下载模板</Button>
          <Button @click="closeImport()"
                 style="margin-left: 10px" icon="md-close">关闭</Button>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script>
export default {
  name: 'ExcelImportManager',
  components: {

  },
  data () {
    return {
      formModal: {
        show: false,
        formName: 'excelImport',
        width: 600,
        title: 'Excel文件导入',
        uploadFileUrl: '',
        importTemplateApi: null,
        importExplain: '请先下载模板文件，根据导入模板文件填写数据，删除多余的数据行，只支持 Excel 2010 以上格式文件(xlsx)，如果不是Excel 2010以上格式，请在Excel中另存为 “Excel 工作簿(*.xlsx)” 文件。',
        importInfo: '',
        importFileName: '',
        importTotal: 0,
        refreshApi: null,
        loadding: false,
        formData: {

        }
      }
    }
  },
  methods: {
    openImport (uploadUrl, downloadImportTemplateApi, importTitle, refreshCallback) {
      this.formModal.loadding = false
      this.formModal.uploadFileUrl = uploadUrl || ''
      this.formModal.importTemplateApi = downloadImportTemplateApi
      this.formModal.importInfo = '待导入...'
      this.formModal.importFileName = ''
      this.formModal.importTotal = 0
      this.formModal.refreshApi = refreshCallback
      this.formModal.title = importTitle || 'Excel文件导入'
      this.formModal.show = true
    },
    onUploadBefore (file) {
      let exceededNum = file.name.lastIndexOf('.')
      let exceededStr = file.name.substring(exceededNum + 1, file.name.length).trim()
      if (exceededStr.toLowerCase() !== 'xlsx') {
        this.formModal.importInfo = file.name + ' 文件格式不正确, 请选择Excel 2010格式以上的文件(*.xlsx)'
        return false
      }
      this.formModal.loadding = true
      this.formModal.importFileName = file.name
      this.formModal.importInfo = '正在上传和导入...'
      return true
    },
    onUploadSuccess (response, file, fileList) {
      this.formModal.loadding = false
      if (response.success) {
        this.formModal.importTotal += response.result
        this.formModal.importInfo = '导入成功，总共 ' + response.result + ' 条记录。'
        if (this.formModal.refreshApi != null) {
          this.formModal.refreshApi()
        }
      } else {
        let message = response.error ? response.error.message : '未知错误'
        this.formModal.importInfo = '导入出错：' + message
      }
    },
    onUploadError (error, file, fileList) {
      this.formModal.loadding = false
      let message = error.message || '检查网络是否正常，服务器是否限制上传大小。'
      this.formModal.importInfo = '上传失败：' + message
      this.$Message.error('上传失败：' + message)
    },
    downloadTemplate () {
      if (this.formModal.importTemplateApi !== null) {
        this.formModal.importTemplateApi()
          .then(result => {
            this.$Message.success('生成Excel模板成功，正在下载...')
            window.location.href = result.accessUrlPath
          })
      }
    },
    closeImport () {
      this.formModal.importTemplateApi = null
      this.formModal.refreshApi = null
      this.formModal.show = false
    }
  },
  mounted () {

  }
}
</script>
<style>
.modal-con-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
.modal-form .ivu-upload {
  width: 133px;
}
.upload-Info {
    display: inline-block;
    font-size: 14px;
    font-weight: 700;
    color: #666666;
}
.file-name {
    display: inline-block;
    font-size: 14px;
    color: #0d0242;
}
.import-total {
    font-size: 14px;
    font-weight: 700;
    color: #0d045e;
}
</style>
