<template>
    <div class="container">
        <span class="title">{{ translatedCategory }}</span>
        <RouterView :category="category" />
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';

// Vue Router에서 현재 경로 정보 가져오기
const route = useRoute();

// 카테고리 ref로 설정
const category = ref(route.params.category || '공유 물품');

// 카테고리 한글 변환 객체
const categoryTranslations = {
    NECESSITIES: '생활품',
    KITCHENWARES: '주방용품',
    CLOTHES: '의류',
    TOY: '놀이',
    DEVICE: '전자기기',
    ETC: '기타',
};

// 한글로 변환된 카테고리 초기화
const translatedCategory = ref(categoryTranslations[category.value] || '공유 물품');

// 경로의 'category' 값이 변경될 때마다 반응하여 업데이트
watch(
    () => route.params.category,
    (newCategory) => {
        category.value = newCategory || '공유 물품'; // 카테고리가 없으면 디폴트 설정
        translatedCategory.value = categoryTranslations[newCategory] || '공유 물품'; // 번역 적용
    },
    { immediate: true } // 초기 로딩 시에도 값 적용
);
</script>


<style scoped>
.container {
    padding: 2rem;
}

.title {
    font-size: 2.4rem;
    font-weight: 600;
    margin-bottom: 3rem;
}
</style>