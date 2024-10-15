<template>
  <div class="page-wrapper">
    <!-- 등록 버튼 -->
    <button class="register-button">등록</button>

  <div class="container">
      <!-- 공지사항 목록 -->
      <div class="notice-list">

        <div v-for="item in notices" :key="item.id" class="notice-item" @click="goToNoticeDetail(item.id)">
          <span class="notice-title">{{ item.title }}</span>
          <span class="notice-date">formatTimeStamp(item.createdAt)</span>
        </div>
      </div>
    </div>
    </div>
  </template>
  
  <script setup>
    import {userRouter} from 'vue-router';
    import {ref, onMounted} from 'vue';
    import {axios} from 'axios';

    const router = useRouter();
    const notices = ref([]);

    const fetchNoticeItems = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/notice');
        if(!response.ok) throw new Error('에러 발생');
        const data = await response.json();
        notices.value = data;
      } catch (error) {
        console.error(error);
      }
    }

    const formatTimeStamp = (timestamp) => {
      const date = new Date(timestamp);
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작 1을 더해줌
      const day = String(date.getDate()).padStart(2, '0');

      return `${month}-${day}`;
    }

    /* 상세조회로 */
    const goToNoticeDetail = (id) => {
      router.push(`/notice/${id}`);
    }

  </script>
  
  <style scoped>

/* 전체 페이지 래퍼 */
.page-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center; /* 컨테이너 중앙 정렬 */
  width: 100%;
  padding-top: 2rem;
  position: relative; /* 버튼이 래퍼 기준으로 배치됨 */
}

/* 등록 버튼 스타일 */
.register-button {
  align-self: flex-end; /* 페이지 래퍼의 우측 끝에 위치 */
  margin-right: 15%; /* 컨테이너와 균형을 맞추기 위해 */
  margin-bottom: 1rem;
  padding: 0.75rem 1.5rem;
  border: 1px solid #439aff;
  background-color: transparent;
  color: #439aff;
  font-size: 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
}
  /* 전체 컨테이너 */
  .container {
    width: 70%;
    margin: 0 auto;
    border: 1px solid #bdc7d5;
    border-radius: 1rem;
    padding: 2rem;
    background-color: #fff;
    overflow: auto;
  }
  .create-btn {
      background-color: #4CAF50;
      color: white;
      border: none;
      padding: 10px 15px;
      margin-top: 20px;
      cursor: pointer;
      border-radius: 4px;
      display: flex;
      justify-content: center;
    }
  /* 공지사항 목록 스타일 */
  .notice-list {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    overflow-y: auto;
  }
  
  /* 각 공지사항 항목 */
  .notice-item {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #e0e0e0;
    padding: 1rem 0;
  }
  
  /* 공지사항 제목과 날짜 */
  .notice-title {
    font-size: 1.25rem; /* 20px */
  }
  
  .notice-date {
    font-size: 1rem; /* 16px */
    color: #9e9e9e;
  }
  </style>
  