<template>
  <div class="Landing">
    <div class="page">
      <img src="@/assets/image/home/banner1.png" alt="Banner" class="banner-1" />
      <div class="text-1">
        {{ translatedText1 }}
      </div>
      <div class="text-2">
        {{ translatedText2 }}
      </div>
    </div>

    <div class="page">
      <img src="@/assets/image/home/banner2.png" alt="Banner" class="banner-2" />
    </div>
  </div>
</template>

<script setup>
import { ref, inject, watch  } from 'vue';
import { useRouter } from 'vue-router';
import { translateText } from '@/assets/language/deepl'; // DeepL API 모듈

const currentLang = inject('currentLang');
// const setLanguage = inject('changeLanguage');


const originalText1 = '완벽한 여행을 즐기는 방법';
const originalText2 = '지금 바로 WSU(We Share You)에서 만나보세요!';

// 번역된 텍스트 상태
const translatedText1 = ref(originalText1);
const translatedText2 = ref(originalText2);
// const router = useRouter();

// 언어 변경 시 텍스트를 번역하는 함수
const translateContent = async (lang) => {
  translatedText1.value = await translateText(originalText1, lang);
  translatedText2.value = await translateText(originalText2, lang);
};

// 전역 언어 상태를 감시하고, 변경될 때마다 번역 실행
watch(currentLang, (newLang) => {
  translateContent(newLang);
});

// 페이지 초기 로딩 시 현재 언어로 번역 실행
translateContent(currentLang.value);
</script>

<style scoped>
.language-switcher {
  margin: 2rem;
  display: flex;
  justify-content: center;
}

button {
  margin: 0.5rem;
  padding: 0.5rem 1rem;
  font-size: 1.2rem;
  cursor: pointer;
}

.images {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2vh;
}

.banner-1, .banner-2 {
  width: 100%;
  height: auto;
  object-fit: contain;
}

.text-1 {
  font-size: 3.2vw;
  color: #414141;
  text-align: center;
  width: auto; 
  max-width: 100%;
  margin: 5vh 0 0 0;
}

.text-2 {
  font-size: 3.2vw;
  color: #000000;
  text-align: center;
  width: auto; 
  max-width: 100%;
  margin: 0 0 3vh 0;
}
</style>
