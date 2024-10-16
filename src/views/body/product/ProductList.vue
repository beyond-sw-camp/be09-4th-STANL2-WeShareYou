<template>
    <div class="product-grid">
        <div v-for="item in products" :key="item.id" class="product-card" @click="goToProductDetail(item.id)">
            <img v-if="item.imageUrl" :src="item.imageUrl" :alt="item.title" class="product-image" />
            <div class="product-info">
                <span class="available">{{ getStatusText(item.status) }}</span>
                <h3>{{ item.title }}</h3>
                <p>{{ item.content }}</p>
            </div>
        </div>
        <p v-if="products.length === 0" class="no-products">No products available.</p>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import axios from 'axios';

const router = useRouter();
const products = ref([]); // 제품 목록을 저장할 ref

const props = defineProps({
    category: {
        type: String,
        default: 'NECESSITIES',
    },
});

// API에서 제품 아이템을 가져오는 함수
const fetchProductItems = async () => {
    try {
        console.log("Selected Category: " + props.category);
        const response = await axios.get(`http://localhost:8080/api/v1/product/category/${props.category}?cursor=&size=3`);

        console.log("API Response:", response.data);
        console.log("API Response:", response);
        const result = response.data;

        if (result.success) {
            products.value = result.result.contents || [];
        } else {
            console.error("데이터 가져오기 실패:", result.error || "알 수 없는 오류");
        }
    } catch (error) {
        console.error("에러 발생:", error);
    }
};

// 제품 상세 페이지로 이동하는 함수
const goToProductDetail = (id) => {
    router.push(`/product/${id}`);
};

// 상태 텍스트 변환 함수
const getStatusText = (status) => {
    return status === 'RENTAL' ? '대여 중' : '사용 가능';
};

// 컴포넌트가 마운트될 때 fetchProductItems 함수 호출
onMounted(() => {
    fetchProductItems();
});
</script>

<style scoped>
.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
    margin: 0 auto;
}

@media (min-width: 1200px) {
    .product-grid {
        grid-template-columns: repeat(4, 1fr);
        /* 4개씩 보여주기 */
    }
}

.product-card {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    text-align: left;
    /* 텍스트 왼쪽 정렬 */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
}

.product-image {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    margin-bottom: 15px;
}

.product-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    /* 요소들 왼쪽 정렬 */
}

.available {
    background-color: #d9eaff;
    color: #1a73e8;
    border-radius: 12px;
    padding: 5px 10px;
    font-size: 12px;
    margin-bottom: 10px;
}

h3 {
    font-size: 18px;
    margin: 0 0 5px 0;
    /* 아래쪽 여백 추가 */
}

p {
    color: #666;
    font-size: 14px;
    margin: 0;
    /* 불필요한 여백 제거 */
}
</style>