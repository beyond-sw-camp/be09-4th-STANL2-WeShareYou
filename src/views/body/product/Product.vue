<template>
    <div class="container">
        <div class="title">
            <span class="title">{{ translatedCategory }}</span>

            <button v-if="isAdmin && isProductListPage" class="btn" @click="goToProductRegist">
                상품 등록
            </button>

            <div v-if="isAdmin && isProductDetailPage" class="update-wrapper">
                <button class="btn-u" @click="goToProductModify">수정</button>
                <button class="btn-d" @click="showDeleteModal">삭제</button>
                <button class="btn-u" @click="goToProductList">목록</button>
            </div>
        </div>

        <RouterView :category="category" :key="$route.fullPath" />

        <ProductRemove v-if="isDeleteModalVisible" @confirm="handleDelete" @close="hideDeleteModal" />
    </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import ProductRemove from '../../../components/cud/PostRemove.vue';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const userInfo = JSON.parse(localStorage.getItem('userInfo')) || {};
const id1 = route.params.id;

const isAdmin = computed(() => userInfo.authorities === 'ROLE_ADMIN');
const isProductListPage = computed(() => route.name === 'ProductList');
const isProductDetailPage = computed(() => route.name === 'ProductDetail');

const isDeleteModalVisible = ref(false);
const category = ref(route.params.category || '공유 물품');
const translatedCategory = ref(''); // 초기화된 번역 카테고리

const categoryTranslations = {
    NECESSITIES: '생활품',
    KITCHENWARES: '주방용품',
    CLOTHES: '의류',
    TOY: '놀이',
    DEVICE: '전자기기',
    ETC: '기타',
};

async function goToProductRegist() {
    router.push({
        path: '/product/regist',
        query: { category: category.value },
    });
}

function showDeleteModal() {
    isDeleteModalVisible.value = true;
}

function hideDeleteModal() {
    isDeleteModalVisible.value = false;
}

async function handleDelete() {
    const id = route.params.id;
    const jwtToken = localStorage.getItem('jwtToken');

    try {
        const response = await axios.delete('http://localhost:8080/api/v1/product', {
            headers: { Authorization: `Bearer ${jwtToken}` },
            data: { id },
        });
        console.log('삭제 성공:', response.data);
        router.push(`/product/${category.value}`);
    } catch (error) {
        console.error('삭제 실패:', error);
        alert('삭제하는 중 오류가 발생했습니다. 다시 시도해주세요.');
    } finally {
        hideDeleteModal();
    }
}
</script>

<style scoped>
.container {
    padding: 2rem;
}

.title {
    font-size: 2.4rem;
    font-weight: 600;
    margin-bottom: 1rem;
    display: flex;
    justify-content: space-between;
}

.btn {
    background-color: #439aff;
    color: white;
    border-radius: 1rem;
    cursor: pointer;
    width: 7%;
    height: 4rem;
    font-size: 1.6rem;
}

.btn-d {
    background-color: white;
    color: #FF414C;
    border-radius: 1rem;
    cursor: pointer;
    height: 3.5rem;
    font-size: 1.3rem;
    border: 1px solid #FF414C;
    margin-left: 1rem;
    padding: 0 2rem;
}

.btn-u {
    background-color: white;
    color: #439aff;
    border-radius: 1rem;
    cursor: pointer;
    height: 3.5rem;
    font-size: 1.3rem;
    border: 1px solid #439aff;
    margin-left: 1rem;
    padding: 0 2rem;
}

.update-wrapper {
    display: flex;
    justify-content: end;
    margin-right: 14rem;
}
</style>
