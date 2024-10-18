<template>
    <div class="alarm-modal" v-if="isVisible">
        <div v-for="alarm in alarms" :key="alarm.id" class="alarm-item" :class="{ highlighted: !alarm.readStatus }">
            <img class="alarm-icon" src="../../assets/icon/navigation/profile.png" alt="profile" />
            <span>{{ alarm.message }}</span>
            <span class="alarm-time">{{ formatTimestamp(alarm.createdAt) }}</span>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    props: {
        isVisible: Boolean, // 모달 표시 여부
        memberId: Number, // 로그인된 회원 ID
    },
    data() {
        return {
            alarms: [], // 서버에서 받은 알림 데이터
        };
    },
    watch: {
        isVisible(newVal) {
            if (newVal) {
                this.fetchAlarms(); // 모달이 열릴 때 알림 조회
            }
        },
    },
    methods: {
        async fetchAlarms() {
            const jwtToken = localStorage.getItem('jwtToken'); // JWT 토큰 가져오기
            console.log("token!!!: " + jwtToken);

            try {
                const response = await axios.get(`http://localhost:8080/api/v1/alarm`, {
                    headers: {
                        Authorization: `Bearer ${jwtToken}`, // 헤더에 JWT 토큰 추가
                    },
                    params: {
                        page: 0, // cursorId가 없으면 빈 문자열로 전송
                        size: 5 // 페이지 당 가져올 아이템 수
                    },
                });
                console.log("api result: " + response.data.result.alarms);
                this.alarms = response.data.result.alarms; // 서버 응답 저장
            } catch (error) {
                console.error('Failed to fetch alarms:', error);
            }
        },
        formatTimestamp(timestamp) {
            const date = new Date(timestamp);
            return `${date.getFullYear()}-${(date.getMonth() + 1)
                .toString()
                .padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} 
        ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes()
                    .toString()
                    .padStart(2, '0')}`;
        },
    },
};
</script>

<style scoped>
.alarm-modal {
    position: absolute;
    top: 7rem;
    right: 0;
    width: 32rem;
    background-color: white;
    border: 0.1rem solid #e0e0e0;
    border-radius: 0.5rem;
    box-shadow: 0 0.2rem 1rem rgba(0, 0, 0, 0.1);
    z-index: 1000;
}

.alarm-item {
    display: flex;
    align-items: center;
    padding: 0.8rem;
}

.alarm-item.highlighted {
    background-color: #e0f7ff;
}

.alarm-icon {
    width: 2rem;
    height: 2rem;
    margin-right: 1rem;
    border-radius: 50%;
}

.alarm-time {
    margin-left: auto;
    font-size: 0.9rem;
    color: gray;
}
</style>