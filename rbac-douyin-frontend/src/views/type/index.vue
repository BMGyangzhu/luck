<template>
  <el-button type="primary" @click="handleAdd">新增</el-button>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="name" label="名称" width="180" />
    <el-table-column prop="description" label="描述" width="180" />
    <el-table-column prop="open" label="状态" width="180" />
    <el-table-column prop="labelNames" label="标签" width="180" />
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

  <TypeDialog
    v-model="dialogVisible"
    :is-edit-mode = "isEditMode"
    :form-data="currentType"
    @submit="handleDialogSubmit"
  >

  </TypeDialog>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { apiGetTypes, apiAddType, apiEditType, apiDeleteType } from '@/apis/type';
import TypeDialog from '@/components/TypeDialog.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const pagination = reactive({
  current: 1,
  limit: 10,
  total: 0,
});
// 分页变化
const handleSizeChange = (limit) => {
  pagination.limit = limit;
  getTypes()
}
const handleCurrentChange = (current) => {
  pagination.current = current;
  getTypes()
}
const tableData = ref([{}])
const getTypes = async () => {
  const params = {
    page: pagination.current,
    limit: pagination.limit,
  }
  const res = await apiGetTypes(params)
  tableData.value = res.data.data;
  pagination.total = res.data.count
}

// 对话框相关
const dialogVisible = ref(false);
const currentType = ref({});
const isEditMode = ref(false);

const handleAdd = () => {
  isEditMode.value = false
 currentType.value = {}
  dialogVisible.value = true;
}
const handleEdit = (row) => {
  isEditMode.value = true
  currentType.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm("确定删除该权限吗？", "提示", {
      type: "warning",
    });
    await apiDeleteType(id);
    ElMessage.success("删除成功");
    getTypes()
  } catch (error) {
    console.error("删除失败:", error);
  }
};

// 对话框提交
const handleDialogSubmit = async (formData) => {
  try {
    if (isEditMode.value) {
      await apiEditType(formData);
      console.log("formData: ", formData);
      ElMessage.success("修改成功");
    } else {
      await apiAddType(formData);
      ElMessage.success("新增成功");
    }
    dialogVisible.value = false;
    getTypes();
  } catch (error) {
    console.error("操作失败:", error);
  }
};
getTypes()
</script>