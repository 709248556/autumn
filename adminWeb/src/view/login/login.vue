<style lang="less">
@import './login.less';
</style>

<template>
  <div class="login"
       :style="loginStyle">
    <div class="head-title">
      <div id="logo">
        <img width="125"
             :src="systemInfo.logoUploadFile?systemInfo.logoUploadFile.accessUrlPath:logo"
             alt="logo">
      </div>
      <h1 id="title">{{systemInfo.systemName}}<br>{{loginType == 3 ? '忘记密码' : '后台登录'}}</h1>
    </div>
    <div class="con">
      <div class="login-form">
        <div v-if="loginType == 1"
             class="form-content">
          <div class="item-label">登录账号</div>
          <div class="form-item item-userName">
            <input type="text"
                   v-model="formData.userName"
                   name="userName"
                   id="userName"
                   placeholder="请输入您的帐号">
          </div>
          <div class="p-error">{{error1}}</div>
          <div class="item-label">密码</div>
          <div class="form-item item-password">
            <input type="password"
                   v-model="formData.password"
                   autocomplete="false"
                   name="password"
                   id="password"
                   placeholder="请输入您的密码">
          </div>
          <div class="p-error">{{error2}}</div>
          <div v-if="mustImageCode"
               class="item-label">验证码</div>
          <div v-if="mustImageCode"
               class="form-item item-imgCode">
            <input type="text"
                   v-model="formData.imageCode"
                   name="imageCode"
                   autocomplete="false"
                   placeholder="请输入您的图形验证码">
            <img :src="'data:image/png;base64,'+imageCodeBase64"
                 @click="getImgCaptcha"
                 alt="">
          </div>
          <button class="btn-submit"
                  @click="handleSubmit">立即登录</button>
          <div class="remember-me">
            <Checkbox v-model="formData.rememberMe">记住登录状态</Checkbox>
            <div style="float: right">
              <a href="javascript: void(0)"
                 @click="loginType = 2; getImgCaptcha(); resetForm()"
                 class="a-link">短信验证码登录</a>
              <a href="javascript: void(0)"
                 @click="loginType = 3; getImgCaptcha(); resetForm()"
                 class="a-link">忘记密码</a>
            </div>
          </div>
        </div>
        <div v-if="loginType == 2 || loginType == 3"
             class="form-content content3">
          <div class="item-label">手机号</div>
          <div class="form-item item-userName">
            <input type="text"
                   v-model="formData.userName"
                   name="userName"
                   id="mobilePhone"
                   placeholder="请输入您的手机号">
          </div>
          <div class="item-label">图形验证码</div>
          <div class="form-item item-imgCode">
            <input type="text"
                   v-model="formData.imageCode"
                   name="imageCode"
                   autocomplete="false"
                   placeholder="请输入您的图形验证码">
            <img :src="'data:image/png;base64,'+imageCodeBase64"
                 @click="getImgCaptcha"
                 alt="">
          </div>
          <div class="item-label">手机验证码</div>
          <div class="form-item item-password">
            <input type="text"
                   v-model="formData.smsCode"
                   name="smsCode"
                   id="smsCode"
                   placeholder="请输入验证码">
            <a href="javascript: void(0)"
               @click="sendSmsCode(loginType == 2 ? 1 : loginType == 3 ? 3 : '')"
               class="a-link"
               :disabled="sendButton.disable">{{sendButton.text}}</a>
          </div>
          <div v-if="loginType == 3"
               class="item-label">密码</div>
          <div v-if="loginType == 3"
               class="form-item item-password">
            <input type="password"
                   v-model="formData.password"
                   autocomplete="false"
                   name="password"
                   placeholder="请设置密码">
          </div>
          <div v-if="loginType == 3"
               class="form-item item-password">
            <input type="password"
                   v-model="formData.rPassword"
                   autocomplete="false"
                   name="password"
                   placeholder="请再次输入密码">
          </div>
          <button class="btn-submit"
                  @click="handleSubmit">{{loginType == 3 ? '确认' : '立即登录'}}</button>
          <div class="remember-me">
            <div style="float: right">
              <a href="javascript: void(0)"
                 @click="loginType = 1; resetForm()"
                 class="a-link">帐号密码登录</a>
              <a href="javascript: void(0)"
                 @click="loginType = 3; getImgCaptcha(); resetForm()"
                 class="a-link">忘记密码</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <img src="@/assets/images/login/icon-footer.png"
           alt=""> copyright © 2019 {{systemInfo.systemName}} 版权所有 {{systemInfo.systemKeepOnRecordNo }}</div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import { imageCaptcha, sendSms } from '@/api/administrator/verification/verification'
