<template>
  <el-container style="height: 100vh">
    <!-- 顶部 -->
    <el-header height="60px">
      <defaultHeader @click-event="clickEvent" />
    </el-header>

    <!-- 内容区域 -->
    <el-container>
      <!-- 左侧导航 -->
      <el-aside :width="leftMenu ? '200px' : '64px'">
        <navigation v-model="leftMenu" />
      </el-aside>

      <!-- 主内容 -->
      <el-main style="padding: 16px">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import defaultHeader from './header.vue'
import navigation from './navigation.vue'

const leftMenu = ref(true)

const clickEvent = (type, data) => {
  if (type === 1) {
    leftMenu.value = !leftMenu.value
  }
}
</script>
