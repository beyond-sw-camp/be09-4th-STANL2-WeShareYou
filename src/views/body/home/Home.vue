<template>
  <div class="Landing">
    <div class="page">
      <img src="@/assets/image/home/LandingPage.png" alt="Banner" class="LandingPage" />
      <div class="overlay1">
        <div class="text-1">{{ translatedText1 }}</div>
        <div class="text-2">{{ originalText2 }}</div>
        <div class="text-3">{{ originalText3 }}</div>
        <div class="text-4">{{ translatedText2 }}</div>
      </div>
      <span class="overlay2">
        <span class="text-1">{{ translatedText3 }}</span>
      </span>
      <span class="overlay3">
        <span class="text-1">{{ translatedText4 }}</span>
      </span>
      <span class="overlay4">
        <span class="text-1">{{ translatedText5 }}</span>
      </span>
      <span class="overlay5">
        <span class="text-1">{{ translatedText6 }}</span>
      </span>
      <span class="overlay6">
        <span class="text-1">{{ translatedText7 }}</span>
      </span>
      <span class="overlay7">
        <span class="text-1">{{ translatedText8 }}</span>
      </span>

    </div>
  </div>
</template>

<script setup>
import { ref, inject, watch  } from 'vue';
import { translateText } from '@/assets/language/deepl'; // DeepL API 모듈

const currentLang = inject('currentLang');
// const setLanguage = inject('changeLanguage');


const originalText1 = '완벽한 여행을 즐기는 방법';
const originalText2 = 'WSU';
const originalText3 = 'We Share You';
const originalText4 = '여행자들을 위한 물품 공유 서비스';
const originalText5 = '게시판';
const originalText6 = '공유물품';
const originalText7 = '채팅';
const originalText8 = '알림';


// 번역된 텍스트 상태
const translatedText1 = ref(originalText1);
const translatedText2 = ref(originalText4);
const translatedText3 = ref(originalText5);
const translatedText4 = ref(originalText6);
const translatedText5 = ref(originalText7);
const translatedText6 = ref(originalText8);


// 언어 변경 시 텍스트를 번역하는 함수
const translateContent = async (lang) => {
  translatedText1.value = await translateText(originalText1, lang);
  translatedText2.value = await translateText(originalText4, lang);
  translatedText3.value = await translateText(originalText5, lang);
  translatedText4.value = await translateText(originalText6, lang);
  translatedText5.value = await translateText(originalText7, lang);
  translatedText6.value = await translateText(originalText8, lang);
};

// 전역 언어 상태를 감시하고, 변경될 때마다 번역 실행
watch(currentLang, (newLang) => {
  translateContent(newLang);
});

// 페이지 초기 로딩 시 현재 언어로 번역 실행
translateContent(currentLang.value);
</script>

<style scoped>
.Landing {
  height: 900vh; /* 전체 화면 높이에 맞추기 */
  overflow: hidden; /* 오버플로우를 숨김 */
  scroll-snap-type: y mandatory; /* 세로 방향으로 스냅, 강제 스냅 */
  scroll-behavior: smooth; /* 부드러운 스크롤 */
}

.page {
  position: relative; /* 자식 요소의 절대 위치를 기준으로 설정 */
  height: 100%; /* 부모 요소와 같은 높이 */
}
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

.LandingPage, .banner-2 {
  width: 100%;
  height: auto;
  object-fit: contain;
  
}

.overlay1 {
  position: absolute;
    bottom: 380vh;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: white;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.7);
}

.overlay2 {
  position: absolute;
    bottom: 120vh;
    margin-left: 8rem;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: center;
    color: white;
    text-shadow: 1px 1px 1px 
}

.overlay3 {
  position: absolute;
    bottom: -5vh;
    margin-left: -55rem;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: end;
    justify-content: center;
    color: white;
    text-shadow: 1px 1px 1px 
}
.overlay4 {
  position: absolute;
    bottom: -188vh;
    margin-left: 3vw;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: start;
    justify-content: center;
    color: #4A87CF;
    text-shadow: 1px 1px 1px 
}
.overlay5 {
  position: absolute;
    bottom: -355vh;
    margin-left: -45vw;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: end;
    justify-content: center;
    color: white;
    text-shadow: 1px 1px 1px 
}


.text-1{
  flex-direction: column;
  margin-top:1rem;
  font-size:4rem;
  font-weight:bold;
}

.text-2{
  margin-top: -5rem;
  font-weight: bold;
  font-size: 16rem;
  text-align: center;
}
.text-3{
  font-weight: bolder;
  margin-left: 20rem;
  margin-top: -5rem;
  font-size: 2rem;
}
.text-4 {
  font-weight: bold;
  font-size: 3.5vw;
  text-align: center;
  margin: 5vh 0;
}
.overlay2 .text-1{
  font-weight: bold;
  font-size: 3.5vw;
  text-align: center;
  margin: 5vh 0;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(50px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.overlay1, .overlay2, .overlay3, .overlay4, .overlay5, .overlay6, .overlay7 {
  animation: fadeIn 2s ease-in-out, slideUp 2s ease-in-out; /* 페이드인과 슬라이드 업 효과 */
}

.text-1, .text-2, .text-3, .text-4 {
  animation: fadeIn 2s ease-in-out; /* 텍스트에 페이드인 효과 */
}


</style>
