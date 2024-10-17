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
        <!-- Content Display Logic -->
        <div>
          <p class="board-content" v-if="!item.showFullContent">
            <span v-html="formatContent(getFirstLine(item.content))"></span>
            <span v-if="item.content.includes('\n') || item.content.length > getFirstLine(item.content).length">...</span>
          </p>
          <p class="board-content" v-else>
            <span v-html="formatContent(item.content)"></span>
          </p>
        </div>
        <button v-if="item.content.length > 30" @click="toggleContent(item)" class="more-button">
          {{ item.showFullContent ? '닫기' : '더보기' }}
        </button>


        <div class="board-footer">
          <span class="interaction-count">❤️</span>
          <span class="interaction-count">💬</span>
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
        
        console.log("Parsed Data:", data);
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
// "더보기" 버튼 클릭 시 전체 내용 토글 함수
const toggleContent = (item) => {
    item.showFullContent = !item.showFullContent;
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

const formatContent = (text) => {
  return text.replace(/\n/g, '<br>');
}

const getFirstLine = (text) => {
      return text.split('\n')[0]; // \n 기준으로 첫 번째 줄 반환
}

const goToComments = (item) => {
      router.push(`/detail/${item.id}`); // 댓글 페이지로 이동
    };


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
  width: 100rem;
  margin: 30rem;
  margin-top: 8rem;
}

.board-card {
  background: #FFFFFF;
  border: 0.0625rem solid #E0E0E0; /* 1px = 0.0625rem */
  border-radius: 1.5rem; /* 8px = 0.5rem */
  padding: 1rem; /* 16px = 1rem */
  margin-bottom: 1.5rem; /* 15px = 0.9375rem */
  margin-top: 1.5rem; /* 15px = 0.9375rem */
  box-shadow: 0px 1.5px 4.6px 0px rgba(0, 0, 0, 0.25);

}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 0.75rem;
}

.profile-image {
  width: 5rem; /* 40px = 2.5rem */
  height: 5rem; /* 40px = 2.5rem */
  border-radius: 50%;
  margin-right: 0.75rem; /* 12px = 0.75rem */

}

.nickname {
  font-weight: bold;
}

.board-title {
  font-size: 2rem; /* 18px = 1.125rem */
  margin-bottom: 2rem; /* 12px = 0.75rem */
}

.board-content {
  font-size: 2.3rem; /* 16px = 1rem */
  margin-bottom: 0; /* 마진 제거 */
  padding-bottom: 0; /* 필요시 패딩도 제거 */

}

.image-container {
  display: flex;
  gap: 1.5rem; /* 8px = 0.5rem */
  margin-bottom: 0.75rem; /* 12px = 0.75rem */

}

.board-image {
  width: 30rem;
  height: 30rem;
  border-radius: 0.25rem; /* 모서리 둥글게 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 기본 그림자 */
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out; /* 애니메이션 */
  cursor: pointer; /* 마우스 커서 변경 */

}

.board-image:hover {
  transform: scale(1.05); /* 호버 시 이미지 확대 */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15); /* 호버 시 그림자 진하게 */
}

.board-image:active {
  transform: scale(1.1); /* 클릭 시 더 커짐 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* 클릭 시 그림자 깊게 */
}


.board-footer {
  justify-content: space-between;
}

.interaction-count {
  font-size: 0.875rem; /* 14px = 0.875rem */
  color: #666
  ;
}
.more-button {
  background: none;
  border: none;
  color: #999; /* Grey color similar to Instagram UI */
  font-size: 1.0rem; /* Adjusted size to fit the style */
  cursor: pointer;
  padding: 0;
  margin-left: 0.25rem; /* Slight margin for alignment */
  text-decoration: none;
  transition: color 0.2s;
}

.more-button:hover {
  color: #555; /* Darker grey on hover */
}


.loading {
  text-align: center;
  padding: 1.25rem; /* 20px = 1.25rem */
  font-size: 1.125rem; /* 18px = 1.125rem */
  color: #666;
}
</style>