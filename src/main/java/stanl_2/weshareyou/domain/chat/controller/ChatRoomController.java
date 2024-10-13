package stanl_2.weshareyou.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.chat.entity.ChatMessage;
import stanl_2.weshareyou.domain.chat.entity.ChatRoom;
import stanl_2.weshareyou.domain.chat.entity.ChatRoomMessage;
import stanl_2.weshareyou.domain.chat.service.ChatMessageService;
import stanl_2.weshareyou.domain.chat.service.ChatRoomMessageService;
import stanl_2.weshareyou.domain.chat.service.ChatRoomService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
//@Controller
@RestController
@Slf4j
@RequestMapping("api/v1/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomMessageService chatRoomMessageService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/chat/rooms")
    @Operation(summary="채팅방 조회하기")
//    public String showRooms(Model model, @RequestAttribute("nickname") String nickname) {
//
//        List<ChatRoom> rooms = chatRoomService.findRoomsByUser(nickname);
//        model.addAttribute("rooms", rooms);
//        model.addAttribute("user", nickname);
//
//        return "/chat/room_list";
//    }
    public Map<String, Object> showRooms(@RequestAttribute("nickname") String nickname) {
        List<ChatRoom> rooms = chatRoomService.findRoomsByUser(nickname);

        Map<String, Object> response = new HashMap<>();
        response.put("rooms", rooms);
        response.put("user", nickname);

        return response; // JSON 데이터 반환
    }
    @GetMapping("/chat/room/{roomId}")
    public String roomDetail(@PathVariable String roomId
                            ,Model model
                            ,@RequestAttribute("nickname") String nickname) {
        ChatRoom room = chatRoomService.findRoomById(roomId);
        ChatRoomMessage messages = chatRoomMessageService.getMessagesByRoomId(roomId);
        chatRoomMessageService.markMessagesAsRead(roomId, nickname);

        model.addAttribute("user", nickname);
        model.addAttribute("room", room);
        model.addAttribute("messages", messages.getMessages());

        return "chat/room_detail";
    }

    // 모든 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoom> getAllRooms() {
        return chatRoomService.findAllRoom();
    }

    // 새로운 채팅방 생성
    @PostMapping("/chat/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody Map<String, String> requestBody) {
        String sender = requestBody.get("sender");
        String receiver = requestBody.get("receiver");
        return chatRoomService.createChatRoom(sender, receiver);
    }
    // 채팅방 세부 정보 조회
    @GetMapping("/room/{roomId}")
    public ChatRoom roomDetail(@PathVariable String roomId) {
        return chatRoomService.findRoomById(roomId);
    }
}
