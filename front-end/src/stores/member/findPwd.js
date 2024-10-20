import { defineStore } from 'pinia';
import axios from 'axios';

export const useFindPwdStore = defineStore('findPwd', {
  actions: {
    async sendVerificationCode(email) {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/member/mail', { email });
        if (!response.data.success) {
          throw new Error(response.data.error || '인증번호 전송 실패');
        }
      } catch (error) {
        console.error('인증번호 전송 실패:', error);
        throw error;
      }
    },

    async verifyCode(email, code) {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/member/mail/check', { email, code });
        if (!response.data.success) {
          throw new Error(response.data.error || '인증번호 확인 실패');
        }
      } catch (error) {
        console.error('인증번호 확인 실패:', error);
        throw error;
      }
    },

    async resetPassword(loginId, password) {
      try {
        const response = await axios.put('http://localhost:8080/api/v1/member/password', { loginId, password });
        if (!response.data.success) {
          throw new Error(response.data.error || '비밀번호 재설정 실패');
        }
      } catch (error) {
        console.error('비밀번호 재설정 실패:', error);
        throw error;
      }
    },
  },
});
