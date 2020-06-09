<template>
  <Card>
    <div class="card-title">帮助文档详情</div>
    <Divider />
    <Form :ref="formModal.formName"
          :model="formModal.formData"
          :rules="formModal.ruleValidate"
          :label-width="100">
      <FormItem label="文件名称"
                prop="name">
        <Input v-model="formModal.formData.name"
               placeholder="请输入文件名称"
               style="width:70%"></Input>
      </FormItem>
      <FormItem label="友好名称"
                prop="friendlyName">
        <Input v-model="formModal.formData.friendlyName"
               placeholder="请输入友好名称"
               style="width:70%"></Input>
      </FormItem>
      <FormItem label="访问地址"
                prop="accessPath"
                v-if="formModal.isUpdate">
        <Input v-model="formModal.formData.accessPath"
               placeholder="未生成文件访问地址"
               readonly
               style="width:70%"></Input>
      </FormItem>
      <FormItem label="排序"
                prop="sortId">
        <InputNumber v-model="formModal.formData.sortId"
                     placeholder="请输入排序"
                     :min="1"
                     style="width:40%"></InputNumber>
      </FormItem>
      <FormItem label="备注"
                prop="remarks">
        <Input v-model="formModal.formData.remarks"
               placeholder="请输入备注"
               type="textarea"
               style="width:70%;"></Input>
      </FormItem>
      <FormItem label="文档内容"
                prop="htmlContent">
        <VueUeditorWrap v-model="formModal.formData.htmlContent"
                        :config="myConfig"></VueUeditorWrap>
      </FormItem>
      <FormItem>
        <Button type="primary"
                @click="onSave()">保存</Button>
        <Button @click="resetForm()"
                style="margin-left: 8px">取消</Button>
      </FormItem>
    </Form>
  </Card>
</template>
<script>
import VueUeditorWrap from 'vue-ueditor-wrap'
import { addFile, updateFile, queryById } from '@/api/administrator/sys/help-center'
export default {
  name: 'help_center_edit',
  components: {
    VueUeditorWrap
  },
  data () {
    return {
      myConfig: {
        // 编辑器不自动被内容撑高
        autoHeightEnabled: false,
        // 初始容器高度
        initialFrameHeight: 400,
        // 初始容器宽度
        initialFrameWidth: '100%',
        UEDITOR_HOME_URL: '/ueditor/'
      },
      formModal: {
        isUpdate: false,
        formName: 'help_center_edit',
        formData: {
          htmlContent: '',
          id: 0,
          name: '',
          friendlyName: '',
          parentId: null,
          remarks: '',
          sortId: 1,
          status: 1
        },
        ruleValidate: {
          name: [
            { required: true, message: '名称必填', trigger: 'blur' }
          ],
          friendlyName: [
            { required: true, message: '友好名称必填', trigger: 'blur' }
          ],
          htmlContent: [
            { required: true, message: '内容必填', trigger: 'blur' }
          ]
        }
      }
    }
  },
  methods: {
    onUpdate (id) {
      let _this = this
      queryById({
        id: id
      }).then(result => {
        _this.formModal.formData = result
      })
    },
    onSave () {
      let that = this
      that.$refs[that.formModal.formName].validate(valid => {
        if (valid) {
          let input = Object.assign({}, that.formModal.formData)
          if (that.formModal.isUpdate) {
            updateFile(input).then(result => {
              that.$Message.success('保存成功!')
              that.onBack(true)
            })
          } else {
            addFile(input).then(result => {
              that.$Message.success('保存成功!')
              that.onBack(true)
            })
          }
        } else {
          that.$Message.error('请检查必填项后继续!')
          that.formModal.isUpdate = false
        }
      })
    },
    onBack (isRefresh) {
      this.$refs[this.formModal.formName].resetFields()
      this.$router.replace({
        name: 'help_center',
        query: {
          refresh: isRefresh || false
        }
      })
    },
    resetForm () {
      this.onBack(false)
    }
  },
  mounted () {
    let query = this.$route.query
    this.formModal.formData.parentId = query && query.parentId ? query.parentId : null
    if (query && query.isUpdate) {
      this.formModal.isUpdate = query.isUpdate
      this.onUpdate(query.id)
    } else {
      this.formModal.isUpdate = false
      this.formModal.formData.sortId = query && query.sortId ? query.sortId : 1
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      let query = vm.$route.query
      vm.formModal.formData.parentId = query && query.parentId ? query.parentId : null
      if (query && query.isUpdate) {
        vm.formModal.isUpdate = query.isUpdate
        vm.onUpdate(query.id)
      } else {
        vm.formModal.isUpdate = false
        vm.formModal.formData.sortId = query && query.sortId ? query.sortId : 1
      }
    })
  }
}
</script>
