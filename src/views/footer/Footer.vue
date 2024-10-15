<template>
    <footer>
        <div class="footer-left">
            <FooterLeft />
        </div>
        <div class="footer-right">
            <FooterRight />
        </div>
    </footer>
</template>

<script setup>
import FooterLeft from '../../components/footer/FooterLeft.vue';
import FooterRight from '../../components/footer/FooterRight.vue';
</script>

<style scoped>
footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: #F0F1F5;
    color: #979797;
    bottom: 0;
    width: 100%;
    box-sizing: border-box;
    font-size: 14px;
}

.footer-left, .footer-right {
    flex: 1;
}

.footer-left {
    text-align: left;
    padding-left: 10px;
}

.footer-right {
    text-align: right;
    padding-right: 10px;
}
</style>
