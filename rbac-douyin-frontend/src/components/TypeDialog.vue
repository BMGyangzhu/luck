<template>
    <el-dialog 
    :close-on-click-modal = "false"
    :close-on-press-escape = "false"
    :model-value="dialogVisible"
    :title="isEditMode ? '编辑分类' : '新增分类'"
     width="700px"
     @update:model-value="$emit('update:dialogVisible', $event)"
     :before-close="handleClose"
    >
    <el-form :model="localForm" ref="formRef" 
    >
      
       <el-form-item label="分类名称" prop="name">
        <el-input v-model="localForm.name" placeholder="请输入分类名称" />
      </el-form-item>

      <el-form-item label="描述" prop="href">
        <el-input v-model="localForm.description"  />
      </el-form-item>

      <el-form-item label="状态" prop="open">
        <el-input v-model="localForm.open" />
      </el-form-item>

       <el-form-item label="标签" prop="open">
        <el-input v-model="localForm.labelNames" />
      </el-form-item>

    </el-form>
    
    <template #footer>
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
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
const emit = defineEmits(['update:dialogVisible', 'submit'])
// 复制一份可编辑的 formData 避免直接改 props
const localForm = reactive({ ...props.formData })
// 如果 props.formData 变化，需要同步到 localForm
watch(() => props.formData, (newVal) => {
  Object.assign(localForm, newVal)
})

const formRef = ref(null)
const handleSubmit = async () => {
  try {
    if (!localForm.name) {
      ElMessage.warning('请填写完整的权限信息')
      return
    }
    
    await formRef.value.validate()

    emit('submit', { ...localForm })
  } catch (error) {
    console.error('表单验证失败:', error)
  }
}


const handleClose = (done) => {
  done(); 
}
const closeDialog = () => {
  emit('update:modelValue', false)
}

</script>