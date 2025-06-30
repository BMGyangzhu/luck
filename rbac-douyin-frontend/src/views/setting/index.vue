<template>
  <el-form :model="form" label-width="160px"  >
    <!-- 资源白名单 -->
    <el-form-item label="白名单IP">
      <el-input v-model="form.whiteIp" placeholder="请输入白名单ip,多个用,分割" />
    </el-form-item>

    <!-- 热门视频设置 -->
    <el-divider content-position="left">热门视频热度（大于该值则显示在热门视频上）</el-divider>
    <el-form-item label="热度值">
      <el-input v-model="form.hotThreshold" />
    </el-form-item>
    <el-form-item label="审核开关">
      <el-switch v-model="form.auditEnabled" />
    </el-form-item>

    <!-- 自动审核通过比例 -->
    <el-divider content-position="left">自动审核通过比例</el-divider>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="图片鉴黄最小值">
          <el-input v-model="form.auto.pornMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片鉴黄最大值">
          <el-input v-model="form.auto.pornMax" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="图片鉴暴恐最小值">
          <el-input v-model="form.auto.terrorMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片鉴暴恐最大值">
          <el-input v-model="form.auto.terrorMax" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="图片敏感人物识别最小值">
          <el-input v-model="form.auto.personMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片敏感人物识别最大值">
          <el-input v-model="form.auto.personMax" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 人工审核比例 -->
    <el-divider content-position="left">人工审核比例</el-divider>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="图片鉴黄最小值">
          <el-input v-model="form.manual.pornMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片鉴黄最大值">
          <el-input v-model="form.manual.pornMax" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="图片鉴暴恐最小值">
          <el-input v-model="form.manual.terrorMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片鉴暴恐最大值">
          <el-input v-model="form.manual.terrorMax" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="图片敏感人物识别最小值">
          <el-input v-model="form.manual.personMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片敏感人物识别最大值">
          <el-input v-model="form.manual.personMax" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- PASS审核比例 -->
    <el-divider content-position="left">PASS审核比例</el-divider>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="图片鉴黄最小值">
          <el-input v-model="form.pass.pornMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片鉴黄最大值">
          <el-input v-model="form.pass.pornMax" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="图片鉴暴恐最小值">
          <el-input v-model="form.pass.terrorMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片鉴暴恐最大值">
          <el-input v-model="form.pass.terrorMax" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="图片敏感人物识别最小值">
          <el-input v-model="form.pass.personMin" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="图片敏感人物识别最大值">
          <el-input v-model="form.pass.personMax" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 提交按钮 -->
    <el-form-item>
      <el-button type="primary" @click="submit">保存配置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue'
import { apiGetSetting, apiEditSetting } from '@/apis/setting'
import { ElMessage } from 'element-plus'

const form = reactive({
  whiteIp: '',
  hotThreshold: '',
  auditEnabled: true,
  auto: {
    pornMin: '',
    pornMax: '',
    terrorMin: '',
    terrorMax: '',
    personMin: '',
    personMax: ''
  },
  manual: {
    pornMin: '',
    pornMax: '',
    terrorMin: '',
    terrorMax: '',
    personMin: '',
    personMax: ''
  },
  pass: {
    pornMin: '',
    pornMax: '',
    terrorMin: '',
    terrorMax: '',
    personMin: '',
    personMax: ''
  }
})

const getSetting = async () => {
  const res = await apiGetSetting()
  const data = res.data.data
  const audit = data.settingScoreJson

  form.id = data.id
  form.whiteIp = data.allowIp
  form.hotThreshold = data.hotLimit
  form.auditEnabled = data.auditOpen

  form.auto = {
    pornMin: audit.successScore.minPulp,
    pornMax: audit.successScore.maxPulp,
    terrorMin: audit.successScore.minTerror,
    terrorMax: audit.successScore.maxTerror,
    personMin: audit.successScore.minPolitician,
    personMax: audit.successScore.maxPolitician
  }

  form.manual = {
    pornMin: audit.manualScore.minPulp,
    pornMax: audit.manualScore.maxPulp,
    terrorMin: audit.manualScore.minTerror,
    terrorMax: audit.manualScore.maxTerror,
    personMin: audit.manualScore.minPolitician,
    personMax: audit.manualScore.maxPolitician
  }

  form.pass = {
    pornMin: audit.passScore.minPulp,
    pornMax: audit.passScore.maxPulp,
    terrorMin: audit.passScore.minTerror,
    terrorMax: audit.passScore.maxTerror,
    personMin: audit.passScore.minPolitician,
    personMax: audit.passScore.maxPolitician
  }
}

const submit = async () => {
  try {
    // 结构转换为后端格式
    const payload = {
      id: form.id,
      allowIp: form.whiteIp,
      hotLimit: form.hotThreshold,
      auditOpen: form.auditEnabled,
      settingScoreJson: {
        successScore: {
          minPulp: form.auto.pornMin,
          maxPulp: form.auto.pornMax,
          minTerror: form.auto.terrorMin,
          maxTerror: form.auto.terrorMax,
          minPolitician: form.auto.personMin,
          maxPolitician: form.auto.personMax,
          auditStatus: 0
        },
        manualScore: {
          minPulp: form.manual.pornMin,
          maxPulp: form.manual.pornMax,
          minTerror: form.manual.terrorMin,
          maxTerror: form.manual.terrorMax,
          minPolitician: form.manual.personMin,
          maxPolitician: form.manual.personMax,
          auditStatus: 3
        },
        passScore: {
          minPulp: form.pass.pornMin,
          maxPulp: form.pass.pornMax,
          minTerror: form.pass.terrorMin,
          maxTerror: form.pass.terrorMax,
          minPolitician: form.pass.personMin,
          maxPolitician: form.pass.personMax,
          auditStatus: 2
        }
      }
    }

    await apiEditSetting(payload)
    ElMessage.success('保存成功')
  } catch (err) {
    console.error('保存失败:', err)
    ElMessage.error('保存失败')
  }
}

getSetting()
</script>
