<template>
    <div class="profile-container">
        <h1 class="profile-title">프로필 수정</h1>

        <div class="profile-header">

            <!-- 이미지 클릭 시 파일 선택 이벤트 연결 -->
            <img :src="previewImage || profileUrl" alt="프로필 이미지" class="profile-img" @click="triggerFileInput" />
            <!-- 숨겨진 파일 입력 필드 -->
            <input type="file" ref="fileInput" @change="handleFileChange" accept="image/*" style="display: none;" />

            <div class="profile-intro">
                <div class="form-group">
                    <label class="a">Nickname</label>
                    <input v-model="nickname" placeholder="별명을 입력하세요" />
                </div>

                <div class="form-group">
                    <label class="a">한줄 소개</label>
                    <input v-model="introduction" placeholder="한줄 소개를 입력하세요" />
                </div>

                <div class="form-group">
                    <label class="a">사용 가능한 언어</label>
                    <input v-model="language" placeholder="언어를 입력하세요" />
                </div>
            </div>
        </div>

        <div class="m-btn">
            <button class="modify-button" @click="submitProfile">수정 완료</button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { modifyProfile } from '@/stores/member/modifyProfile';
import { fetchProfileData } from '@/stores/member/profile';

// 초기 값 선언
const router = useRouter();
const nickname = ref('');
const introduction = ref('');
const language = ref('');
const profileUrl = ref('');
const selectedFile = ref(null);
const previewImage = ref(null); // 미리보기 이미지 URL
const fileInput = ref(null);

onMounted(async () => {
    try {
        const profile = await fetchProfileData();
        // 프로필 데이터를 UI에 반영
        nickname.value = profile.nickname;
        introduction.value = profile.introduction;
        language.value = profile.language;
        profileUrl.value = profile.profileUrl;
    } catch (error) {
        console.error('프로필 정보를 불러오지 못했습니다:', error);
        alert('프로필 정보를 불러오는데 실패했습니다. 다시 시도해 주세요.');
    }
});

// 파일 변경 핸들러 (미리보기 이미지 설정)
function handleFileChange(event) {
    selectedFile.value = event.target.files[0];
    previewImage.value = URL.createObjectURL(selectedFile.value);
}

// 이미지 클릭 시 파일 입력 요소 클릭 트리거
function triggerFileInput() {
    fileInput.value.click();
}

// 프로필 수정 제출 핸들러
async function submitProfile() {
    try {
        await modifyProfile(
            nickname.value,
            introduction.value,
            language.value,
            selectedFile.value
        );
    } catch (error) {
        console.error('프로필 수정 실패:', error);
    }
    router.push('/profile');
}
</script>

<style scoped>
.profile-container {
    max-width: 60rem;
    margin: 4rem auto;
    padding: 2rem;
    background: #fff;
    border-radius: 1rem;
    box-shadow: 0 0.2rem 0.8rem rgba(0, 0, 0,
            0.1);
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
    object-fit: cover;
}

.profile-intro {
    display: flex;
    flex-direction: column;
}

.form-group {
    margin-bottom: 1.5rem;
}

.a {
    font-size: 1.8rem;
    font-weight: bold;
    padding-right: 1rem;
}

input {
    display: flex;
    padding: 1rem;
    font-size: 1.6rem;
    border: 0.1rem solid #ddd;
    border-radius: 0.5rem;
    transition: border-color 0.3s;
}

input:focus {
    border-color: #007bff;
    outline: none;
}

.m-btn {
    display: flex;
    justify-content: flex-end;
}

.modify-button {
    width: 25%;
    padding: 1.5rem 3rem;
    background-color: white;
    color: #007bff;
    border: 0.2rem solid #007bff;
    border-radius: 0.5rem;
    font-size: 1.7rem;
    cursor: pointer;
    transition: background-color 0.3s;
}

.modify-button:hover {
    background-color: #0056b3;
}
</style>