<template>
    <div class="profile-container">
        <div class="messageIcon">
                <img :src="messageIcon" alt="Message Icon" @click="handleIconClick"/>
            </div>
        <h1 class="profile-title">프로필</h1>        

        <div v-if="loading" class="loading">로딩 중...</div>

        <!-- 프로필 정보 출력 -->
        <div class="profile-header" v-else-if="userInfo">
            <img :src="userInfo.profileUrl || defaultProfileImage" alt="프로필 이미지" class="profile-img" />
            <div class="profile-intro">
                <p class="a">Nickname</p>
                <p class="nickname">{{ userInfo.nickname || 'Unknown' }}</p>
                <p class="a">한줄 소개</p>
                <p class="introduction">{{ userInfo.introduction || '한줄소개가 없습니다.' }}</p>
                <p class="a">사용 가능한 언어</p>
                <p class="language">{{ userInfo.language || '미설정' }}</p>
            </div>
        </div>

        <div v-else class="error-message">
            <p>{{ errorMessage }}</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

import messageIcon from '@/assets/icon/member/message.svg';

const route = useRoute();
const userInfo = ref(null); // 초기값을 null로 설정
const errorMessage = ref(null); // 에러 메시지 저장
const loading = ref(true); // 로딩 상태 관리
const defaultProfileImage = 'https://via.placeholder.com/150'; // 기본 프로필 이미지

// 서버에서 프로필 데이터 가져오기
async function fetchProfile(nickname) {
    loading.value = true;
    errorMessage.value = null;

    try {
        const response = await axios.get(
            `http://localhost:8080/api/v1/member/otherprofile?nickname=${nickname}`
        );
        console.log(response);

        // JSON 객체가 여러 개일 경우 첫 번째 객체만 추출
        const firstJson = response.data.match(/\{.*?\}(?=\{|\s*$)/s)[0];
        const parsedData = JSON.parse(firstJson);

        if (parsedData.success && parsedData.result) {
            userInfo.value = parsedData.result;
        } else {
            errorMessage.value = parsedData.error?.message || '프로필 정보를 찾을 수 없습니다.';
        }
        console.log(userInfo.value); // userInfo 값 확인

    } catch (error) {
        console.error('Failed to load profile data:', error);
        errorMessage.value = '프로필 정보를 불러오는 데 실패했습니다. 다시 시도해 주세요.';
    } finally {
        loading.value = false;
    }
}

// 닉네임이 변경될 때마다 프로필 정보 가져오기
watch(
    () => route.params.nickname,
    (newNickname) => {
        if (newNickname) fetchProfile(newNickname);
    }
);

// 컴포넌트가 마운트될 때 프로필 데이터 가져오기
onMounted(() => {
    fetchProfile(route.params.nickname);
});
</script>

<style scoped>
.messageIcon{
    display: flex;    
    justify-content: flex-end;
}
.profile-container {
    max-width: 60rem;
    margin: 4rem auto;
    padding: 2rem;
    background: #fff;
    border-radius: 1rem;
    box-shadow: 0 0.2rem 0.8rem rgba(0, 0, 0, 0.1);
}

.profile-title {
    text-align: center;
    font-size: 4rem;
    margin-bottom: 3rem;
    font-weight: bold;
}

.profile-header {
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin-bottom: 3rem;
}

.profile-img {
    width: 20rem;
    height: 20rem;
    border-radius: 50%;
    margin-right: 2rem;
}

.profile-intro {
    display: flex;
    flex-direction: column;
}

.a {
    font-size: 2.2rem;
    font-weight: bold;
}

.nickname,
.introduction,
.language {
    font-size: 1.3rem;
    margin-top: 0.5rem;
    padding: 1rem;
    border: 0.2rem solid #ddd;
    border-radius: 1rem;
    background-color: #f9f9f9;
}

.loading {
    text-align: center;
    font-size: 2rem;
    margin: 2rem 0;
}

.error-message {
    text-align: center;
    color: red;
    font-size: 1.5rem;
}
</style>
