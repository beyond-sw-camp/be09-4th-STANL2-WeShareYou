package stanl_2.weshareyou.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.chat.entity.ChatRoom;
import stanl_2.weshareyou.domain.chat.repository.ChatRoomRepository;
import stanl_2.weshareyou.domain.member.service.MemberService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    public List<ChatRoom> findAllRoom() {

        return chatRoomRepository.findAll();
    }

    public List<ChatRoom> findRoomsByUser(String user) {

        return chatRoomRepository.findAll().stream()
                .filter(room -> (room.getSender() != null && room.getSender().equals(user) && !room.getSenderDelete()) ||
                        (room.getReceiver() != null && room.getReceiver().equals(user) && !room.getReceiverDelete()))
                .collect(Collectors.toList());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId); // ID로 채팅방 조회
    }

    public ChatRoom createChatRoom(String sender, String receiver) {

//        /* 설명. 존재하는 아이디인지 검사 */
//        if(memberService.findNickname(receiver)) {
//            ChatRoom chatRoom = ChatRoom.create(sender, receiver);
//            chatRoomRepository.save(chatRoom); // DB에 저장
//            return chatRoom;
//        }
//
//        return null;

        ChatRoom chatRoom = ChatRoom.create(sender, receiver);
        chatRoomRepository.save(chatRoom); // DB에 저장
        return chatRoom;
    }

    @Transactional
    public boolean deleteChatRoom(String roomId, String nickname) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        /* 설명. sender와 nickname이 같을 때 senderDelete = false */
        if(chatRoom.getSender().equals(nickname)){
            chatRoom.setSenderDelete(true);
        }else{
            chatRoom.setReceiverDelete(true);
        }

        chatRoomRepository.save(chatRoom);

        return true;
    }
}
