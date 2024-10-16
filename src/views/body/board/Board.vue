<template>
    <div class="board-grid">
      <div
        v-for="(item, index) in boards"
        :key="index"
        class="board-card"
        @click="goToBoardDetail(index)"
      >
        <!-- 게시글 정보 -->
        <div class="board-info">
          <img 
            v-if="item.memberProfileUrl" 
            :src="item.memberProfileUrl" 
            alt="Profile" 
            class="profile-image" 
          />
          <div class="board-header">
            <span class="nickname">{{ item.memberNickname }}</span>
            <h3 class="board-title">{{ item.title }}</h3>
          </div>
        </div>
  
        <!-- 이미지 출력 -->
        <div class="board-images" v-if="item.imageObj && item.imageObj.length > 0">
          <img
            v-for="(image, i) in item.imageObj"
            :key="i"
            :src="image.imageUrl"
            :alt="image.fileName"
            class="board-image"
          />
        </div>
  
        <!-- 게시글 하단 -->
        <div class="board-footer">
          <span class="comments">💬 댓글 {{ item.commentCount }}</span>
          <span class="likes">❤️ 좋아요 {{ item.likesCount }}</span>
        </div>
      </div>
  
      <!-- 게시글이 없을 경우 메시지 -->
      <p v-if="boards.length === 0" class="no-boards">게시글이 없습니다.</p>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router';
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  
  const router = useRouter();
  const boards = ref([]); // 게시글 목록 저장
  const cursorId = ref(''); // cursorId를 빈 문자열로 초기화
  const hasNext = ref(true); // 다음 페이지 여부를 서버 응답으로 관리
  const loading = ref(false);
  
  const props = defineProps({
    category: {
      type: String,
      default: 'GUIDE',
    },
  });
  
  const fetchBoardItems = async () => {
    
    try {
      console.log("Selected Category:", props.category);
  
      const response = await axios.get(`http://localhost:8080/api/v1/board/${props.category}`, {
        params: { cursor: cursorId.value, size: 4 }
      });
  
      console.log("Raw API Response:", response.data);
      
      let data = response.data;
      let newContents = [];

      if (typeof data === 'string') {
        console.log("Received JSON as String. Attempting to parse...");

        // JSON이 중첩되어 붙어 있는 경우 처리
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
        boards.value = [...boards.value, ...newContents];
        console.log("Products after assignment:", boards.value);

        if (boards.value.length === 0) {
            console.warn("No boards found.");
        }
    } catch (error) {
        console.error("API 호출 에러:", error.response?.data || error.message);
    } finally {
        loading.value = false; // 로딩 종료
    }
};

const handleScroll = () => {
    const gridElement = document.querySelector('.product-grid');
    if (gridElement.scrollTop + gridElement.clientHeight >= gridElement.scrollHeight) {
        fetchProductItems(); // 페이지 끝에 도달하면 데이터 요청
    }
};
  // 게시글 상세 페이지로 이동
  const goToBoardDetail = (id) => {
    router.push(`/board/${id}`);
  };
  
  // 컴포넌트가 마운트될 때 게시글 목록 로드
  onMounted(() => {
    fetchBoardItems();
  });
  </script>
  
  <style scoped>
  .board-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
    margin: 0 auto;
  }
  
  @media (min-width: 1200px) {
    .board-grid {
      grid-template-columns: repeat(4, 1fr);
    }
  }
  
  .board-card {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: transform 0.2s;
  }
  
  .board-card:hover {
    transform: scale(1.02);
  }
  
  .profile-image {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
  }
  
  .board-info {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }
  
  .board-header {
    display: flex;
    flex-direction: column;
  }
  
  .nickname {
    font-weight: bold;
    font-size: 1.2rem;
  }
  
  .board-title {
    font-size: 18px;
    margin-top: 5px;
  }
  
  .board-images {
    display: flex;
    gap: 10px;
    margin-top: 10px;
  }
  
  .board-image {
    width: 100%;
    max-height: 200px;
    object-fit: cover;
    border-radius: 8px;
  }
  
  .board-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
    color: #666;
    font-size: 0.9rem;
  }
  
  .no-boards {
    grid-column: span 4;
    text-align: center;
    font-size: 1.2rem;
    color: #999;
    margin-top: 20px;
  }
</style>
  