<template>
  <div>
    <div class="board-container">
      <div v-for="item in boards" :key="item.id" class="board-card">
        <div class="user-info">
          <img :src="item.memberProfileUrl" alt="User Profile" class="profile-image">
          <span class="nickname">{{ item.memberNickname }}</span>
        </div>
        <h3 class="board-title">{{ item.title }}</h3>
        <div class="image-container">
          <img v-for="(image, i) in item.imageObj.slice(0, 3)" :key="i" :src="image.imageUrl" :alt="image.fileName" class="board-image">
        </div>
        <p class="board-content">{{ item.content }}</p>
        <div class="board-footer">
          <span class="interaction-count">❤️</span>
          <span class="interaction-count">💬</span>
          <span class="interaction-count">➡️</span>
        </div>
      </div>
    </div>
    <div v-if="loading" class="loading">Loading...</div>
    <div ref="sentinel" style="height: 1px;"></div>
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted, onUnmounted, watch } from 'vue';
import axios from 'axios';

const boards = ref([]);
const cursorId = ref('');
const hasNext = ref(true);
const loading = ref(false);
const sentinel = ref(null);

const router = useRouter();
const route = useRoute();

const tag = ref(route.params.tag || 'GUIDE');

const fetchBoardItems = async (reset = false) => {
    if (loading.value || (!reset && !hasNext.value)) return;

    loading.value = true;

    if (reset) {
        boards.value = [];
        cursorId.value = '';
        hasNext.value = true;
    }

    try {
        console.log('Fetching board items...'); // 디버깅 로그
        const response = await axios.get(`http://localhost:8080/api/v1/board/${tag.value}`, {
            params: { cursor: cursorId.value || '', size: 3 }
        });

        let data = response.data;
        let newContents = [];

        if (typeof data === 'string') {
            const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || [];
            if (jsonParts.length > 0) {
                try {
                    const parsed = JSON.parse(jsonParts[0]);
                    newContents = parsed.result?.comment || [];
                    cursorId.value = parsed.result?.cursorId || '';
                    hasNext.value = parsed.result?.hasNext;
                } catch (error) {
                    console.error("JSON 파싱 실패:", error);
                }
            }
        } else {
            newContents = data.result?.comment || [];
            cursorId.value = data.result?.cursorId || '';
            hasNext.value = data.result?.hasNext;
        }

        boards.value = [...boards.value, ...newContents];
        console.log('New contents added:', newContents.length); // 디버깅 로그

        if (boards.value.length === 0) {
            console.warn("No boards found.");
        }
    } catch (error) {
        console.error("API 호출 에러:", error.response?.data || error.message);
    } finally {
        loading.value = false;
    }
};

const observerOptions = {
    root: null,
    rootMargin: '0px',
    threshold: 1.0
};

const intersectionObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting && !loading.value && hasNext.value) {
            console.log('Intersection observed, fetching more items...'); // 디버깅 로그
            fetchBoardItems();
        }
    });
}, observerOptions);

watch(
    () => route.params.tag,
    (newTag) => {
        tag.value = newTag || 'GUIDE';
        fetchBoardItems(true);
    }
);

onMounted(() => {
    fetchBoardItems();
    if (sentinel.value) {
        intersectionObserver.observe(sentinel.value);
    }
});

onUnmounted(() => {
    if (sentinel.value) {
        intersectionObserver.unobserve(sentinel.value);
    }
});
</script>

<style scoped>
/* 스타일은 이전과 동일 */
</style>

<style scoped>
.board-container {
  max-width: 600px;
  margin: 0 auto;
}

.board-card {
  background: #FFFFFF;
  border: 1px solid #E0E0E0;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 15px;
  margin-top: 15px;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.profile-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 12px;
}

.nickname {
  font-weight: bold;
}

.board-title {
  font-size: 18px;
  margin-bottom: 12px;
}

.image-container {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.board-image {
  width: calc(33.333% - 5.333px);
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.board-content {
  margin-bottom: 12px;
}

.board-footer {
  display: flex;
  justify-content: space-between;
}

.interaction-count {
  font-size: 14px;
  color: #666;
}

.loading {
  text-align: center;
  padding: 20px;
  font-size: 18px;
  color: #666;
}
</style>