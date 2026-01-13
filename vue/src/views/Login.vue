<template>
  <div class="login-container">
    <!-- 左侧系统介绍 -->
    <div class="left-section">
      <div class="system-title">个人健康管理系统</div>
      <div class="system-subtitle">基于AI技术的全生命周期健康管理平台</div>
      
      <div class="feature-list">
        <div class="feature-item">
          <div class="feature-icon" style="background: #e3f2fd;">
            <svg style="width: 32px; height: 32px; color: #2196f3;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M22 12h-4l-3 9L9 3l-3 9H2"></path>
            </svg>
          </div>
          <div class="feature-content">
            <div class="feature-title">智能健康监测</div>
            <div class="feature-desc">实时记录和分析您的健康指标，提供个性化健康建议</div>
          </div>
        </div>
        
        <div class="feature-item">
          <div class="feature-icon" style="background: #f1f8e9;">
            <svg style="width: 32px; height: 32px; color: #8bc34a;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
            </svg>
          </div>
          <div class="feature-content">
            <div class="feature-title">AI健康助手</div>
            <div class="feature-desc">24小时在线的AI助手，随时解答您的健康疑问</div>
          </div>
        </div>
        
        <div class="feature-item">
          <div class="feature-icon" style="background: #e8f5e9;">
            <svg style="width: 32px; height: 32px; color: #4caf50;" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
          </div>
          <div class="feature-content">
            <div class="feature-title">数据安全保障</div>
            <div class="feature-desc">采用银行级加密技术，确保您的健康数据安全</div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 右侧登录表单 -->
    <div class="right-section">
      <div class="login-box">
        <div class="welcome-title">欢迎使用</div>
        <div class="welcome-subtitle">登录或注册开始您的健康管理之旅</div>
        
        <el-tabs v-model="data.activeTab" class="login-tabs">
          <el-tab-pane label="登录" name="login"></el-tab-pane>
          <el-tab-pane label="注册" name="register"></el-tab-pane>
        </el-tabs>
        
        <el-form ref="formRef" :model="data.form" :rules="data.rules">
          <div class="form-label">用户名</div>
          <el-form-item prop="username">
            <el-input size="large" v-model="data.form.username" :placeholder="data.activeTab === 'login' ? '请输入用户名' : '请输入用户名（仅支持字母、数字和下划线）'"></el-input>
          </el-form-item>
          <div class="form-label">密码</div>
          <el-form-item prop="password">
            <el-input show-password size="large" v-model="data.form.password" :placeholder="data.activeTab === 'login' ? '请输入密码' : '请输入密码（至少6位）'"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button v-if="data.activeTab === 'login'" size="large" type="primary" style="width: 100%; border-radius: 8px;" @click="login">登录</el-button>
            <el-button v-else size="large" type="primary" style="width: 100%; border-radius: 8px;" @click="register">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { User, Lock } from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const data = reactive({
  activeTab: 'login',
  form: { role: 'ADMIN' },
  rules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
    ]
  }
})

const formRef = ref()

const login = () => {
  formRef.value.validate(valid => {
    if (valid) { // 表示表单校验通过
      request.post('/login', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('登录成功')
          // 存储用户信息到浏览器的缓存
          localStorage.setItem('xm-user', JSON.stringify(res.data))
          router.push('/manager/home')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

const register = () => {
  formRef.value.validate(valid => {
    if (valid) { // 表示表单校验通过
      request.post('/register', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('注册成功，请登录')
          data.activeTab = 'login'
          // 清空表单
          data.form.username = ''
          data.form.password = ''
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 100px;
  background: linear-gradient(135deg, #e3f2fd 0%, #f5f5f5 100%);
  padding: 40px;
}

/* 左侧系统介绍 */
.left-section {
  flex: 0 0 auto;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.system-title {
  font-size: 48px;
  font-weight: bold;
  color: #1976d2;
  margin-bottom: 20px;
  letter-spacing: 2px;
}

.system-subtitle {
  font-size: 18px;
  color: #757575;
  margin-bottom: 80px;
}

.feature-list {
  display: flex;
  flex-direction: column;
  gap: 40px;
  width: 100%;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  gap: 20px;
  text-align: left;
}

.feature-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.feature-content {
  flex: 1;
}

.feature-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.feature-desc {
  font-size: 14px;
  color: #757575;
  line-height: 1.6;
}

/* 右侧登录表单 */
.right-section {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-box {
  width: 420px;
  padding: 50px 40px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12), 0 4px 12px rgba(0, 0, 0, 0.08);
}

.welcome-title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 10px;
}

.welcome-subtitle {
  font-size: 14px;
  color: #999;
  text-align: center;
  margin-bottom: 40px;
}

.login-tabs {
  margin-bottom: 30px;
}

.login-tabs :deep(.el-tabs__nav-wrap::after) {
  display: none;
}

.login-tabs :deep(.el-tabs__item) {
  font-size: 16px;
  padding: 0 30px;
  color: #666;
}

.login-tabs :deep(.el-tabs__item.is-active) {
  color: #409eff;
  font-weight: 600;
}

.login-tabs :deep(.el-tabs__active-bar) {
  height: 3px;
  border-radius: 3px;
}

.form-label {
  font-size: 14px;
  color: #333;
  margin-bottom: 8px;
  font-weight: 500;
}

.el-form-item {
  margin-bottom: 20px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 0 0 1px #e0e0e0 inset;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #409eff inset;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #409eff inset !important;
}
</style>