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
    jwtToken: null, // JWT 토큰 저장
    userInfo: {},   // JWT에서 추출한 유저 정보 저장
  }),
  actions: {
    setToken(token) {
      this.jwtToken = token;
      const decoded = parseJwt(token); // JWT 디코딩
      if (decoded) {
        this.userInfo = decoded; // 유저 정보 저장
      }
    },
    clearToken() {
      this.jwtToken = null;
      this.userInfo = {}; // 유저 정보 초기화
    },
  },
});
