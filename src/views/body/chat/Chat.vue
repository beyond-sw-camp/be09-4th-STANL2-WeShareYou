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
<<<<<<< HEAD
              <span class="message-time1">{{ message.message }}</span><br>
              <span class="message-time2">{{ formatTime(message.createdAt) }}</span>
=======
              <span class="message-time">{{ message.message }}</span>
              <span class="message-time" v-if="message.createdAt">{{ formatTime(message.createdAt) }}</span>
              <span class="message-time" v-if="message.sendTime">{{ message.sendTime }}</span>
>>>>>>> 31b4b6704400a99f576238fad8752281eccd4828
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
  font-size: 2.3rem;
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
  height: 112vh;
  display: flex;
  flex-direction: column;
  padding-bottom: 17rem;
  padding-top: 2rem;
  padding-left: 20rem;
  padding-right: 10rem;
}

.chat-room-title {
  font-size: 4rem;
  margin-bottom: -3rem;
}

.message-area {
  margin: 5rem 1rem;
  margin-bottom: 2rem;
  flex: 1;
  width:40vw;
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
  font-size: 2rem;
  padding-right: 1vw;
}

.message-content {
  background-color: #e5e5ea;
  padding: 0.5rem 2rem;
  border-radius: 1rem;
  max-width: 25vw;
  word-break: break-word;
}
.message-time1{
  font-size:1vw;
}
.message-time2{
  font-size:0.8vw;
}

.my-message .message-content {
  background-color: #007bff;
  color: white;
}

.message-time {
  font-size: 1.2rem;
  margin-top: 0.5rem;
  color: white;
  display: block;
}

.message-input {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.message-input input {
  margin-left: 1.4rem;
  flex: 0.8;
  padding: 1.5rem;
  font-size: 1.4rem;
}

.message-input button {
  padding: 1rem 2rem;
  font-size: 2rem;
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
  margin-left: -2rem;
  margin-top: 1rem;
  position: relative; /* 버튼을 이 항목 내에서 절대 위치하도록 설정 */
  display: flex; /* 버튼과 텍스트를 한 줄에 배치 */
  align-items: center;
  padding: 1rem;
  background-color: white;
  margin-bottom: 0.5rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 2.3rem;
}

.room-item-container:hover {
background-color: #e9ecef;
}
/* 모달 오버레이 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3); /* 반투명 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

/* 모달 내용 */
.modal-content {
  position: relative;
  background-color: white !important;
  padding: 2rem;
  border-radius: 4px !important;
  text-align: center;
  width: 40rem !important; /* 고정 너비 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000 !important;
  width: 60%;
}

/* 아이콘과 메시지 */
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

/* 메시지 폰트 */
.delete-font {
  font-size: 2rem;
  color: #333;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: center;
  margin-top: 1.5rem;
}

/* 확인 버튼 */
.confirm-btn {
  background-color: white;
  color: #ff414c;
  border: 1px solid #ff414c;
  border-radius: 5px;
  padding: 1rem 5rem;
  cursor: pointer;
  font-size: 1.5rem;
  transition: background-color 0.3s;
}

.confirm-btn:hover {
  background-color: #ff414c;
  color: white;
}

/* 취소 버튼 */
.cancel-btn {
  background-color: white;
  color: #439aff;
  border: 1px solid #439aff;
  border-radius: 5px;
  padding: 1rem 5rem;
  cursor: pointer;
  font-size: 1.5rem;
  transition: background-color 0.3s;
}

.cancel-btn:hover {
  background-color: #439aff;
  color: white;
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
  