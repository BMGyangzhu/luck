<template>
  <div class="header-bar">
    <div class="left-section">
      <el-icon @click="emit('toggle-menu')" class="menu-icon">
        <Fold v-if="!isCollapse" />
        <Expand v-else />
      </el-icon>
    </div>

    <div class="right-section">
      <el-button type="danger" plain @click="logout">退出登录</el-button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import { Fold, Expand } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const props = defineProps({
  isCollapse: Boolean,
})
const emit = defineEmits(['toggle-menu'])

const router = useRouter()

const logout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    // 清除登录信息
    sessionStorage.clear()
    ElMessage.success('退出成功')
    router.replace('/login')
  }).catch(() => {
    // 用户取消
  })
}
</script>

<style scoped>
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.left-section .menu-icon {
  font-size: 22px;
  cursor: pointer;
  transition: all 0.3s;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 12px;
}
</style>
