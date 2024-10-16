<template>
    <div class="product-grid" ref="gridElement">
        <div v-for="item in products" :key="item.id" class="product-card" @click="goToProductDetail(item.id, category)">
            <img v-if="item.imageUrl" :src="item.imageUrl" :alt="item.title" class="product-image" />
            <div class="product-info">
                <div style="margin-bottom: 1rem;">
                    <span class="available">{{ getStatusText(item.status) }}</span>
                    <span class="available1" style="margin-left: 0.5rem;">{{ getIsRentalText(item.rental) }}</span>
                </div>
                <h3>{{ item.title }}</h3>
                <p>{{ item.content }}</p>
            </div>
        </div>

        <p v-if="products.length === 0" class="no-products">
            No products available.
        </p>
        <p v-if="loading" class="loading">Loading more products...</p>
    </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

// 제품 목록과 상태 관리를 위한 ref
const products = ref([]);
const cursorId = ref(''); // cursorId를 빈 문자열로 초기화
const hasNext = ref(true); // 다음 페이지 여부를 서버 응답으로 관리
const loading = ref(false); // 로딩 중인지 여부를 관리

// Vue Router 사용
const router = useRouter();
const route = useRoute(); // 현재 경로 정보 가져오기

// 카테고리 props로 전달 (URL 파라미터에서 가져옴)
const category = ref(route.params.category || 'NECESSITIES');

// API에서 제품 아이템을 가져오는 함수
const fetchProductItems = async (reset = false) => {
    if (loading.value || (!reset && !hasNext.value)) return; // 중복 호출 방지

    loading.value = true; // 로딩 시작

    if (reset) {
        // 초기화할 경우 기존 데이터 및 상태를 리셋
        products.value = [];
        cursorId.value = '';
        hasNext.value = true;
    }

    try {
        const response = await axios.get(
            `http://localhost:8080/api/v1/product/category/${category.value}`,
            {
                params: {
                    cursor: cursorId.value || '', // cursorId가 없으면 빈 문자열로 전송
                    size: 8 // 페이지 당 가져올 아이템 수
                }
            }
        );

        console.log("API Response:", response.data);

        let data = response.data;
        let newContents = []; // 새로 추가할 데이터

        // 1. 응답이 문자열인 경우 처리
        if (typeof data === 'string') {
            console.log("Received JSON as String. Attempting to parse...");
            const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || [];

            if (jsonParts.length > 0) {
                try {
                    const parsed = JSON.parse(jsonParts[0]);
                    console.log("Parsed JSON:", parsed.result.contents);

                    newContents = parsed.result?.contents || [];
                    cursorId.value = parsed.result?.cursorId || ''; // cursorId 업데이트
                    hasNext.value = parsed.result?.hasNext; // hasNext 상태 업데이트
                } catch (error) {
                    console.error("JSON 파싱 실패:", error);
                }
            }
        } else {
            console.log("Parsed Data:", data);

            // 2. 데이터가 이미 객체일 경우 바로 처리
            newContents = data.result?.contents || [];
            cursorId.value = data.result?.cursorId || ''; // cursorId 업데이트
            hasNext.value = data.result?.hasNext; // hasNext 상태 업데이트
        }

        // 3. 기존 제품 목록에 새 데이터를 추가
        products.value = [...products.value, ...newContents];
        console.log("Products after assignment:", products.value);

        if (products.value.length === 0) {
            console.warn("No products found.");
        }
    } catch (error) {
        console.error("API 호출 에러:", error.response?.data || error.message);
    } finally {
        loading.value = false; // 로딩 종료
    }
};

// 스크롤이 페이지 끝에 도달했는지 확인하는 함수
const handleScroll = () => {
    const gridElement = document.querySelector('.product-grid');
    if (gridElement.scrollTop + gridElement.clientHeight >= gridElement.scrollHeight) {
        fetchProductItems(); // 페이지 끝에 도달하면 데이터 요청
    }
};

// 제품 상세 페이지로 이동하는 함수
const goToProductDetail = (id, category) => {
    router.push(`/product/${category}/${id}`);
};

// 상태 텍스트 변환 함수
const getStatusText = (status) => {
    return status === 'RENTAL' ? '대여' : '공유';
};

const getIsRentalText = (rental) => {
    return status === 'false' ? '대여 가능' : '대여 중';
};

// 카테고리가 변경될 때마다 데이터를 새로 불러옴
watch(
    () => route.params.category,
    (newCategory) => {
        category.value = newCategory || 'NECESSITIES';
        fetchProductItems(true); // 데이터 리셋 후 새로 불러오기
    }
);

// 컴포넌트가 마운트될 때 첫 페이지 데이터 가져오기
onMounted(() => {
    fetchProductItems();
    const gridElement = document.querySelector('.product-grid');
    gridElement.addEventListener('scroll', handleScroll); // 스크롤 이벤트 등록
});
</script>

<style scoped>
.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 2rem;
    margin-top: 2rem;
    height: 80vh; /* 전체 화면 높이 */
    overflow-y: auto; /* 스크롤 가능 */
}

@media (min-width: 1200px) {
    .product-grid {
        grid-template-columns: repeat(4, 1fr);
    }
}

.product-card {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    text-align: left;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    height: 33.2rem;
}

.product-image {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    margin-bottom: 1.5rem;
}

.product-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.available {
    background-color: #d9eaff;
    color: #1a73e8;
    border-radius: 12px;
    padding: 5px 10px;
    font-size: 1.2rem;
    margin-bottom: 1rem;
}

.available1 {
    color: #1a73e8;
    border: 1.3px solid #d9eaff;
    border-radius: 12px;
    padding: 5px 10px;
    font-size: 1.2rem;
    margin-bottom: 1rem;
}

h3 {
    font-size: 1.8rem;
    margin: 0 0 5px 0;
}

p {
    color: #666;
    font-size: 1.4rem;
    margin: 0;
}

.loading {
    text-align: center;
    margin-top: 1rem;
    color: #666;
}

.no-products {
    text-align: center;
    margin-top: 2rem;
    font-size: 1.8rem;
    color: #888;
}
</style>
