package stanl_2.weshareyou.domain.chat.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.weshareyou.domain.chat.entity.ChatMessage;
import stanl_2.weshareyou.domain.chat.service.ChatRoomMessageService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class ChatController {

    private final ChatRoomMessageService chatRoomMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/sub/{roomId}")
    public void sendMessage(ChatMessage message, @DestinationVariable String roomId) {

        log.info("여기까지 왔냐???? : " + message.getMessage());
//        // 메시지를 해당 roomId에 추가
//        chatRoomMessageService.addMessageToRoom(message.getRoomId(), message.getSender(), message.getMessage());
//        // 구독자들에게 메시지 전송
////        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//        messagingTemplate.convertAndSend("/sub/" + message.getRoomId(), message);
        try {
//            System.out.println("Received message: " + objectMapper.writeValueAsString(message));

            chatRoomMessageService.addMessageToRoom(message.getRoomId(), message.getSender(), message.getMessage());

//            messagingTemplate.convertAndSend("/sub/" + message.getRoomId(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

