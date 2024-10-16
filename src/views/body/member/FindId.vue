<template>
  <div class="find-id-container">
    <h1 class="page-title">아이디 찾기</h1>
    <label for="name">이름</label>
    <div class="form-group">
      <input v-model="name" id="name" type="text" placeholder="성함을 입력해 주세요." />
    </div>
    <label for="phone">연락처</label>
    <div class="row-btn">
      <div class="form-group">
        <input v-model="phone" id="phone" type="text" placeholder="연락처를 입력해 주세요." />
      </div>

      <div class="form-group with-button">
        <button class="small-button" @click="sendSms">인증번호 전송</button>
      </div>
    </div>

    <div class="row-btn">
      <div class="form-group">
        <input v-model="verificationCode" type="text" placeholder="인증번호를 입력해 주세요." />
      </div>
      <div class="form-group">
        <button class="small-button" @click="verifyCode">인증번호 확인</button>
        <p v-if="verificationStatus" :class="{ 'success-text': isVerified, 'error-text': !isVerified }">
          {{ verificationStatus }}
        </p>
      </div>
    </div>

    <button class="submit-button" :disabled="!isVerified" @click="findId">
      아이디 찾기
    </button>

    <p v-if="loginId" class="result-text">아이디: {{ loginId }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useFindIdStore } from '@/stores/member/findId'; // Store를 import

const findIdStore = useFindIdStore();
const name = ref('');
const phone = ref('');
const verificationCode = ref('');
const verificationStatus = ref('');
const loginId = ref('');
const isVerified = ref(false); // 인증 여부 플래그

async function sendSms() {
  try {
    await findIdStore.sendSms(name.value, phone.value); // Store에서 API 호출
    alert('인증번호가 전송되었습니다.');
  } catch (error) {
    console.error('인증번호 전송 실패:', error);
    alert('인증번호 전송에 실패했습니다.');
  }
}

async function verifyCode() {
  try {
    await findIdStore.verifyCode(phone.value, verificationCode.value);
    verificationStatus.value = '인증 확인되었습니다.';
    alert('인증 확인되었습니다.');
    isVerified.value = true; // 인증 완료
  } catch (error) {
    console.error('인증번호 확인 실패:', error);
    verificationStatus.value = '인증번호가 올바르지 않습니다.';
    alert('인증번호가 올바르지 않습니다.');
  }
}

async function findId() {
  try {
    const response = await findIdStore.findId();
    loginId.value = response.loginId;
  } catch (error) {
    console.error('아이디 찾기 실패:', error);
    alert('아이디를 찾는 중 오류가 발생했습니다.');
  }
}
</script>

<style scoped>
.find-id-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 30px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  font-size: 2.5rem;
}

.page-title {
  text-align: center;
  margin-bottom: 30px;
  font-size: 24px;
  color: #007bff;
}

.form-group {
  font-size:1.5rem;
  margin-bottom: 15px;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.form-group input {
  font-size:1.7rem;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.with-button {
  display: flex;
  gap: 10px;
}

.row-btn {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
}

.small-button {
  padding: 10px 15px;
  background-color: white;
  color: #007bff;
  border: 2px solid #007bff;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  width: 35.6%;
  width: 100%;
  margin-left: 1rem;
}

.small-button:hover {
  background-color: #007bff;
  color: white;
}

.submit-button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 1.6rem;
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
  margin-top: 5px;
}

.error-text {
  color: red;
  margin-top: 5px;
}

.result-text {
  margin-top: 20px;
  font-weight: bold;
  color: #333;
  text-align: center;
}
</style>