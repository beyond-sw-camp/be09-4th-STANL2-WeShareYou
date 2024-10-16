<template>
    <span class="rounded-button">
        {{ text }}
    </span>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  text: {
    type: String,
    required: true,
  },
});
</script>

<style scoped>
    #rounded-button {
        display: inline-block;
        padding: 10px 20px;
        border-radius: 20px;
        background-color: #e0e0e0;
        color: #5f6c80;
        font-size: 2px;
        text-align: center;
        margin: 10px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        cursor: pointer;
}
</style>