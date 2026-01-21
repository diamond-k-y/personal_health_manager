<template>
  <div class="manager-container">
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">个人健康管理系统</div>
      </div>
      <div class="manager-header-center">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/manager/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{ router.currentRoute.value.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="manager-header-right">
        <el-dropdown style="cursor: pointer">
          <div style="padding-right: 20px; display: flex; align-items: center">
            <img style="width: 40px; height: 40px; border-radius: 50%;" :src="data.user.avatar" alt="">
            <span style="margin-left: 5px; color: white">{{ data.user.name }}</span><el-icon color="#fff"><arrow-down /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/manager/person')">个人资料</el-dropdown-item>
              <el-dropdown-item @click="router.push('/manager/password')">修改密码</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!-- 下面部分开始 -->
    <div style="display: flex">
      <div class="manager-main-left">
        <el-menu :default-active="router.currentRoute.value.path"
                 :default-openeds="['1', '2', '3']"
                 router
        >
          <el-menu-item index="/manager/home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-menu-item index="/manager/globalForum">
            <el-icon><Apple /></el-icon>
            <span>健康论坛</span>
          </el-menu-item>


          <el-sub-menu index="1">
            <template #title>
              <el-icon><Notebook /></el-icon>
              <span>健康记录</span>
            </template>
            <el-menu-item index="/manager/bodyRecords">
              <el-icon><DataAnalysis /></el-icon>
              <span>身体指标记录</span>
            </el-menu-item>
            <el-menu-item index="/manager/sportsRecords">
              <el-icon><Trophy /></el-icon>
              <span>运动指标记录</span>
            </el-menu-item>
            <el-menu-item index="/manager/eatingRecords">
              <el-icon><Food /></el-icon>
              <span>饮食指标记录</span>
            </el-menu-item>
            <el-menu-item index="/manager/sleepRecords">
              <el-icon><MoonNight /></el-icon>
              <span>睡眠指标记录</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/forum">
              <el-icon><ChatDotSquare /></el-icon>
              <span>帖子信息</span>
            </el-menu-item>
            <el-menu-item index="/manager/comment">
              <el-icon><Comment /></el-icon>
              <span>评论信息</span>
            </el-menu-item>
            <el-menu-item index="/manager/notice" v-if="data.user.role === 'ADMIN'">
              <el-icon><Bell /></el-icon>
              <span>系统公告</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2" v-if="data.user.role === 'ADMIN'">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">
              <el-icon><Avatar /></el-icon>
              <span>管理员信息</span>
            </el-menu-item>
            <el-menu-item index="/manager/user">
              <el-icon><User /></el-icon>
              <span>普通用户信息</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <div class="manager-main-right">
        <RouterView @updateUser="updateUser" />
      </div>
    </div>
    <!-- 下面部分结束 -->

    <AiAssistant />
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";

const data = reactive({
  user: {}
})

const logout = () => {
  localStorage.removeItem('xm-user')
  router.push('/login')
}

const updateUser = () => {
  data.user =  JSON.parse(localStorage.getItem('xm-user') || '{}')
}

// 在组件挂载时检查用户信息
onMounted(() => {
  data.user = JSON.parse(localStorage.getItem('xm-user') || '{}')
  if (!data.user.id) {
    logout()
    ElMessage.error('请登录！')
  }
})
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>