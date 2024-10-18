<template>
    <div>
        <h1>Server Response</h1>
        <div v-if="data">
            <p><strong>IP:</strong> {{ data.ip }}</p>
        </div>
        <div v-else>
            <p>Loading...</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const data = ref(null)  // 서버 응답 데이터를 담을 변수

// 서버에서 데이터 가져오기
const fetchData = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/v1/ip')  // 올바른 경로 사용
        data.value = response.data
    } catch (error) {
        console.error('Error fetching data:', error)
    }
}

// 컴포넌트가 마운트될 때 데이터 가져오기
onMounted(fetchData)
</script>

<style scoped>
/* 스타일 정의 */
h1 {
    margin-bottom: 20px;
}
p {
    font-size: 1.2em;
}
</style>
