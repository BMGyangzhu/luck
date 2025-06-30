<template>
    <el-dialog
    :close-on-click-modal = "false"
    :close-on-press-escape = "false"
    title="授权"
    width="500"
    :model-value="dialogVisible"
    @update:model-value="$emit('update:dialogVisible', $event)"
    :before-close="handleClose"
    >
    <el-tree
    ref="treeRef"
    style="max-width: 600px"
    :data="data"
    show-checkbox
    node-key="id"
    :props="defaultProps"
    :check-strictly="true" 
  />
    <template #footer>
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>

    </el-dialog>
  
</template>

<script  setup>
import { ref, watch, nextTick } from 'vue'
import { apiGetPermissions } from '@/apis/permission';
import { apiGetRolePermission } from '@/apis/role';
const defaultProps = {
  children: 'children',
  label: 'name',
}
const props = defineProps({
  dialogVisible: Boolean,
  roleId: Number
})
const emit = defineEmits(['update:dialogVisible', 'submit'])
const dialogVisible = ref(props.modelValue)
const data = ref([])
const checked = ref([])
const treeRef = ref()


const getRolePermission = async (roleId) => {
    const res = await apiGetRolePermission(roleId)
    return res
    
}

const getPermissions = async () => {
  const res = await apiGetPermissions()
  data.value = res.data

}
const handleConfirm = () => {
  const checkedKeys = treeRef.value.getCheckedKeys()
  emit('submit', checkedKeys)
  emit('update:dialogVisible', false)
}
const closeDialog = () => {
  emit('update:modelValue', false)
}
const handleClose = (done) => {
  done(); 
}
watch(
  () => props.roleId,
  async (newVal) => {
    if (newVal) {
      const res = await getRolePermission(newVal)
      checked.value = res.data

     await nextTick() // 等 el-tree 渲染出来
      if (treeRef.value) {
        treeRef.value.setCheckedKeys([])        // 清空旧勾选
        treeRef.value.setCheckedKeys(res.data)  // 设置新勾选

        
      }
    }
  },
  { immediate: true }
)
getPermissions()

</script>