<script setup>
import { RouterView } from 'vue-router';
import Header from './views/header/Header.vue';
import Footer from './views/footer/Footer.vue';
import Navigation from './views/navigation/Navigation.vue';

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
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
header {
  line-height: 1.5;
}
.content {
  flex: 1;
}
</style>
