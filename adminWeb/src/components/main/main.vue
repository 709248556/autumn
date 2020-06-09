<template>
  <Layout style="height: 100%" class="main">
    <Header class="header-con">
      <Row>
        <Col span="5">
          <h1 class="main-title">
            <div class="img-wrap">
              <img
                :src="
                  systemInfo && systemInfo.logoUploadFile
                    ? systemInfo.logoUploadFile.accessUrlPath
                    : maxLogo
                "
              />
            </div>
            <div class="title-wrap">
              <div
                :class="
                  userInfo.organizeName ? 'title-text' : 'title-text ft16'
                "
              >
                {{ systemInfo.systemName }}
              </div>
              <div class="orgName" v-if="userInfo.organizeName">
                {{ userInfo.organizeName }}
              </div>
            </div>
          </h1>
        </Col>
        <Col span="16">
          <Menu
            ref="mainMenu"
            mode="horizontal"
            theme="dark"
            :active-name="activeName"
            @on-select="mSelect"
          >
            <MenuItem
              v-if="menuList.length <= 5"
              v-for="(item, index) in menuList"
              :name="index"
              :key="item.id"
            >
              <Icon :type="item.icon" /> {{ item.customName }}
            </MenuItem>
            <MenuItem
              :name="index"
              v-for="(item, index) in menuList"
              v-if="menuList.length > 5 && index < 4"
              :key="item.id"
            >
              <Icon :type="item.icon" /> {{ item.customName }}
            </MenuItem>
            <Submenu name="subMore" v-if="menuList.length > 5">
              <template slot="title">
                <Icon type="md-apps"></Icon>
                更多
              </template>
              <MenuItem
                :name="index"
                v-for="(item, index) in menuList"
                v-if="index >= 4"
                :key="item.id"
              >
                <Icon :type="item.icon" /> {{ item.customName }}
              </MenuItem>
            </Submenu>
          </Menu>
        </Col>
        <Col span="3">
          <header-bar>
            <user :user-avator="userAvator" :user-name="userName" />
            <fullscreen v-model="isFullscreen" style="margin-right: 10px;" />
            <Tooltip content="查看帮助文档" placement="bottom">
              <Icon
                type="ios-help-circle-outline"
                @click="onHelpClick"
                style="margin: 21px 5px;font-size: 23px;cursor: pointer;"
              />
            </Tooltip>
          </header-bar>
        </Col>
      </Row>
    </Header>
    <Layout class="main-content-con">
      <Sider>
        <Menu
          ref="asideMenu"
          theme="dark"
          width="200"
          :accordion="true"
          :active-name="subAmenu"
          :open-names="openNames"
          @on-select="handleClick"
          @on-open-change="openChange"
        >
          <template v-for="(menu, index) in subMenuList">
            <Submenu
              v-if="menu.children && menu.children.length >= 1"
              :key="index"
              :name="menu.id"
            >
              <template slot="title">
                <Icon :type="menu.icon" size="18" /> {{ menu.customName }}
              </template>
              <menu-item
                v-for="child in menu.children"
                :key="child.id"
                :name="child.id"
              >
                <Icon :type="child.icon" size="16" />{{ child.customName }}
              </menu-item>
            </Submenu>
            <menu-item v-if="!menu.children" :key="menu.id" :name="menu.id">
              <Icon :type="menu.icon" size="16" />{{ menu.customName }}
            </menu-item>
          </template>
        </Menu>
      </Sider>
      <Layout>
        <custom-bread-crumb
          show-icon
          :list="breadCrumbList"
          style="padding: 10px 20px; background-color: #fff; border-left: 1px solid #F0F5F7;"
        ></custom-bread-crumb>
        <Content class="content-wrapper">
          <keep-alive :exclude="notCacheName">
            <router-view />
          </keep-alive>
          <ABackTop
            :height="100"
            :bottom="80"
            :right="50"
            container=".content-wrapper"
          ></ABackTop>
        </Content>
      </Layout>
    </Layout>
  </Layout>
</template>
<script>
import SideMenu from './components/side-menu'
import HeaderBar from './components/header-bar'
import User from './components/user'
import ABackTop from './components/a-back-top'
import Fullscreen from './components/fullscreen'
import customBreadCrumb from './components/header-bar/custom-bread-crumb'
import routers from '@/router/routers'
import { mapMutations, mapActions } from 'vuex'
import { sessionRead, sessionSave } from '@/libs/util'
import maxLogo from '@/assets/images/logo.png'

import defaultAvatorM from '@/assets/images/default-m.jpg'
import defaultAvatorG from '@/assets/images/default-g.jpg'
import defaultAvator from '@/assets/images/default.jpg'

