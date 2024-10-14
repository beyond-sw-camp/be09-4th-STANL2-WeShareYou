import { defineStore } from 'pinia';
import axios from 'axios'; // 기본 axios 사용

export const useMemberStore = defineStore('member', {
    state: () => ({
        healthData: {},  // healthData 초기화
    }),
    actions: {
        async fetchHealthData() {
        const token = localStorage.getItem('token');
        if (!token) {
            console.error('JWT 토큰이 없습니다. 다시 로그인하세요.');
            router.push('/login'); // 로그인 페이지로 이동
            return;
        }
            try {
                const response = await axios.get('http://localhost:8080/api/v1/member/health', {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`, // JWT 토큰 설정
                        'Content-Type': 'application/json', // JSON 데이터 형식 설정
                    },
                });

                console.log('Health data:', response.data.result); // 서버 응답 데이터 확인
                this.healthData = response.data.result;  // 응답 데이터를 상태에 저장
            } catch (error) {
                console.error('Error fetching health data:', error); // 에러 로그 출력
                alert('유효하지 않은 세션입니다. 다시 로그인 해주세요.');
            }
        }
    }
});
