package stanl_2.weshareyou.domain.chat.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;


@Getter
@Setter
@Document(collection  = "message") // com.websocket.chat.test 데이터베이스에 message 테이블 생성하여 메시지 내용 저장
public class ChatMessage {

    private String id;
    private String roomId;
    private String sender;
    private String message;
    private Timestamp receivedTime;
}
