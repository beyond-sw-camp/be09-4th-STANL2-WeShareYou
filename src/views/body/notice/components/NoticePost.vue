<template>
    <div class="container-regist">
        <div class="wrapper-regist">
            <button @click="submitPost" class="submit-btn">등록</button>
            <button @click="listPost" class="list-btn">취소</button>
        </div>

        <PostRegist @updateTitle="handleTitleUpdate" 
          @updateContent="handleContentUpdate" 
         />
    </div>
</template>

<script setup>
import PostRegist from "@/components/cud/PostRegist.vue";
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const title = ref('');
const content = ref('');

// 자식 컴포넌트로부터 데이터 수신
const handleTitleUpdate = (newTitle) => {
  title.value = newTitle;
};

const handleContentUpdate = (newContent) => {
  content.value = newContent;
};

// 취소 버튼 클릭 시
const listPost = () => {
  console.log('취소 버튼 클릭!');
  router.back();
};

const submitPost = async () => {
    console.log("ddd: " + title.value);
    console.log("content: " + content.value);
    if (!title.value || !content.value) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    const jwtToken = localStorage.getItem('jwtToken');
    const userInfoString = localStorage.getItem('userInfo');

    const userInfo = JSON.parse(userInfoString);

    const id = userInfo?.id;

    try {
        const response = await axios.post('http://localhost:8080/api/v1/notice', 
            {
                title: title.value,
                content: content.value,        
                id: id
            },
            {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            }
            } 
        );
        console.log("id: " + id);
        alert('공지가 등록되었습니다.');
        console.log('응답:', response.data.result);
        router.push('/notice');
    } catch (error) {
        console.error('등록 실패:', error);
        alert('공지가 등록되지 않았습니다.');
    }
};



</script>

<style>
.wrapper-regist {
    display: flex;
    justify-content: end;
    align-items: center;
    margin-right: 6rem;
    margin-top: 3rem;
}

.list-btn {
    color: #FF414C;
    background-color: white;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 0rem 2rem;
    border: 1px solid #FF414C;
    margin-right: 4.5rem;
}

.submit-btn {
    background-color: white;
    color: #439aff;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 0rem 2rem;
    border: 1px solid #439aff !important;
    margin-right: 0.5rem;
}

.category-select {
    text-align: center;
    font-size: 1.5rem;
    height: 3rem;
    margin-right: 1rem;
    border: 1px solid #BDC7D5;
    border-radius: 4px;
    color: #353E4F;
}
</style>