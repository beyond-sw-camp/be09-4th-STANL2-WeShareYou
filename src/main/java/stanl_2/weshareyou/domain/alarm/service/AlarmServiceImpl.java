package stanl_2.weshareyou.domain.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.AlatmType;
import stanl_2.weshareyou.domain.alarm.repository.AlarmRepository;
import stanl_2.weshareyou.domain.alarm.repository.EmitterRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;

import java.io.IOException;
import java.util.Map;

@Service
public class AlarmServiceImpl implements AlarmService {

    // SSE 연결 지속 시간 설정 (한시간)
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmServiceImpl(EmitterRepository emitterRepository, AlarmRepository alarmRepository) {
        this.emitterRepository = emitterRepository;
        this.alarmRepository = alarmRepository;
    }

    @Override
    public SseEmitter subscribe(Long memberId, String lastEventId) {

        // 고유한 아이디 생성
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 시간 초과나 비동기 요청이 안되면 자동으로 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 최초 연결시 더미데이터가 없으면 503 오류가 발생하기 때문에 해당 더미 데이터 생ㅅ헝
        sendToClient(emitter, emitterId, "EventSteam Created. [memberId=" + memberId + "]");

        // lastEventId가 있다는 것은 연결이 종료됐다는 뜻. 그래서 해당 데이터가 남아있는지 살펴보고 있다면 남은 데이터를 전송
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    public void send(Member receiver, AlatmType alatmType, String message, String url) {
        Alarm alarm = alarmRepository.save(createAlarm(receiver, alatmType, url, message));
        String memberId = String.valueOf(receiver.getId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(memberId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendToClient(emitter, key, "새로운 알림");
                }
        );
    }

    private Alarm createAlarm(Member receiver, AlatmType alatmType, String url, String message) {
        Alarm alarm = new Alarm();
        alarm.setMemberId(receiver);
        alarm.setAlatmType(alatmType);
        alarm.setUrl(url);
        alarm.setMessage(message);
        return alarm;
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            // 예외처리 날리기
        }
    }

    public void sendRentalAlarm(Product product, Long memberId) {
        Member adminId = product.getAdminId();
        String message = memberId + "번님이 " + product.getTitle() + "을 대여 신청하셨습니다.";
        String url = "/api/v1/alarm/product/rental/" + product.getId();

        send(adminId, AlatmType.RENTAL, message, url);
    }
}
