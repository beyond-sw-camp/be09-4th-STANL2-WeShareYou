<template>
  <div class="page-wrapper">
    <!-- 등록 버튼 -->
    <!-- <button class="back-button">뒤로가기</button> -->
    <div class="buttons">
      <button @click="noticeModify" class="modify-button" v-if="isAdmin">수정</button>
      <button @click="noticeDelete" class="delete-button" v-if="isAdmin">삭제</button>
    </div>

    <!-- 공지사항 상세 컨테이너 -->
    <div class="container">
      <h1 class="title">공지사항 제목</h1>
      <p class="date">2024-10-15</p>
      <div class="content">
        <p>
          공지사항의 상세 내용을 여기에 표시합니다. 여러 줄의 텍스트가 포함될 수 있으며, 
          공지사항의 중요 정보가 깔끔하게 정리됩니다.
        </p>
      </div>
    </div>
    <button class="back-button">뒤로가기</button>

  </div>
</template>

<script setup>
    import { ref, onMounted} from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    const route = useRoute();
    const router = useRouter();

    const noticeId = ref('');
  
    const noticeValues = ref({});
    const isAdmin = ref(false);

    const checkRole = () => {
      const roleString = localStorage.getItem('Roles');   
      if(roleString){
        const roles = roleString.split(',');
        isAdmin.value = roles.includes('ADMIN');
      }else{
        isAdmin.value = false;
      }
    }

    const token = localStorage.getItem("jwtToken");

    const formatTimeStamp = (timestamp) => {
      const date = new Date(timestamp);
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작 1을 더해줌
      const day = String(date.getDate()).padStart(2, '0');

      return `${month}-${day}`;
    }

    const fetchNoticeData = async () => {
        const response = await fetch(`https://localhost:8080/api/v1/notice/${noticeId.value}`, {
                      headers: {
                Authorization: `Bearer ${token}`,
              }
        });
        const data = await response.json();

        noticeValues.value = {
            id: data.id,
            title: data.title,
            content: data.content,
            createdAt: formatTimeStamp(data.createdAt),
            updatedAt: formatTimeStamp(data.updatedAt),
            active: data.active,
        }
    }
    

    const goBack = () => {
        router.push('/notice');
    }

    onMounted(() => {
      checkRole();
      fetchNoticeData();
      noticeId.value = route.params.noticeId;
    }) 

</script>

<style scoped>
/* 전체 페이지 래퍼 */
.page-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding-top: 2rem;
  gap: 2rem; /* 상단 버튼과 컨테이너 간격 */
}


/* 공지사항 상세 컨테이너 */
.container {
  width: 70%;
  border: 1px solid #bdc7d5;
  border-radius: 1rem;
  padding: 2rem;
  background-color: #fff;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 제목 스타일 */
.title {
  font-size: 1.75rem; /* 28px */
  font-weight: bold;
  margin-bottom: 0.5rem;
}

/* 날짜 스타일 */
.date {
  font-size: 1.25rem; /* 20px */
  color: #9e9e9e;
  padding-bottom: 1rem;
  margin-bottom: 1rem;
  border-width:  0.1rem 0.1rem 1rem 0.1rem;
  border-bottom: 1px solid #bdc7d5;
}

/* 내용 스타일 */
.content {
  font-size: 2rem; /* 20px */
  line-height: 1.5; /* 텍스트 가독성을 위한 줄 간격 */
}


/* 뒤로가기 버튼 */
.back-button {
  align-self: flex-start;
  margin-left: 15%; /* 왼쪽 여백 */
  padding: 0.75rem 1.5rem;
  border: 1px solid #439aff;
  background-color: transparent;
  color: #439aff;
  font-size: 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
}

  .buttons {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
  
  .modify-button {
    margin-right: 10px;
    padding: 10px 20px;
    font-size: 1rem;
    border: 1px solid #439aff;
    color: #439aff;
    background-color: transparent;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .delete-button {
    font-size: 1rem;
    padding: 10px 20px;
    border: 1px solid #ff414c;
    color: #ff414c;
    background-color: transparent;
    border-radius: 4px;
    cursor: pointer;
  }

</style>
