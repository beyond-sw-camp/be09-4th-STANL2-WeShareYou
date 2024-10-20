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
    return JSON.parse(jsonPayload); 
  } catch (error) {
    console.error('Invalid JWT Token:', error);
    return null;
  }
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    jwtToken: null,
    userInfo: {},
    isLoggedIn: false,
  }),

  actions: {
    setToken(token) {
      this.jwtToken = token;
      const decoded = parseJwt(token);
      if (decoded) {
        this.userInfo = decoded;
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('userInfo', JSON.stringify(decoded));
        this.isLoggedIn = true;
      }
    },

    loadFromLocalStorage() {
      const token = localStorage.getItem('jwtToken');
      const userInfo = localStorage.getItem('userInfo');

      if (token) this.jwtToken = token;
      if (userInfo) this.userInfo = JSON.parse(userInfo);
      this.isLoggedIn = !!token && !!userInfo;
    },

    clearToken() {
      this.jwtToken = null;
      this.userInfo = {};
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('userInfo');
      this.isLoggedIn = false;
    },
  },
});
