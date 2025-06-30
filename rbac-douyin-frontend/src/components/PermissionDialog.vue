<template>
    <el-dialog 
    :close-on-click-modal = "false"
    :close-on-press-escape = "false"
    :model-value="dialogVisible"
    :title="isEditMode ? '编辑权限' : '新增权限'"
     width="700px"
     @update:model-value="$emit('update:dialogVisible', $event)"
     :before-close="handleClose"
    >
    <el-form :model="localForm" ref="formRef" 
    >
      <el-form-item label="上级菜单">
       <el-cascader  v-model="cascaderOptions.value" clearable 
       :props="{ checkStrictly: true}"
       :options="cascaderOptions" @change="handleChange" />
      </el-form-item>

       <el-form-item label="菜单名称" prop="name">
        <el-input v-model="localForm.name" placeholder="请输入菜单名称" />
      </el-form-item>

      <el-form-item label="菜单地址" prop="href">
        <el-input v-model="localForm.href" placeholder="例如: page/xxx.html" />
      </el-form-item>

      <el-form-item label="权限标识" prop="path">
        <el-input v-model="localForm.path" placeholder="例如: system:list" />
      </el-form-item>

      <el-form-item label="菜单类型" prop="isMenu">
      <el-radio-group v-model="localForm.isMenu">
        <el-radio :label="0">菜单</el-radio>
        <el-radio :label="1">按钮</el-radio>
        <el-radio :label="-1">目录</el-radio>
      </el-radio-group>
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
const cascaderOptions = ref([])
const props = defineProps({
  dialogVisible: Boolean,
  formData: Object,
  isEditMode: Boolean,
  options: Array // 接收父组件传进来的级联数据
})
const emit = defineEmits(['update:dialogVisible', 'submit'])
// 复制一份可编辑的 formData 避免直接改 props
const localForm = reactive({ ...props.formData })
// 如果 props.formData 变化，需要同步到 localForm
watch(() => props.formData, (newVal) => {
  Object.assign(localForm, newVal)
})
watch(
  () => props.options,
  (newVal) => {
    cascaderOptions.value = convertToCascaderOptions(newVal)
  },
  { immediate: true }
)
const formRef = ref(null)
const handleSubmit = async () => {
  try {
    // 简单的判空逻辑（你可以按需扩展）
    if (!localForm.name  || !('isMenu' in localForm)) {
      ElMessage.warning('请填写完整的权限信息')
      return
    }

    // 可选：判断 pid 是否为 number
    if (localForm.pid !== 0 && !localForm.pid) {
      ElMessage.warning('请选择上级菜单')
      return
    }

    // 表单校验（如果用了 <el-form> 的 prop 规则）
    await formRef.value.validate()

    // 发送表单
    emit('submit', { ...localForm })
  } catch (error) {
    console.error('表单验证失败:', error)
  }
}
// 处理级联选择器变化
const handleChange = (val) => {
  console.log('选中的上级菜单:', val)
  localForm.pid = val.length > 0 ? val[val.length - 1] : 0
}
function convertToCascaderOptions(data) {
  return data.map(item => {
    const option = {
      label: item.name,
      value: item.id,
    }
    if (item.children && item.children.length > 0) {
      option.children = convertToCascaderOptions(item.children)
    }
    return option
  })
}
const handleClose = (done) => {
  done(); 
}
const closeDialog = () => {
  emit('update:modelValue', false)
}

</script>