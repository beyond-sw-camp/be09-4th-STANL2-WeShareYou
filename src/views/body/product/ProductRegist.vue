<template>
    <div class="container-regist">
        <div class="wrapper-regist">
            <select v-model="category" class="category-select">
                <option value="NECESSITIES">생활품</option>
                <option value="KITCHENWARES">주방용품</option>
                <option value="CLOTHES">의류</option>
                <option value="TOY">놀이</option>
                <option value="DEVICE">전자기기</option>
                <option value="ETC">기타</option>
            </select>
            <button @click="listPost" class="list-btn">취소</button>
            <button @click="submitPost" class="submit-btn">등록</button>
        </div>

        <PostRegist v-model:title="title" v-model:content="content" v-model:image="image" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import PostRegist from '@/components/cud/PostRegist.vue';

const title = ref('');
const content = ref('');
const image = ref(null); // 이미지 파일
const category = ref('NECESSITIES');
const route = useRoute();
const router = useRouter();

// LocalStorage에서 id 값 가져오기
const userId = getUserIdFromLocalStorage();

onMounted(() => {
    if (route.query.category) {
        category.value = route.query.category;
    }
});

async function submitPost() {
    if (!title.value || !content.value || !image.value || !userId) {
        alert('모든 필드와 사용자 정보가 필요합니다.');
        return;
    }

    const formData = new FormData();
    formData.append('file', image.value); // 이미지 파일 추가
    formData.append(
        'vo',
        new Blob(
            [
                JSON.stringify({
                    title: title.value,
                    content: content.value,
                    category: category.value,
                    status: 'RENTAL',
                }),
            ],
            { type: 'application/json' }
        )
    );

    try {
        const response = await axios.post('http://localhost:8080/api/v1/product', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
                'id': userId, // 헤더에 사용자 ID 추가
            },
        });

        if (response.status === 201) {
            alert('상품이 성공적으로 등록되었습니다!');
            router.push('/product');
        } else {
            console.error('응답 상태 코드:', response.status);
            alert('상품 등록에 실패했습니다.');
        }
    } catch (error) {
        console.error('상품 등록 실패:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}

function listPost() {
    router.push('/product');
}

// JWT에서 ID 추출 함수
function getUserIdFromLocalStorage() {
    const user = localStorage.getItem('id'); // 'user'로 저장된 경우
    if (user) {
        try {
            const parsedUser = JSON.parse(user);
            return parsedUser.id; // ID 추출
        } catch (error) {
            console.error('JWT 파싱 실패:', error);
            return null;
        }
    }
    return null;
}
</script>

<style scoped>
.container-regist {
    /* 생략 */
}

.wrapper-regist {
    display: flex;
    justify-content: end;
    align-items: center;
    margin-right: 14rem;
    margin-top: 3rem;
}

.list-btn {
    color: #439aff;
    background-color: white;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 1rem 2rem;
    border: 1px solid #439aff;
    margin-right: 1rem;
}

.submit-btn {
    background-color: #439aff;
    color: white;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 0.5rem 2rem;
    border: none !important;
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