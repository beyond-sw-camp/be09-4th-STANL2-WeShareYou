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

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class ChatController {

    private final ChatRoomMessageService chatRoomMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message/{roomId}")
//    @SendTo("/sub/{roomId}")
    public void sendMessage(ChatMessage message, @DestinationVariable String roomId) {
        try {
//            System.out.println("Received message: " + objectMapper.writeValueAsString(message));

            chatRoomMessageService.addMessageToRoom(message.getRoomId(), message.getSender(), message.getMessage());

//            messagingTemplate.convertAndSend("/sub/" + message.getRoomId(), message);
            messagingTemplate.convertAndSend("/sub/" + roomId, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

