<template>
  <Card>
    <div class="card-title">系统信息配置</div>
    <div style="width: 100%; margin: 0 auto;">
      <Form :ref="formModal.formName"
            :model="formModal.formData"
            :rules="formModal.ruleValidate"
            :label-width="200">
        <Tabs value="basic-info">
          <TabPane label="单位信息"
                   name="basic-info">
            <FormItem label="单位名称"
                      prop="company">
              <Input v-model="formModal.formData.company"
                     placeholder="请输入单位名称"></Input>
            </FormItem>
            <FormItem label="英文单位名称"
                      prop="companyEnglish">
              <Input v-model="formModal.formData.companyEnglish"
                     placeholder="请输入英文单位名称"></Input>
            </FormItem>
            <FormItem label="单位简称"
                      prop="companySimpleName">
              <Input v-model="formModal.formData.companySimpleName"
                     placeholder="请输入单位简称"></Input>
            </FormItem>
            <FormItem label="单位英文简称"
                      prop="companyEnglishSimpleName">
              <Input v-model="formModal.formData.companyEnglishSimpleName"
                     placeholder="请输入单位英文简称"></Input>
            </FormItem>
            <FormItem label="联系电话"
                      prop="contactPhone">
              <Input v-model="formModal.formData.contactPhone"
                     placeholder="请输入联系电话"></Input>
            </FormItem>
            <FormItem label="logo图片"
                      prop="logoUploadId">
              <ImageUploadSingle :uploadImageUrl="formModal.uploadFileUrl"
                               :max-size="5120"
                               :showImageSize="120"
                               :showImageUrl="formModal.formData.logoUploadFile.accessUrlPath"
                               @on-set-upload-image="onSetLogoUploadImage"
                               @on-clear-upload-image="onClearLogoUploadImage">
              </ImageUploadSingle>
            </FormItem>
          </TabPane>
          <TabPane label="站点信息配置"
                   name="site-configure">
            <FormItem label="站点名称"
                      prop="siteName">
              <Input v-model="formModal.formData.siteName"
                     placeholder="请输入站点名称"></Input>
            </FormItem>
            <FormItem label="站点英文名称"
                      prop="siteEnglishName">
              <Input v-model="formModal.formData.siteEnglishName"
                     placeholder="请输入站点英文名称"></Input>
            </FormItem>
            <FormItem label="站点简称"
                      prop="siteSimpleName">
              <Input v-model="formModal.formData.siteSimpleName"
                     placeholder="请输入站点简称"></Input>
            </FormItem>
            <FormItem label="站点英文简称"
                      prop="siteEnglishSimpleName">
              <Input v-model="formModal.formData.siteEnglishSimpleName"
                     placeholder="请输入站点英文简称"></Input>
            </FormItem>
            <FormItem label="网站地址"
                      prop="siteAddress">
              <Input v-model="formModal.formData.siteAddress"
                     placeholder="请输入网站地址"></Input>
            </FormItem>
            <FormItem label="站点备案号"
                      prop="siteKeepOnRecordNo">
              <Input v-model="formModal.formData.siteKeepOnRecordNo"
                     placeholder="请输入站点备案号"></Input>
            </FormItem>
            <FormItem label="站点帮助文档号"
                      prop="siteHelpDocNo">
              <Input v-model="formModal.formData.siteHelpDocNo"
                     placeholder="请输入站点帮助文档号"></Input>
            </FormItem>
            <FormItem label="站点登录Banner图片"
                      prop="siteLoginBannerUploadId">
              <ImageUploadSingle :uploadImageUrl="formModal.uploadFileUrl"
                               :max-size="5120"
                               :showImageSize="120"
                               :showImageUrl="formModal.formData.siteLoginBannerUploadFile.accessUrlPath"
                               @on-set-upload-image="onSetSiteUploadImage"
                               @on-clear-upload-image="onClearSiteUploadImage">
              </ImageUploadSingle>
            </FormItem>
          </TabPane>
          <TabPane label="网站后台配置"
                   name="admin-configure">
            <FormItem label="系统后台名称"
                      prop="systemName">
              <Input v-model="formModal.formData.systemName"
                     placeholder="请输入系统后台名称"></Input>
            </FormItem>
            <FormItem label="系统后台英文名称"
                      prop="systemEnglishName">
              <Input v-model="formModal.formData.systemEnglishName"
                     placeholder="请输入系统后台英文名称"></Input>
            </FormItem>
            <FormItem label="系统后台简称"
                      prop="systemSimpleName">
              <Input v-model="formModal.formData.systemSimpleName"
                     placeholder="请输入系统后台简称"></Input>
            </FormItem>
            <FormItem label="系统后台英文简称"
                      prop="systemEnglishSimpleName">
              <Input v-model="formModal.formData.systemEnglishSimpleName"
                     placeholder="请输入系统后台英文简称"></Input>
            </FormItem>
            <FormItem label="网站后台地址"
                      prop="systemAddress">
              <Input v-model="formModal.formData.systemAddress"
                     placeholder="请输入网站后台地址"></Input>
            </FormItem>
            <FormItem label="网站后台备案号"
                      prop="systemKeepOnRecordNo">
              <Input v-model="formModal.formData.systemKeepOnRecordNo"
                     placeholder="请输入网站后台备案号"></Input>
            </FormItem>
            <FormItem label="网站后台帮助文档号"
                      prop="systemHelpDocNo">
              <Input v-model="formModal.formData.systemHelpDocNo"
                     placeholder="请输入网站后台帮助文档号"></Input>
            </FormItem>
            <FormItem label="站点登录Banner图片"
                      prop="systemLoginBannerUploadId">
              <ImageUploadSingle :uploadImageUrl="formModal.uploadFileUrl"
                               :max-size="5120"
                               :showImageSize="120"
                               :showImageUrl="formModal.formData.systemLoginBannerUploadFile.accessUrlPath"
                               @on-set-upload-image="onSetSystemLoginUploadImage"
                               @on-clear-upload-image="onClearSystemLoginUploadImage">
              </ImageUploadSingle>              <
            </FormItem>
          </TabPane>
        </Tabs>
        <FormItem>
          <Button type="primary"
                  @click="onSave">保存</Button>
        </FormItem>
      </Form>
    </div>
  </Card>
