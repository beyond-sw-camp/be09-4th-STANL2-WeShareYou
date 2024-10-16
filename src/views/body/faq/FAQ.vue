<template>
    <div class="faq-container">
      <h2>자주 묻는 질문</h2>
      <div
        v-for="(item, index) in faqItems"
        :key="index"
        class="faq-item"
      >
        <div class="question" @click="toggleAnswer(index)">
          Q: {{ item.question }}
        </div>
        <div v-if="item.isOpen" class="answer">
          <p v-html="item.answer"></p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { reactive } from 'vue'
  
  const faqItems = reactive([
    {
      question: "대여 물품을 어떻게 반납하나요?",
      answer: "대여한 물품은 약정된 반납일에 지정된 반납 장소로 직접 반납하거나,<br> 반납 택배 서비스를 이용하실 수 있습니다. 반납 방법은 대여할 때 선택 가능합니다.",
      isOpen: false,
    },
    {
      question: "대여 가능한 물품의 종류는 무엇인가요?",
      answer: "대여 가능한 물품은 자전거, 카메라, 텐트 등 다양합니다.",
      isOpen: false,
    },
    {
      question: "대여 비용은 어떻게 계산되나요?",
      answer: "대여 비용은 시간 단위로 계산되며, 물품마다 상이합니다.",
      isOpen: false,
    },
    {
      question: "다른 사용자에게 물품을 공유하고 싶어요. 어떻게 하나요?",
      answer: "공유하고 싶은 물품을 앱을 통해 등록할 수 있습니다.",
      isOpen: false,
    },
    {
      question: "물품의 상태는 어떻게 보장되나요?",
      answer: "물품의 상태는 대여 전후로 점검되며, 손상이 있을 경우 보상 절차가 있습니다.",
      isOpen: false,
    },
    {
      question: "대여 가능한 지역은 어디인가요?",
      answer: "대여 가능한 지역은 서울, 부산 등 주요 도시입니다.",
      isOpen: false,
    },
    {
      question: "대여한 물품을 연장하고 싶은데, 가능한가요?",
      answer: "앱에서 연장 신청을 하시면 가능합니다.",
      isOpen: false,
    },
  ])
  
  const toggleAnswer = (index) => {
    faqItems[index].isOpen = !faqItems[index].isOpen
  }
  </script>
  

<style scoped>
  .faq-container h2{
    font-size:3.2rem;
    font-weight: bold;
    color:#212632;
  }
  .faq-container {
    width: 100%;
    margin: 6rem 0 0 12rem;
  }
  
  .faq-item {
    margin-left: 4vw;
    width: 62vw;
    font-size: 3.2rem;
    border-bottom: 1px solid #ccc;
    padding: 1rem 0;
  }
  
  .question {
    font-weight: regular;
    cursor: pointer;
    color: #627086;
  }
  
  .answer {
    padding: 1rem 0;
    color: #34495e;
  }
  </style>