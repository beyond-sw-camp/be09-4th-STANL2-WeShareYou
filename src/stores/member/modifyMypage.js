import { defineStore } from 'pinia';
import axios from 'axios';

export const useMypageStore = defineStore('mypage', {
    state: () => ({
        jwtToken: localStorage.getItem('jwtToken'), // JWT 토큰 로드
        userInfo: JSON.parse(localStorage.getItem('userInfo')) || {}, // 유저 정보 로드
}),

actions: {
    async fetchUserInfo() {
        try {
            const response = await axios.get('http://localhost:8080/api/v1/member/mypage', {
            headers: {
            Authorization: `Bearer ${this.jwtToken}`,
        },
            });
            this.userInfo = response.data.result;
            localStorage.setItem('userInfo', JSON.stringify(this.userInfo)); // 유저 정보 저장
        } catch (error) {
            console.error('유저 정보 불러오기 실패:', error);
            throw error;
        }
    },

    async updatePhone(newPhone) {
        try {
            const response = await axios.put(
            'http://localhost:8080/api/v1/member/mypage',
            { phone: newPhone },
            {
            headers: {
                Authorization: `Bearer ${this.jwtToken}`,
                },
            }
        );

        if (response.data.success) {
            this.userInfo.phone = newPhone;
            localStorage.setItem('userInfo', JSON.stringify(this.userInfo)); // 수정된 정보 저장
        } else {
            throw new Error('전화번호 수정 실패');
        }
        } catch (error) {
            console.error('전화번호 수정 실패:', error);
            throw error;
        }
    },
},
});
