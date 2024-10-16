<template>
    <div class="mypage-container">
        <h1 class="page-title">마이페이지</h1>
        <div class="profile-box" v-if="userInfo">
            <div class="profile-item">
                <label>이름</label>
                <p>{{ userInfo.name }}</p>
            </div>

            <div class="profile-item">
                <label>아이디</label>
                <p>{{ userInfo.loginId }}</p>
            </div>

            <div class="form-group">
                <label for="phone">연락처</label>
                <input v-model="phone" id="phone" type="text" placeholder="새로운 연락처를 입력해 주세요." />
            </div>

            <div class="profile-item row-group">
                <div class="gender-container">
                    <label>성별</label>
                    <p>{{ userInfo.sex === 'FEMALE' ? '여성' : '남성' }}</p>
                </div>
                <div class="age-container">
                    <label>나이</label>
                    <p>{{ userInfo.age }}</p>
                </div>
            </div>

            <div class="profile-item">
                <label>국적</label>
                <p>{{ userInfo.nationality }}</p>
            </div>

            <div class="profile-item">
                <label>포인트</label>
                <p>{{ userInfo.point }}</p>
            </div>

            <div class="modify-button">
                <button class="submit-button" @click="submitForm">수정 완료</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useMypageStore } from '@/stores/member/modifyMypage';

const router = useRouter();
const mypageStore = useMypageStore();
const phone = ref(''); // 초기값 빈 문자열로 설정
const userInfo = computed(() => mypageStore.userInfo); // store의 userInfo 반영

onMounted(async () => {
    try {
        if (!mypageStore.jwtToken) {
            router.push('/login'); // JWT 없으면 로그인 페이지로 리다이렉트
        } else {
            await mypageStore.fetchUserInfo(); // 유저 정보 로드
            phone.value = userInfo.value.phone; // 초기 전화번호 설정
        }
    } catch (error) {
        console.error('페이지 로드 중 오류 발생:', error);
    }
});

async function submitForm() {
    try {
        await mypageStore.updatePhone(phone.value); // API 호출하여 전화번호 수정
        alert('연락처가 성공적으로 수정되었습니다.');
        router.push('/mypage'); // 수정 후 마이페이지로 이동
    } catch (error) {
        console.error('전화번호 수정 실패:', error);
        alert('수정 중 문제가 발생했습니다.');
    }
}
</script>

<style scoped>
.mypage-container {
    max-width: 600px;
    margin: 50px auto;
    padding: 30px;
    background-color: #fff;
    border-radius: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.page-title {
    text-align: center;
    margin-bottom: 30px;
    font-size: 28px;
    font-weight: bold;
    color: #007bff;
}

.profile-box {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.profile-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #e6e6e6;
    padding: 12px 0;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 5px;
}

.form-group input {
    padding: 10px;
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    transition: border-color 0.3s;
}

.form-group input:focus {
    border-color: #007bff;
    outline: none;
}

.row-group {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    width: 100%;
}

.gender-container,
.age-container {
    flex: 1;
}

.modify-button {
    display: flex;
    justify-content: flex-end;
}

.submit-button {
    padding: 10px 15px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-button:hover {
    background-color: #0056b3;
}
</style>
