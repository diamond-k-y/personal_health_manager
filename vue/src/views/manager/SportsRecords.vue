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
        <el-table-column prop="type" label="运动类型"></el-table-column>
        <el-table-column prop="during" label="运动时长">
          <template v-slot="scope">
            {{ scope.row.during }}分钟
          </template>
        </el-table-column>
        <el-table-column prop="distance" label="运动距离">
          <template v-slot="scope">
            {{ scope.row.distance }}km
          </template>
        </el-table-column>
        <el-table-column prop="calorie" label="卡路里">
          <template v-slot="scope">
            {{ scope.row.calorie }}kcal
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

    <el-dialog title="运动指标信息" v-model="data.formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="120px" style="padding: 20px">
        <el-form-item prop="type" label="运动类型">
          <el-select style="width: 200px" v-model="data.form.type">
            <el-option v-for="item in ['游泳', '跑步', '快走', '健身', '爬山']" :key="item" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="during" label="运动时长(分钟)">
          <el-input-number style="width: 200px" v-model="data.form.during" placeholder="请输入运动时长"></el-input-number>
        </el-form-item>
        <el-form-item prop="distance" label="运动距离(km)">
          <el-input-number style="width: 200px" v-model="data.form.distance" placeholder="请输入运动距离"></el-input-number>
        </el-form-item>
        <el-form-item prop="calorie" label="卡路里(kcal)">
          <el-input-number style="width: 200px" v-model="data.form.calorie" placeholder="请输入卡路里"></el-input-number>
        </el-form-item>
        <el-form-item prop="date" label="记录日期">
          <el-date-picker style="width: 200px" v-model="data.form.date" type="date" placeholder="记录日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
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
    type: [
      {  required: true, message: '请选择运动类型', trigger: 'change' }
    ],
    during: [
      {  required: true, message: '请输入运动时长', trigger: 'blur' }
    ],
    distance: [
      {  required: true, message: '请输入运动距离', trigger: 'blur' }
    ],
    calorie: [
      {  required: true, message: '请输入消耗的卡路里', trigger: 'blur' }
    ],
  }
})

const formRef = ref()

const load = () => {
  request.get('/sportsRecords/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      date: data.date,
      userId: data.user.role === 'USER' ? data.user.id : null
    }
  }).then(res => {
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
  request.post('/sportsRecords/add', data.form).then(res => {
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
  request.put('/sportsRecords/update', data.form).then(res => {
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
    request.delete('/sportsRecords/delete/' + id).then(res => {
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
    request.delete("/sportsRecords/delete/batch", {data: data.ids}).then(res => {
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