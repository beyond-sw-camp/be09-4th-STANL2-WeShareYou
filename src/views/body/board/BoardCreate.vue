<!-- BoardManagement.vue -->
<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <h2>게시글 작성</h2>
      <textarea v-model="content" placeholder="게시글 내용을 입력하세요"></textarea>
      <div class="actions">
        <button @click="submitPost">작성하기</button>
        <button @click="close">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const content = ref('');
const emit = defineEmits(['close', 'submit']);

const close = () => emit('close');
const submitPost = () => {
  emit('submit', content.value); // 작성 완료 시 데이터 전송
  close();
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 10px;
  min-width: 300px;
}

.actions {
  display: flex;
  justify-content: space-between;
}
</style>
