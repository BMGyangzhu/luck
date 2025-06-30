<template>
  <el-button type="primary" @click="handleAdd">新增</el-button>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="nickName" label="昵称" />
    <el-table-column prop="description" label="描述"  />
    <el-table-column prop="sex" label="性别" :formatter="formatSex"></el-table-column>
     <el-table-column label="操作">
    <template #default="scope">
      <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
      <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
    </template>
  </el-table-column>
  </el-table>

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

  <UserDialog
    v-model="dialogVisible"
    :is-edit-mode = "isEditMode"
    :form-data="currentUser"
    @submit="handleDialogSubmit"
  >

  </UserDialog>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { apiGetUsers, apiAddUser, apiEditUser, apiDeleteUser } from '@/apis/user';
import UserDialog from '@/components/UserDialog.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const formatSex = (row) => {
  return row.sex ? '男' : '女'
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
const tableData = ref([{}])
const getUsers = async () => {
  const params = {
    page: pagination.current,
    limit: pagination.limit,
  }
  const res = await apiGetUsers(params)
  tableData.value = res.data.data;
  pagination.total = res.data.count
}

// 对话框相关
const dialogVisible = ref(false);
const currentUser = ref({});
const isEditMode = ref(false);

const handleAdd = () => {
  isEditMode.value = false
 currentUser.value = {}
  dialogVisible.value = true;
}
const handleEdit = (row) => {
  isEditMode.value = true
  currentUser.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm("确定删除该权限吗？", "提示", {
      type: "warning",
    });
    await apiDeleteUser(id);
    ElMessage.success("删除成功");
    getUsers()
  } catch (error) {
    console.error("删除失败:", error);
  }
};

// 对话框提交
const handleDialogSubmit = async (formData) => {
  try {
    if (isEditMode.value) {
      await apiEditUser(formData);
      console.log("formData: ", formData);
      ElMessage.success("修改成功");
    } else {
      await apiAddUser(formData);
      ElMessage.success("新增成功");
    }
    dialogVisible.value = false;
    getUsers();
  } catch (error) {
    console.error("操作失败:", error);
  }
};
getUsers()
</script>