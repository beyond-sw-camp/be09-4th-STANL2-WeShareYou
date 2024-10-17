package stanl_2.weshareyou.domain.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import stanl_2.weshareyou.domain.chat.entity.ChatRoomMessage;

@Repository("mongoChatRoomMessageRepository")
public interface ChatRoomMessageRepository extends MongoRepository<ChatRoomMessage, String> {
    ChatRoomMessage findByRoomId(String roomId);
}