</template>

<script>
import { mapActions } from 'vuex'
import { query, save, uploadFileUrl } from '@/api/administrator/sys/app-configre'
import ImageUploadSingle from '@/components/autumn/image-upload-single'
export default {
  name: 'app_onfigre',
  components: {
    ImageUploadSingle
  },
  data () {
    return {
      formModal: {
        formName: 'AppConfigre',
        uploadFileUrl: uploadFileUrl,
        formData: {
          company: '',
          companyEnglish: '',
          companyEnglishSimpleName: '',
          companySimpleName: '',
          contactPhone: '',
          logoUploadFile: null,
          logoUploadId: 0,
          siteAddress: '',
          siteEnglishName: '',
          siteEnglishSimpleName: '',
          siteKeepOnRecordNo: '',
          siteLoginBannerUploadFile: null,
          siteLoginBannerUploadId: 0,
          siteName: '',
          siteSimpleName: '',
          siteHelpDocNo: null,
          systemAddress: '',
          systemEnglishName: '',
          systemEnglishSimpleName: '',
          systemKeepOnRecordNo: '',
          systemLoginBannerUploadFile: null,
          systemLoginBannerUploadId: 0,
          systemName: '',
          systemSimpleName: '',
          systemHelpDocNo: null
        },
        ruleValidate: {
          company: [
            { required: true, message: '单位名称必填', trigger: 'blur' },
            { type: 'string', max: 100, message: '单位名称不能超过100个字符', trigger: 'blur' }
          ],
          // companyEnglish: [
          //   { required: true, message: '单位英文名称必填', trigger: 'blur' },
          //   { type: 'string', max: 100, message: '单位英文名称不能超过100个字符', trigger: 'blur' }
          // ],
          companySimpleName: [
            { required: true, message: '单位简称必填', trigger: 'blur' },
            { type: 'string', max: 50, message: '单位简称不能超过50个字符', trigger: 'blur' }
          ],
          // companyEnglishSimpleName: [
          //   { required: true, message: '单位英文简称必填', trigger: 'blur' },
          //   { type: 'string', max: 50, message: '单位英文简称不能超过50个字符', trigger: 'blur' }
          // ],
          siteName: [
            { required: true, message: '站点名称必填', trigger: 'blur' },
            { type: 'string', max: 100, message: '站点名称不能超过100个字符', trigger: 'blur' }
          ],
          // siteEnglishName: [
          //   { required: true, message: '站点英文名称必填', trigger: 'blur' },
          //   { type: 'string', max: 100, message: '站点英文名称不能超过100个字符', trigger: 'blur' }
          // ],
          siteSimpleName: [
            { required: true, message: '站点简称必填', trigger: 'blur' },
            { type: 'string', max: 50, message: '站点简称不能超过50个字符', trigger: 'blur' }
          ],
          // siteEnglishSimpleName: [
          //   { required: true, message: '站点英文简称必填', trigger: 'blur' },
          //   { type: 'string', max: 50, message: '站点英文简称不能超过50个字符', trigger: 'blur' }
          // ],
          systemName: [
            { required: true, message: '系统后台名称必填', trigger: 'blur' },
            { type: 'string', max: 100, message: '系统后台名称不能超过100个字符', trigger: 'blur' }
          ],
          // systemEnglishName: [
          //   { required: true, message: '系统后台英文名称必填', trigger: 'blur' },
          //   { type: 'string', max: 100, message: '系统后台英文名称不能超过100个字符', trigger: 'blur' }
          // ],
          systemSimpleName: [
            { required: true, message: '系统后台简称必填', trigger: 'blur' },
            { type: 'string', max: 50, message: '系统后台简称不能超过50个字符', trigger: 'blur' }
          ],
          // systemEnglishSimpleName: [
          //   { required: true, message: '系统后台英文简称必填', trigger: 'blur' },
          //   { type: 'string', max: 50, message: '系统后台英文简称不能超过50个字符', trigger: 'blur' }
          // ],
          contactPhone: [
            { type: 'string', max: 50, message: '联系电话不能超过50个字符', trigger: 'blur' }
          ],
          siteAddress: [
            { type: 'string', max: 255, message: '网站地址不能超过255个字符', trigger: 'blur' }
          ],
          siteKeepOnRecordNo: [
            { type: 'string', max: 100, message: '站点备案号不能超过100个字符', trigger: 'blur' }
          ],
          systemAddress: [
            { type: 'string', max: 255, message: '站点备案号不能超过255个字符', trigger: 'blur' }
          ],
          systemKeepOnRecordNo: [
            { type: 'string', max: 100, message: '网站后台备案号不能超过100个字符', trigger: 'blur' }
          ]
        }
      }
    }
  },
  methods: {
    ...mapActions([
      'syncSystemInfo'
    ]),
    // 保存机构信息
    onSave () {
      let that = this
      this.$refs[this.formModal.formName].validate((valid) => {
        if (valid) {
          let input = Object.assign({}, that.formModal.formData)

          if (input.logoUploadFile) {
            input.logoUploadId = input.logoUploadFile.id
            delete input.logoUploadFile
          }

          if (input.siteLoginBannerUploadFile) {
            input.siteLoginBannerUploadId = input.siteLoginBannerUploadFile.id
            delete input.siteLoginBannerUploadFile
          }

          if (input.systemLoginBannerUploadFile) {
            input.systemLoginBannerUploadId = input.systemLoginBannerUploadFile.id
            delete input.systemLoginBannerUploadFile
          }

          save(input).then(res => {
            this.$Message.success('保存成功!')
            this.formModal.formData.id = res.id
            this.syncSystemInfo()
          })
        } else {
          this.$Message.error('请检查必填项后继续!')
        }
      })
    },
    onClearLogoUploadImage () {
      this.formModal.formData.logoUploadFile = this.formModal.formData.logoUploadFile || {}
      this.formModal.formData.logoUploadFile.accessUrlPath = ''
    },
    onSetLogoUploadImage (result) {
      this.formModal.formData.logoUploadFile = result
    },
    onClearSiteUploadImage () {
      this.formModal.formData.siteLoginBannerUploadFile = this.formModal.formData.siteLoginBannerUploadFile || {}
      this.formModal.formData.siteLoginBannerUploadFile.accessUrlPath = ''
    },
    onSetSiteUploadImage (result) {
      this.formModal.formData.siteLoginBannerUploadFile = result
    },
    onClearSystemLoginUploadImage () {
      this.formModal.formData.systemLoginBannerUploadFile = this.formModal.formData.systemLoginBannerUploadFile || {}
      this.formModal.formData.systemLoginBannerUploadFile.accessUrlPath = ''
    },
    onSetSystemLoginUploadImage (result) {
      this.formModal.formData.systemLoginBannerUploadFile = result
    }
  },
  mounted () {
    query().then(result => {
      this.formModal.formData = result
      this.formModal.formData.logoUploadFile = this.formModal.formData.logoUploadFile || {}
      this.formModal.formData.siteLoginBannerUploadFile = this.formModal.formData.siteLoginBannerUploadFile || {}
      this.formModal.formData.systemLoginBannerUploadFile = this.formModal.formData.systemLoginBannerUploadFile || {}
    })
  }
}
</script>
