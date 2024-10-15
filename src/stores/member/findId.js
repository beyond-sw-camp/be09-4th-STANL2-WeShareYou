// src/stores/findId.js
import { defineStore } from 'pinia';
import axios from 'axios';

export const useFindIdStore = defineStore('findId', {
  state: () => ({
    jwtToken: localStorage.getItem('jwtToken'), // JWT 토큰 불러오기
    isVerifying: false, // 중복 방지 플래그
  }),

  actions: {
    async sendSms(name, phone) {
      try {
        await axios.post('http://localhost:8080/api/v1/member/sms', { name, phone });
      } catch (error) {
        console.error('인증번호 전송 실패:', error);
        throw error;
      }
    },

    async verifyCode(phone, code) {
        if (this.isVerifying) return; // 중복 호출 방지
        this.isVerifying = true;

      try {
        const response = await axios.post('http://localhost:8080/api/v1/member/sms/check', {
          phone,
          code,
        });

        console.log('응답:', response.data); // 응답 데이터 확인
        console.log('==', phone);
        console.log('==', code);

        if (!response.data.success) {
          throw new Error('인증 실패');
        }
      } catch (error) {
        console.error('인증 실패:', error);
        throw error;
      }finally {
        this.isVerifying = false; // 플래그 초기화
      }
    },

    async findId() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/member', {
          headers: {
            Authorization: `Bearer ${this.jwtToken}`,
          },
        });
        return response.data.result;
      } catch (error) {
        console.error('아이디 찾기 실패:', error);
        throw error;
      }
    },
  },
});
