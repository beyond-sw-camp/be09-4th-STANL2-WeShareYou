package stanl_2.weshareyou.domain.chat.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import stanl_2.weshareyou.domain.chat.entity.ChatMessage;
import stanl_2.weshareyou.domain.chat.service.ChatRoomMessageService;

@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class ChatController {

    private final ChatRoomMessageService chatRoomMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessage message) {
        // 메시지를 해당 roomId에 추가
        chatRoomMessageService.addMessageToRoom(message.getRoomId(), message.getSender(), message.getMessage());

        // 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}

