package stanl_2.weshareyou.domain.alarm.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.AlatmType;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;

public interface AlarmService {
    SseEmitter subscribe(Long id, String lastEventId);

    void send(Member receiver, AlatmType alatmType, String message, String url, String createdAt);

    Alarm createAlarm(Member receiver, AlatmType alatmType, String url, String message, String createdAt);

    void sendToClient(SseEmitter emitter, String emitterId, Object data);

    void sendRentalAlarm(ProductDTO productDto, Long memberId);
}
