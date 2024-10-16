<template>
    <div class="card">
        <h3 class="product-title">{{ product.title }} 제품 상세 정보</h3>
        <p class="product-description">{{ product.content }}</p>
        <img v-if="product.imageUrl" class="product-image" :src="product.imageUrl" alt="Product Image" />
        <p><strong>카테고리:</strong> {{ product.category }}</p>
        <p><strong>상태:</strong> {{ product.status }}</p>
        <p><strong>대여 가능:</strong> {{ product.rental ? '예' : '아니요' }}</p>
        <p><strong>대여 시작일:</strong> {{ formatDate(product.startAt) }}</p>
        <p><strong>대여 종료일:</strong> {{ formatDate(product.endAt) }}</p>
        <p><strong>생성일:</strong> {{ formatDate(product.createdAt) }}</p>
        <p><strong>수정일:</strong> {{ formatDate(product.updatedAt) }}</p>
        <p><strong>관리자 ID:</strong> {{ product.adminId }}</p>
        <p><strong>회원 ID:</strong> {{ product.memberId || '없음' }}</p>
        <p v-if="loading">제품 정보를 로딩 중...</p>
        <p v-if="error" class="error">{{ error }}</p>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const id = route.params.id; // URL에서 제품 ID 가져오기

const product = ref({});
const loading = ref(true);
const error = ref(null);

// 날짜 형식을 변환하는 함수
const formatDate = (dateString) => {
    const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit', timeZoneName: 'short' };
    return new Date(dateString).toLocaleString('ko-KR', options);
};

// API에서 제품 상세 정보를 가져오는 함수
const fetchProductDetail = async () => {
    loading.value = true; // 로딩 시작
    error.value = null; // 이전 에러 초기화

    try {
        const response = await axios.get(`http://localhost:8080/api/v1/product/${id}`);
        console.log("Product detail response:", response.data);

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

            // 2. 데이터가 이미 객체일 경우 바로 처리
            newContents = data.result || [];
        }

        product.value = newContents;
    } catch (err) {
        error.value = '제품 정보를 불러오는 데 실패했습니다.'; // 에러 처리
        console.error('API 호출 에러:', err);
    } finally {
        loading.value = false; // 로딩 종료
    }
};

// 컴포넌트가 마운트될 때 제품 상세 정보 가져오기
onMounted(() => {
    fetchProductDetail();
});
</script>

<style scoped>
.card {
    width: 1189px; /* 카드 너비 설정 */
    height: auto; /* 카드 높이를 자동으로 조정 */
    background-color: #ffffff; /* 카드 배경색 */
    border-radius: 10px; /* 카드 모서리 둥글게 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
    display: flex; /* Flexbox 사용 */
    flex-direction: column; /* 세로 방향으로 배치 */
    padding: 20px; /* 내부 여백 */
}

.product-image {
    width: 100%; /* 이미지 너비 조정 */
    height: auto; /* 자동 높이 조정 */
    border-radius: 10px; /* 이미지 모서리 둥글게 */
}

.product-title {
    font-size: 3rem; /* 제목 크기 조정 */
    margin: 1rem 0; /* 위 아래 여백 추가 */
    font-weight: bold; /* 제목 두껍게 */
}

.product-description {
    font-size: 3rem; /* 설명 크기 조정 */
    color: #666; /* 설명 텍스트 색상 */
    margin-bottom: 1rem; /* 아래 여백 추가 */
}

.error {
    color: red; /* 에러 메시지 색상 */
    margin-top: 1rem; /* 위 여백 추가 */
}

p {
    font-size: 2rem;
}
</style>
