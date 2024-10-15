<template>
    <div class="container">
      <div class="form">
          <input type="text" placeholder="제목을 입력하세요." class="input-title" />
          <textarea placeholder="내용을 입력하세요." class="input-content"></textarea>
        </div>
    </div>
    <div class="buttons">
      <button class="register-button">등록</button>
      <button class="cancel-button">취소</button>
    </div>
  
  </template>
  
  <script setup>
  
  </script>
  
  <style>
  .container{
    width: 70%;
    border: 1px solid #bdc7d5;
    border-radius: 12px;
    overflow:auto;
    display: flex;
    flex-direction: column;
  }
  
  .form {
    display: flex;
    flex-direction: column;
    overflow: auto;
  }
  
  .input-title {
    margin-bottom: 20px;
    padding: 10px;
    font-size: 28px;
    border-width:  0px 0px 1px 0px;
    border-bottom: 1px solid #bdc7d5;
    outline: none;
  }
  
  .input-content {
    height: 300px;
    padding: 10px;
    font-size: 20px;
    border: 0px solid #ddd;
    border-radius: 4px;
    resize: none;
    outline: none;
  }
  
  .buttons {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
  
  .register-button {
    margin-right: 10px;
    padding: 10px 20px;
    border: 1px solid #439aff;
    color: #439aff;
    background-color: transparent;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .cancel-button {
    padding: 10px 20px;
    border: 1px solid #ff414c;
    color: #ff414c;
    background-color: transparent;
    border-radius: 4px;
    cursor: pointer;
  }
  </style>