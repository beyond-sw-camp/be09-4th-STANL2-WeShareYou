<template>
    <div class="modify-profile-container">
        <h1 class="modify-title">프로필 수정</h1>

        <div class="modify-form">
            <!-- 프로필 이미지 업로드 -->
            <div class="form-group">
                <label for="profileImage">프로필 이미지</label>
                <input type="file" @change="handleFileChange" accept="image/*" />
            </div>

            <!-- 닉네임 입력 -->
            <div class="form-group">
                <label for="nickname">Nickname</label>
                <input v-model="nickname" placeholder="별명을 입력하세요" />
            </div>

            <!-- 한줄 소개 입력 -->
            <div class="form-group">
                <label for="introduction">한줄 소개</label>
                <input v-model="introduction" placeholder="한줄 소개를 입력하세요" />
            </div>

            <!-- 언어 선택 -->
            <div class="form-group">
                <label for="language">사용 가능한 언어</label>
                <input v-model="language" placeholder="언어를 입력하세요" />
            </div>

            <!-- 수정 버튼 -->
            <button class="submit-button" @click="submitProfile">수정 완료</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { modifyProfile } from '@/stores/member/modifyProfile';

// 사용자 입력값을 관리하는 ref 선언
const nickname = ref('');
const introduction = ref('');
const language = ref('');
const selectedFile = ref(null); // 선택된 파일

// 파일 변경 핸들러
function handleFileChange(event) {
    selectedFile.value = event.target.files[0];
}

// 프로필 수정 제출
async function submitProfile() {
    try {
        const updatedProfile = await modifyProfile(
            nickname.value,
            introduction.value,
            language.value,
            selectedFile.value
        );
        console.log('수정된 프로필:', updatedProfile);
        alert('프로필이 성공적으로 수정되었습니다!');
    } catch (error) {
        console.error('프로필 수정 중 오류:', error);
    }
}
</script>

<style scoped>
.modify-profile-container {
    max-width: 60rem;
    margin: 4rem auto;
    padding: 2rem;
    background: #fff;
    border-radius: 1rem;
    box-shadow: 0 0.2rem 0.8rem rgba(0, 0, 0, 0.1);
}

.modify-title {
    text-align: center;
    font-size: 3rem;
    margin-bottom: 2rem;
    font-weight: bold;
}

.form-group {
    margin-bottom: 1.5rem;
    display: flex;
    flex-direction: column;
}

.form-group label {
    font-size: 1.6rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
}

.form-group input {
    padding: 1rem;
    font-size: 1.6rem;
    border: 0.1rem solid #ddd;
    border-radius: 0.5rem;
    transition: border-color 0.3s;
}

.form-group input:focus {
    border-color: #007bff;
    outline: none;
}

.submit-button {
    width: 100%;
    padding: 1.5rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 0.5rem;
    font-size: 2rem;
    cursor: pointer;
    transition: background-color 0.3s;
}

.submit-button:hover {
    background-color: #0056b3;
}
</style>