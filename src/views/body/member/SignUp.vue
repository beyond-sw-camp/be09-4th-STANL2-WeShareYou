<template>
    <div class="signup-page">
        <div class="signup-container">
            <div class="signup-box">
                <h1 class="signup-title">회원가입</h1>
                <p class="signup-subtitle">본인 정보를 입력하여 회원가입을 진행해 주세요!</p>
                <form @submit.prevent="validateAndRegister">
                    <div class="input-group">
                        <label for="name">
                            이름 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="name" id="name" placeholder="이름을 입력해 주세요." required />
                    </div>

                    <div class="input-group">
                        <label for="nickname">
                            별명 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="nickname" id="nickname" placeholder="별명을 입력해 주세요." required />
                    </div>

                    <label for="loginId">
                        아이디 (이메일) <span class="required-text">*필수</span>
                    </label>
                    <div class="input-group with-button">
                        <input v-model="loginId" id="loginId" type="email" placeholder="이메일 형식의 아이디를 입력해 주세요."
                            required />
                        <button type="button" class="small-button" @click="sendVerificationCode">인증번호 전송</button>
                    </div>

                    <div class="input-group with-button">
                        <input v-model="verificationCode" placeholder="인증번호를 입력해 주세요." />
                        <button type="button" class="small-button" @click="verifyCode">인증번호 확인</button>
                    </div>

                    <div class="input-group">
                        <label for="password">
                            비밀번호 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="password" id="password" type="password" placeholder="비밀번호를 입력해 주세요."
                            @input="validatePasswordMatch" required />
                    </div>

                    <div class="input-group">
                        <label for="confirmPassword">
                            비밀번호 확인 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="confirmPassword" id="confirmPassword" type="password"
                            placeholder="비밀번호를 한번 더 입력해 주세요." @input="validatePasswordMatch" required />
                        <span :class="passwordMatchMessageClass">{{ passwordMatchMessage }}</span>
                    </div>

                    <div class="input-group">
                        <label for="phone">
                            연락처 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="phone" id="phone" placeholder="연락처를 입력해 주세요." required />
                    </div>

                    <div class="input-row">
                        <div class="input-group">
                            <div class="input-group row-group">
                                <!-- 성별 -->
                                <div class="gender-container">
                                    <label for="sex">
                                        성별 <span class="required-text">*필수</span>
                                    </label>
                                    <div class="radio-group">
                                        <label class="radio-option">
                                            <input type="radio" v-model="sex" value="MALE" /> 남성
                                        </label>
                                        <label class="radio-option">
                                            <input type="radio" v-model="sex" value="FEMALE" /> 여성
                                        </label>
                                    </div>
                                </div>

                                <!-- 나이 -->
                                <div class="age-container">
                                    <label for="age" class="age-label">
                                        나이 <span class="required-text">*필수</span>
                                    </label>
                                    <div class="select-container">
                                        <select v-model="age" id="age" required>
                                            <option value="" disabled selected>나이를 선택하세요</option>
                                            <option v-for="n in 100" :key="n" :value="n">{{ n }}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="input-group">
                        <label for="nationality">
                            국적 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="nationality" id="nationality" placeholder="국적을 입력해 주세요." required />
                    </div>

                    <div class="input-group">
                        <label for="language">
                            언어 <span class="required-text">*필수</span>
                        </label>
                        <input v-model="language" id="language" placeholder="사용 언어를 입력해 주세요." required />
                    </div>
                    <button type="submit" class="submit-button">회원가입</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const loginId = ref('');
const password = ref('');
const confirmPassword = ref('');
const name = ref('');
const age = ref(20);
const nationality = ref('');
const sex = ref('MALE');
const phone = ref('');
const nickname = ref('');
const language = ref('');
const verificationCode = ref('');
const isEmailVerified = ref(false);

const passwordMatchMessage = ref('');
const passwordMatchMessageClass = ref('');

const isFormValid = computed(() => {
    return (
        loginId.value &&
        password.value &&
        confirmPassword.value &&
        name.value &&
        age.value &&
        nationality.value &&
        sex.value &&
        phone.value &&
        nickname.value &&
        language.value &&
        password.value === confirmPassword.value
    );
});

const validatePasswordMatch = () => {
    if (password.value === confirmPassword.value) {
        passwordMatchMessage.value = '비밀번호가 일치합니다.';
        passwordMatchMessageClass.value = 'success-message';
    } else {
        passwordMatchMessage.value = '비밀번호가 일치하지 않습니다.';
        passwordMatchMessageClass.value = 'error-message';
    }
};

const sendVerificationCode = async () => {
    try {
        const response = await axios.post('http://localhost:8080/api/v1/member/mail', {
            email: loginId.value,
        });
        if (response.status === 200) {
            alert('인증번호가 전송되었습니다.');
        } else {
            throw new Error('인증번호 전송에 실패했습니다.');
        }
    } catch (error) {
        console.error('인증번호 전송 실패:', error);
        alert('인증번호 전송 중 문제가 발생했습니다.');
    }
};

