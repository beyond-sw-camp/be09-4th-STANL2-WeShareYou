package stanl_2.weshareyou.domain.chat.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import stanl_2.weshareyou.domain.chat.entity.ChatMessage;

import java.util.List;

@Repository("mongoChatMessageRepository")
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAll();

    List<ChatMessage> findByRoomId(String roomId);
}
