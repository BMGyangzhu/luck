<template>
    <el-dialog
    :close-on-click-modal = "false"
    :close-on-press-escape = "false"
     :title="isEditMode ? '编辑分类' : '新增分类'"
    title="编辑用户"
    width="700"
    :model-value="dialogVisible"
    @update:model-value="$emit('update:dialogVisible', $event)"
    :before-close="handleClose"
    >
     <el-form :model="localForm" ref="formRef" 
    >
      
       <el-form-item label="名称" prop="name">
        <el-input v-model="localForm.nickName" placeholder="请输入名称" />
      </el-form-item>

      <el-form-item label="描述" prop="description">
        <el-input v-model="localForm.description"  />
      </el-form-item>

         <el-form-item label="性别" prop="sex">
        <el-input v-model="localForm.sex"  />
      </el-form-item>


    </el-form>
   
    <template #footer>
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>

    </el-dialog>
  
</template>

<script setup>
import { ref, watch, reactive } from 'vue'
import { ElMessage } from 'element-plus'


const props = defineProps({
  dialogVisible: Boolean,
  formData: Object,
  isEditMode: Boolean,
})

const localForm = reactive({ ...props.formData })
watch(() => props.formData, (newVal) => {
  Object.assign(localForm, newVal)
})
const emit = defineEmits(['update:dialogVisible', 'submit'])
const dialogVisible = ref(props.modelValue)


const handleConfirm = () => {
   emit('submit', { ...localForm })
}
const closeDialog = () => {
  emit('update:modelValue', false)
}
const handleClose = (done) => {
  done(); 
}

</script>