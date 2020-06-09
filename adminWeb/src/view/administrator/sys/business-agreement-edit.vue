<template>
  <Card>
    <div class="card-title">用户协议详情</div>
    <Divider />
    <Form :ref="formModal.formName"
          :model="formModal.formData"
          :rules="formModal.ruleValidate"
          :label-width="100">
      <FormItem label="协议名称"
                prop="name">
        <Input v-model="formModal.formData.name"
               placeholder="请输入协议名称"
               style="width:70%"></Input>
      </FormItem>
      <FormItem label="协议类型"
                prop="agreementType">
        <Select v-model="formModal.formData.agreementType"
                style="width:210px">
          <Option v-for="item in formModal.agreementTypeList"
                  :value="item.value"
                  :key="item.value">{{ item.label }}</Option>
        </Select>
      </FormItem>
      <Row>
        <Col span="6">
        <FormItem label="排序"
                  prop="sortId">
          <InputNumber :min="1"
                       v-model="formModal.formData.sortId"
                       style="width:210px"></InputNumber>
        </FormItem>
        </Col>
        <Col span="3">
        <FormItem label="默认模板"
                  style="width: 20%;float:left">
          <i-switch v-model="formModal.formData.defaultAgreement"
                    size="large">
            <span slot="open">默认</span>
            <span slot="close">其他</span>
          </i-switch>
        </FormItem>
        </Col>
        <Col span="3">
        <FormItem label="状态">
          <i-switch v-model="dicStatus"
                    size="large">
            <span slot="open">启用</span>
            <span slot="close">禁用</span>
          </i-switch>
        </FormItem>
        </Col>
      </Row>
      <FormItem label="备注"
                prop="remarks">
        <Input v-model="formModal.formData.remarks"
               placeholder="请输入备注"
               type="textarea"
               style="width:70%;"></Input>
      </FormItem>
      <FormItem label="协议内容"
                prop="agreementContent">
        <VueUeditorWrap v-model="formModal.formData.agreementContent"
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
import { agreementTypeList, add, update, queryById } from '@/api/administrator/sys/business-agreement'
export default {
  name: 'business_agreement_edit',
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
        formName: 'business_agreement_edit',
        agreementTypeList: [],
        formData: {
          agreementContent: '',
          agreementType: null,
          agreementTypeName: '',
          defaultAgreement: true,
          id: 0,
          name: '',
          remarks: '',
          sortId: 1,
          status: 1
        },
        ruleValidate: {
          name: [
            { required: true, message: '协议名称必填', trigger: 'blur' }
          ],
          agreementContent: [
            { required: true, message: '协议内容必填', trigger: 'blur' }
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
            update(input).then(result => {
              that.$Message.success('保存成功!')
              that.onBack(true)
            })
          } else {
            add(input).then(result => {
              that.$Message.success('保存成功!')
              that.onBack(true)
            })
          }
        } else {
          that.$Message.error('请检必填项后继续!')
          that.formModal.isUpdate = false
        }
      })
    },
    onBack (isRefresh) {
      this.$refs[this.formModal.formName].resetFields()
      this.$router.replace({
        name: 'business_agreement',
        query: {
          refresh: isRefresh || false
        }
      })
    },
    resetForm () {
      this.onBack(false)
    }
  },
  computed: {
    dicStatus: {
      get () {
        let status = this.formModal.formData.status === 1
        return status
      },
      set (newVal) {
        if (newVal) {
          this.formModal.formData.status = 1
        } else {
          this.formModal.formData.status = 2
        }
      }
    }
  },
  mounted () {
    let query = this.$route.query
    agreementTypeList().then(res => {
      res.forEach(item => {
        this.formModal.agreementTypeList.push({
          value: item.value,
          label: item.name
        })
      })
    })
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
