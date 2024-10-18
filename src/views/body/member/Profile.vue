<template>
  <div class="profile-container">
    <h1 class="profile-title">프로필</h1>

    <!-- 상단 프로필 정보 출력 -->
    <div class="profile-header" v-if="userInfo">
      <img :src="userInfo.profileUrl" alt="프로필 이미지" class="profile-img" />
      <div class="profile-intro">
        <p class="a">Nickname</p>
        <p class="nickname">{{ userInfo.nickname || 'Unknown' }}</p>
        <p class="a">한줄 소개</p>
        <p class="introduction">{{ userInfo.introduction || '한줄소개가 없습니다.' }}</p>
        <p class="a">사용 가능한 언어</p>
        <p class="language">{{ userInfo.language || '미설정' }}</p>
      </div>
    </div>
    <br><br><br><br>
    <hr />
    <div class="profile-info" v-if="userInfo">
      <br><br>
      <!-- 내가 쓴 댓글 -->
      <div class="profile-section">
        <h2>작성한 댓글</h2>
        <div v-if="latestComments.length > 0">
          <div v-for="(comment, index) in latestComments" :key="index" class="comment-card">
            <p>댓글 내용: {{ comment.content }}</p>
            <span>작성일: {{ formatDate(comment.createdAt) }}</span>
          </div>
        </div>
        <p v-else class="tung">작성한 댓글이 없습니다.</p>
      </div>
      <br><br><br><br>
      <hr />
      <br><br>
      <!-- 내가 쓴 글 -->
      <div class="profile-section">
        <h2>작성한 게시글</h2>
        <div v-if="latestPosts.length > 0">
          <div v-for="(post, index) in latestPosts" :key="index" class="board-card">
            <h3>{{ post.title }}</h3>
            <p>{{ post.content }}</p>
            <div class="board-stats">
              <span>댓글 수: {{ post.commentCount }}</span>
              <span>좋아요 수: {{ post.likesCount }}</span>
              <span>작성일: {{ formatDate(post.createdAt) }}</span>
            </div>
          </div>
        </div>
        <p v-else class="tung">작성한 게시글이 없습니다.</p>
      </div>
      <br><br><br><br>
      <hr />
      <br><br>
      <!-- 좋아한 게시판 -->
      <div class="profile-section">
        <h2>좋아요한 게시판</h2>
        <div v-if="latestLikes.length > 0">
          <div v-for="(board, index) in latestLikes" :key="index" class="board-card">
            <h3>{{ board.title }}</h3>
            <p>{{ board.content }}</p>
            <div class="board-stats">
              <span>댓글 수: {{ board.commentCount }}</span>
              <span>좋아요 수: {{ board.likesCount }}</span>
            </div>
          </div>
        </div>
        <p v-else class="tung">좋아요한 게시판이 없습니다.</p>
      </div>
      <br><br>
      <hr />
      <div class="m-btn">
        <button class="modify-button" @click="goToModify">수정하러 가기</button>  
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { fetchProfileData } from '@/stores/member/profile';

const router = useRouter();
const userInfo = ref(null);

onMounted(async () => {
  try {
    userInfo.value = await fetchProfileData();
    console.log('Fetched User Info:', userInfo.value); // 디버깅용 로그
  } catch (error) {
    console.error('Failed to load profile data:', error);
    alert('프로필 정보를 불러오는데 실패했습니다. 다시 시도해 주세요.');
  }
});

const latestComments = computed(() =>
  [...(userInfo.value?.boardComment || [])]
    .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    .slice(0, 3)
);

const latestPosts = computed(() =>
  [...(userInfo.value?.board || [])]
    .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    .slice(0, 3)
);

const latestLikes = computed(() =>
  [...(userInfo.value?.boardLike || [])]
    .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    .slice(0, 3)
);

function formatDate(dateStr) {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateStr).toLocaleDateString('ko-KR', options);
}

function goToModify() {
  router.push('/modifyProfile');
  // window.location.href = '/modifyProfile'; // 수정 페이지로 이동
}
</script>

<style scoped>
.profile-container {
  max-width: 60rem;
  margin: 4rem auto;
  padding: 2rem;
  background: #fff;
  border-radius: 1rem;
  box-shadow: 0 0.2rem 0.8rem rgba(0, 0, 0, 0.1);
}

.profile-title {
  text-align: center;
  font-size: 4rem;
  margin-bottom: 3rem;
  font-weight: bold;
}

.profile-header {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-bottom: 3rem;
}

.profile-img {
  width: 20rem;
  height: 20rem;
  border-radius: 50%;
  margin-right: 2rem;
}

.profile-intro {
  display: flex;
  flex-direction: column;
}

.a {
  font-size: 2.2rem;
  font-weight: bold;
}

.nickname,
.introduction,
.language {
  font-size: 1.3rem;
  margin-top: 0.5rem;
  padding: 1rem;
  /* 내부 여백 */
  border: 0.2rem solid #ddd;
  /* 테두리 추가 */
  border-radius: 1rem;
  /* 모서리 둥글게 */
  background-color: #f9f9f9;
  /* 배경색 추가 */
}

.board-card,
.comment-card {
  border: 0.2rem solid #ddd;
  border-radius: 1rem;
  padding: 2rem;
  margin-bottom: 2rem;
  font-size: 2.2rem;
}

.board-stats {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
  font-size: 2rem;
}

.tung {
  font-family: 'BM DoHyeon', sans-serif;
  font-size: 4rem;
  text-align: center;
  margin-top: 2rem;
  color: #555;
}

.m-btn{
  display: flex;
  justify-content: flex-end;
}
.modify-button {
  width: 28%;
  margin-top: 3rem;
  padding: 1.5rem 3rem;
  background-color: white;
  color: #007bff;
  border: 0.2rem solid #007bff;
  border-radius: 0.5rem;
  font-size: 1.7rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.modify-button:hover {
  background-color: #0056b3;
}
</style>
