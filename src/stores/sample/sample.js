import { defineStore } from 'pinia';
import axios from 'axios'; // 기본 axios 사용

// Axios 인스턴스 생성
const apiClient = axios.create({
  baseURL: 'http://localhost:8080',  // 기본 URL 설정
  headers: {
    'Content-Type': 'application/json',  // 기본 Content-Type 설정
  },
});

// 요청마다 JWT 토큰을 자동으로 설정하도록 인터셉터 추가
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log('토큰이 설정되었습니다:', token);
    } else {
      console.error('JWT 토큰이 없습니다. 다시 로그인하세요.');
      router.push('/login'); // 로그인 페이지로 이동
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const useMemberStore = defineStore('member', {
  state: () => ({
    healthData: {}, // healthData 초기화
  }),
  actions: {
    async fetchHealthData() {
      try {
        const response = await apiClient.get('/api/v1/member/health');
        console.log('Health data:', response.data.result); // 서버 응답 데이터 확인
        this.healthData = response.data.result; // 응답 데이터를 상태에 저장
      } catch (error) {
        console.error('Error fetching health data:', error); // 에러 로그 출력
        alert('유효하지 않은 세션입니다. 다시 로그인 해주세요.');
      }
    },
  },
});
