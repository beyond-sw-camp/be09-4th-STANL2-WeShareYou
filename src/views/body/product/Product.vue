<template>
    <div class="container">
        <span class="title">{{ translatedCategory  }}</span>
        <ProductList :category = "category" />
    </div>
</template>

<script setup>
import ProductList from './ProductList.vue';

import { ref, watch } from 'vue';
import { useRoute, RouterView, useRouter } from 'vue-router';

// Vue Router에서 현재 경로 정보 가져오기
const route = useRoute();

// 동적 경로에서 'category' 값을 가져옴
const category = ref(route.params.category || '공유 물품');

const categoryTranslations = {
    NECESSITIES: '생활품',
    KITCHENWARES: '주방용품',
    CLOTHES: '의류',
    TOY: '놀이',
    DEVICE: '전자기기',
    ETC: '기타',
    // 필요에 따라 추가
};

// 한글로 변환된 카테고리
const translatedCategory = ref(categoryTranslations[category.value] || category.value);

// 'category' 값이 변경될 때마다 반응하도록 설정
watch(
    () => route.params.category,
    (newCategory) => {
        category.value = newCategory || '공유 물품';
        translatedCategory.value = categoryTranslations[category.value] || category.value; // 매핑된 한글 카테고리 업데이트
    }
);

</script>


<style scoped>
.container {
    padding: 2rem;
}

.title {
    font-size: 2.4rem;
    font-weight: 600;
    margin-bottom: 2rem;
}
</style>