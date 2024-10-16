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
        padding: 1rem 2rem;
        border-radius: 2rem;
        background-color: #e0e0e0;
        color: #5f6c80;
        font-size: 0.2rem;
        text-align: center;
        margin: 1rem;
        box-shadow: 0 0.2rem 0.5rem rgba(0, 0, 0, 0.1);
        cursor: pointer;
}
</style>