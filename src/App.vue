<script setup>
import { ref, provide } from 'vue';
// import { RouterView } from 'vue-router';
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
}
</style>