import { loginBySmsToken } from '@/api/login/user'
import logo from '@/assets/images/login/login-logo.png'
import loginBanner from '@/assets/images/login/login-banner.png'
export default {
  data () {
    return {
      logo: logo,
      loginType: 1, // 1 帐号密码登录  2 手机验证码登录  3.忘记密码
      loginBanner: loginBanner,
      formData: {
        userName: '',
        password: '',
        rPassword: '',
        rememberMe: false,
        imageCode: '',
        sendType: 1,
        smsCode: '',
        token: ''
      },
      sendButton: {
        disable: false,
        text: '获取验证码'
      },
      mustImageCode: false,
      imageCodeBase64: '',
      error1: '',
      error2: '',
      systemInfo: {},
      loginStyle: '',
      loading: true,
      loaddingText: '正在登录...'
    }
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo',
      'loginRequest',
      'getSystemInfo'
    ]),
    handleSubmit () {
      if (this.loginType === 1) {
        let [userName, password, imageCode, rememberMe] =
          [
            this.formData.userName,
            this.formData.password,
            this.formData.imageCode,
            this.formData.rememberMe
          ]
        if (!userName.trim()) {
          this.error1 = '请输入帐号'
          return
        } else {
          this.error1 = ''
        }
        if (!password || !password.trim()) {
          this.error2 = '请输入密码'
          return
        } else {
          this.error2 = ''
        }
        this.loading = true
        this.handleLogin({ userName, password, imageCode, rememberMe }).then(res => {
          if (res.success) {
            this.$router.push({
              name: this.$config.homeName
            })
          } else {
            this.resetForm()
            this.$Message.error(res.error.message)
            if (res.error.code === -2) {
              this.mustImageCode = true
              this.getImgCaptcha()
            }
          }
        }).finally(() => {
          this.loading = false
        })
      } else if (this.loginType === 2 || this.loginType === 3) {
        let [userName, token, smsCode, password, rPassword] = [this.formData.userName, this.formData.token, this.formData.smsCode, this.formData.password, this.formData.rPassword]
        if (!userName.trim()) {
          this.$Message.error('请输入手机号')
          return false
        }
        if (!smsCode.trim()) {
          this.$Message.error('请输入验证码')
          return false
        }
        if (this.loginType === 2) {
          loginBySmsToken({
            token,
            smsCode
          }).then(res => {
            this.$router.push({
              name: this.$config.homeName
            })
          }).catch(error => {
            if (error && error.code === -2) {
              this.mustImageCode = true
              this.getImgCaptcha()
            }
          })
        } else if (this.loginType === 3) {
          if (!password.trim()) {
            this.$Message.error('请输入新密码')
            return false
          }
          if (!rPassword.trim()) {
            this.$Message.error('请再输入一次新密码')
            return false
          }
          if (password.trim() !== rPassword.trim()) {
            this.$Message.error('两次输入的密码不一致')
            return false
          }
          loginBySmsToken({
            password,
            token,
            smsCode
          }).then(res => {
            this.resetForm()
            this.$Message.success('修改成功，请登录')
            this.loginType = 1
          }).catch(error => {
            if (error && error.code === -2) {
              this.mustImageCode = true
              this.getImgCaptcha()
            }
          })
        }
      }
    },
    // 发送短信验证码
    sendSmsCode (sendType) {
      if (!this.formData.userName) {
        this.$Message.error('请输入正确的手机号码！')
        return false
      }
      // 发送短信验证码
      sendSms({
        imageCode: this.formData.imageCode ? this.formData.imageCode : null,
        mobilePhone: this.formData.userName,
        sendType: sendType || 1
      }).then(res => {
        this.formData.token = res.token
        this.startInteval(res.repeatInterval)
      }).catch(error => {
        if (error && error.code === '31000') {
          this.mustImageCode = true
          this.getImgCaptcha()
        }
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
      imageCaptcha({
        width: 120,
        height: 40
      }).then(res => {
        this.imageCodeBase64 = res.imageBase64
      })
    },
    // 重置输入框
    resetForm () {
      this.formData.password = ''
      this.formData.rPassword = ''
      this.formData.imageCode = ''
      this.formData.smsCode = ''
      this.formData.token = ''
    },
    initSystemInfo () {
      let _this = this
      this.getSystemInfo().then(res => {
        _this.systemInfo = res
        if (res && res.systemLoginBannerUploadFile) {
          _this.loginStyle = `position: relative;width: 100%;min-height: 100%;background: url("${res.systemLoginBannerUploadFile.accessUrlPath}") center center no-repeat;background-size: cover;`
        }
      })
    }
  },
  mounted () {
    this.initSystemInfo()
    let _this = this
    this.loginRequest().then(res => {
      _this.mustImageCode = res.result.mustImageCode
      if (_this.mustImageCode) {
        _this.getImgCaptcha()
      }
    })
  }
}
</script>
