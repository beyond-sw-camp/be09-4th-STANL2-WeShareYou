package stanl_2.weshareyou.domain.alarm.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AlarmService {
    SseEmitter subscribe(Long id, String lastEventId);
}