const verifyCode = async () => {
    try {
        const response = await axios.post('http://localhost:8080/api/v1/member/mail/check', {
            email: loginId.value,
            code: verificationCode.value,
        });
        if (response.data.success) {
            isEmailVerified.value = true;
            alert('인증번호가 확인되었습니다.');
        } else {
            throw new Error('인증번호가 올바르지 않습니다.');
        }
    } catch (error) {
        console.error('인증번호 확인 실패:', error);
        alert('인증번호 확인 중 문제가 발생했습니다.');
    }
};

const validateAndRegister = () => {
    if (!isFormValid.value) {
        alert('모든 필드를 올바르게 입력해 주세요.');
        return;
    }
    if (!isEmailVerified.value) {
        alert('이메일 인증을 완료해 주세요.');
        return;
    }
    register();
};

const register = async () => {
    try {
        const response = await axios.post('http://localhost:8080/api/v1/member/register', {
            loginId: loginId.value,
            password: password.value,
            name: name.value,
            age: age.value,
            nationality: nationality.value,
            sex: sex.value,
            phone: phone.value,
            nickname: nickname.value,
            language: language.value,
        });

        if (response.data.success) {
            alert('회원가입이 완료되었습니다!');
            router.push('/login');
        } else {
            throw new Error(response.data.error);
        }
    } catch (error) {
        console.error('회원가입 실패:', error);
        alert('회원가입 중 문제가 발생했습니다.');
    }
};
</script>

<style scoped>
.required-text {
    color: #dc3545;
    font-size: 0.75rem;
    margin-left: 0.25rem;
}

.success-message {
    color: #28a745;
    font-size: 0.75rem;
    margin-top: 0.5rem;
    display: block;
    font-size: 0.7rem;
}

.error-message {
    color: #dc3545;
    font-size: 0.75rem;
    margin-top: 0.5rem;
    display: block;
    font-size: 0.7rem;
}

.signup-page {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    background-color: #f5f6f8;
    font-size: 5rem;
}

.signup-container {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 2rem;
}

.signup-box {
    width: 100%;
    max-width: 40rem;
    padding: 2rem;
    background-color: white;
    border-radius: 1rem;
    box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
}

.signup-title {
    margin-bottom: 1rem;
    font-size: 1.5rem;
    color: #007bff;
    text-align: center;
}

.signup-subtitle {
    margin-bottom: 2rem;
    font-size: 1rem;
    text-align: center;
    color: #6c757d;
}

.input-group {
    margin-bottom: 1rem;
    display: flex;
    flex-direction: column;
    font-size: 1.5rem;
}

.with-button {
    display: flex;
    align-items: center;
    flex-direction: row;
    gap: 0.5rem;
    width: 100%;
    min-width: 0;
    font-size: 1rem;
}

.with-button input {
    flex: 1;
    min-width: 0;
}

/* 성별과 나이 컨테이너 가로 정렬 */
.row-group {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 1rem;
    width: 100%;
}
.gender-container,
.age-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    font-size: 1.5rem;
}

/* 성별 섹션 */
.gender-container {
    display: flex;
    flex-direction: column;
}

.radio-group {
    display: flex;
    gap: 1rem;
    margin-top: 0.5rem;
}

.radio-option {
    display: flex;
    align-items: center;
    gap: 1rem;
    font-size: 0.75rem;
}

.radio-option input {
    width: 1rem;
    height: 1rem;
    margin: 0;
    cursor: pointer;
}

/* 나이 섹션 */
.age-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.select-container select {
    padding: 0.5rem;
    border: none;
    border-bottom: 0.1rem solid #ccc;
    width: 8rem;
    outline: none;
    transition: border-color 0.3s;
}

.select-container select:focus {
    border-bottom: 0.1rem solid #007bff;
}

/* 라벨 스타일 */
.required-text {
    color: #dc3545;
    font-size: 0.75rem;
    margin-left: 0.25rem;
}

.age-label {
    margin-bottom: 0.5rem;
}

input,
select {
    padding: 0.625rem;
    border: none;
    border-bottom: 0.1rem solid #ccc;
    width: 100%;
    box-sizing: border-box;
    outline: none;
    transition: border-color 0.3s;
}

input:focus,
select:focus {
    border-bottom: 1px solid #007bff;
}

.small-button {
    flex-shrink: 0;
    padding: 0.5rem 1rem;
    background-color: white;
    color: #007bff;
    border: 0.2rem solid #007bff;
    border-radius: 0.4rem;
    cursor: pointer;
    white-space: nowrap;
    transition: background-color 0.3s, color 0.3s;
}

.small-button:hover {
    background-color: #007bff;
    color: white;
}

.submit-button {
    width: 100%;
    padding: 0.75rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 0.4rem;
    cursor: pointer;
}

.submit-button:hover {
    background-color: #0056b3;
}
</style>
