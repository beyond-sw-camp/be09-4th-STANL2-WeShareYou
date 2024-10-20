<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <button class="close-button" @click="close">✕</button>

      <!-- 이미지 슬라이더 -->
      <div class="modal-left">
        <img :src="currentImage" alt="Board Image" class="board-image" />
        <div class="navigation">
          <button v-if="currentImageIndex === 0" @click="nextImage" :class="['right-button', 'first-button']">❯</button>
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
          <hr class="comment-divider"/>
          <h3>Comments</h3>
          <ul>
            <li v-for="(comment, index) in comments" :key="index" class="comment-item">
              <img 
                v-if="comment.memberProfileUrl" 
                :src="comment.memberProfileUrl" 
                alt="Profile Image" 
                class="profile-img" 
              />
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
            <img src="@/assets/icon/boardIcons/comment.svg" class="svg-icon" alt="Comment Icon" @click="focusCommentInput" />
            <img src="@/assets/icon/boardIcons/letter.svg" class="svg-icon" alt="Message Icon" @click="goToChat(board.id)" />\

            <div v-if="isAuthor" class="more-options">
              <button class="more-button" @click="toggleDropdown">⋯</button>
              <div v-if="showDropdown" class="dropdown-menu">
                <button @click="editPost">게시글 수정</button>
                <button @click="deletePost">게시글 삭제</button>
              </div>
            </div>
          </div>  

          <!-- 중간: 좋아요 및 댓글 수 -->
          <div class="interaction-info">
            <span>좋아요 {{ board.likesCount }}개</span>
            <span>댓글 {{ board.commentCount }}개</span>
          </div>

          <!-- 하단: 댓글 입력과 전송 -->
          <div class="comment-input">
            <input v-model="newComment" ref="commentInput" placeholder="Write a comment..." @keyup.enter="addComment"/>
            <img src="@/assets/icon/boardIcons/send.svg" @click="addComment" class="svg-icon send-icon" alt="Send Icon" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { useRouter } from 'vue-router';
import { defineProps, defineEmits, ref, computed, onMounted } from 'vue';

const router = useRouter();
const userInfo = localStorage.getItem('userInfo');

const props = defineProps({
  board: {
    type: Object,
    required: true,
  },
  initialImageIndex: {
    type: Number,
    default: 0,
  },
  tag: {  // Define tag prop
    type: String,
    required: true,
  }

});

const emit = defineEmits(['close']);
const close = () => {
  emit('close');  // Emit the close event to close the modal

  // Navigate to the route and refresh the page
  router.push(`/board/${props.tag}`).then(() => {
    window.location.reload();  // Refresh the page after navigation
  });
};


const token = localStorage.getItem('jwtToken'); // JWT 토큰 가져오기
const showDropdown = ref(false);

const isAuthor = ref(false); // 초기값 설정

try {
  const user = JSON.parse(userInfo); 
  isAuthor.value = user.id === props.board.memberId; // 작성자 여부 확인
} catch (error) {
  console.error('userInfo 파싱 오류:', error);
}

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};

const editPost = () => {
  router.push(`/board/update/${props.board.id}`); // Navigate to edit page
};

const deletePost = async () => {
  const confirmed = confirm('정말로 이 게시글을 삭제하시겠습니까?');
  if (confirmed) {
    try {
            
      const token = localStorage.getItem('jwtToken');

      await fetch(`http://localhost:8080/api/v1/board`, { 
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': `Bearer ${token}` 
        },
        body: props.board.id
      });
      

      alert('게시글이 삭제되었습니다.');
      close(); 
    } catch (error) {
      console.error('게시글 삭제 에러:', error);
    }
  }
};

// 이미지 슬라이드 상태 관리
const currentImageIndex = ref(props.initialImageIndex);

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
const newComment = ref(''); 
const commentInput = ref(null); // 입력창에 접근하기 위한 ref

const focusCommentInput = () => {
  commentInput.value?.focus(); // 입력창에 포커스
};

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

const addComment = async () => {
  if (newComment.value.trim() === '') return; // 빈 댓글 방지

  try {
    
    const payload = JSON.parse(userInfo); // JWT 토큰에서 유저 정보 추출
    const nickName = payload.nickname || '익명'; // 닉네임 추출 (없을 시 '익명')
    const profileUrl = payload.profile || null; // 프로필 이미지 URL 추출

    const response = await fetch('http://localhost:8080/api/v1/board-comment', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json' ,
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({
        content: newComment.value, // 댓글 내용
        boardId: props.board.id, // 게시물 ID
      }),
    });

    if (!response.ok) {
      console.error('댓글 작성 실패:', response.statusText);
      return;
    }

    const result = await response.json(); // 서버 응답을 JSON으로 파싱

    // 댓글을 작성자 정보와 함께 실시간으로 추가
    comments.value.push({
      memberProfileUrl: profileUrl,
      nickname: nickName,
      content: newComment.value, // 작성한 댓글 내용
    });

    // 댓글 수 업데이트
    props.board.commentCount += 1;

    // 입력창 초기화
    newComment.value = '';
    console.log('댓글 작성 성공:', result);
  } catch (error) {
    console.error('댓글 작성 에러:', error);
  }
};

const likePost = () => {
  console.log('Post liked!');
};

const goToChat = () => {
  router.push('/chat');
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
  /* object-fit: cover; */
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
  margin: 0.3rem 0 0 0;
}

.comment-divider {
  border: 0;
  height: 1px; 
  background-color: #c8c8c8; 
  margin: 0.3rem 0 0 0;
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
  margin: 0;
  overflow-y: scroll;
  scrollbar-width: none; /* Firefox용 */
}

/* 크롬, 사파리, 엣지 등의 웹킷 기반 브라우저용 */
.comments::-webkit-scrollbar {
  display: none;
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
  flex-shrink: 0; /* 이미지 크기 고정 */
}

.comment-content {
  display: flex;
  align-items: center;
  gap: 0.5rem; /* 닉네임과 댓글 간 여백 */
  flex-wrap: wrap; /* 긴 텍스트가 있을 경우 줄바꿈 */
}

.nickname {
  font-weight: bold;
  margin-right: 0.5rem;
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
  margin-top: auto;
}

.interaction-divider{
  border: 0;
  height: 1.3px; /* 더 두껍게 */
  background-color: #444; /* 더 진한 회색 */
  margin: 0 0 0.5rem 0;
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
  font-size: 2rem;
  color: #666;
  gap: 0.7rem;
  margin-bottom: 1rem;
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

.close-button {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  color: #999;
  transition: color 0.2s;
}

.close-button:hover {
  color: #333;
}

.more-options {
  margin-left: auto;
  position: relative;
}

.more-button {
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  color: #999;
  transition: color 0.2s;
}

.more-button:hover {
  color: #333;
}

.dropdown-menu {
  position: absolute;
  top: 2.5rem;
  right: 0;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 100;
  display: flex;
  flex-direction: column;
  padding: 0.5rem 1rem;
  gap: 0.5rem;
}

.dropdown-menu button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  text-align: left;
}

.dropdown-menu button:hover {
  color: red;
}
</style>
