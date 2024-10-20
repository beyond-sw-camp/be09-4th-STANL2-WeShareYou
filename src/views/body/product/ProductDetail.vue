<template>
    <div class="container-detail">
        <div class="left-column">
            <img v-if="product.imageUrl" class="product-image" :src="product.imageUrl" alt="Product Image" />
            <p class="product-description">{{ product.content }}</p>
        </div>

        <div class="right-column">
            <h2 class="product-title">{{ product.title }}</h2>
            <span>{{ product.category }}</span>

            <form @submit.prevent="submitProduct">
                <div class="form-group">
                    <label for="rentalDate">대여일시:</label>
                    <div class="input-group">
                        <input v-model="startDate" type="text" id="rentalDate" placeholder="YYYY.MM.DD" class="date-input" />
                        <select v-model="startTime" class="time-select">
                            <option v-for="hour in hours" :key="hour" :value="hour">{{ hour }}</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="returnDate">반납일시:</label>
                    <div class="input-group">
                        <input v-model="endDate" type="text" id="returnDate" placeholder="YYYY.MM.DD" class="date-input" />
                        <select v-model="endTime" class="time-select">
                            <option v-for="hour in hours" :key="hour" :value="hour">{{ hour }}</option>
                        </select>
                    </div>
                </div>

                <button 
                    type="submit" 
                    class="submit-btn" 
                    :disabled="product.rental"
                    :class="{ disabled: product.rental }">
                    {{ product.rental ? "대여완료" : "빌리기" }}
                </button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const id = route.params.id; // URL에서 제품 ID 가져오기
const category = route.params.category;

const product = ref({});
const loading = ref(true);
const error = ref(null);

const startDate = ref('');
const startTime = ref('');
const endDate = ref('');
const endTime = ref('');

const hours = Array.from({ length: 24 }, (_, i) => i.toString().padStart(2, '0') + ':00');

const submitProduct = async () => {
    if (!startDate.value || !startTime.value || !endDate.value || !endTime.value) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    const jwtToken = localStorage.getItem('jwtToken');

    // 날짜와 시간을 결합하여 Timestamp로 변환
    const startAt = new Date(`${startDate.value} ${startTime.value}`).toISOString();
    const endAt = new Date(`${endDate.value} ${endTime.value}`).toISOString();

    const payload = {
        productId: id,
        startAt,
        endAt
    };

    try {
        const response = await axios.put(`http://localhost:8080/api/v1/product/share/${id}`, payload, {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            }
        });
        alert('대여가 완료되었습니다.');

        // 상세 조회를 다시 불러와서 상태 업데이트
        await fetchProductDetail();

        router.push(`/product/${category}/${id}`);
    } catch (error) {
        console.error('등록 실패:', error);
        alert('이미 대여된 상품입니다.');
    }
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
.container-detail {
    display: flex;
    width: 100%;
    max-width: 1000px;
    margin: 0 auto;
    padding: 2.5rem;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.left-column {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding-right: 40px;
}

.product-image {
    width: 100%;
    height: auto;
    max-width: 500px;
    border-radius: 10px;
    margin-bottom: 20px;
}

.product-description {
    font-size: 1.5rem;
    color: #333;
}

.right-column {
    flex: 1;
    display: flex;
    flex-direction: column;
}

.product-title {
    border-bottom: 1px solid rgba(189, 199, 213, 1);
    font-size: 2.5rem;
    margin-bottom: 20px;
    color: #333;
}

form {
    display: flex;
    flex-direction: column;
}

.form-group {
    margin-bottom: 1rem;
    margin-top: 2rem;
}

label {
    font-size: 1.6rem;
    margin-bottom: 8px;
    display: block;
    color: #666;
}

.input-group {
    display: flex;
    align-items: center;
}

.date-input {
    flex: 2;
    padding: 1rem;
    font-size: 1.6rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-right: 0.5rem;
}

.time-select {
    flex: 1;
    padding: 1rem;
    font-size: 1.6rem;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.quantity-select {
    padding: 1rem;
    font-size: 1.6rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    width: 100%;
}

.submit-btn {
    font-size: 1.8rem;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-top: 2rem;
}
.submit-btn:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}

.submit-btn:hover {
    background-color: #0056b3;
}

.submit-btn.disabled:hover {
    background-color: #cccccc; /* 비활성화된 버튼에 hover 효과 제거 */
}
</style>
