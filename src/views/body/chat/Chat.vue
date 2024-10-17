<template>
    <div class="chat-container">
      <!-- 왼쪽 채팅방 목록 -->
      <div class="chat-list">
        <div class="input-group">
          <input
            v-model="receiver"
            type="text"
            placeholder="채팅방 이름 입력"
            @keyup.enter="createRoom"
          />
          <button @click="createRoom">채팅방 생성</button>
        </div>
  
        <ul v-if="rooms && rooms.length > 0">
          <li v-for="room in rooms" :key="room.roomId">
            <p
              class="room-item"
              @click="setSelectedRoom(room)"
            >
              {{ room.sender === user.name ? room.receiver : room.sender }}
            </p>
          </li>
        </ul>
        <p v-else class="no-rooms">No chat rooms available.</p>
      </div>
  
      <!-- 오른쪽 채팅방 내용 -->
      <div class="chat-room">
        <h3 class="chat-room-title">
          {{ '채팅방: ' + (selectedUser.sender === user.name ? selectedUser.receiver : selectedUser.sender) }}
        </h3>
  
        <div id="messageArea" class="message-area">
          <template v-for="(message, index) in messages" :key="index">
            <!-- 날짜 변경 시 날짜 표시 -->
            <!-- <div v-if="shouldDisplayDate(index)" class="message-date">
              {{ formatDate(message.createdAt) }}
            </div> -->
  
            <!-- 메시지 내용 -->
            <div class="message-wrapper" :class="message.sender === user.name ? 'my-message' : 'their-message'">
              <div class="message-sender">{{ message.sender }}</div>
              <div class="message-content">
                {{ message.message }}
                <span class="message-time">{{ formatTimeStamp(message.createdAt) }}</span>
              </div>
            </div>
          </template>
        </div>
  
        <div class="message-input">
          <input
            type="text"
            v-model="messageInput"
            placeholder="메시지 입력"
            @keyup.enter="sendMessage"
          />
          <button @click="sendMessage">전송</button>
        </div>
      </div>
    </div>
  </template>
  
  
    
    <script>
    import { ref, onMounted, reactive, onBeforeUnmount } from 'vue';
    import axios from 'axios';
    import {useRouter} from 'vue-router';
    import SockJS from 'sockjs-client';
    import Stomp from 'stompjs';
    import { Client } from '@stomp/stompjs'
    export default {
      setup() {
          /* chatRoomList */
          const rooms = reactive([]);  // 반응형 상태로 채팅방 리스트 저장
          const user = reactive({name: ''}); 
          const receiver = ref('');  // 채팅방 상대방 입력 필드
          const sender = ref('');
  
          /* chatRoomDetail */
          const stompClient = ref(null);
          const roomId = ref('');
          const messages = reactive([]);  // 메시지 목록
          const messageInput = ref('');  // 메시지 입력 필드
          const selectedUser = reactive({sender: ''}, {receiver: ''}); // 선택된 
   
        // 로그인한 사용자의 JWT token
        const token = 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTVEFOTDIiLCJzdWIiOiJKV1QgVG9rZW4iLCJpZCI6NCwibG9naW5JZCI6InRlc3Q1QGdtYWlsLmNvbSIsIm5hdGlvbmFsaXR5Ijoic2VvdWwiLCJzZXgiOiJGRU1BTEUiLCJwb2ludCI6MCwibmlja25hbWUiOiLqsIDsp4DrgqgiLCJsYW5ndWFnZSI6IktPUkVBTiIsImF1dGhvcml0aWVzIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE3MjkwNzc3NTUsImV4cCI6MTcyOTEwNzc1NX0.uT5R0Z52DqWZ4owF9rWLdU0qUr2J3mmTMDu4SbWpnOQ';  
        // 서버에서 데이터를 가져오는 함수
        const fetchChatRooms = async () => {
          try {
              console.log("Fetching chat rooms...");
              const response = await axios.get('http://localhost:8080/api/v1/chat', {
              headers: {
                  Authorization: `Bearer ${token}`,
              }
          });
          rooms.splice(0, rooms.length, ...response.data.rooms);
          user.name = response.data.user;    // 사용자 이름 저장
          console.log(user.name);
          } catch (error) {
            console.error("Error fetching chat rooms:", error);
          }
        };
        
      // 새로운 채팅방을 생성하는 함수
      const createRoom = async () => {
          if (!receiver.value) {
              alert("상대방 이름을 입력해주세요.");
              return;
          }
          try {
              const response = await axios.post('http://localhost:8080/api/v1/chat', {
              sender: user.name,   // 로그인한 사용자 (채팅방 생성자)
              receiver: receiver.value  // 입력된 상대방 이름
              }, {
              headers: {
                  Authorization: `Bearer ${token}`,
              }
              });
              alert(`${receiver.value}와의 채팅방이 생성되었습니다.`);
              fetchChatRooms();  // 채팅방 목록을 다시 가져와서 업데이트
          } catch (error) {
              console.error("채팅방 생성 실패", error);
              alert("채팅방 생성에 실패했습니다. 다시 시도해주세요.");
          }
          receiver.value = '';
      };
  
  
      /* roomId 설정 함수 */
      const setSelectedRoom = (room) => {
          if (stompClient.value) {
              stompClient.value.disconnect();
              console.log('Disconnected');
          }
          fetchChatRoomDetail(room); // 선택한 채팅방의 상세 내용을 불러오는 함수 호출
          connect(room);
          roomId.value = room.roomId;
      };
  
      /* WebSocket을 통해 서버에 연결 */
      const connect = (room) => {
        // const socket = new SockJS('/ws-stomp');
      const socket = new SockJS('http://localhost:8080/ws-stomp');
      stompClient.value = Stomp.over(socket);
  
  
      console.log("connect에서의 roomId: " + room.roomId);
  
        stompClient.value.connect({}, (frame) => {
          console.log('Connected: ' + frame);
          console.log('roomid value: ' + room.roomId);
          stompClient.value.subscribe(`/sub/${room.roomId}`, (message) => {
            showMessage(JSON.parse(message.body));
          });
        }, (error) => {
          console.log('Connection error: ', error);
        });
      }
    
      /* 메시지를 불러오는 함수 */
      const fetchChatRoomDetail = async (room) => {
          if (!room) {
              console.error("room is not set.");
              return;
          }
  
          try {
              console.log("Fetching message rooms...");
              console.log("Fetching에서의 roomId: " + room.roomId);
              const response = await axios.get(`http://localhost:8080/api/v1/chat/${room.roomId}`, {
                  headers: {
                  Authorization: `Bearer ${token}`,
                  } 
              });
  
              
              // user.name = response.data.user;    // 사용자 이름 저장
              selectedUser.sender = response.data.room.sender;
              selectedUser.receiver = response.data.room.receiver;
              // console.log(selectedUser);
              if(response.data.messages != null){
                messages.splice(0, messages.length, ...response.data.messages);
                messages.forEach(message => {
                if (message.createdAt instanceof Object && message.createdAt.$date) {
                  message.createdAt = new Date(message.createdAt.$date).toISOString();
                }
              });
   
              }
              else{
                messages.splice(0, messages.length);
              }
          } catch (error) {
              console.error("Error fetching chat rooms:", error);
          }
        };
  
      /* 메시지 보내기 함수 */
      const sendMessage = async () => {
        if (!messageInput.value) {
          alert('메시지를 입력해주세요.');
          return;
        }
  
        const message = {
          roomId: roomId.value,
          sender: user.name,
          message: messageInput.value,
          type: 'TALK'
        };
  
        console.log("stompClient is alive?" + stompClient);
        console.log('Sending message:', JSON.stringify(message));
  
        // stompClient.value.send('/pub/chat/message', {}, JSON.stringify(message));
        // stompClient.value.send('/pub/message', {}, JSON.stringify(message));
        // messageInput.value = '';  // 입력창 비우기
        try {
          console.log("roomId.value : " + roomId.value);
          // await stompClient.value.send('/pub/message', {}, JSON.stringify(message));
          await stompClient.value.send(`/pub/message/${roomId.value}`, {}, JSON.stringify(message));
          messageInput.value = '';
        } catch (error) {
          console.error('메시지 전송 실패:', error);
          alert('메시지 전송에 실패했습니다. 다시 시도해주세요.');
        }
      };
  
      // 수신된 메시지를 표시하는 함수
      const showMessage = (message) => {
          messages.push(message);
          const messageArea = document.getElementById('messageArea');
          messageArea.scrollTop = messageArea.scrollHeight;  // 스크롤을 가장 아래로
      };
  
      // 컴포넌트가 마운트될 때 데이터 가져오기
      onMounted(async () => {
          console.log("Component is mounted. Now fetching chat rooms...");
          await fetchChatRooms();
      });
  
          // 컴포넌트가 언마운트될 때 WebSocket 연결 해제
        onBeforeUnmount(() => {
          if (stompClient.value) {
              stompClient.value.disconnect();
              console.log('Disconnected');
          }
      });
  
        /* timestamp 바꾸기 */
        const formatTimeStamp = (timestamp) => {
            console.log(timestamp);
            const date = new Date(timestamp);
            /* 한국에 맞춘 지역 시간 */
            const hour = String(date.getHours()).padStart(2, '0'); // 월은 0부터 시작 1을 더해줌
            const minute = String(date.getMinutes()).padStart(2, '0');
  
            return `${hour}:${minute}`;
          }
  
        
      /* 날짜 포맷 */
      const formatDate = (timestamp) => {
        const date = new Date(timestamp);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
      }
  
  
      /* 날짜 변경 시 날짜 표시 */
      const shouldDisplayDate = (index) => {
        if (index === 0) return true;
        const currentMessageDate = new Date(this.messages[index].createdAt).toDateString();
        const previousMessageDate = new Date(this.messages[index - 1].createdAt).toDateString();
        return currentMessageDate !== previousMessageDate;
      }
  
  
        return {
          rooms,
          user,
          receiver,
          createRoom,
          messages,
          messageInput,
          sendMessage,
          selectedUser,
          setSelectedRoom,
          formatTimeStamp,
          formatDate,
          shouldDisplayDate
          // message
        };
      }
      }
  
    </script>
    
    <style >
  
  .chat-container {
    display: flex;
    height: 100vh;
  }
  
  /* 채팅방 목록 */
  .chat-list {
    width: 30vw;
    background-color: #f8f9fa;
    padding: 2rem;
    border-right: 1px solid #ddd;
    overflow-y: auto; /* 목록 스크롤 */
  }
  
  .input-group {
    display: flex;
    gap: 1rem;
  }
  
  .input-group input {
    flex: 1;
    padding: 1rem;
    font-size: 1.4rem;
  }
  
  .input-group button {
    padding: 1rem 2rem;
    font-size: 1.4rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
  }
  
  .room-item {
    padding: 1rem;
    background-color: white;
    margin-bottom: 0.5rem;
    border-radius: 0.5rem;
    cursor: pointer;
    font-size: 1.6rem;
  }
  
  .room-item:hover {
    background-color: #e9ecef;
  }
  
  .no-rooms {
    font-size: 1.4rem;
    color: #888;
  }
  
  /* 채팅방 상세 */
  .chat-room {
    width: 70vw;
    padding: 2rem;
    display: flex;
    flex-direction: column;
  }
  
  .chat-room-title {
    font-size: 2rem;
    margin-bottom: 1rem;
  }
  
  .message-area {
    flex: 1;
    border: 1px solid #ddd;
    border-radius: 0.5rem;
    padding: 1rem;
    overflow-y: auto; /* 메시지 스크롤 */
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .message-date {
    text-align: center;
    font-size: 1.4rem;
    color: #555;
    margin: 1rem 0;
  }
  
  .message-wrapper {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .my-message {
    align-self: flex-end;
    text-align: right;
  }
  
  .their-message {
    align-self: flex-start;
    text-align: left;
  }
  
  .message-sender {
    font-weight: bold;
    font-size: 1.6rem;
  }
  
  .message-content {
    background-color: #e5e5ea;
    padding: 1rem;
    border-radius: 1rem;
    max-width: 50vw;
    word-break: break-word;
  }
  
  .my-message .message-content {
    background-color: #007bff;
    color: white;
  }
  
  .message-time {
    font-size: 1.2rem;
    margin-top: 0.5rem;
    color: #555;
    display: block;
  }
  
  .message-input {
    display: flex;
    gap: 1rem;
    margin-top: 1rem;
  }
  
  .message-input input {
    flex: 1;
    padding: 1rem;
    font-size: 1.4rem;
  }
  
  .message-input button {
    padding: 1rem 2rem;
    font-size: 1.4rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
  }
  
  
  
    /* ul {
      list-style-type: none;
      padding: 0;
    }
    
    li {
      padding: 8px;
      margin-bottom: 4px;
      background-color: #f0f0f0;
      border-radius: 4px;
    }
    
    h1 {
      font-size: 24px;
      margin-bottom: 16px;
    }
    
    p {
      font-size: 16px;
      color: #888;
    } */
    </style>
    