<template>
  <div class="button-container">
    <button @click="goToCreatenotice" class="register-button" v-if="isAdmin">등록</button>
  </div>

  <div class="notice-grid" ref="gridElement">
      <div v-for="item in notices" :key="item.id" class="notice-card" @click="goTonoticeDetail(item.id)">
        <h3>{{ item.title }}</h3>
      </div>
      <div ref="sentinel" style="height: 1px;"></div>

      <p v-if="notices.length === 0" class="no-notices">
          No notices available.
      </p>
      <p v-if="loading" class="loading">Loading more notices...</p>
      <!-- <div ref="sentinel" style="height: 1px;"></div> -->
  </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';

// 제품 목록과 상태 관리를 위한 ref
const notices = ref([]);
const cursorId = ref(''); // cursorId를 빈 문자열로 초기화
const hasNext = ref(true); // 다음 페이지 여부를 서버 응답으로 관리
const loading = ref(false); // 로딩 중인지 여부를 관리
const sentinel = ref(null);
const isAdmin = ref(false);
// Vue Router 사용
const router = useRouter();
const route = useRoute(); // 현재 경로 정보 가져오기

const token = localStorage.getItem("jwtToken");

const checkRole = () => {
    const roleString = localStorage.getItem('Roles');   
    if(roleString){
      const roles = roleString.split(',');
      isAdmin.value = roles.includes('ADMIN');
    }else{
      isAdmin.value = false;
    }
  }

// API에서 제품 아이템을 가져오는 함수
const fetchnoticeItems = async (reset = false) => {
  if (loading.value || (!reset && !hasNext.value)) return; // 중복 호출 방지

  loading.value = true; // 로딩 시작

  if (reset) {
      // 초기화할 경우 기존 데이터 및 상태를 리셋
      notices.value = [];
      cursorId.value = '';
      hasNext.value = true;
  }

  try {
      const response = await axios.get(
          `http://localhost:8080/api/v1/notice`,
          {
              params: {
                  cursor: cursorId.value || '', // cursorId가 없으면 빈 문자열로 전송
                  size: 8 // 페이지 당 가져올 아이템 수
              },
              headers: {
                  Authorization: `Bearer ${token}`,
              }
          }
      );

      console.log("API Response:", response.data);

      let data = response.data;
      let newContents = []; // 새로 추가할 데이터

      

      // 1. 응답이 문자열인 경우 처리
      if (typeof data === 'string') {
          console.log("Received JSON as String. Attempting to parse...");
          const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || [];

          if (jsonParts.length > 0) {
              try {
                  const parsed = JSON.parse(jsonParts[0]);
                  console.log("Parsed JSON:", parsed.result.comment);

                  newContents = parsed.result?.comment || [];
                  cursorId.value = parsed.result?.cursorId || ''; // cursorId 업데이트
                  hasNext.value = parsed.result?.hasNext; // hasNext 상태 업데이트
              } catch (error) {
                  console.error("JSON 파싱 실패:", error);
              }
          }
      } else {
          console.log("Parsed Data:", data);

          // 2. 데이터가 이미 객체일 경우 바로 처리
          newContents = data.result?.comment || [];
          cursorId.value = data.result?.cursorId || ''; // cursorId 업데이트
          hasNext.value = data.result?.hasNext; // hasNext 상태 업데이트
      }

      // 3. 기존 제품 목록에 새 데이터를 추가
      notices.value = [...notices.value, ...newContents];


      if (notices.value.length === 0) {
          console.warn("No notices found.");
      }
  } catch (error) {
      console.error("API 호출 에러:", error.response?.data || error.message);
  } finally {
      loading.value = false; // 로딩 종료
  }
};

const observerOptions = {
    root: null,
    // rootMargin: '0px',
    rootMargin: '0px 0px 100px 0px',
    threshold: 0,
};

const intersectionObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      console.log('Intersection observed:', entry);
        if (entry.isIntersecting && !loading.value && hasNext.value) {
            console.log('Intersection observed, fetching more items...'); // 디버깅 로그
            fetchnoticeItems();
        }
    });
}, observerOptions);


// 제품 상세 페이지로 이동하는 함수
const goTonoticeDetail = (id) => {
  router.push(`/${id}`);
};

// 컴포넌트가 마운트될 때 첫 페이지 데이터 가져오기
onMounted(() => {  
  fetchnoticeItems();
  checkRole();
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
.notice-grid {
  display: grid;
  /* grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); */
  grid-template-columns: 1fr;
  gap: 2rem;
  margin-top: 2rem;
  height: 80vh; /* 전체 화면 높이 */
  overflow-y: auto; /* 스크롤 가능 */
}

@media (min-width: 1200px) {
  .notice-grid {
      grid-template-columns: repeat(1, 1fr);
  }
}

.notice-card {
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0; /* 리스트 구분선 */
  padding: 10px 15px;
  text-align: left;
  cursor: pointer;
  height: auto; /* 높이를 자동으로 맞춤 */
  display: flex;
  align-items: center;
  gap: 1rem; /* 제목과 아이콘 같은 요소 간의 간격 */
  transition: background-color 0.3s ease;
}

/* 마우스 호버 시 배경 색상 변경 */
.notice-card:hover {
  background-color: #f5f5f5;
}

.available {
  background-color: #d9eaff;
  color: #1a73e8;
  border-radius: 12px;
  padding: 5px 10px;
  font-size: 1.2rem;
  margin-bottom: 1rem;
}

h3 {
  font-size: 1.8rem;
  margin: 0 0 5px 0;
}

p {
  color: #666;
  font-size: 1.4rem;
  margin: 0;
}

.loading {
  text-align: center;
  margin-top: 1rem;
  color: #666;
}

.no-notices {
  text-align: center;
  margin-top: 2rem;
  font-size: 1.8rem;
  color: #888;
}

.button-container {
  display:flex;
  align-items: center;
  justify-content: end;
}

.register-button {
    display:flex;
    justify-content: center;
    align-items:end;
    font-size: 1.25rem;
    margin-right: 1rem;
    padding: 1rem 2rem;
    border: 1px solid #439aff;
    color: #439aff;
    background-color: transparent;
    border-radius: 4px;
    cursor: pointer;
  }
</style>
