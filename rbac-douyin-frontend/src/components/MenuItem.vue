<!-- src/components/MenuItem.vue -->
<template>
  <!-- 子菜单 -->
  <el-sub-menu v-if="item.children && item.children.length" :index="String(item.id)">
    <template #title>
      <i :class="item.icon" style="margin-right: 6px" />
      <span>{{ item.name }}</span>
    </template>
    <MenuItem v-for="children in item.children" :key="children.id" :item="children" />
  </el-sub-menu>

  <!-- 普通菜单项 -->
  <el-menu-item v-else :index="item.href || String(item.id)">
    <i :class="item.icon" style="margin-right: 6px" />
    <span>{{ item.name }}</span>
  </el-menu-item>
</template>

<script lang="ts" setup>
import MenuItem from './MenuItem.vue'
defineProps<{
  item: any
}>()
</script>
