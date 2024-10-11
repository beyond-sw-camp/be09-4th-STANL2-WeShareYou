package stanl_2.weshareyou.domain.chat.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.chat.entity.ChatMessage;
import stanl_2.weshareyou.domain.chat.entity.ChatRoomMessage;
import stanl_2.weshareyou.domain.chat.repository.ChatRoomMessageRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class ChatRoomMessageService {

    private final ChatRoomMessageRepository chatRoomMessageRepository;
    private final MongoTemplate mongoTemplate;
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    // 특정 채팅방의 메시지 조회
    public ChatRoomMessage getMessagesByRoomId(String roomId) {

        log.info("messages: " + chatRoomMessageRepository.findByRoomId(roomId));

        return chatRoomMessageRepository.findByRoomId(roomId);
    }

    // 특정 채팅방에 메시지 추가 (업데이트 방식으로)
    public void addMessageToRoom(String roomId, String sender, String message) {
        ChatRoomMessage.Message newMessage = new ChatRoomMessage.Message();
        newMessage.setSender(sender);
        newMessage.setMessage(message);
        /* 설명. default: 안읽음, 생성시각 추가*/
        newMessage.setReadYn(false);
        newMessage.setCreatedAt(LocalDateTime.now().format(FORMATTER));


        // MongoDB 쿼리로 부분 업데이트 수행
        Query query = new Query(Criteria.where("roomId").is(roomId));
        Update update = new Update().push("messages", newMessage);

        // document가 있으면 업데이트, 없으면 생성
        mongoTemplate.upsert(query, update, ChatRoomMessage.class);
    }

    public void markMessagesAsRead(String roomId, String user) {

        MongoCollection<Document> collection = mongoTemplate.getCollection("roomMessages");


// MongoDB 쿼리: roomId에 해당하는 문서 찾기
        Document query = new Document("roomId", roomId);

// 업데이트: sender가 지정된 사용자가 아니고 readYn이 false인 메시지의 readYn을 true로 설정
        Document update = new Document("$set", new Document("messages.$[elem].readYn", true));
// 배열 필터 정의
        List<Document> arrayFilters = Collections.singletonList(
                new Document("elem.sender", new Document("$ne", user))
                        .append("elem.readYn", false)
        );


// UpdateOptions 설정
        UpdateOptions options = new UpdateOptions().arrayFilters(arrayFilters);

// MongoDB에서 배열 필드를 업데이트
        UpdateResult result = collection.updateMany(query, update, options);


    }

}
