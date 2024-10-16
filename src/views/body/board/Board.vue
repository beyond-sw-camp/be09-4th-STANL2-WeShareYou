<template>
    <div class="게시판 페이지" @scroll="onScroll">
        <div
            v-for="(post, index) in posts"
            :key="post.id"
            class="post-card"
        >
        <h2>{{ post.title }}</h2>
        <div class="post-images">
            <img v-for="(img, idx) in post.images" :key="idx" :src="img" />
        </div>
        <p>{{ post.content }}</p>
        <div class="post-footer">
            <span>❤️ {{ post.likesCount }}</span>
            <span>💬 {{ post.commentCount }} 댓글</span>
        </div>
        </div>
        <div v-if="isLoading" class="loading">Loading...</div>
    </div>
</template>

<script>
    export default {
    data() {
        return {
        posts: [], // 게시글 목록
        isLoading: false, // 로딩 중 상태
        cursor: null, // 다음 데이터를 가져올 커서
        hasMore: true, // 더 많은 데이터 여부
        };
    },
    methods: {
        async fetchPosts() {
        if (this.isLoading || !this.hasMore) return;
        this.isLoading = true;

        try {
          // API 호출 (커서 기반 데이터 요청)
            const response = await fetch(
            `/api/posts?cursor=${this.cursor || ''}`
            );
            const { data, nextCursor } = await response.json();

            this.posts.push(...data);
            this.cursor = nextCursor;
          this.hasMore = !!nextCursor; // 다음 커서가 있으면 true
        } catch (error) {
            console.error('Error fetching posts:', error);
        } finally {
            this.isLoading = false;
        }
        },
        onScroll() {
        const bottom =
            this.$el.scrollTop + this.$el.clientHeight >=
            this.$el.scrollHeight - 10;
        if (bottom) {
          this.fetchPosts(); // 스크롤이 바닥에 닿으면 게시글 요청
        }
        },
    },
    mounted() {
      this.fetchPosts(); // 첫 로딩 시 게시글 가져오기
    },
    };
    </script>

    <style scoped>
    .feed-container {
        height: 100vh;
        overflow-y: auto;
        padding: 20px;
    }

    .post-card {
        border: 1px solid #ddd;
        border-radius: 10px;
        margin-bottom: 20px;
        padding: 15px;
    }    
  
    .post-images img {
        width: 30%;
        margin-right: 10px;
        border-radius: 8px;
    }
  
    .loading {
        text-align: center;
        margin-top: 20px;
    }
</style>
  