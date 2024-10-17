<template>
  <div class="container-regist">
      <div class="wrapper-regist">
          <select v-model="category1" class="category-select">
              <option value="NECESSITIES">생활품</option>
              <option value="KITCHENWARES">주방용품</option>
              <option value="CLOTHES">의류</option>
              <option value="TOY">놀이</option>
              <option value="DEVICE">전자기기</option>
              <option value="ETC">기타</option>
          </select>
          <button @click="listPost" class="list-btn">취소</button>
          <button @click="submitPost" class="submit-btn">수정</button>
      </div>

      <PostModify 
          :initialTitle="product.title" 
          :initialContent="product.content" 
          :initialImageUrl="product.imageUrl" 
          @updateTitle="handleTitleUpdate" 
          @updateContent="handleContentUpdate" 
          @imageUploaded="handleImageUpload" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import PostModify from '@/components/cud/PostModify.vue';

const route = useRoute();
const router = useRouter();
const title = ref('');
const content = ref('');
const imageFile = ref(null);
const category1 = ref('NECESSITIES');
const product = ref({});
const loading = ref(true);
const error = ref(null);
const id = ref(route.params.id); // URL에서 ID 가져오기

// API에서 제품 상세 정보를 가져오는 함수
const fetchProductModify = async () => {
    loading.value = true; // 로딩 시작
    error.value = null; // 이전 에러 초기화

    try {
        const response = await axios.get(`http://localhost:8080/api/v1/product/${id.value}`);

        console.log("Product detail response:", response.data);

        let data = response.data;

        if (typeof data === 'string') {
            const jsonParts = data.match(/\{.*?\}(?=\{|\s*$)/g) || [];

            if (jsonParts.length > 0) {
                try {
                    const parsed = JSON.parse(jsonParts[0]);
                    product.value = parsed.result; // 제품 정보를 저장
                } catch (error) {
                    console.error("JSON 파싱 실패:", error);
                }
            }
        } else {
            product.value = data.result; // 데이터가 객체일 경우 처리
        }
    } catch (err) {
        error.value = '제품 정보를 불러오는 데 실패했습니다.'; // 에러 처리
        console.error('API 호출 에러:', err);
    } finally {
        loading.value = false; // 로딩 종료
    }
};

// 컴포넌트가 마운트될 때 제품 상세 정보 가져오기
onMounted(() => {
    fetchProductModify();
});

// 자식 컴포넌트로부터 데이터 수신
const handleTitleUpdate = (newTitle) => {
  title.value = newTitle;
};

const handleContentUpdate = (newContent) => {
  content.value = newContent;
};

const handleImageUpload = (file) => {
  imageFile.value = file;
};

console.log("title: " + title.value);
console.log("content: " + content.value);
console.log("imageFile: " + imageFile.value);

// 취소 버튼 클릭 시
const listPost = () => {
    console.log('취소 버튼 클릭!');
    // 필요에 따라 다른 로직 추가 가능
};

// 서버로 데이터 전송
const submitPost = async () => {
    if (!title.value || !content.value || !imageFile.value || !category1.value) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    const formData = new FormData();
    formData.append('file', imageFile.value);
    formData.append(
        'vo',
        new Blob(
            [
                JSON.stringify({
                    title: title.value,
                    content: content.value,
                    category: category1.value,
                    status: "RENTAL",
                }),
            ],
            { type: 'application/json' }
        )
    );

    const jwtToken = localStorage.getItem('jwtToken');

    try {
        const response = await axios.put('http://localhost:8080/api/v1/product', 
        formData, 
        {
            headers: {
                'Content-Type': 'multipart/form-data',
                Authorization: `Bearer ${jwtToken}`,
            },
        });
        alert('상품이 수정되었습니다.');
        console.log('응답:', response.data.result);
        router.push(`/product/${category1.value}`);
    } catch (error) {
        console.error('수정 실패:', error);
        alert('상품이 수정되지 않았습니다.');
    }
};
</script>



<style scoped>
.wrapper-regist {
    display: flex;
    justify-content: end;
    align-items: center;
    margin-right: 6rem;
    margin-top: 3rem;
}

.list-btn {
    color: #439aff;
    background-color: white;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 1rem 2rem;
    border: 1px solid #439aff;
    margin-right: 1rem;
}

.submit-btn {
    background-color: #439aff;
    color: white;
    border-radius: 1rem;
    cursor: pointer;
    text-align: center;
    height: 4rem;
    font-size: 1.6rem;
    padding: 0.5rem 2rem;
    border: none !important;
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