import './main.less'
export default {
  name: 'Main',
  components: {
    SideMenu,
    HeaderBar,
    Fullscreen,
    User,
    ABackTop,
    customBreadCrumb
  },
  data () {
    return {
      collapsed: false,
      maxLogo,
      isFullscreen: false,
      activeName: 0,
      subMenuList: [],
      subAmenu: '',
      openNames: [],
      cacheChaildName: '',
      systemInfo: {}
    }
  },
  computed: {
    breadCrumbList () {
      return this.$store.state.app.breadCrumbList
    },
    userAvator () {
      if (this.$store.state.user.avatorImgPath) {
        return this.$store.state.user.avatorImgPath
      }
      let userinfo = this.$store.state.user.userLoginInfo
      if (userinfo) {
        if (userinfo.sex === '男') {
          return defaultAvatorM
        }
        if (userinfo.sex === '女') {
          return defaultAvatorG
        }
      }
      return defaultAvator
    },
    userName () {
      return this.$store.state.user.userName
    },
    userInfo () {
      return this.$store.state.user
    },
    menuList () {
      let menus = this.$store.state.app.menuList
      return menus
    },
    notCacheName () {
      return this.$route.meta && this.$route.meta.notCache
        ? this.$route.name
        : ''
    }
  },
  methods: {
    ...mapMutations(['setBreadCrumb', 'setHomeRoute']),
    ...mapActions([
      'handleLogin',
      'getMenus',
      'getUserInfo',
      'getSystemInfo',
      'queryHelpDocumentVisit'
    ]),
    turnToPage (route) {
      let { name, params, query } = {}
      if (typeof route === 'string') name = route
      else {
        name = route.name
        params = route.params
        query = route.query
      }
      if (name.indexOf('isTurnByHref_') > -1) {
        window.open(name.split('_')[1])
        return
      }
      if (name && this.$route.name !== name) {
        this.$router.push({
          name,
          params,
          query
        })
      }
    },
    handleCloseTag (res, type, route) {
      if (type === 'all') {
        this.turnToPage(this.$config.homeName)
      } else if (routeEqual(this.$route, route)) {
        if (type !== 'others') {
          const nextRoute = getNextRoute(this.tagNavList, route)
          this.$router.push(nextRoute)
        }
      }
      this.setTagNavList(res)
    },
    handleClick (id) {
      var currentMenu = null
      var fun = item => {
        if (item.id === id) {
          currentMenu = item
          return
        }
        if (item.children != null) {
          for (let i = 0; i < item.children.length; i++) {
            let menu = item.children[i]
            fun(menu)
            if (currentMenu != null) {
              break
            }
          }
        }
      }
      if (this.menuList != null) {
        for (let i = 0; i < this.menuList.length; i++) {
          let menu = this.menuList[i]
          fun(menu)
          if (currentMenu != null) {
            break
          }
        }
      }
      if (currentMenu != null) {
        let identification = currentMenu.identification
          ? currentMenu.identification
          : ''
        this.turnToPage(identification)
      }
    },
    mSelect (name) {
      this.openNames = []
      this.activeName = name
      this.subMenuList = this.menuList[name].children
      let aMenu = this.subMenuList.length
        ? this.subMenuList[0].identification
        : ''
      if (aMenu) {
        this.openNames.push(aMenu)
      }
      sessionSave('activeName', name)
      sessionSave('subMenuList', this.subMenuList)
      this.$nextTick(() => {
        this.$refs.asideMenu.updateOpened()
        this.$refs.asideMenu.updateActiveName()
      })
    },
    openChange (name) {
      sessionSave('openNames', name)
    },
    initSystemInfo () {
      let systemInfo = this.$store.state.app.systemInfo
      if (systemInfo.isDefault) {
        this.getSystemInfo().then(res => {
          this.systemInfo = res
        })
      } else {
        this.systemInfo = this.$store.state.app.systemInfo
      }
    },
    onHelpClick () {
      let helpDocumentVisit = this.$store.state.app.helpDocumentVisit
      if (!helpDocumentVisit) {
        this.queryHelpDocumentVisit().then(res => {
          window.open(res.accessPath, '_black')
        })
      } else {
        if (helpDocumentVisit) {
          window.open(helpDocumentVisit.accessPath, '_black')
        }
      }
    }
  },
  watch: {
    $route (newRoute) {
      this.setBreadCrumb(newRoute)
      this.subAmenu = newRoute.name
    }
  },
  mounted () {
    this.initSystemInfo()
    /**
     * @description 初始化设置面包屑导航和标签导航
     */
    this.getUserInfo()
    let activeName = parseInt(sessionRead('activeName'))
    if (activeName) {
      this.activeName = activeName
    }
    this.subMenuList = sessionRead('subMenuList')
      ? sessionRead('subMenuList')
      : []
    this.openNames = sessionRead('openNames') ? sessionRead('openNames') : []
    this.setHomeRoute(routers)
    this.setBreadCrumb(this.$route)
    this.subAmenu = this.$route.name
    this.getMenus().then(res => {
      this.subMenuList = res[this.activeName].children
      this.$nextTick(() => {
        this.$refs.mainMenu.updateOpened()
        this.$refs.mainMenu.updateActiveName()
        this.$refs.asideMenu.updateOpened()
        this.$refs.asideMenu.updateActiveName()
      })
    })
  }
}
</script>
