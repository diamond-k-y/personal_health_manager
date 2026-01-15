<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-date-picker style="width: 240px; margin-right: 10px" v-model="data.date" type="date" placeholder="请选择日期查询" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" plain @click="handleAdd" v-if="data.user.role === 'USER'">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="userName" label="用户名称"></el-table-column>
        <el-table-column prop="height" label="身高">
          <template v-slot="scope">
            {{ scope.row.height }}cm
          </template>
        </el-table-column>
        <el-table-column prop="weight" label="体重">
          <template v-slot="scope">
            {{ scope.row.weight }}kg
          </template>
        </el-table-column>
        <el-table-column prop="lowPressure" label="低压">
          <template v-slot="scope">
            {{ scope.row.lowPressure }}mmHg
          </template>
        </el-table-column>
        <el-table-column prop="highPressure" label="高压">
          <template v-slot="scope">
            {{ scope.row.highPressure }}mmHg
          </template>
        </el-table-column>
        <el-table-column prop="bloodSugar" label="血糖">
          <template v-slot="scope">
            {{ scope.row.bloodSugar }}mmol/L
          </template>
        </el-table-column>
        <el-table-column prop="heartRate" label="心率">
          <template v-slot="scope">
            {{ scope.row.heartRate }}次/分钟
          </template>
        </el-table-column>
        <el-table-column prop="date" label="记录日期"></el-table-column>

        <el-table-column label="操作" width="100" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)" v-if="data.user.role === 'USER'"></el-button>
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row.id)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>

    <el-dialog title="身体指标信息" v-model="data.formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="120px" style="padding: 20px">
        <el-form-item prop="height" label="身高(cm)">
          <el-input-number style="width: 240px" v-model="data.form.height" placeholder="请输入身高"></el-input-number>
        </el-form-item>
        <el-form-item prop="weight" label="体重(kg)">
          <el-input-number style="width: 240px" v-model="data.form.weight" placeholder="请输入体重"></el-input-number>
        </el-form-item>
        <el-form-item prop="lowPressure" label="低压(mmHg)">
          <el-input-number style="width: 240px" v-model="data.form.lowPressure" placeholder="请输入低压"></el-input-number>
        </el-form-item>
        <el-form-item prop="highPressure" label="高压(mmHg)">
          <el-input-number style="width: 240px" v-model="data.form.highPressure" placeholder="请输入高压"></el-input-number>
        </el-form-item>
        <el-form-item prop="bloodSugar" label="血糖(mmol/L)">
          <el-input-number style="width: 240px" v-model="data.form.bloodSugar" placeholder="请输入血糖"></el-input-number>
        </el-form-item>
        <el-form-item prop="heartRate" label="心率(次/分钟)">
          <el-input-number style="width: 240px" v-model="data.form.heartRate" placeholder="请输入心率"></el-input-number>
        </el-form-item>
        <el-form-item prop="date" label="记录日期">
          <el-date-picker style="width: 240px" v-model="data.form.date" type="date" placeholder="记录日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
        </el-form-item>

      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>

import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";


const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  formVisible: false,
  form: {},
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  date: null,
  ids: [],
  rules: {
    height: [
      {  required: true, message: '请输入身高', trigger: 'blur' }
    ],
    weight: [
      {  required: true, message: '请输入体重', trigger: 'blur' }
    ],
    lowPressure: [
      {  required: true, message: '请输入低压', trigger: 'blur' }
    ],
    highPressure: [
      {  required: true, message: '请输入高压', trigger: 'blur' }
    ],
    bloodSugar: [
      {  required: true, message: '请输入血糖', trigger: 'blur' }
    ],
    heartRate: [
      {  required: true, message: '请输入心率', trigger: 'blur' }
    ],
  }
})

const formRef = ref()

const load = () => {
  // 管理员查看所有数据，普通用户只能查看自己的数据
  const params = {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    date: data.date
  }
  // 如果是普通用户，添加userId过滤条件
  if (data.user.role !== 'ADMIN') {
    params.userId = data.user.id
  }
  request.get('/bodyRecords/selectPage', { params }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []
      data.total = res.data?.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const add = () => {
  data.form.userId = data.user.id
  request.post('/bodyRecords/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const update = () => {
  request.put('/bodyRecords/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.form.id ? update() : add()
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/bodyRecords/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success("删除成功")
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("请选择数据")
    return
  }
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete("/bodyRecords/delete/batch", {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const reset = () => {
  data.date = null
  load()
}

load()
</script>