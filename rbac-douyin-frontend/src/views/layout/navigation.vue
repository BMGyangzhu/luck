<!-- src/views/DynamicMenu.vue -->
<template>
  <el-menu
    class="el-menu-vertical-demo"
    default-active="1"
    active-text-color="#ffd04b"
    @open="handleOpen"
    @close="handleClose"
    
    router
  >
    <template v-for="item in menus" :key="item.id">
      <MenuItem :item="item" />
    </template>
  </el-menu>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import MenuItem from '@/components/MenuItem.vue'
import { apiGetMenus } from '@/apis/permission'

// 响应式菜单数据
const menus = ref([])

const getMenus = async () => {
  const res = await apiGetMenus()
  menus.value = res.data
  
}

onMounted(() => {
  getMenus()
})

const handleOpen = (key: string, keyPath: string[]) => {
  console.log('open', key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log('close', key, keyPath)
}
</script>
