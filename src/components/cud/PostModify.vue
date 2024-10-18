<template>
  <div class="container">
    <div class="title-container">
      <input
        type="text"
        placeholder="제목을 작성해주세요."
        v-model="localTitle"
        class="title-input"
        @input="emitTitleUpdate"
      />
      <button class="image-upload-btn" @click="triggerFileInput">
        <img src="@/assets/icon/Img_box_light.png" alt="이미지 업로드" />
      </button>
    </div>

    <!-- 이미지 미리보기 -->
    <div v-if="previewImageUrl" class="image-preview">
      <img :src="previewImageUrl" alt="이미지 미리보기" />
    </div>

    <div
      class="content-area"
      contenteditable="true"
      ref="contentArea"
      @input="updateContent"
      placeholder="내용을 입력하세요."
    >
      {{ localContent }}
    </div>

    <input
      type="file"
      ref="fileInput"
      @change="handleImageUpload"
      accept="image/*"
      style="display: none;"
    />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';

// 지역 상태 정의
const localTitle = ref('');
const localContent = ref('');
const previewImageUrl = ref(''); // 이미지 미리보기 URL

// props로 받아온 초기값을 설정합니다.
const props = defineProps({
  initialTitle: String,
  initialContent: String,
  initialImageUrl: String
});

// 초기값으로 설정
watch(() => props.initialTitle, (newTitle) => {
  localTitle.value = newTitle;
});

watch(() => props.initialContent, (newContent) => {
  localContent.value = newContent;
});

// 이미지 URL을 미리보기로 설정
watch(() => props.initialImageUrl, (newImageUrl) => {
  previewImageUrl.value = newImageUrl;
});

// 부모에게 이벤트를 발생시키기 위해 정의
const emit = defineEmits(['updateTitle', 'updateContent', 'imageUploaded']);

// 제목 변경 시 부모에게 알림
const emitTitleUpdate = () => {
  console.log("localContent: " +localTitle.value);
  emit('updateTitle', localTitle.value);
};

// 내용 변경 시 부모에게 알림
const updateContent = () => {
  localContent.value = contentArea.value.innerHTML; // contenteditable의 내용을 가져옴
  console.log("localContent: " + localContent.value);
  emit('updateContent', localContent.value);
};

// 파일 선택창 열기
const fileInput = ref(null);
const triggerFileInput = () => {
  fileInput.value.click();
};

// 이미지 업로드 처리
const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    previewImageUrl.value = URL.createObjectURL(file); // 미리보기 이미지 변경
    
    console.log("아아아: " +file);
    emit('imageUploaded', file); // 부모에게 파일 업로드 알림
  }
};

// 내용 입력 참조
const contentArea = ref(null);
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  border: 1px solid #e0e0e0;
  border-radius: 1rem;
  padding: 5rem;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  width: 100%;
  height: 66vh;
  margin: 2rem auto;
  overflow-y: auto;
}

/* 제목과 이미지 업로드 버튼을 가로로 배치 */
.title-container {
  display: flex;
  align-items: center;
  width: 100%;
  margin-bottom: 2rem;
}

.title-input {
  flex: 1;
  /* 입력 필드가 가능한 공간을 차지하도록 */
  border: none;
  border-bottom: 1px solid #ddd;
  font-size: 2.8rem;
  padding: 0.5rem 0;
  outline: none;
  margin-right: 1rem;
  /* 이미지 버튼과의 간격 */
}

.image-upload-btn {
  background: none;
  border: none;
  cursor: pointer;
}

.image-upload-btn img {
  width: 32px;
  height: 32px;
}

.content-area {
  width: 100%;
  min-height: 300px;
  font-size: 1.6rem;
  padding: 1rem;
  overflow-y: auto;
  margin: 3rem 0 2rem 0;
  white-space: pre-wrap;
  outline: none;
  /* 포커스 시 기본 outline 제거 */
}

@media (max-width: 768px) {
  .container {
    padding: 3rem;
  }

  .title-input {
    font-size: 2.4rem;
  }

  .content-area {
    font-size: 1.8rem;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 2rem;
  }

  .title-input {
    font-size: 2rem;
  }

  .content-area {
    font-size: 1.6rem;
    height: 200px;
  }

  .image-upload-btn img {
    width: 24px;
    height: 24px;
  }
}
</style>