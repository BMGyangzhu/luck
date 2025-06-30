<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">后台登录</h2>

      <el-form :model="form" ref="formRef" :rules="rules" label-position="top">
        <el-form-item prop="email">
          <el-input
            v-model="form.email"
            prefix-icon="el-icon-user"
            placeholder="邮箱/学号"
          />
        </el-form-item>
        <!-- <el-form-item prop="name">
          <el-input
            v-model="form.name"
            prefix-icon="el-icon-user"
            placeholder="邮箱/学号"
          />
        </el-form-item> -->

        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            prefix-icon="el-icon-lock"
            placeholder="密码"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" class="login-btn" @click="handleLogin" block>
            登 录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { apiLogin } from '@/apis/login'
import { useUserStore } from '@/store/'
import { useRouter } from 'vue-router';

const router = useRouter()
const userStore = useUserStore()
const form = ref({
  email: '',
  password: ''
})
// const form = ref({
//   name: '',         
//   password: ''
// })
const rules = {
  email: [{ required: true, message: '请输入邮箱/学号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const formRef = ref()



const handleLogin = async () => {
  try {
    await formRef.value.validate()

    const res = await apiLogin(form.value)
    const { token, user } = res.data.data

    userStore.token = token
    userStore.info = user

    sessionStorage.setItem('token', token)
    sessionStorage.setItem('user', JSON.stringify(user))
    ElMessage.success('登录成功')
  router.push("/"); 

  } catch (err) {
    console.log(err)
    ElMessage.error('登录失败')
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f2f6fc;
}

.login-card {
  width: 350px;
  padding: 30px 25px;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  font-size: 22px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 20px;
}

.login-btn {
  width: 100%;
  font-weight: bold;
}
</style>
