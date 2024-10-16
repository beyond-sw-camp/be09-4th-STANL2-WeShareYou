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
            <div class="profile-item">
                <label>연락처</label>
                <p>{{ userInfo.phone }}</p>
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
                <button class="edit-button" @click="goToEdit">수정하러 가기</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useMypageStore } from '@/stores/member/mypage';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

    const mypageStore = useMypageStore();
    const router = useRouter();
    const userInfo = ref(mypageStore.userInfo);

    onMounted(async () => {
    if (!mypageStore.jwtToken) {
        router.push('/login');
    } else {
        await mypageStore.fetchUserInfo();
        userInfo.value = mypageStore.userInfo;
    }
});

    function goToEdit() {
        router.push('/modifyMypage');
    }
</script>

<style scoped>
.mypage-container {
    max-width: 60rem;
    margin: 5rem auto;
    padding: 3rem;
    background-color: #fff;
    border-radius: 1.5rem;
    box-shadow: 0 0.4rem 0.8rem rgba(0, 0, 0, 0.1);
}

.page-title {
    text-align: center;
    margin-bottom: 3rem;
    font-size: 2.8rem;
    font-weight: bold;
    color: #007bff;
}

.profile-box {
    display: flex;
    flex-direction: column;
    gap: 2rem;
}

.profile-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 0.1rem solid #e6e6e6;
    padding: 1.2rem 0;
}

.row-group {
    display: flex;
    justify-content: space-between;
    width: 100%;
    gap: 1rem;
}

.gender-container,
.age-container {
    flex: 1;
}

.edit-button {
    margin-top: 3rem;
    padding: 1.5rem;
    background-color: white;
    color: #007bff;
    border: 0.2rem solid #007bff;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    transition: background-color 0.3s, color 0.3s;
    width: 25%;
    font-size: 1.6rem;
}
.modify-button{
    display: flex;
    flex-direction: row-reverse;
    align-items: flex-start; 
}

.edit-button:hover {
    background-color: #007bff;
    color: white;
}

label {
    font-weight: bold;
    font-size: 1.6rem;
    color: #333;
}

p {
    font-size: 1.6rem;
    color: #555;
    margin: 0;
}
</style>
