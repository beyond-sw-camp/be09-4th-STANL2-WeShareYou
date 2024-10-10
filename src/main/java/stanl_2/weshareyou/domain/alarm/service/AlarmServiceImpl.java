package stanl_2.weshareyou.domain.alarm.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.AlatmType;
import stanl_2.weshareyou.domain.alarm.repository.AlarmRepository;
import stanl_2.weshareyou.domain.alarm.repository.EmitterRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.product.aggregate.entity.Product;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@Slf4j
public class AlarmServiceImpl implements AlarmService {

    // SSE 연결 지속 시간 설정 (한시간)
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    @Autowired
    public AlarmServiceImpl(EmitterRepository emitterRepository, AlarmRepository alarmRepository, MemberRepository memberRepository) {
        this.emitterRepository = emitterRepository;
        this.alarmRepository = alarmRepository;
        this.memberRepository = memberRepository;
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

    @Override
    public void send(Member receiver, AlatmType alatmType, String message, String url, String createdAt) {

        Alarm alarm = alarmRepository.save(createAlarm(receiver, alatmType, url, message, createdAt));
        String memberId = String.valueOf(receiver.getId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(memberId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendToClient(emitter, key, "새로운 알림");
                }
        );
    }

    @Override
    public Alarm createAlarm(Member receiver, AlatmType alatmType, String url, String message, String createdAt) {
        Alarm alarm = new Alarm();
        alarm.setMemberId(receiver);
        alarm.setAlatmType(alatmType);
        alarm.setUrl(url);
        alarm.setMessage(message);
        alarm.setCreatedAt(createdAt);
        alarm.setReadStatus(false);
        return alarm;
    }

    @Override
    public void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            // 예외처리 날리기
        }
    }

    @Override
    public void sendRentalAlarm(ProductDTO productDto, Long memberId) {

        Member adminId = memberRepository.findById(memberId)
                .orElseThrow(()-> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        String message = memberId + "번님이 " + productDto.getTitle() + "을 대여 신청하셨습니다.";
        String url = "/api/v1/product/share/" + productDto.getId();
        String createdAt = LocalDateTime.now().format(FORMATTER);

        send(adminId, AlatmType.RENTAL, message, url, createdAt);
    }
}
