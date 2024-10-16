import { defineStore } from 'pinia';

// JWT 디코딩 함수
function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload); // JSON으로 파싱된 Payload 반환
  } catch (error) {
    console.error('Invalid JWT Token:', error);
    return null;
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    jwtToken: null,  // JWT 토큰 저장
    userInfo: {},    // JWT에서 추출한 유저 정보 저장
  }),

  actions: {
    // JWT 토큰 설정 및 localStorage에 저장
    setToken(token) {
      this.jwtToken = token;
      const decoded = parseJwt(token); // JWT 디코딩
      if (decoded) {
        this.userInfo = decoded; // 유저 정보 저장
        // localStorage에 저장
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('userInfo', JSON.stringify(decoded));
      }
    },

    // localStorage에서 저장된 토큰과 유저 정보 복원
    loadFromLocalStorage() {
      const token = localStorage.getItem('jwtToken');
      const userInfo = localStorage.getItem('userInfo');

      if (token) {
        this.jwtToken = token;
      }
      if (userInfo) {
        this.userInfo = JSON.parse(userInfo);
      }
    },

    // 토큰과 유저 정보 초기화 및 localStorage에서 제거
    clearToken() {
      this.jwtToken = null;
      this.userInfo = {};
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('userInfo');
    },
  },
});
