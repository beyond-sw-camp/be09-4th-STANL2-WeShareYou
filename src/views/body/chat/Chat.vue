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
        <li v-for="room in rooms" :key="room.roomId" class="room-item-container">
          <p class="room-item"@click="setSelectedRoom(room)" >
            {{ room.sender === user.name ? room.receiver : room.sender }}
          </p>
          <!-- 버튼을 마우스 오버 시 표시 -->
          <button class="delete-button" @click="openDeleteModal(room)">삭제</button>
        </li>
      </ul>
      <p v-else class="no-rooms">No chat rooms available.</p>
    </div>

        <div v-if="showModal" class="modal-overlay">
    <div class="modal-content">
      <p>정말 취소하시겠습니까?</p>
      <div class="modal-actions">
        <button @click="confirmDelete">확인</button>
        <button @click="closeModal">취소</button>
      </div>
    </div>
  </div>

    <!-- 오른쪽 채팅방 내용 -->
    <div class="chat-room"  v-if="selectedRoom">
      <h3 class="chat-room-title">
        <!-- <img :src= "profile.name || 'default-image-url'" alt="Profile Image" class="profile" />
        {{ (selectedUser.sender === user.name ? selectedUser.receiver : selectedUser.sender) }} -->
        <img :src="selectedRoom.sender === user.name ? selectedRoom.receiverProfileUrl : selectedRoom.senderProfileUrl" alt="Profile Image" class="profile" />
        {{ selectedRoom.sender === user.name ? selectedRoom.receiver : selectedRoom.sender }}
      </h3>

      <div id="messageArea" class="message-area">
        <template v-for="(message, index) in messages" :key="index">
          <div v-if="message.createdAt"></div>
            <div v-if="shouldDisplayDate(message.createdAt) && message.createdAt" class="message-date">
              {{  formatDate(message.createdAt) }}  
            </div>
            <!-- 실시간으로 날짜 바뀌었을 때(추후) -->
          <!-- <div v-else></div>
            <div v-if="shouldDisplayDate(message.sendTime) && message.sendTime" class="message-date">
              {{  formatDate(message.sendTime) }}  
            </div> -->

          <!-- 메시지 내용 -->
          <div class="message-wrapper" :class="message.sender === user.name ? 'my-message' : 'their-message'">
            <div class="message-sender">{{ message.sender }}</div>
            <div class="message-content">
              <span class="message-time">{{ message.message }}</span>
              <span class="message-time" v-if="message.createdAt">{{ formatTime(message.createdAt) }}</span>
              <span class="message-time" v-if="message.sendTime">{{ message.sendTime }}</span>
            </div>
            <div v-if="isLastMessageRead(index, message)">
              <span class="message-time">읽음</span>
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
  import SockJS from 'sockjs-client';
  import Stomp from 'stompjs';
  import { nextTick } from 'vue';
  export default {
    setup() {
        /* chatRoomList */
        const rooms = reactive([]);  // 반응형 상태로 채팅방 리스트 저장
        const user = reactive({name: ''}); 
        const profile = reactive({name: ''}); 
        const receiver = ref('');  // 채팅방 상대방 입력 필드
        const selectedRoom = ref(null);
        /* chatRoomDetail */
        const stompClient = ref(null);
        const roomId = ref('');
        const messages = reactive([]);  // 메시지 목록
        const messageInput = ref('');  // 메시지 입력 필드

        const selectedUser = reactive({sender: ''}, {receiver: ''}); // 선택된 
        const saveDate = ref('');
        const roomName = ref('');

      // 로그인한 사용자의 JWT token
      const token = localStorage.getItem('jwtToken'); // 'jwt'는 저장된 키 이름

      // 서버에서 데이터를 가져오는 함수
      const fetchChatRooms = async () => {
        try {
            console.log("Fetching chat rooms...");
            const response = await axios.get('http://localhost:8080/api/v1/chat', {
            headers: {
                Authorization: `Bearer ${token}`,}
        });
        rooms.splice(0, rooms.length, ...response.data.rooms);
        user.name = response.data.user; 
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
            }
            , {
            headers: {
                Authorization: `Bearer ${token}`,
            }
            }
          );
            alert(`${receiver.value}와의 채팅방이 생성되었습니다.`);
            fetchChatRooms();  // 채팅방 목록을 다시 가져와서 업데이트
        } catch (error) {
            console.error("채팅방 생성 실패", error);
            alert("채팅방 생성에 실패했습니다. 다시 시도해주세요.");
        }
        receiver.value = '';
    };


    /* roomId 설정 함수 */
    const setSelectedRoom = async (room) => {
        if (stompClient.value) {
            stompClient.value.disconnect();
            console.log('Disconnected');
        }
        selectedRoom.value = room;
        roomName.value = room;
        roomId.value = room.roomId;

        profile.name = room.sender === user.name ? room.receiverProfileUrl : room.senderProfileUrl;

        connect(room);
        fetchChatRoomDetail(room).catch((error) => console.error(error));
    };

    /* WebSocket을 통해 서버에 연결 */
    const connect = (room) => {
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
            const response = await axios.get(`http://localhost:8080/api/v1/chat/${room.roomId}`
            , {
                headers: {
                Authorization: `Bearer ${token}`,
                } 
            }
          );
          
            selectedUser.sender = response.data.room.sender;
            selectedUser.receiver = response.data.room.receiver;
            messages.length = 0; // 기존 메시지 초기화
            const newMessages = response.data.messages.map((message) => {
            if (message.createdAt instanceof Object && message.createdAt.$date) {
              message.createdAt = new Date(message.createdAt.$date).toISOString();
            }
            return message;
          });

          messages.push(...newMessages); // 상태를 한 번만 갱신

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

      const sendMessage = {
        roomId: roomId.value,
        sender: user.name,
        message: messageInput.value,
        sendTime: new Date(),
        type: 'TALK'
      };

      console.log("stompClient is alive?" + stompClient);
      console.log('Sending message:', JSON.stringify(sendMessage));

      try {
        await stompClient.value.send(`/pub/message/${roomId.value}`, {}, JSON.stringify(sendMessage));
        messageInput.value = '';
        fetchChatRoomDetail(roomName.value);
      } catch (error) {
        console.error('메시지 전송 실패:', error);
        alert('메시지 전송에 실패했습니다. 다시 시도해주세요.');
      }
    };

    const showMessage = (message) => {

      const formatSendTime = (sendTime) => {
        const newDate = new Date(sendTime);
        const newHour = String(newDate.getHours()).padStart(2, '0'); // 월은 0부터 시작 1을 더해줌
        const newMinute = String(newDate.getMinutes()).padStart(2, '0');

        return `${newHour}:${newMinute}`;
      };

      // sendTime 변환 후 재할당
      message.sendTime = formatSendTime(message.sendTime);
      messages.push(message);  // 메시지 추가

      nextTick(() => {
        const messageArea = document.getElementById('messageArea');
        if (messageArea) {
          messageArea.scrollTop = messageArea.scrollHeight;  // 스크롤을 아래로 이동
        }
      });
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
    const formatTime = (timestamp) => {
        // console.log(timestamp);
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
    const shouldDisplayDate = (timestamp) => {
      const date = new Date(timestamp);

      // 저장된 값과 입력 된 값이 일치하지 않을 때
      if(String(date.getDate()).padStart(2, '0') != saveDate.value) {
        saveDate.value = String(date.getDate()).padStart(2, '0');
        return true;
      }
      return false;
    }

    /* 읽음 표시를 위한 함수 */
    const isLastMessageRead = (index, message) => {
      return (
        index === messages.length - 1 &&
        message.sender === user.name &&
        message.readYn
      );
    };

    // 모달~
    const showModal = ref(false); // 모달 표시 여부

    // 모달 열기
    const openDeleteModal = (room) => {
      selectedRoom.value = room;
      showModal.value = true;
    };

    // 모달 닫기
    const closeModal = () => {
      selectedRoom.value = null;
      showModal.value = false;
    };

    // 삭제 확인
    const confirmDelete = async () => {
      if (!selectedRoom.value) return;

      console.log(selectedRoom.value.roomId);

      try {
        // 삭제 API 호출
        await axios.delete(`http://localhost:8080/api/v1/chat/${selectedRoom.value.roomId}`
        ,{
          headers: {
              Authorization: `Bearer ${token}`,
          }
        });
        // 삭제 후 목록에서 제거
        rooms.splice(rooms.findIndex(r => r.roomId === selectedRoom.value.roomId), 1);
        closeModal(); // 모달 닫기
      } catch (error) {
        console.error('방 삭제 실패:', error);
        alert('방 삭제에 실패했습니다.');
      }
    };

      return {
        rooms,
        profile,
        user,
        receiver,
        createRoom,
        messages,
        messageInput,
        sendMessage,
        selectedUser,
        setSelectedRoom,
        fetchChatRooms,
        formatTime,
        formatDate,
        shouldDisplayDate,
        isLastMessageRead,
        showModal,
        selectedRoom,
        openDeleteModal,
        closeModal,
        confirmDelete,
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
  background-color: white;
  color: #439aff;
  border: 1px solid #439aff;
  border-radius: 4px;
}

.room-item {
  padding: 1rem;
  background-color: white;
  margin-bottom: 0.5rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 1.6rem;
  /* 추가 */
  flex: 1;
  margin: 0;
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
  background-color: white;
  color: #439aff;
  border: 1px solid #439aff;
  border-radius: 4px;
}

.delete-button {
position: absolute;
right: 1rem; /* 오른쪽 여백 */
display: none; /* 기본적으로 숨김 */
background-color: white;
color: red;
border: 1px solid #ff414c;
padding: 0.5rem 1rem;
border-radius: 4px;
cursor: pointer;
}
.room-item-container:hover .delete-button {
display: block; /* 마우스 오버 시 버튼 표시 */
}
.room-item-container {
position: relative; /* 버튼을 이 항목 내에서 절대 위치하도록 설정 */
display: flex; /* 버튼과 텍스트를 한 줄에 배치 */
align-items: center;
padding: 1rem;
background-color: white;
margin-bottom: 0.5rem;
border-radius: 0.5rem;
cursor: pointer;
font-size: 1.6rem;
}

.room-item-container:hover {
background-color: #e9ecef;
}
.modal-overlay {
position: fixed;
top: 0;
left: 0;
width: 100%;
height: 100%;
background-color: rgba(0, 0, 0, 0.6); /* 반투명 배경 */
display: flex;
justify-content: center;
align-items: center;
z-index: 1000;
}

/* 모달 컨테이너 */
.modal-container {
background-color: white;
width: 30%; /* 화면의 30% 너비 */
max-width: 500px; /* 최대 너비 제한 */
min-width: 300px; /* 최소 너비 보장 */
height: 25%; /* 화면의 25% 높이 */
padding: 2rem;
border-radius: 12px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
text-align: center;
animation: fadeIn 0.3s ease-out;
}

/* 모달 타이틀 */
.modal-title {
font-size: 2rem;
font-weight: bold;
margin-bottom: 1rem;
color: #333;
}

/* 모달 메시지 */
.modal-message {
font-size: 1.6rem;
margin-bottom: 2rem;
color: #555;
}

/* 모달 버튼 영역 */
.modal-actions {
display: flex;
justify-content: space-around;
gap: 1rem;
}

/* 확인 버튼 */
.confirm-button {
background-color: #ff4d4f;
color: white;
border: none;
padding: 1rem 2rem;
border-radius: 8px;
font-size: 1.4rem;
cursor: pointer;
transition: background-color 0.3s;
flex: 1;
max-width: 120px;
}

.confirm-button:hover {
background-color: #ff7875;
}

/* 취소 버튼 */
.cancel-button {
background-color: #f0f0f0;
color: #333;
border: none;
padding: 1rem 2rem;
border-radius: 8px;
font-size: 1.4rem;
cursor: pointer;
transition: background-color 0.3s;
flex: 1;
max-width: 120px;
}

.cancel-button:hover {
background-color: #d9d9d9;
}
.profile {
width: 50px; /* 이미지의 너비를 50px로 설정 */
height: 50px; /* 이미지의 높이를 50px로 설정 */
border-radius: 50%; /* 동그랗게 만들고 싶다면 추가 */
object-fit: cover; /* 이미지 비율을 유지하며 크기를 맞추기 */
}

/* 모달 애니메이션 */
@keyframes fadeIn {
from {
  opacity: 0;
  transform: scale(0.9);
}
to {
  opacity: 1;
  transform: scale(1);
}
}
</style>
  