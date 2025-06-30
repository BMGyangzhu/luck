<template>
  <div style="margin-bottom: 20px; display: flex; gap: 10px;">
  <el-input v-model="searchForm.yv" placeholder="请输入 YV 号" clearable />
  <el-input v-model="searchForm.title" placeholder="请输入视频名称" clearable />
  <el-button type="primary" @click="handleSearch">搜索</el-button>
  <el-button @click="handleReset">重置</el-button>
</div>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="yv" label="YV号" width="180" />
    <el-table-column prop="title" label="名称" width="180" />
    <el-table-column prop="auditStateName" label="审核状态"/>
    <el-table-column prop="openName" label="视频状态"/>
    <el-table-column prop="msg" label="审核信息"/>
    <el-table-column prop="typeName" label="分类"/>
    <el-table-column prop="userName" label="上传者"/>


    <el-table-column>
      <template #default="scope">
           <el-button type="primary" @click="handleViolation(scope.row.id)">下架</el-button>
        <el-button type="primary" @click="handleAudit(scope.row)">审核</el-button>
        <el-button type="danger" @click="handleDelete(scope.row.id, scope.row.userId)">删除</el-button>
      </template>
     
    </el-table-column>

  </el-table>

   <!-- 分页 -->
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

<script setup>
import { reactive,ref } from 'vue'
import { apiGetVideos, apiDeleteVideo, apiAuditVideo, apiViolation} from '@/apis/video'
import { ElMessage, ElMessageBox } from 'element-plus'

const searchForm = reactive({
  yv: '',
  title: ''
})
const tableData = ref([{}])
// 分页
const pagination = reactive({
  current: 1,
  limit: 10,
  total: 0,
});
const handleSearch = () => {
  pagination.current = 1
  getVideos()
}
const handleReset = () => {
  searchForm.yv = ''
  searchForm.title = ''
  pagination.current = 1
  getVideos()
}
// 分页变化
const handleSizeChange = (limit) => {
  pagination.limit = limit;
  getVideos()
};

const handleCurrentChange = (current) => {
  pagination.current = current;
  getVideos()
};

// 封禁视频
const handleViolation = async (id) => {
  try {
    await ElMessageBox.confirm(
      '确定要封禁该视频吗？',
      '提示',
      { type: 'warning' }
    )
    await apiViolation(id)
    ElMessage.success('封禁成功')
    getVideos()
  } catch (error) {
    // 用户点击取消或请求异常都不处理
  }
}

// 审核通过视频
const handleAudit = async (row) => {
  try {
    await ElMessageBox.confirm(
      '确定要审核通过该视频吗？',
      '提示',
      { type: 'warning' }
    )
    const res = await apiAuditVideo(row);
    ElMessage.info(res.data.message)
    getVideos()
  } catch (error) {
    // 用户点击取消或请求异常都不处理
  }
}

// 删除视频
const handleDelete = async (id, userId) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除该视频吗？此操作不可恢复！',
      '警告',
      { type: 'warning' }
    )
    await apiDeleteVideo(id, userId)
    ElMessage.success('删除成功')
    getVideos()
  } catch (error) {
    // 用户点击取消或请求异常都不处理
  }
}
const getVideos = async () => {
    const params = {
      page: pagination.current,
      limit: pagination.limit,
      YV: searchForm.yv.trim(),
      title: searchForm.title.trim()
    };
    const res = await apiGetVideos(params)
    tableData.value = res.data.data
      pagination.total = res.data.count
}
getVideos()
</script>
<style>
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>