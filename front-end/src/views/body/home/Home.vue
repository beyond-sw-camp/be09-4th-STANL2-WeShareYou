<template>
  <div class="Landing">
    <div class="page">
      <img src="@/assets/image/home/LandingPage.png" alt="Banner" class="LandingPage" />
    </div>
  </div>
</template>

<script setup>

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

</style>
