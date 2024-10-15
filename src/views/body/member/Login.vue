<template>
  <div class="login-container">
    <div class="login-box">
      <h1 class="login-title">로그인</h1>
      <form @submit.prevent="login">
        <div class="input-group">
          <label for="loginId">아이디</label>
          <input 
            v-model="loginId" 
            id="loginId" 
            placeholder="아이디를 입력해 주세요." 
            autocomplete="username" 
            required 
          />
        </div>
        
        <div class="input-group">
          <label for="password">비밀번호</label>
          <input 
            v-model="password" 
            id="password" 
            type="password" 
            placeholder="비밀번호를 입력해 주세요." 
            autocomplete="current-password" 
            required 
          />
        </div>

        <button type="submit" class="login-button">로그인</button>
      </form>

      <div class="login-options">
        <router-link to="/signup">회원가입</router-link>
        <span>|</span>
        <router-link to="/findId">아이디 찾기</router-link>
        <span>|</span>
        <router-link to="/findPwd">비밀번호 찾기</router-link>

      </div>
    </div>
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
    router.push('/'); // 로그인 후 페이지 이동
  } catch (error) {
    console.error('Login failed:', error);
    alert(error.message || '로그인 실패! 아이디와 비밀번호를 확인해주세요.');
  }
};
</script>

<style scoped>
/* 컨테이너 스타일 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f6f8;
}

/* 로그인 박스 */
.login-box {
  width: 400px;
  padding: 40px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* 타이틀 스타일 */
.login-title {
  margin-bottom: 20px;
  color: #007bff;
  font-size: 24px;
}

/* 인풋 그룹 */
.input-group {
  margin-bottom: 15px;
  text-align: left;
  color: #090F16;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
  /* font-weight: bold; */
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

/* 로그인 버튼 */
.login-button {
  width: 100%;
  padding: 10px;
  background-color: #439AFF;
  color: white;
  border: none;
  cursor: pointer;
}

.login-button:hover {
  background-color: #45a049;
}

/* 로그인 옵션 (회원가입, 아이디 찾기, 비밀번호 찾기) */
.login-options {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  gap: 10px;
  font-size: 14px;
}

.login-options a {
  text-decoration: none;
  color: #627086;
}
.login-options :hover{
  text-decoration: none;
  color: #94C7FF;
}

.login-options span {
  color: #ccc;
}
</style>
