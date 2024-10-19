<script setup>
import { ref, provide, onMounted, onBeforeUnmount } from 'vue';
// import { RouterView } from 'vue-router';
import { useRouter } from 'vue-router';
import Header from './views/header/Header.vue';
import Footer from './views/footer/Footer.vue';
import Navigation from './views/navigation/Navigation.vue';

const currentLang = ref('KO'); // 기본값은 한국어

// 언어 변경 함수
const changeLanguage = (lang) => {
  currentLang.value = lang;
};

// provide로 전역 상태를 자식 컴포넌트들에게 전달
provide('currentLang', currentLang);
provide('changeLanguage', changeLanguage);

// 알림 상태와 SSE 연결 객체
const alarms = ref([]); // 알림 목록 상태
const eventSource = ref(null); // SSE 연결 객체
const router = useRouter(); // Vue Router 사용

// SSE 연결 함수
const connectToSSE = (memberId) => {
  const jwtToken = localStorage.getItem('jwtToken');

  // SSE 연결 생성
  eventSource.value = new EventSource(
    `http://localhost:8080/api/v1/subscribe?memberId=${memberId}`
  );

  // 이벤트 수신 시 알림 추가
  eventSource.value.onmessage = (event) => {
    const newAlarm = JSON.parse(event.data); // 알림 데이터 파싱
    console.log("New Alarm Received:", newAlarm);

    // 알림 추가
    alarms.value = [...alarms.value, newAlarm];
  };

  // 오류 시 연결 종료
  eventSource.value.onerror = (error) => {
    console.error("SSE 연결 오류:", error);
    eventSource.value.close();
  };
};

// SSE 연결 해제 함수
const disconnectFromSSE = () => {
  if (eventSource.value) {
    eventSource.value.close(); // 연결 종료
    console.log("SSE 연결이 종료되었습니다.");
  }
};

// 로그인 상태 확인 후 SSE 연결
const checkLoginStatus = () => {
  const token = localStorage.getItem('jwtToken');
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));

  if (token && userInfo) {
    connectToSSE(userInfo.id); // 로그인된 사용자 ID로 SSE 연결
  } else {
    disconnectFromSSE(); // 로그인 안 됐을 경우 SSE 해제
  }
};

// 컴포넌트 마운트 시 로그인 상태 확인 및 SSE 연결 설정
onMounted(() => {
  checkLoginStatus(); // 로그인 상태 확인 및 SSE 연결
});

// 컴포넌트 언마운트 시 SSE 연결 해제
onBeforeUnmount(() => {
  disconnectFromSSE();
});
</script>

<template>
  <div id="app">
    <header>
      <div class="wrapper">
        <Header />
      </div>
    </header>

    <Navigation />

    <main class="content">
      <RouterView />
    </main>

    <footer class="footer">
      <Footer />
    </footer>
  </div>
</template>

<style scoped>
#app {
  width: 100%;
  display: flex;
  flex-direction: column;
  height: 100vh;
}
header {
  line-height: 1.5;
}
.content {
  flex: 1;
  background-color: #F9F9F9;
}
</style>
