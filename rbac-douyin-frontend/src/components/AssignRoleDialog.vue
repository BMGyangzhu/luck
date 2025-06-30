<template>
    <el-dialog
    :close-on-click-modal = "false"
    :close-on-press-escape = "false"
    title="分配角色"
    width="500"
    :model-value="dialogVisible"
    @update:model-value="$emit('update:dialogVisible', $event)"
    :before-close="handleClose"
    >
    <el-select
            v-model="selectedOptions.rId"
            multiple
            placeholder="请选择"
          >
            <el-option
              v-for="item in rolesOptions"
              :key="item.value"
              :label="item.title"
              :value="item.value"
            ></el-option>
          </el-select>

    <template #footer>
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>

    </el-dialog>
  
</template>

<script setup>
import { ref } from 'vue'
import { apiInitRole } from '@/apis/role'

const emit = defineEmits(['update:dialogVisible', 'submit'])
const props = defineProps({
  dialogVisible: Boolean,
  userId: Number
})
const dialogVisible = ref(props.modelValue)
const handleConfirm = () => {
   selectedOptions.value.uId = props.userId
  emit('submit', selectedOptions.value)
}
const closeDialog = () => {
  emit('update:modelValue', false)
}
const handleClose = (done) => {
  done(); 
}


const selectedOptions = ref({
    uId: props.userId,
    rId: []
})
const rolesOptions = ref([{}])
const initRole = async () => {
    const res = await apiInitRole()
    rolesOptions.value = res.data
}

initRole()
</script>