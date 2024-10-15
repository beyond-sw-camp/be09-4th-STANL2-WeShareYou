<template>
    <div class="product-grid">
        <div class="product-card" v-for="item in products" :key="item.id" @click="goToProductDetail(item.id)">
            <img :src="item.imageUrl" :alt="item.title" class="product-image" />
            <div class="product-info">
                <span class="available">{{item.status}}</span>
                <h3>{{ item.title }}</h3>
                <p>{{ item.content }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
    // import {userRouter} from 'vue-router';
    // import {ref, onMounted} from 'vue';
    // import axios from 'axios';

    // const router = userRouter();
    // const products = ref([]);

    // const fetchProductItems = async () => {
    //     try {
    //         const response = await axios.get('http://localhost:8080/api/v1/product');
    //         if(!response.ok) throw new Error('에러 발생');
    //         const data = await response.json();
    //         products.value = data;
    //     } catch (error) {
    //         console.log(error);
    //     }
    // }

    // const goToProductDetail = (id) => {
    //     router.push(`/product/${id}`);
    // }


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