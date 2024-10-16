<template>
    <div class="find-pwd-container">
        <h1 class="page-title">비밀번호 찾기</h1>

        <label for="loginId" class="loginId">아이디 (이메일)</label>
        <div class="form-group with-button">
            <input v-model="loginId" id="loginId" type="email" placeholder="아이디를 입력해 주세요." />
            <button class="small-button" @click="sendVerificationCode">인증번호 전송</button>
        </div>

        <div class="form-group with-button">
            <input v-model="verificationCode" placeholder="인증번호를 입력해 주세요." />
            <button class="small-button" @click="verifyCode">인증번호 확인</button>
        </div>

        <p v-if="verificationStatus" :class="{ 'success-text': isVerified, 'error-text': !isVerified }">
            {{ verificationStatus }}
        </p>

        <div class="form-group">
            <label for="newPassword" class="pwd">새 비밀번호</label>
            <input v-model="newPassword" id="newPassword" type="password" placeholder="새 비밀번호를 입력해 주세요." />
        </div>

        <div class="form-group">
            <label for="confirmPassword" class="pwd">비밀번호 확인</label>
            <input v-model="confirmPassword" id="confirmPassword" type="password" placeholder="비밀번호를 한번 더 입력해 주세요."
                @input="validatePasswordMatch" />
            <p :class="passwordMatchMessageClass">{{ passwordMatchMessage }}</p>
        </div>

        <button class="submit-button" :disabled="!isVerified || !passwordsMatch" @click="resetPassword">
            비밀번호 재설정
        </button>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useFindPwdStore } from '@/stores/member/findPwd'; // Store import

const findPwdStore = useFindPwdStore();
const loginId = ref('');
const verificationCode = ref('');
const verificationStatus = ref('');
const isVerified = ref(false);
const newPassword = ref('');
const confirmPassword = ref('');
const passwordMatchMessage = ref('');
const passwordMatchMessageClass = ref('');
const passwordsMatch = ref(false);

async function sendVerificationCode() {
    try {
        await findPwdStore.sendVerificationCode(loginId.value);
        alert('인증번호가 전송되었습니다.');
    } catch (error) {
        console.error('인증번호 전송 실패:', error);
        alert('인증번호 전송에 실패했습니다.');
    }
}

async function verifyCode() {
    try {
        await findPwdStore.verifyCode(loginId.value, verificationCode.value);
        verificationStatus.value = '인증 확인되었습니다.';
        isVerified.value = true;
    } catch (error) {
        console.error('인증번호 확인 실패:', error);
        verificationStatus.value = '인증번호가 올바르지 않습니다.';
        isVerified.value = false;
    }
}

function validatePasswordMatch() {
    if (newPassword.value === confirmPassword.value) {
        passwordMatchMessage.value = '비밀번호가 일치합니다.';
        passwordMatchMessageClass.value = 'success-text';
        passwordsMatch.value = true;
    } else {
        passwordMatchMessage.value = '비밀번호가 일치하지 않습니다.';
        passwordMatchMessageClass.value = 'error-text';
        passwordsMatch.value = false;
    }
}

async function resetPassword() {
    if (!passwordsMatch.value) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    }

    try {
        await findPwdStore.resetPassword(loginId.value, newPassword.value);
        alert('비밀번호가 성공적으로 재설정되었습니다.');
    } catch (error) {
        console.error('비밀번호 재설정 실패:', error);
        alert('비밀번호 재설정 중 문제가 발생했습니다.');
    }
}
</script>

<style scoped>
.loginId, .pwd{
    font-size: 1.6rem;
}
.find-pwd-container {
    max-width: 40rem;
    margin: 10rem auto;
    padding: 3rem;
    background-color: white;
    border-radius: 1rem;
    box-shadow: 0 0.4rem 0.8rem rgba(0, 0, 0, 0.1);
    font-size: 1.6rem;
}

.page-title {
    text-align: center;
    margin-bottom: 3rem;
    font-size: 2.4rem;
    color: #007bff;
}

.form-group {
    margin-bottom: 1.5rem;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    font-size: 1.6rem;
}

.form-group input {
    padding: 1rem;
    border: 0.1rem solid #ccc;
    border-radius: 0.5rem;
    font-size: 1.4rem;
    width: 100%;
}

.with-button {
    display: flex;
    flex-direction: row;
    gap: 1rem;
}

.small-button {
    padding: 1rem 1.5rem;
    background-color: white;
    color: #007bff;
    border: 0.2rem solid #007bff;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
    font-size: 1.2rem;
    width: 39.5%;
}

.small-button:hover {
    background-color: #007bff;
    color: white;
}

.submit-button {
    width: 100%;
    padding: 1.2rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: background-color 0.3s;
    font-size: 1.8rem;
}

.submit-button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.submit-button:hover:enabled {
    background-color: #0056b3;
}

.success-text {
    color: green;
    margin-top: 0.5rem;
    font-size: 1.4rem;
}

.error-text {
    color: red;
    margin-top: 0.5rem;
    font-size: 1.4rem;
}
</style>