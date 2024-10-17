<template>
	<div id="footer-right">
		<p v-html="translatedFooterText"></p>
	</div>
</template>

<script setup>
import { ref, inject, watch} from 'vue';
import { translateText } from '@/assets/language/deepl';

const currentLang = inject('currentLang');
console.log(currentLang);

const translatedFooterText = ref('');

const originalFooterText = `
  대표 (주)STANL_2  사업자 등록 번호 120-89-69321<br>
  통신 판매업 신고번호 제2024-서울신대방-8631호<br>
  주소 서울특별시 동작구 보라매로 87 호스팅사업자 (주)STANL_2<br>
  고객센터 0507-1354-8120 이메일 stanl2e2@naver.com<br>
  WSU @ 2024. All rights reserved.
`;

const translateFooter = async (lang) => {
  translatedFooterText.value = await translateText(originalFooterText, lang);
};

watch(currentLang, (newLang) => {
	translateFooter(newLang);
});

translateFooter(currentLang.value);
</script>

<style scoped>
    .footer-right {
		text-align: right;
		padding-right: 10px;
  		line-height: 1.6; /* Adjusts line spacing for better readability */
	}
	
	p {
		margin-bottom: 0 !important;
		font-size: 1.7vh
	}
</style>