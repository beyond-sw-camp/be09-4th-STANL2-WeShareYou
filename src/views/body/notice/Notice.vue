<template>
    <div>
        <h2>공지사항</h2>
        <div class="notice-list">
            <NoticeList />
        </div>
    </div>
</template>

<script setup>
import NoticePost from "./components/NoticePost.vue"
import NoticeModify from "./components/NoticeModify.vue"
import NoticeList from "./components/NoticeList.vue"
import NoticeDetail from "./components/NoticeDetail.vue"
import { useRouter, RouterView} from 'vue-router';

</script>

<style scoped>

</style>