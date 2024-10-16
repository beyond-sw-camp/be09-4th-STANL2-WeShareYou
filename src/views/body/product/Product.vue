<template>
    <div class="container">
        <span>{{ category }}</span>
        <ProductList :category = "category" />
    </div>
</template>

<script setup>
import ProductList from './ProductList.vue';
import ProductPost from './ProductPost.vue';

import { ref, watch } from 'vue';
import { useRoute, RouterView, useRouter } from 'vue-router';

// Vue Router에서 현재 경로 정보 가져오기
const route = useRoute();

// 동적 경로에서 'category' 값을 가져옴
const category = ref(route.params.category || '공유 물품');

// 'category' 값이 변경될 때마다 반응하도록 설정
watch(
    () => route.params.category,
    (newCategory) => {
        category.value = newCategory || '공유 물품';
    }
);

</script>


<style scoped>
.container {
    padding: 2rem;
}

h2 {
    font-size: 3.2rem;
    margin-bottom: 2rem;
}
</style>