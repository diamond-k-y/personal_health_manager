<template>
  <div>
    <div class="card" style="margin-bottom: 5px">尊敬的<b>{{ data.user?.name }}</b>，您好！欢迎使用本系统,祝您今天有个好心情！</div>

    <div style="margin-bottom: 10px" v-if="data.user.role === 'USER'">
      <el-row :gutter="10">
        <el-col :span="12">
          <div class="card" style="padding: 20px">
            <div id="line1" style="height: 400px"></div>
          </div>
        </el-col>

        <el-col :span="12">
          <div class="card" style="padding: 20px">
            <div id="line2" style="height: 400px"></div>
          </div>
        </el-col>
      </el-row>
    </div>

    <div>
      <el-row :gutter="10">
        <el-col :span="12">
          <div class="card" style="flex: 50%; height: 520px">
            <div style="font-weight: bold; font-size: 18px; padding: 10px 0 30px 10px">系统公告</div>
            <el-timeline style="max-width: 600px">
              <el-timeline-item
                  color="green"
                  v-for="(item, index) in data.noticeData"
                  :key="index"
                  :timestamp="item.time"
              >
                {{ item.content }}
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-col>

        <el-col :span="12">
          <div class="card" style="flex: 50%; height: 400px" v-if="data.user.role === 'USER'">
            <div style="font-weight: bold; font-size: 18px; padding: 10px 0 30px 10px">今日健康提醒</div>
            <div style="padding: 20px; font-size: 16px">
              <div style="margin-bottom: 10px">身体指标：
                <span style="margin-left: 20px; color: #139313" v-if="data.record.body">已记录</span>
                <span style="margin-left: 20px; color: red" v-else>未记录</span>
              </div>
              <div style="margin-bottom: 10px">饮食情况：
                <span style="margin-left: 20px; color: #139313" v-if="data.record.eating">已记录</span>
                <span style="margin-left: 20px; color: red" v-else>未记录</span>
              </div>
              <div style="margin-bottom: 10px">运动情况：
                <span style="margin-left: 20px; color: #139313" v-if="data.record.sports">已记录</span>
                <span style="margin-left: 20px; color: red" v-else>未记录</span>
              </div>
              <div style="margin-bottom: 10px">睡眠情况：
                <span style="margin-left: 20px; color: #139313" v-if="data.record.sleep">已记录</span>
                <span style="margin-left: 20px; color: red" v-else>未记录</span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <div style="display: flex">

      <div style="flex: 50%"></div>
    </div>
  </div>
</template>

<script setup>

import {reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import * as echarts from 'echarts'

const lineOption1 = {
  title: {
    text: '近7天身体指标血压变化趋势',
    left: 'center',
    top: '1%'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left',
    top: '5%'
  },
  grid: {
    top: '20%',
    bottom: '10%'
  },
  xAxis: {
    name: '日期',
    type: 'category',
    data: []
  },
  yAxis: {
    name: 'mmHg',
    type: 'value'
  },
  series: [
    {
      name: '低压',
      data: [],
      type: 'line',
      smooth: true,
      areaStyle: {
        opacity: 0.8, // 阴影的透明度
        color: 'rgb(185,190,255)' // 阴影的颜色和透明度
      },
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
    {
      name: '高压',
      data: [],
      type: 'line',
      smooth: true,
      areaStyle: {
        opacity: 0.8, // 阴影的透明度
        color: 'rgb(185,190,255)' // 阴影的颜色和透明度
      },
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
  ]
}

const lineOption2 = {
  title: {
    text: '近7天运动时长趋势',
    subtext: '',
    left: 'center',
    top: '1%'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left',
    top: '5%'
  },
  grid: {
    top: '20%',
    bottom: '10%'
  },
  xAxis: {
    name: '日期',
    type: 'category',
    data: []
  },
  yAxis: {
    name: '运动时长（min）',
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'line',
      smooth: true,
      areaStyle: {
        opacity: 0.8, // 阴影的透明度
        color: 'rgb(185,190,255)' // 阴影的颜色和透明度
      },
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
  ]
}

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  noticeData: [],
  record: {}
})

// 等页面所有元素加载完成后再设置 echarts图表
onMounted(() => {
  if (data.user.role === 'USER') {
    // 请求数据  初始化图表
    // 折线图
    let lineDom1 = document.getElementById('line1')
    let lineChart1 = echarts.init(lineDom1)
    request.get('/selectLine1').then(res => {
      lineOption1.xAxis.data = res.data.date || []
      lineOption1.series[0].data = res.data.low || []
      lineOption1.series[1].data = res.data.high || []
      lineChart1.setOption(lineOption1)
    })

    // 折线图
    let lineDom2 = document.getElementById('line2')
    let lineChart2 = echarts.init(lineDom2)
    request.get('/selectLine2').then(res => {
      lineOption2.xAxis.data = res.data.date || []
      lineOption2.series[0].data = res.data.during || []
      lineChart2.setOption(lineOption2)
    })
  }

})
const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadNotice()

const loadRecord = () => {
  request.get('/selectRecord').then(res => {
    if (res.code === '200') {
      data.record = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
if (data.user.role === 'USER') {
  loadRecord()
}
</script>