<template>
    <div class="container-regist">
        <div class="wrapper-regist">
            <button @click="submitPost" class="submit-btn">수정</button>
            <button @click="listPost" class="list-btn">취소</button>
        </div>

        <PostModify :initialTitle="notice.title" :initialContent="notice.content"
            @updateTitle="handleTitleUpdate" @updateContent="handleContentUpdate" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import PostModify from '@/components/cud/PostModify.vue';

const route = useRoute();
const router = useRouter();
const notice = ref({});
const loading = ref(true);
const error = ref(null);
const id = ref(route.query.id); // URL에서 ID 가져오기

const token = localStorage.getItem("jwtToken");

// API에서 제품 상세 정보를 가져오는 함수
const fetchnoticeModify = async () => {
    loading.value = true; // 로딩 시작
    error.value = null; // 이전 에러 초기화

    console.log("id: " + id.value);

    try {
        const response = await axios.get(`http://localhost:8080/api/v1/notice/detail/${id.value}`,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }
        );

        console.log("notice detail response:", response.data);

        let data = response.data;

        if (typeof data === 'string') {
            const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || [];

            if (jsonParts.length > 0) {
                try {
                    const parsed = JSON.parse(jsonParts[0]);
                    notice.value = parsed.result; // 제품 정보를 저장
                } catch (error) {
                    console.error("JSON 파싱 실패:", error);
                }
            }
        } else {
            notice.value = data.result; // 데이터가 객체일 경우 처리
        }
    } catch (err) {
        error.value = '제품 정보를 불러오는 데 실패했습니다.'; // 에러 처리
        console.error('API 호출 에러:', err);
    } finally {
        loading.value = false; // 로딩 종료
    }
};

// 컴포넌트가 마운트될 때 제품 상세 정보 가져오기
onMounted(() => {
    fetchnoticeModify();
});

/// 자식 컴포넌트로부터 데이터 수신
const handleTitleUpdate = (newTitle) => {
    // 자식으로부터 받은 값이 null이면 기존 값을 유지
    console.log("부모에서 받은 제목:", newTitle);
    notice.value.title = newTitle !== null ? newTitle : notice.value.title;
    console.log("notice.value.title:", notice.value.title);
};

const handleContentUpdate = (newContent) => {
    // 자식으로부터 받은 값이 null이면 기존 값을 유지
    notice.value.content = newContent !== null ? newContent : notice.value.content;
    console.log("notice.value.title:", notice.value.content);
};

// const handleImageUpload = (file) => {
//     // 파일이 없으면 기존 이미지 URL 유지
//     if (file) {
//         imageFile.value = file;
//         console.log("이미지ㅣ짖11111: " + notice.imageUrl);
//     } else {
//         imageFile.value = notice.imageUrl;
//         console.log("이미지ㅣ짖: " + notice.imageUrl);
//     }
// };

// 취소 버튼 클릭 시
const listPost = () => {
    console.log('취소 버튼 클릭!');
    // 필요에 따라 다른 로직 추가 가능
    router.back();
};

// 서버로 데이터 전송
const submitPost = async () => {
    console.log("title: " + notice.value.title);
    console.log("content: " + notice.value.content);
    console.log("id: " + id.value);
    if (!notice.value.title || !notice.value.content) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    try {
        const response = await axios.put(`http://localhost:8080/api/v1/notice`,
            {
                title: notice.value.title,
                content: notice.value.content,
                id: id.value,
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
        alert('공지가 수정되었습니다.');
        console.log('응답:', response.data.result);
        router.push(`/notice`);
    } catch (error) {
        console.error('수정 실패:', error);
        alert('공지가 수정되지 않았습니다.');
    }
};
</script>


<style scoped>
.wrapper-regist {
    display: flex;
    justify-content: end;
    align-items: center;
    margin-right: 6rem;
    margin-top: 3rem;
}

.list-btn {
    color: #FF414C;
    background-color: white;
    border-radius: 0.5rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 0rem 2rem;
    border: 1px solid #FF414C;
    margin-right: 4.5rem;
}

.submit-btn {
    background-color: white;
    color: #439aff;
    border-radius: 0.5rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 0rem 2rem;
    border: 1px solid #439aff !important;
    margin-right: 0.5rem;
}

.category-select {
    text-align: center;
    font-size: 1.5rem;
    height: 3rem;
    margin-right: 1rem;
    border: 1px solid #BDC7D5;
    border-radius: 4px;
    color: #353E4F;
}
</style>