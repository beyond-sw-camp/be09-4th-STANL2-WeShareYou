<template>
  <div class="login">
    <h1>Login</h1>
    <form @submit.prevent="login">
      <input v-model="loginId" placeholder="아이디" autocomplete="username" required />
      <input v-model="password" type="password" placeholder="비밀번호" autocomplete="current-password" required />
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/member/login'; // Pinia store import

const authStore = useAuthStore();
const router = useRouter();

const loginId = ref('');
const password = ref('');

// userInfo를 computed로 가져오기
const userInfo = computed(() => authStore.userInfo);

const login = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/v1/member/login', {
      loginId: loginId.value,
      password: password.value,
    });

    if (!response.data.success) {
      throw new Error(response.data.error?.message || '로그인 실패: 서버에서 인증하지 못했습니다.');
    }

    const jwtToken = response.data.result?.jwtToken;
    if (!jwtToken) {
      throw new Error('로그인 실패: 토큰을 받을 수 없습니다.');
    }

    authStore.setToken(jwtToken); // JWT 토큰을 Pinia에 저장

    console.log('User Info:', authStore.userInfo); // 유저 정보 출력

    alert('로그인 성공!');
    // router.push('/sample'); // 로그인 후 페이지 이동
  } catch (error) {
    console.error('Login failed:', error);
    alert(error.message || '로그인 실패! 아이디와 비밀번호를 확인해주세요.');
  }
};
</script>

<style scoped>
.login{
  display: flex;
  justify-content: center;
  margin-top: 5em;
}
input {
  display: block;
  margin-bottom: 10px;
  padding: 8px;
  width: 100%;
  max-width: 300px;
  box-sizing: border-box;
}

button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

h2 {
  margin-top: 20px;
}
</style>
