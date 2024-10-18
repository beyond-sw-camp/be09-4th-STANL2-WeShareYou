<template>
    <div class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <div class="modal-icon">
                <img src="@/assets/icon/delete-icon.png" alt="Delete Icon" />
                <p class="delete-font">삭제하시겠습니까?</p>
            </div>
            
            <div class="button-group">
                <button class="confirm-btn" @click="confirmDelete">확인</button>
                <button class="cancel-btn" @click="closeModal">취소</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineEmits } from 'vue';

const emit = defineEmits(['confirm', 'close']);

function confirmDelete() {
    emit('confirm');
}

function closeModal() {
    emit('close');
}
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: white;
    padding: 2rem;
    border-radius: 10px;
    text-align: center;
    width: 40rem;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.modal-icon {
    font-size: 3rem;
    margin-bottom: 2rem;
    margin-top: 4rem;
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-icon img {
    width: 3rem;
    height: 3rem;
    margin-bottom: 1rem;
    margin-right: 1rem;
}

.button-group {
    display: flex;
    justify-content: space-around;
    margin-top: 1.5rem;
}

.confirm-btn {
    background-color: white;
    color: #ff414c;
    border: 1px solid #ff414c;
    border-radius: 5px;
    padding: 1rem 5rem;
    cursor: pointer;
    font-size: 1.5rem;
}

.cancel-btn {
    background-color: white;
    color: #439aff;
    border: 1px solid #439aff;
    border-radius: 5px;
    padding: 0 5rem;
    cursor: pointer;
    font-size: 1.5rem;
}

.delete-font {
    font-size: 2rem;
}
</style>