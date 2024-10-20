<template>
  <div class="card">
    <h2>게시글 정보</h2>
    <!-- Image Upload Section -->
    <div class="image-container">
      <div v-for="(image, index) in images" :key="index" class="image-wrapper">
        <img :src="image.preview" alt="Uploaded image" @click="() => triggerFileInput(index)" />
        <button class="delete-button" @click.stop="deleteImage(index)">
          <img src="@/assets/icon/boardIcons/delete.svg" alt="Delete" />
        </button>
      </div>
      <div v-if="images.length < 3" class="add-image" @click="() => triggerFileInput(images.length)">+</div>
      <input type="file" ref="fileInput" @change="handleImageUpload" style="display: none" accept="image/*" />
    </div>

    <div class="form-group tag-group">
      <label for="tag">태그</label>
      <select id="tag" v-model="selectedTag">
        <option v-for="(tag, index) in tags" :key="index" :value="tag">{{ tag }}</option>
      </select>
    </div>

    <div class="form-group">
      <label for="title">제목</label>
      <input id="title" v-model="title" type="text" placeholder="제목을 입력하세요" />
    </div>

    <div class="form-group content-group">
      <label for="content">내용</label>
      <textarea id="content" v-model="content" placeholder="내용을 입력하세요"></textarea>
    </div>

    <div class="button-container">
      <button class="btn btn-primary" @click="submitForm">수정</button>
      <button class="btn btn-secondary" @click="goToMain">취소</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const boardId = route.params.id;

// Form state
const images = ref([]);
const title = ref('');
const content = ref('');
const selectedTag = ref('GUIDE');
const token = localStorage.getItem('jwtToken');
const userInfo = localStorage.getItem('userInfo');
const deletedFileIds = ref([]);

// Tag options
const tags = ref(['GUIDE', 'FREEMARKET', 'ACCOMPANY', 'TIP']);

const fileInput = ref(null);
const currentImageIndex = ref(null);

const triggerFileInput = (index) => {
  currentImageIndex.value = index;
  fileInput.value.click();
};

const handleImageUpload = (event) => {
  const files = event.target.files;
  if (files.length > 0) {
    const file = files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      const imageObject = { file, preview: e.target.result };

      if (currentImageIndex.value !== null && currentImageIndex.value < images.value.length) {
        images.value.splice(currentImageIndex.value, 1, imageObject);
      } else if (images.value.length < 3) {
        images.value.push(imageObject);
      }
    };
    reader.readAsDataURL(file);
  }
  event.target.value = ''; // Reset input to allow re-upload of the same file
};

const deleteImage = (index) => {
  const image = images.value[index];
  if (image.id) {
    deletedFileIds.value.push(image.id); // 삭제할 이미지 ID 저장
    console.log(deletedFileIds);
  }
  images.value.splice(index, 1); // UI에서 이미지 삭제
};

const fetchPost = async (postId) => {
  try {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      console.error('No token found');
      return;
    }

    const response = await axios.get(`http://localhost:8080/api/v1/board/detail/${postId}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });

    console.log('Full response:', response);

    if (response.data.success) {
      const post = response.data.result;
      title.value = post.title;
      content.value = post.content;

      // 이미지 처리
      images.value = post.imageObj?.map(img => ({ 
        id: img.id,
        preview: img.imageUrl
      })) || [];

    } else {
      console.error('API responded with an error:', response.data.error);
    }
  } catch (error) {
    console.error('Error fetching post:', error);
    if (error.response) {
      console.error('Server responded with:', error.response.data);
      console.error('Status code:', error.response.status);
    } else if (error.request) {
      console.error('No response received:', error.request);
    } else {
      console.error('Error setting up request:', error.message);
    }
  }
};

const submitForm = async () => {
  try {
    const formData = new FormData();
    const payload = JSON.parse(userInfo);
    const memberId = payload?.id;

    images.value.forEach((image) => formData.append('newFiles', image.file));

    formData.append('deletedFileIds', new Blob([JSON.stringify(deletedFileIds.value)], { type : 'application/json'}));

    const boardUpdateRequestVO = {
      id: route.params.id,
      title: title.value,
      content: content.value,
      tag: selectedTag.value,
      memberId: memberId,
    };

    formData.append('vo', new Blob([JSON.stringify(boardUpdateRequestVO)], { type: 'application/json' }));


    const response = await axios.put(`http://localhost:8080/api/v1/board`, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'multipart/form-data',
      },
    });

    console.log('Form submitted', response.data);

    if (response.data.success) {
      goToMain();
    } else {
      console.error('Error updating board:', response.data.error);
    }
  } catch (error) {
    console.error('Error submitting form', error);
  }
};

const goToMain = () => {
  router.push('/board/GUIDE');
};

onMounted(() => {
  console.log(boardId);
  fetchPost(boardId);
});

</script>

<style scoped>
h2{
  font-size: 2.7rem;
  padding: 3rem;
  text-align: center;
}

.card {
  background-color: white;
  width: 70rem;
  height: 80rem;
  border-radius: 1rem;
  box-shadow: 0px 6px 16px rgba(19, 24, 48, 0.15);
  padding: 4rem;
  margin: 4rem auto;
  font-size: 2.5rem;
  display: flex;
  flex-direction: column;
}

.image-container {
  display: flex;
  gap: 2rem;
  margin-bottom: 1rem;
  width: 100%;
  justify-content: flex-start;
  
}

.image-wrapper,
.add-image {
  flex: 0 0 calc((100% - 5rem) / 3);
  aspect-ratio: 1;
  cursor: pointer;
  position: relative;
  border-radius: 26px;
  border: 2px solid #ddd;
  overflow: auto;
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  border-color: #000000;
  object-fit: cover;
  transition: transform 0.3s ease-in-out;
}

.image-wrapper:hover {
  transform: scale(1.1);
}

.delete-button {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  width: 2rem;
  height: 2rem;
  background-color: rgba(255, 255, 255, 0.7);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  padding: 0;
}

.delete-button img {
  width: 100%;
  height: 100%;
}

.add-image {
  background-color: #f0f0f0;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
  color: #999;
  transition: background-color 0.3s ease;
}

.add-image:hover {
  background-color: #e0e0e0;
}

.form-group {
  margin-bottom: 1rem;
}

.tag-group {
  margin-bottom: 0.5rem;
  width: 10rem;
  margin-top: 1rem;
}

.tag-group select {
  font-size: 1.8rem;
  padding: 0.5rem;
}

label {
  display: block;
  margin-bottom: 0.3rem;
  font-weight: bold;
  font-size: 1.5rem;
}

input,
textarea,
select {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-sizing: border-box;
  font-size: 1.7rem;
  margin-bottom: 2rem;
}

.content-group {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

textarea {
  flex-grow: 1;
  resize: none;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 3rem;
  margin-top: 1rem;
}

.btn {
  display: flex;
  width: 100px;
  height: 35px;
  justify-content: center;
  align-items: center;
  border-radius: 6px;
  border: 1px solid #439aff;
  background-color: #fff;
  cursor: pointer;
  font-size: 1.8rem;
}

.btn-primary {
  color: #439aff;
}

.btn-secondary {
  color: #999;
}
</style>