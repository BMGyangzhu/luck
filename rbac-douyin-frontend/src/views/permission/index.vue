<template>
  <div>
    <div style="margin-bottom: 10px;">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="warning" @click="handleEdit(selectedRows[0])" :disabled="selectedRows.length !== 1">修改</el-button>
      <el-button type="danger" @click="handleDelete(selectedRows[0]?.id)" :disabled="!selectedRows.length">删除</el-button>
      <!-- <el-button @click="loadData">刷新</el-button> -->
    </div>
    <el-table :data="tableData" style="width: 100%; margin-bottom: 20px" row-key="id"
     border
     default-expand-all
     :tree-props="{ children: 'children', checkStrictly: true }"
      @selection-change="handleSelectionChange"
     >
     <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="path" label="权限路径" />
      <el-table-column prop="href" label="跳转地址" />
      <el-table-column prop="isMenu" :formatter="(row) => {
          return row.isMenu === -1
            ? '目录'
            : row.isMenu === 0
              ? '菜单'
              : '按钮';
        }
        " label="菜单类型" />
    </el-table>


    <PermissionDialog
      v-model="dialogVisible"
      :form-data="currentPermission"
      :is-edit-mode="isEditMode"
      :options="permissionCascader"
      @submit="handleDialogSubmit"
      >

    </PermissionDialog>
  </div>
</template>

<script setup>
import { apiGetPermissions, apiGetPermissionCascader, apiAddPermission, apiEditPermission, apiDeletePermission  } from "@/apis/permission";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus'
import PermissionDialog from "@/components/PermissionDialog.vue"

const permissionCascader = ref([{}])

const getPermissionCascader = async () => {
  const res = await apiGetPermissionCascader()
  permissionCascader.value = res.data;
}
// 对话框相关
const dialogVisible = ref(false);
const currentPermission = ref({});
const isEditMode = ref(false);

const handleAdd = () => {
  isEditMode.value = false
 currentPermission.value = {}
  dialogVisible.value = true;
}
const handleEdit = (row) => {
  isEditMode.value = true
  currentPermission.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm("确定删除该权限吗？", "提示", {
      type: "warning",
    });
    await apiDeletePermission(id);
    ElMessage.success("删除成功");
    getPermissions();
  } catch (error) {
    console.error("删除失败:", error);
  }
};

// 对话框提交
const handleDialogSubmit = async (formData) => {
  try {
    if (isEditMode.value) {
      await apiEditPermission(formData);
      console.log("formData: ", formData);
      ElMessage.success("修改成功");
    } else {
      await apiAddPermission(formData);
      ElMessage.success("新增成功");
    }
    dialogVisible.value = false;
    getPermissions();
  } catch (error) {
    console.error("操作失败:", error);
  }
};

const selectedRows = ref([])
const getPermissions = async () => {
  const res = await apiGetPermissions();
  tableData.value = res.data;
};
 const handleSelectionChange = (val) => {
    selectedRows.value = val
    console.log('选中的菜单：', selectedRows.value)
}

getPermissions();
getPermissionCascader();
const tableData = ref([{}]);
</script>
