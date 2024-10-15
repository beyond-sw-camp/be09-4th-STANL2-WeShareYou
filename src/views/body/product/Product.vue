<template>
    <div class="container">
        <h2>주방용품</h2>
        <ProductList />
    </div>
</template>

<script setup>
import ProductList from './ProductList.vue';

</script>

<style scoped>
.container {
    padding: 20px;
}

h2 {
    font-size: 24px;
    margin-bottom: 20px;
}
</style>