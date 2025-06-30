<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="Id" width="180" />
    <el-table-column prop="nickName" label="昵称" width="180" />
    <el-table-column label="角色" width="200">
      <template #default="scope">
        <el-tag
          v-for="(role, index) in scope.row.roleName"
          :key="index"
          type="danger"
          style="margin-right: 5px; font-weight: bold;"
        >
          {{ role }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column>
      <template #default="scope">
        <el-button type="primary" @click="open(scope.row)">分配角色</el-button>
      </template>
    </el-table-column>

  </el-table>

   <AssignRoleDialog
        v-model="dialogVisible"
        :userId = "currentUserId"
        @submit="handleAssign"
    />

     <div class="pagination">
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.limit"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
</template>

<script  setup>
import { ref, reactive } from 'vue'
import  AssignRoleDialog  from '@/components/AssignRoleDialog.vue'
import { apiGetUsers } from '@/apis/user';
import { apiAssignRole } from '@/apis/role'
const currentUserId = ref(null)
const dialogVisible = ref(false);
const open = (row) => {
  currentUserId.value = row.id;
  dialogVisible.value = true;
}
const pagination = reactive({
  current: 1,
  limit: 10,
  total: 0,
});
// 分页变化
const handleSizeChange = (limit) => {
  pagination.limit = limit;
  getUsers()
}
const handleCurrentChange = (current) => {
  pagination.current = current;
  getUsers()
}
const handleAssign = async (selectedOptions) => {
  const uId = selectedOptions.uId
  const rId = selectedOptions.rId
  await apiAssignRole(uId, rId)
  dialogVisible.value = false;
  getUsers()
}

const tableData = ref([{}])
const getUsers = async () => {
  const params = {
    page: pagination.current,
    limit: pagination.limit,

  }
  const res = await apiGetUsers(params)
  tableData.value = res.data.data
  pagination.total = res.data.count

}

getUsers()
</script>