<template>
    <div class="faq-container">
      <h2>자주 묻는 질문</h2>
      <div
        v-for="(item, index) in faqItems" 
        :key="index"
        class="faq-item"
      >
        <div class="question" @click="toggleAnswer(index)">
          <span>Q: {{ item.question }}</span>
          <span class="dropdown-arrow" v-html="item.isOpen ? '&#9650;' : '&#9660;'"></span>
        </div>
        <div v-if="item.isOpen" class="answer">
          <div class="tags">
            <rounded-button 
              v-for="(tag, tagIndex) in item.tags" 
              :key="tagIndex" 
              :text="tag"
            />
          </div>
          <p v-html="item.answer"></p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  import RoundedButton from '@/components/faq/tags.vue';
  
  const faqItems = reactive([
    {
      question: "대여 물품을 어떻게 반납하나요?",
      answer: "대여한 물품은 약정된 반납일에 지정된 장소로 직접 반납하거나, 반납 택배 서비스를 이용하실 수 있습니다.<br> 반납 방식은 대여 시 선택 가능합니다. 직접 반납이 어려울 경우, 택배 반납을 통해 간편하게 반납할 수 있습니다.",
      tags: ["대여", "물품"],
      isOpen: false,
    },
    {
      question: "대여 가능한 물품의 종류는 무엇인가요?",
      answer: "자전거, 카메라, 텐트 등 다양한 레저용품부터 가전제품까지 대여 가능합니다.<br> 물품 목록은 대여 플랫폼에서 확인할 수 있으며, 각 지역마다 대여 가능한 품목이 다를 수 있습니다.",
      tags: ["대여", "물품"],
      isOpen: false,
    },
    {
      question: "대여 비용은 어떻게 계산되나요?",
      answer: "대여 비용은 시간 또는 일 단위로 계산되며, 물품마다 다릅니다.<br> 장기 대여 시 할인이 제공될 수 있으며, 대여 전에 정확한 비용을 확인할 수 있습니다.",
      tags: ["대여", "물품"],
      isOpen: false,
    },
    {
      question: "다른 사용자에게 물품을 공유하고 싶어요. 어떻게 하나요?",
      answer: "앱을 통해 공유하고 싶은 물품을 등록할 수 있습니다.<br> 물품 상태, 대여 가능 기간 등을 기재한 후 등록하면 다른 사용자가 대여할 수 있습니다.",
      tags: ["물품"],
      isOpen: false,
    },
    {
      question: "물품의 상태는 어떻게 보장되나요?",
      answer: "물품은 대여 전후로 점검됩니다. 손상이 발생할 경우 보증금을 통해 보상 절차가 이루어지며, 물품 상태는 대여 플랫폼에서 관리합니다.",
      tags: ["물품"],
      isOpen: false,
    },
    {
      question: "대여 가능한 지역은 어디인가요?",
      answer: "대여 가능한 지역은 서울, 부산 등 주요 도시입니다.<br> 지역별로 대여 가능한 물품이 다를 수 있으며, 대여 플랫폼에서 해당 지역의 정보를 확인할 수 있습니다..",
      tags: ["대여", "물품","지역"],
      isOpen: false,
    },
    {
      question: "대여한 물품을 연장하고 싶은데, 가능한가요?",
      answer: "전화를 통해 대여 연장을 신청할 수 있습니다. 연장 가능한 기간과 비용은 대여 시점에 따라 달라질 수 있습니다.",
      tags:["대여"],
      isOpen: false,
    },
  ])
  
  const toggleAnswer = (index) => {
    faqItems[index].isOpen = !faqItems[index].isOpen
  }
  </script>
  

<style scoped>
  .faq-container h2{
    margin-bottom: 3rem;
    font-size:3.2rem;
    font-weight: bold;
    color:#212632;
  }
  .faq-container {
    margin: 6rem 0 0 15rem;
  }
  
  .faq-item {
    margin-left: 4vw;
    width: 62vw;
    font-size: 3.2rem;
    border-bottom: 1px solid #ccc;
    padding-top: 1rem;
    padding-bottom: 0.5rem;
  }
  
  .question {
    font-size: 4vh;
    cursor: pointer;
    color: #627086;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .answer {
    color: #34495e;
  }

  .tags {
    margin-bottom: 2.5rem;
    display: flex;
    gap: 0.5rem; /* 버튼들 간 간격 */
  }

  .rounded-button {
    margin-top: 1rem;
    color: #627086;
    font-size: 1.3rem;
    white-space: nowrap;
    background: #D9D9D9;
    height: 2rem;
    width: 5rem;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 1.2rem;
  }

  .p{
    font-size: 1.25rem;
  }

  .dropdown-arrow {
    color:#BDC7D5;
    font-size: 1.2rem;
    margin-left: auto; 
    transition: transform 0.3s ease;
  }
</style>