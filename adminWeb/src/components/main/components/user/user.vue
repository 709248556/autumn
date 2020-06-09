<template>
  <div class="user-avator-dropdown">
    <Dropdown @on-click="handleClick">
      <Badge :dot="!!messageUnreadCount">
        <Avatar :src="userAvator" />
      </Badge>
      <span style="margin: 0 10px; color: #fff;">{{userName}}</span>
      <Icon :size="18"
            type="md-arrow-dropdown"></Icon>
      <DropdownMenu slot="list">
        <!-- <DropdownItem name="message">
          消息中心<Badge style="margin-left: 10px"
                 :count="messageUnreadCount"></Badge>
        </DropdownItem> -->
        <DropdownItem name="userinfo">个人资料</DropdownItem>
        <DropdownItem name="modifyPwd">修改密码</DropdownItem>
        <DropdownItem name="bindMobile">绑定手机</DropdownItem>
        <DropdownItem name="logout">退出登录</DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <!-- 修改密码弹窗 -->
    <Modal :title="pwdModel.title"
           v-model="pwdModel.show"
           class-name="vertical-center-modal"
           :width="pwdModel.width"
           :mask-closable="false"
           :footer-hide="true"
           :closable="false">
      <Form :ref="pwdModel.formName"
            :model="pwdModel.formData"
            :rules="pwdModel.ruleValidate"
            :label-width="100">
        <FormItem label="旧密码"
                  prop="oldPassword">
          <Input v-model="pwdModel.formData.oldPassword"
                 placeholder="请输入旧密码"
                 style="width:260px"></Input>
        </FormItem>
        <FormItem label="新密码"
                  prop="newPassword">
          <Input v-model="pwdModel.formData.newPassword"
                 placeholder="请输入新密码"
                 style="width:260px"></Input>
        </FormItem>
        <FormItem>
          <Button type="primary"
                  @click="pwdSave()">保存</Button>
          <Button @click="pwdResetForm(pwdModel.formName)"
                  style="margin-left: 8px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <!-- 绑定手机号弹窗 -->
    <Modal :title="bindModel.title"
           v-model="bindModel.show"
           class-name="vertical-center-modal"
           :width="bindModel.width"
           :mask-closable="false"
           :footer-hide="true"
           :closable="false">
      <Form :ref="bindModel.formName"
            :model="bindModel.formData"
            :rules="bindModel.ruleValidate"
            :label-width="100">
        <FormItem label="手机号"
                  prop="mobilePhone">
          <Input v-model="bindModel.formData.mobilePhone"
                 placeholder="请输入手机号"
                 style="width:260px"></Input>
        </FormItem>
        <FormItem label="图形验证码"
                  prop="imageCode">
          <Input type="text"
                 v-model="bindModel.formData.imageCode"
                 name="imageCode"
                 style="width: 160px"
                 placeholder="请输入您的图形验证码"></Input>
          <img :src="'data:image/png;base64,'+imageCodeBase64"
               @click="getImgCaptcha"
               style="vertical-align: middle;"
               alt="">
        </FormItem>
        <FormItem label="短信验证码"
                  prop="smsCode">
          <Input type="text"
                 v-model="bindModel.formData.smsCode"
                 name="smsCode"
                 id="smsCode"
                 style="width: 160px"
                 placeholder="请输入验证码"></Input>
          <a href="javascript: void(0)"
             class="a-link"
             style="margin-left: 20px"
             @click="sendSmsCode"
             :disabled="sendButton.disable">{{sendButton.text}}</a>
        </FormItem>
        <FormItem>
          <Button type="primary"
                  @click="bindSave()">保存</Button>
          <Button @click="bindResetForm(bindModel.formName)"
                  style="margin-left: 8px">取消</Button>
        </FormItem>
      </Form>
    </Modal>
    <!-- 用户资料 -->
    <Modal v-model="userinfoModal.show"
           class-name="vertical-center-modal"
           :title="userinfoModal.title"
           :width="userinfoModal.width"
           :mask-closable="false"
           class="modal-form">
      <Form :ref="userinfoModal.name"
            :model="userinfoModal.model"
            label-position="top">
        <Row>
          <i-col span="6">
            <template>
              <Upload ref="upload"
                      :show-upload-list="false"
                      :on-success="onUpload"
                      :before-upload="onBeforeUpload"
                      accept="image/gif,image/jpeg,image/png"
                      :max-size="2048"
                      :action="userinfoModal.upload.url"
                      :headers="userinfoModal.upload.header"
                      name="avatars"
                      type="drag"
                      style="display: inline-block;">
                <div style="width: 133px;height:133px;line-height: 133px;">
                  <Icon v-if="!userinfoModal.model.headPortraitPath"
                        type="ios-camera"
                        size="60"></Icon>
                  <img v-if="userinfoModal.model.headPortraitPath"
                       :src="userinfoModal.model.headPortraitPath"
                       width="133">
                </div>
              </Upload>
              <Spin fix
                    v-if="userinfoModal.upload.loadding">
                <Icon type="ios-loading"
                      size=30
                      class="modal-con-spin-icon-load"></Icon>
                <div>上传中，请稍后...</div>
              </Spin>
            </template>
          </i-col>
          <i-col span="18">
            <FormItem label="真实姓名"
                      prop="realName">
              <Input v-model="userinfoModal.model.realName"
                     placeholder="请输入真实姓名" />
            </FormItem>
            <FormItem label="昵称"
                      prop="nickName">
              <Input v-model="userinfoModal.model.nickName"
                     placeholder="请输入昵称" />
            </FormItem>
          </i-col>
        </Row>
        <FormItem label="用户名"
                  prop="userName">
          <Tooltip content="用户名不能修改！"
                   style="width:80%"
                   placement="top-start">
            <Input :readonly="true"
                   v-model="userinfoModal.model.userName" />
          </Tooltip>
        </FormItem>
        <Row>
          <i-col span="6">
            <FormItem label="性别"
                      prop="sex">
              <Select v-model="userinfoModal.model.sex"
                      style="width:80%">
                <Option value="保密">保密</Option>
                <Option value="男">男</Option>
                <Option value="女">女</Option>
              </Select>
            </FormItem>
          </i-col>
          <i-col span="18">
            <FormItem label="出生日期">
              <DatePicker type="date"
                          style="width:60%"
                          placeholder="请选择出生日期"
                          v-model="userinfoModal.model.birthday"></DatePicker>
            </FormItem>
          </i-col>
        </Row>
        <FormItem label="手机号"
                  prop="phoneNumber">
          <Tooltip content="手机号不能修改！"
                   style="width:80%"
                   placement="top-start">
            <Input :readonly="true"
                   v-model="userinfoModal.model.phoneNumber" />
          </Tooltip>
        </FormItem>
        <FormItem label="邮箱"
                  prop="emailAddress">
          <Input v-model="userinfoModal.model.emailAddress"
                 type="email"
                 style="width:80%"
                 placeholder="请输入邮箱地址" />
        </FormItem>
        </TabPane>
      </Form>
      <Spin fix
            v-if="userinfoModal.loading">
        <Icon type="ios-loading"
              size=30
              class="modal-con-spin-icon-load"></Icon>
        <div>{{userinfoModal.loaddingText}}</div>
      </Spin>
      <div slot="footer">
        <!-- <Button type="primary"
                @click="onSaveUserinfo">保存</Button> -->
        <Button @click="onCloseUserinfo">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import './user.less'
