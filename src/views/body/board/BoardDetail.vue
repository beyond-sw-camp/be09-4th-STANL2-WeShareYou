<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <!-- 이미지 슬라이더 -->
      <div class="modal-left">
        <img :src="currentImage" alt="Board Image" class="board-image" />
        <div class="navigation">
          <!-- 첫 번째 이미지에서는 오른쪽에만 버튼 표시 -->
          <button
            v-if="currentImageIndex === 0"
            @click="nextImage"
            class="right-button"
          >
            ❯
          </button>

          <!-- 중간 이미지에서는 양쪽에 버튼 표시 -->
          <template v-else-if="currentImageIndex < board.imageUrls.length - 1">
            <button @click="prevImage" class="left-button">❮</button>
            <button @click="nextImage" class="right-button">❯</button>
          </template>

          <!-- 마지막 이미지에서는 왼쪽에만 버튼 표시 -->
          <button
            v-if="currentImageIndex === board.imageUrls.length - 1"
            @click="prevImage"
            class="left-button"
          >
            ❮
          </button>
        </div>
      </div>

      <!-- 게시물 제목과 내용 -->
      <div class="modal-right">
        <div class="user-info">
          <img :src="board.memberProfileUrl" alt="User Profile" class="profile-image" />
          <span class="nickname">{{ board.memberNickname }}</span>
        </div>
        <hr class="divider" />
        <h2>{{ board.title }}</h2>
        <p v-html="formattedContent"></p>
        <button @click="close">Close</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, computed } from 'vue';

const props = defineProps({
  board: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(['close']);
const close = () => emit('close');

// 이미지 슬라이드 상태 관리
const currentImageIndex = ref(0);

const currentImage = computed(() => {
  return props.board.imageUrls?.[currentImageIndex.value] || 'https://via.placeholder.com/300';
});

const prevImage = () => {
  if (currentImageIndex.value > 0) {
    currentImageIndex.value--;
  }
};

const nextImage = () => {
  if (currentImageIndex.value < props.board.imageUrls.length - 1) {
    currentImageIndex.value++;
  }
};

const formattedContent = computed(() => {
  return props.board.content.replace(/\n/g, '<br>');
});
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  display: flex;
  flex-direction: row;
  background: white;
  width: 80vw;
  height: 90vh;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.modal-left {
  flex: 1;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f0f0;
  height: 100%;
}

.board-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.navigation {
  position: absolute;
  top: 50%;
  width: 100%;
  display: flex;
  justify-content: space-between;
  transform: translateY(-50%);
  pointer-events: none;
}

.left-button,
.right-button {
  pointer-events: auto;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  cursor: pointer;
  font-size: 2rem;
}

.left-button {
  margin-left: 1rem;
}

.right-button {
  margin-right: 1rem;
}

.left-button:hover,
.right-button:hover {
  background-color: rgba(0, 0, 0, 0.8);
}

.modal-right {
  flex: 1;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 0.5rem; /* 조금 더 조정 */
}

.profile-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 1rem;
}

.nickname {
  font-weight: bold;
  font-size: 1.5rem;
}

.divider {
  border: 0;
  height: 1.3px; /* 더 두껍게 */
  background-color: #444; /* 더 진한 회색 */
  margin: 1rem 0 1.5rem 0; /* 위로 조금 당김 */
}

h2 {
  margin: 0 0 1rem 0;
  font-size: 2rem;
}

p {
  flex-grow: 1;
  font-size: 1.2rem;
  line-height: 1.5;
}

button {
  align-self: flex-end;
  background-color: #439aff;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #73b3ff;
}
</style>
