<template>
    <div class="container">
        <div class="title">
            <span class="title">{{ translatedCategory }}</span>

            <!-- ProductList.vue 페이지 && ROLE_ADMIN일 때만 버튼 렌더링 -->
            <button v-if="isAdmin && isProductListPage" class="btn" @click="goToProductRegist">
                상품 등록
            </button>
        </div>
        <RouterView :category="category" :key="$route.fullPath" />
    </div>
</template>

<script setup>
import { ref, watch, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router'; // useRouter 추가

// Vue Router에서 현재 경로 정보 가져오기
const route = useRoute();
const router = useRouter(); // router 사용 선언

// 로컬 스토리지에서 userInfo 가져오기
const userInfo = JSON.parse(localStorage.getItem('userInfo')) || {};

// ROLE_ADMIN인지 확인하는 computed 속성
const isAdmin = computed(() => userInfo.authorities === 'ROLE_ADMIN');

// 현재 페이지가 ProductList인지 확인하는 computed 속성
const isProductListPage = computed(() => route.name === 'ProductList');

// 상품 등록 페이지로 이동하는 함수
function goToProductRegist() {
    router.push('/product/regist'); // 경로 이동
}

// 카테고리 설정 및 번역 로직
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

watch(
    () => route.params.category,
    (newCategory) => {
        category.value = newCategory || '공유 물품';
        translatedCategory.value = categoryTranslations[newCategory] || '공유 물품';
    },
    { immediate: true }
);
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
    text-align: center;
    width: 7%;
    height: 4rem;
    font-size: 1.6rem;
}

</style>