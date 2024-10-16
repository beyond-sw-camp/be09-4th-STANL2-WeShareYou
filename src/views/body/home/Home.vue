<template>
    <div class ="Landing">
        <div class="page">
            <img src="../../../assets/image/home/banner1.png" alt="Banner" class="banner-1"/>
            <div class="text-1">
                완벽한 여행을 즐기는 방법
            </div>
            <div class="text-2">
                지금 바로 WSU(We Share You)에서 만나보세요!
            </div>
        </div>
        <div class="page">
            <img src="../../../assets/image/home/banner2.png" alt="Banner" class="banner-2"/>
        </div>
    </div>
</template>

<script setup>
</script>

<style scoped>
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
.text-1{
    font-size: 3.2vw;
    color: #414141;
    text-align: center;
    width: auto; 
    max-width: 100%;
    margin: 5vh 0 0 0;
}
.text-2{
    font-size: 3.2vw;
    color: #000000;
    text-align: center;
    width: auto; 
    max-width: 100%;
    margin: 0 0 3vh 0;
    width: auto; 
    max-width: 80%;
    margin: 5vh 0; 
}
</style>