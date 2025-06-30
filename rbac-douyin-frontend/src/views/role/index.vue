<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="name" label="角色" width="180" />
    <el-table-column prop="remark" label="备注" width="180" />
     <el-table-column label="操作">
    <template #default="scope">
      <el-button type="primary" @click="open(scope.row)">授权</el-button>
      <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
    </template>
  </el-table-column>
  </el-table>

  <AssignPermissionDialog 
    v-model="dialogVisible"
    :roleId="currentRoleId"
    @submit="handleAssign"/>
</template>

<script setup>
import { ref } from 'vue'
import { apiGetRoles, apiAssignRolePermission, apiDeleteRole } from '@/apis/role'
import { ElMessage,ElMessageBox } from 'element-plus'
import AssignPermissionDialog from '@/components/AssignPermissionDialog.vue'

const tableData = ref([{}])
const dialogVisible = ref(false);
const currentRoleId = ref(null)

const getRoles = async () => {
  const res = await apiGetRoles()
  tableData.value = res.data.data
}

const open = (row) => {
  currentRoleId.value = row.id
  dialogVisible.value = true;
}

const handleAssign = async (checkedKeys) => {

  await apiAssignRolePermission(currentRoleId.value, checkedKeys)
  dialogVisible.value = false;
}
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除这个角色吗？删除后无法恢复！',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )

    // 用户点击了“确定”
    await apiDeleteRole(id)
    ElMessage.success('删除成功')
    getRoles()
  } catch (err) {
    // 用户点击了“取消”或关闭了弹窗，不做任何处理
    if (err !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

getRoles()

</script>