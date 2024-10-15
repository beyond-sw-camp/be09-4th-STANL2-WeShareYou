<!-- vbase-3-setup -->
<template>
  <div>
    <h1>Member Information</h1>
    <div v-if="userInfo && jwtToken">
      <p><strong>ID:</strong> {{ userInfo.id }}</p>
      <p><strong>Login ID:</strong> {{ userInfo.loginId }}</p>
      <p><strong>Nationality:</strong> {{ userInfo.nationality }}</p>
      <p><strong>Sex:</strong> {{ userInfo.sex }}</p>
      <p><strong>Nickname:</strong> {{ userInfo.nickname }}</p>
      <p><strong>Language:</strong> {{ userInfo.language }}</p>
      <p><strong>Point:</strong> {{ userInfo.point }}</p>
      <p><strong>JWT Token:</strong> {{ jwtToken }}</p>
    </div>
    <button @click="refreshInfo">Refresh Info</button>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useAuthStore } from '@/stores/member/login'; // Pinia 스토어 가져오기

// Pinia 스토어 사용
const authStore = useAuthStore();

// Pinia 스토어에서 상태를 가져오는 반응형 computed 속성
const userInfo = computed(() => authStore.userInfo);
const jwtToken = computed(() => authStore.jwtToken);

// 새로고침 버튼 클릭 시 회원 정보와 토큰을 다시 로드 (실제 로딩이 필요 없을 경우 생략 가능)
const refreshInfo = () => {
  console.log('User Info:', userInfo.value);
  console.log('JWT Token:', jwtToken.value);
};
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
