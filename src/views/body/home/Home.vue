<template>
    <div>
        <div class="images">
            <img src="../../../assets/image/home/banner1.png" alt="Banner" class="banner-1"/>
            <img src="../../../assets/image/home/text1.png" alt="Banner" class="text-1"/>
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
}
.banner-1, .banner-2 {
    width: 100%; 
    height: auto;
    object-fit: contain;
}
.text-1{
    width: auto; 
    max-width: 80%;
    margin: 5vh 0; 
}
</style>