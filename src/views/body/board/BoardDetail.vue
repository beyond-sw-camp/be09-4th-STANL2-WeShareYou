<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <!-- 이미지 슬라이더 -->
      <div class="modal-left">
        <img :src="currentImage" alt="Board Image" class="board-image" />
        <div class="navigation">
          <button v-if="currentImageIndex === 0" @click="nextImage" class="right-button">❯</button>
          <template v-else-if="currentImageIndex < board.imageUrls.length - 1">
            <button @click="prevImage" class="left-button">❮</button>
            <button @click="nextImage" class="right-button">❯</button>
          </template>
          <button v-if="currentImageIndex === board.imageUrls.length - 1" @click="prevImage" class="left-button">❮</button>
        </div>
      </div>

      <!-- 게시물 제목과 내용 -->
      <div class="modal-right">
        <div class="user-info">
          <img :src="board.memberProfileUrl" alt="User Profile" class="profile-image" />
          <span class="nickname">{{ board.memberNickname }}</span>
        </div>
        <hr class="divider" />

        <div class="comments">
          <h2>{{ board.title }}</h2>
          <p v-html="formattedContent"></p>
          <h3>Comments</h3>
          <ul>
            <li v-for="(comment, index) in comments" :key="index" class="comment-item">
              <img v-if="comment.memberProfileUrl" :src="comment.memberProfileUrl" alt="Profile Image" class="profile-img" />
              <div class="comment-content">
                <strong class="nickname">{{ comment.nickname }}</strong>
                <p class="content">{{ comment.content }}</p>
              </div>
            </li>
          </ul>
        </div>

        <div class="interactions">

          <hr class="interaction-divider" />
          <!-- 상단: 좋아요 아이콘 -->
          <div class="interaction-icons">
            <img src="@/assets/icon/boardIcons/heart.svg" @click="likePost" class="svg-icon" alt="Like Icon" />
            <img src="@/assets/icon/boardIcons/comment.svg" class="svg-icon" alt="Comment Icon" @click="openModal(board)" />
            <img src="@/assets/icon/boardIcons/letter.svg" class="svg-icon" alt="Message Icon" @click="goToChat(board.id)" />
          </div>

          <!-- 중간: 좋아요 및 댓글 수 -->
          <div class="interaction-info">
            <span>좋아요 {{ board.likesCount }}개</span>
            <span>댓글 {{ board.commentCount }}개</span>
          </div>

          <!-- 하단: 댓글 입력과 전송 -->
          <div class="comment-input">
            <input v-model="newComment" placeholder="Write a comment..." />
            <img src="@/assets/icon/boardIcons/send.svg" @click="addComment" class="svg-icon send-icon" alt="Send Icon" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { defineProps, defineEmits, ref, computed } from 'vue';
import { onMounted } from 'vue';

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

const comments = ref([]);

const fetchComments = async () => {
  try {
    const response = await fetch(`http://localhost:8080/api/v1/board-comment/${props.board.id}`);

    let data = await response.text(); // 응답을 텍스트로 읽기
    console.log('Response Text:', data); // 응답 로그 출력

    let parsedData;
    // 응답이 문자열일 경우 JSON을 파싱
    if (typeof data === 'string') {
      const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || []; // 여러 JSON이 중복될 경우 첫 번째만 추출
      if (jsonParts.length > 0) {
        try {
          parsedData = JSON.parse(jsonParts[0]); // 첫 번째 JSON 파싱
        } catch (error) {
          console.error("JSON 파싱 실패:", error);
          return;
        }
      } else {
        console.error('No valid JSON found in response.');
        return;
      }
    } else {
      // 만약 응답이 이미 JSON 객체라면 그대로 할당
      parsedData = data;
    }

    // JSON이 성공적으로 파싱되었을 때 처리
    if (parsedData.success && parsedData.result) {
      comments.value = parsedData.result.map(comment => ({
        memberProfileUrl: comment.memberProfileUrl,
        nickname: comment.nickname,
        content: comment.content,
      }));
    } else {
      console.error('Unexpected data format:', parsedData);
    }

  } catch (error) {
    console.error('Error fetching comments:', error);
  }
};

const addComment = () => {
  if (newComment.value.trim() !== '') {
    comments.value.push({ nickname: 'You', content: newComment.value, memberProfileUrl: null });
    newComment.value = '';
  }
};

const likePost = () => {
  console.log('Post liked!');
};

// 게시물이 열릴 때 댓글을 가져옴
onMounted(() => {
  fetchComments();
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
  margin: 1rem 0 0 0;
}

h2 {
  margin: 0 0 1rem 0;
  font-size: 2rem;
}

p {
  flex-grow: 1;
  font-size: 1.3rem;
  line-height: 1.5;
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.comments {
  /* margin-top: 1rem; */
  margin: 0;
  overflow-y: scroll;
}

.comment-item {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.profile-img {
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  margin-right: 10px;
  object-fit: cover;
}

.comment-content {
  display: flex;
  flex-direction: column;
}

.nickname {
  font-weight: bold;
  margin-bottom: 0.2rem;
}

.content {
  margin: 0;
  font-size: 1rem;
}


.interactions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin: 0;
}

.interaction-divider{
  border: 0;
  height: 1.3px; /* 더 두껍게 */
  background-color: #444; /* 더 진한 회색 */
  margin: 0 0 1rem 0;
}

.interaction-icons {
  display: flex;
  gap: 0.8rem;
}

.svg-icon {
  width: 2.5rem;
  height: 2.5rem;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
}

.svg-icon:hover {
  transform: scale(1.2);
}

.interaction-info {
  display: flex;
  font-size: 1.3rem;
  color: #666;
  gap: 0.7rem;
}

.comment-input {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.comment-input input {
  flex: 1;
  padding: 1rem;
  font-size: 1.2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.send-icon {
  width: 3rem;
  height: 3rem;
}
</style>
