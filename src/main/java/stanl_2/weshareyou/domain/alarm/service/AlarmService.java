package stanl_2.weshareyou.domain.alarm.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.AlarmType;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_recomment.aggregate.dto.BoardReCommentDto;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;

import java.util.List;

public interface AlarmService {
    SseEmitter subscribe(Long id, String lastEventId);

    void send(Member receiver, AlarmType alarmType, String message, String url, String createdAt, String sender);

    Alarm createAlarm(Member receiver, AlarmType alarmType, String url, String message, String createdAt, String sender);

    void sendToClient(SseEmitter emitter, String emitterId, Object data);

    void sendRentalAlarm(ProductDTO productDto);

    void sendLikeAlarm(BoardLikeDto boardLikeDto);

    // 댓글 알림
    void sendCommentAlarm(BoardCommentDto boardCommentDto);

    // 대댓글 알림
    void sendRecommentAlarm(BoardReCommentDto boardReCommentDto);

    List<AlarmDTO> readMemberAlarms(Long memberId);

    AlarmDTO readStatusAlarm(AlarmDTO alarmDTO);
}
