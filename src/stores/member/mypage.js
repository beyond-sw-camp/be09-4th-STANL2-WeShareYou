// src/stores/mypage.js
import { defineStore } from 'pinia';
import axios from 'axios';

export const useMypageStore = defineStore('mypage', {
  state: () => ({
    jwtToken: localStorage.getItem('jwtToken') || null,
    userInfo: JSON.parse(localStorage.getItem('userInfo'))?.result || {}, // `result` 내 유저 정보 추출
  }),

  actions: {
    async fetchUserInfo() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/member/mypage', {
          headers: {
            Authorization: `Bearer ${this.jwtToken}`,
          },
        });
        const userData = response.data.result; // 유저 정보 저장 위치 확인
        this.userInfo = userData;
        localStorage.setItem('userInfo', JSON.stringify(response.data)); // 로컬 스토리지에 저장
      } catch (error) {
        console.error('Failed to fetch user info:', error);
      }
    },

    clearAuth() {
      this.jwtToken = null;
      this.userInfo = {};
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('userInfo');
    },
  },
});
