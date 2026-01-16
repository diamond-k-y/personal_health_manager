<template>
  <div style="width: 60%; margin: 10px auto">
    <div class="card" style="padding: 20px">
      <div style="text-align: center; font-size: 25px; font-weight: bold; margin-bottom: 20px">{{ data.forum.title }}</div>
      <div style="display: flex; align-items: center; justify-content: center; color: #666; font-size: 13px; margin-bottom: 20px">
        <el-icon style="position: relative; top: 2px"><User /></el-icon>
        <span style="margin-left: 5px; margin-right: 20px">{{ data.forum.userName }}</span>
        <el-icon style="position: relative; top: 2px"><View /></el-icon>
        <span style="margin-left: 5px; margin-right: 20px">{{ data.forum.readCount }}</span>
        <el-icon style="position: relative; top: 2px"><Clock /></el-icon>
        <span style="margin-left: 5px;">{{ data.forum.time }}</span>
      </div>

      <div style="padding: 20px; font-size: 16px; color: #444; line-height: 24px" v-html="data.forum.content"></div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import router from "@/router/index.js";
import request from "@/utils/request.js";

const data = reactive({
  id: router.currentRoute.value.query.id,
  forum: {}
})

request.get('/forum/selectById/' + data.id).then(res => {
  data.forum = res.data
})
</script>