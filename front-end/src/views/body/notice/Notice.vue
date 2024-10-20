<template>
    <div class="container">
        <span class="title">공지사항</span>
        <NoticeList/>
    </div>
</template>

<script setup>
import NoticeList from './components/NoticeList.vue';

import { ref, watch } from 'vue';
import { useRoute, RouterView, useRouter } from 'vue-router';

// Vue Router에서 현재 경로 정보 가져오기
const route = useRoute();

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