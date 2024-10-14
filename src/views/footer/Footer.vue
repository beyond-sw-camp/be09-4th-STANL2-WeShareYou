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
    padding: 20px;
    background-color: #F0F1F5;
    bottom: 0;
    width: 100%;
    box-sizing: border-box;
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
