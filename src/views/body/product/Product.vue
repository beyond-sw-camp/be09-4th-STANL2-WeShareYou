<template>
    <div class="container">
        <div class="title">
            <span class="title">{{ translatedCategory }}</span>

            <!-- ProductList.vue 페이지 && ROLE_ADMIN일 때만 버튼 렌더링 -->
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

        <!-- 삭제 모달 -->
        <ProductRemove v-if="isDeleteModalVisible" @confirm="handleDelete" @close="hideDeleteModal" />
    </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import ProductRemove from '../../../components/cud/PostRemove.vue';
import axios from 'axios';
import { translateText } from '@/assets/language/deepl.js'; // DeepL 번역 함수

const route = useRoute();
const router = useRouter();
const userInfo = JSON.parse(localStorage.getItem('userInfo')) || {};
const id1 = route.params.id;

const isAdmin = computed(() => userInfo.authorities === 'ROLE_ADMIN');
const isProductListPage = computed(() => route.name === 'ProductList');
const isProductDetailPage = computed(() => route.name === 'ProductDetail');

const isDeleteModalVisible = ref(false);

function goToProductRegist() {
    router.push({ path: '/product/regist', query: { category: category.value } });
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
            data: { id: id },
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

const category = ref(route.params.category || '공유 물품');
const categoryTranslations = {
    NECESSITIES: '생활품',
    KITCHENWARES: '주방용품',
    CLOTHES: '의류',
    TOY: '놀이',
    DEVICE: '전자기기',
    ETC: '기타',
};

const translatedCategory = ref(categoryTranslations[category.value] || '공유 물품');

// 언어 변경 시 카테고리 번역 업데이트
// watch(
//     () => route.params.category,
//     async (newCategory) => {
//         category.value = newCategory || '공유 물품';
//         translatedCategory.value = await translateText(
//             categoryTranslations[newCategory] || '공유 물품',
//             currentLang.value
//         );
//     },
//     { immediate: true }
// );
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
    border: 1px solid #FF414C;
    margin-left: 1rem;
}

.btn-u {
    background-color: white;
    color: #439aff;
    border-radius: 1rem;
    cursor: pointer;
    border: 1px solid #439aff;
    margin-left: 1rem;
}

.update-wrapper {
    display: flex;
    justify-content: end;
    margin-right: 14rem;
}
</style>
