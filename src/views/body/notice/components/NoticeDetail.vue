<template>
<div class="page-wrapper">
  <div class = "buttons" v-if="isAdmin">
    <button @click = "goBack" class="back-button">뒤로가기</button>
    <div class="button-inner">
      <button @click ="noticeModify(id)" class="modify-button">수정</button>
      <button @click="showDeleteModal" class="delete-button">삭제</button>
    </div>
  </div>

  <div class="container">
    <h1 class="title">{{noticeValues.title}}</h1>
    <p class="date">{{formatTimeStamp(noticeValues.createdAt)}}</p>
    <div class="content">
        {{ noticeValues.content }}
    </div>
  </div>

  <!-- 삭제 모달 -->
  <PostRemove v-if="isDeleteModalVisible" @confirm="handleDelete" @close="hideDeleteModal" />

</div>
</template>

<script setup>
    import { ref, onMounted} from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import axios from 'axios';
    import PostRemove from '@/components/cud/PostRemove.vue';


    const route = useRoute();
    const router = useRouter();

    const noticeId = ref('');
    const id = route.params.id;
    const noticeValues = ref({});
    const isAdmin = ref(false);

    const loading = ref(true);
    const error = ref(null);

    const isDeleteModalVisible = ref(false);

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
      loading.value = true; // 로딩 시작
    error.value = null; // 이전 에러 초기화

    try {
        const response = await axios.get(`http://localhost:8080/api/v1/notice/detail/${id}`, 
          {
              headers: {
                  Authorization: `Bearer ${token}`,
              }
          }
        );
        console.log("noticeValues.detail response:", response.data);

        let data = response.data;
        let newContents = '';

        if (typeof data === 'string') {
            console.log("Received JSON as String. Attempting to parse...");
            const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || [];

            if (jsonParts.length > 0) {
                try {
                    const parsed = JSON.parse(jsonParts[0]);
                    console.log("Parsed JSON:", parsed.result);
                    newContents = parsed.result || [];
                } catch (error) {
                    console.error("JSON 파싱 실패:", error);
                }
            }
        } else {
            console.log("Parsed Data:", data);
            newContents = data.result || [];
        }

        noticeValues.value = newContents;
    } catch (err) {
        error.value = '제품 정보를 불러오는 데 실패했습니다.'; // 에러 처리
        console.error('API 호출 에러:', err);
    } finally {
        loading.value = false; // 로딩 종료
    }
    };

    const showDeleteModal = () => {
    isDeleteModalVisible.value = true;
    }

    const hideDeleteModal = () => {
    isDeleteModalVisible.value = false;
    }

    async function handleDelete() {
      const id = route.params.id; // URL에서 제품 ID 가져오기
      const jwtToken = localStorage.getItem('jwtToken'); // 로컬 스토리지에서 토큰 가져오기

      try {
          // 서버에 DELETE 요청 보내기 (Authorization 헤더 포함)
          const response = await axios.delete('http://localhost:8080/api/v1/notice', {
              headers: {
                  Authorization: `Bearer ${jwtToken}`,
              },
              data: {
                  id: id, // noticeDeleteRequestVO 본문에 id 포함
              },
          });
          console.log('삭제 성공:', response.data);

          // 삭제 성공 시 목록으로 이동
          router.push('/notice');
      } catch (error) {
          console.error('삭제 실패:', error);
          alert('삭제하는 중 오류가 발생했습니다. 다시 시도해주세요.');
      } finally {
          hideDeleteModal(); // 모달 닫기
      }
  }


    const noticeModify = (id) => {
      console.log("noticeId: " + id);
      router.push({name: 'NoticeModify',
        query: {id: id}
    });
    }

    console.log("value: " + noticeValues.value);

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
  /* flex-direction: column; */
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
  margin-bottom: 20rem;
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
}


.buttons {
  line-height: 1.5; /* 텍스트 가독성을 위한 줄 간격 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 2vh 15vw; /* 위아래는 vh, 좌우는 vw로 여백 설정 */
}

.back-button {
  padding: 0.75rem 1.5rem;
  border: 0.1rem solid #439aff;
  background-color: transparent;
  color: #439aff;
  font-size: 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
}

.button-inner {
  display: flex;
  gap: 1vw; /* 버튼 간 간격 설정 */
}

.modify-button,
.delete-button {
  padding: 1rem 2rem;
  font-size: 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  background-color: transparent;
}

.modify-button {
  border: 0.1rem solid #439aff;
  color: #439aff;
}

.delete-button {
  border: 0.1rem solid #ff414c;
  color: #ff414c;
}

</style>