import { sessionSave } from '@/libs/util'
import { dateFormat, getDateWeek } from '@/libs/tools'
import { mapActions } from 'vuex'
export default {
  name: 'User',
  props: {
    userAvator: {
      type: String,
      default: ''
    },
    userName: {
      type: String,
      default: ''
    },
    messageUnreadCount: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      pwdModel: {
        title: '修改密码',
        show: false,
        width: 500,
        formName: 'modifyForm',
        formData: {
          newPassword: '',
          oldPassword: ''
        },
        ruleValidate: {
          oldPassword: [
            { required: true, message: '旧密码必填', trigger: 'blur' }
          ],
          newPassword: [
            { required: true, message: '新密码必填', trigger: 'blur' }
          ]
        }
      },
      bindModel: {
        title: '绑定手机号',
        show: false,
        width: 500,
        formName: 'bindForm',
        formData: {
          'imageCode': '',
          'mobilePhone': '',
          'sendType': 4,
          'smsCode': '',
          'token': ''
        },
        ruleValidate: {
          mobilePhone: [
            { required: true, message: '手机号必填', trigger: 'blur' }
          ],
          imageCode: [
            { required: true, message: '图形验证码必填', trigger: 'blur' }
          ],
          smsCode: [
            { required: true, message: '手机验证码必填', trigger: 'blur' }
          ]
        }
      },
      sendButton: {
        disable: false,
        text: '获取验证码'
      },
      imageCodeBase64: '',
      userinfoModal: {
        show: false,
        loading: false,
        loaddingText: '加载用户信息中，请稍后...',
        title: '个人资料',
        width: 800,
        name: 'userinfoForm',
        upload: {
          url: '',
          header: {},
          loadding: false
        },
        model: {
          birthday: '',
          emailAddress: '',
          headPortraitPath: '',
          nickName: '',
          phoneNumber: '',
          realName: '',
          sessionId: '',
          sessionKey: '',
          sessionTimeout: 0,
          sex: '',
          userId: 0,
          userName: ''
        }
      }
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut',
      'handleUpdatePwd',
      'handleImageCaptcha',
      'handleSendSms',
      'handleUpdatePwdSms'
    ]),
    logout () {
      this.handleLogOut().then(() => {
        sessionSave('subMenuList', [])
        sessionSave('menus', [])
        sessionSave('openNames', [])
        this.$router.push({
          name: 'login'
        })
      })
    },
    // 修改密码
    modifyPwd () {
      this.pwdModel.show = true
    },
    bindMobile () {
      this.bindModel.show = true
      this.getImgCaptcha()
    },
    message () {
      this.$router.push({
        name: 'message_page'
      })
    },
    handleClick (name) {
      switch (name) {
        case 'logout': this.logout()
          break
        case 'modifyPwd': this.modifyPwd()
          break
        case 'bindMobile': this.bindMobile()
          break
        case 'message': this.message()
          break
        case 'userinfo': this.onUserinfo()
          break
      }
    },
    pwdSave () {
      let that = this
      that.$refs[that.pwdModel.formName].validate((valid) => {
        if (valid) {
          this.handleUpdatePwd(that.pwdModel.formData).then(res => {
            that.$Message.success('修改成功，请重新登录!')
            that.$router.push({
              name: 'login'
            })
          })
        } else {
          that.$Message.error('请检查必填项后继续!')
          that.formModal.show = true
          that.formModal.isUpdate = false
        }
      })
    },
    pwdResetForm () {
      this.$refs[this.pwdModel.formName].resetFields()
      this.pwdModel.show = false
    },
    bindResetForm () {
      this.$refs[this.bindModel.formName].resetFields()
      this.bindModel.show = false
    },
    bindSave () {
      let that = this
      if (this.bindModel.formData.password !== this.bindModel.formData.rPassword) {
        this.$Message.error('两次输入密码不一致')
        return false
      }
      if (!this.bindModel.formData.token) {
        this.$Message.error('请先获取验证码')
        return false
      }
      that.$refs[that.bindModel.formName].validate((valid) => {
        if (valid) {
          this.handleUpdatePwdSms({
            'password': this.bindModel.formData.password,
            'smsCode': this.bindModel.formData.smsCode,
            'token': this.bindModel.formData.token
          }).then(res => {
            that.$Message.success('绑定成功')
          })
        } else {
          that.$Message.error('请检查必填项后继续!')
          that.formModal.show = true
          that.formModal.isUpdate = false
        }
      })
    },
    // 发送短信验证码
    sendSmsCode () {
      if (!this.bindModel.formData.mobilePhone) {
        this.$Message.error('请输入正确的手机号码！')
        return false
      }
      if (!this.bindModel.formData.imageCode) {
        this.$Message.error('请输入图形验证码！')
        return false
      }
      // 发送短信验证码
      this.handleSendSms({
        imageCode: this.bindModel.formData.imageCode,
        mobilePhone: this.bindModel.formData.mobilePhone,
        sendType: this.bindModel.formData.sendType
      }).then(res => {
        this.bindModel.formData.token = res.token
        this.startInteval(res.repeatInterval)
      })
    },
    // 启动定时器
    startInteval (repeatInterval) {
      let that = this
      let interval = setInterval(() => {
        repeatInterval--
        that.sendButton = {
          disable: true,
          text: `重新获取(${repeatInterval}s)`
        }
        if (repeatInterval <= 0) {
          clearInterval(interval)
          that.sendButton = {
            disable: false,
            text: `重新获取`
          }
        }
      }, 1000)
    },
    // 获取图形验证码
    getImgCaptcha () {
      this.handleImageCaptcha({
        width: 100,
        height: 32
      }).then(res => {
        debugger
        this.imageCodeBase64 = res.imageBase64
      })
    },
    onUserinfo () {
      this.userinfoModal.loaddingText = '正在加载用户信息，请稍后...'
      this.userinfoModal.loading = true
      this.userinfoModal.model = this.$store.state.user.userLoginInfo
      this.userinfoModal.loading = false
      this.userinfoModal.show = true
    },
    onBeforeUpload () {
      this.userinfoModal.upload.loadding = true
    },
    onUpload (res, file) {
      this.userinfoModal.upload.loadding = false
      if (res.success) {
        this.userinfoModal.model.headPortraitPath = res.result.accessUrl
      } else {
        this.$Message.error(res.error && res.error.message ? res.error.message : '表单验证失败!')
      }
    },
    onCloseUserinfo () {
      this.$refs[this.userinfoModal.name].resetFields()
      this.userinfoModal.show = false
    },
    onSaveUserinfo () {
      // this.$refs[this.userinfoModal.name].validate((valid) => {
      //   if (valid) {
      //     this.userinfoModal.loading = true
      //     this.userinfoModal.loaddingText = '正在保存个人信息，请稍后...'
      //     updateUserInfo(this.userinfoModal.model).then(res => {
      //       this.$Message.success('修改个人信息成功!')
      //       this.getUserInfo()
      //       this.userinfoModal.loading = false
      //       this.userinfoModal.show = false
      //     }).catch(res => {
      //       this.userinfoModal.loading = false
      //     })
      //   } else {
      //     this.$Message.error('表单验证失败!')
      //     this.userinfoModal.show = true
      //     return false
      //   }
      // })
    }
  },
  mounted () {
    this.userName = this.$store.state.user.userName
    if (!this.showTip) {
      let now = new Date()
      let tip = dateFormat('yyyy年MM月dd日 hh:mm:ss', now)
      tip += getDateWeek(now)
      this.$Notice.info({
        title: '欢迎您 ' + this.userName,
        desc: '今天是 ' + tip
      })
      this.showTip = true
    }
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
</style>
