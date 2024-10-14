<template>
    <div>
      <h1>Health Check</h1>
      <div v-if="healthData">
        <p><strong>ID:</strong> {{ healthData.id }}</p>
        <p><strong>Nickname:</strong> {{ healthData.nickname }}</p>
        <p><strong>Language:</strong> {{ healthData.language }}</p>
      </div>
      <button @click="loadHealthData">Refresh Data</button>
    </div>
  </template>
  
  <script setup>
  import { onMounted } from 'vue';
  import { useMemberStore } from '@/stores/sample/sample'; // Pinia 스토어 사용
  
  const memberStore = useMemberStore();
  
  const loadHealthData = async () => {
    await memberStore.fetchHealthData();
  };
  
  onMounted(() => {
    loadHealthData();
  });
  
  const healthData = memberStore.healthData;
  </script>
  
  <style scoped>
  h1 {
    margin-bottom: 10px;
  }
  </style>
  