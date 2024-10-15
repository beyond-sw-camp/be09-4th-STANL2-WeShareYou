<template>
  <div>
    <h1>Health Check</h1>
    <div v-if="healthData">
      <p><strong>ID:</strong> {{ healthData.id }}</p>
      <p><strong>Login ID:</strong> {{ healthData.loginId }}</p>
      <p><strong>Nationality:</strong> {{ healthData.nationality }}</p>
      <p><strong>Sex:</strong> {{ healthData.sex }}</p>
      <p><strong>Nickname:</strong> {{ healthData.nickname }}</p>
      <p><strong>Language:</strong> {{ healthData.language }}</p>
      <p><strong>Point:</strong> {{ healthData.point }}</p>
    </div>
    <button @click="loadHealthData">Refresh Data</button>
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useMemberStore } from '@/stores/sample/sample'; // Pinia 스토어 사용

const memberStore = useMemberStore();

// healthData를 computed로 감싸기 (반응성 문제 해결)
const healthData = computed(() => memberStore.healthData);

// 데이터 로딩 함수
const loadHealthData = async () => {
  await memberStore.fetchHealthData();
};

// 페이지가 마운트될 때 데이터 로딩
onMounted(() => {
  loadHealthData();
});
</script>

<style scoped>
h1 {
  margin-bottom: 10px;
}

p {
  margin: 5px 0;
}

button {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
