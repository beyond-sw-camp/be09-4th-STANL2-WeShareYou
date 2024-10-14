<template>
    <div>
      <h1>Login</h1>
      <input v-model="loginId" placeholder="아이디" />
      <input v-model="password" type="password" placeholder="비밀번호" />
      <button @click="login">로그인</button>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from 'axios'; // Axios 인스턴스 또는 기본 axios 사용.
  import router from '@/router'; // 로그인 성공 시 이동할 라우터.
  
  // 로그인 정보 입력값
  const loginId = ref('');
  const password = ref('');
  
  // 로그인 함수
  const login = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/v1/member/login', {
        loginId: loginId.value,
        password: password.value,
      });
  
      const jwtToken = response.data.jwtToken;
      if (!jwtToken) {
      throw new Error('로그인 실패: 토큰을 받을 수 없습니다.');
    }
      localStorage.setItem('token', jwtToken); // JWT 토큰 저장
  
      alert('로그인 성공!');
      router.push('/sample'); // 로그인 후 health-check 페이지로 이동
    } catch (error) {
      console.error('Login failed:', error);
      alert('로그인 실패! 아이디와 비밀번호를 확인해주세요.');
    }
  };
  </script>
  
  <style scoped>
  input {
    display: block;
    margin-bottom: 10px;
  }
  </style>
  