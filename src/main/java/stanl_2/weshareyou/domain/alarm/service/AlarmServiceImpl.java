package stanl_2.weshareyou.domain.alarm.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.AlarmType;
import stanl_2.weshareyou.domain.alarm.repository.AlarmRepository;
import stanl_2.weshareyou.domain.alarm.repository.EmitterRepository;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;
import stanl_2.weshareyou.domain.board_comment.repository.BoardCommentRepository;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_recomment.aggregate.dto.BoardReCommentDto;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.domain.product.aggregate.dto.ProductDTO;
import stanl_2.weshareyou.domain.alarm.aggregate.vo.response.AlarmResponseVO;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Autowired
    public AlarmServiceImpl(EmitterRepository emitterRepository, AlarmRepository alarmRepository, MemberRepository memberRepository, BoardRepository boardRepository, BoardCommentRepository boardCommentRepository) {
        this.emitterRepository = emitterRepository;
        this.alarmRepository = alarmRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.boardCommentRepository = boardCommentRepository;
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
    public void send(Member receiver, AlarmType alarmType, String message, String url, Timestamp createdAt, String sender) {
        Alarm alarm = alarmRepository.save(createAlarm(receiver, alarmType, url, message, createdAt, sender));
        String memberId = String.valueOf(receiver.getId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(memberId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendToClient(emitter, key, new AlarmResponseVO(alarm));
                }
        );
    }

    @Override
    public Alarm createAlarm(Member receiver, AlarmType alarmType, String url, String message, Timestamp createdAt, String sender) {
        Alarm alarm = new Alarm();
        alarm.setMemberId(receiver);
        alarm.setAlarmType(alarmType);
        alarm.setUrl(url);
        alarm.setMessage(message);
        alarm.setCreatedAt(createdAt);
        alarm.setReadStatus(false);
        alarm.setSender(sender);
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
        }
    }

    // 대여신청 알림
    @Override
    public void sendRentalAlarm(ProductDTO productDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();
        Member member = memberRepository.findById(productDto.getAdminId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Member sender = memberRepository.findById(productDto.getMemberId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        String message = sender.getNickname() + "님이 " + productDto.getTitle() + "을 대여 신청하셨습니다.";
        String url = "/api/v1/product/share/" + productDto.getId();
        Timestamp createdAt = currentTimestamp;

        send(member, AlarmType.RENTAL, message, url, createdAt, sender.getNickname());
    }

    // 좋아요 알림
    @Override
    public void sendLikeAlarm(BoardLikeDto boardLikeDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();
        Member sender = memberRepository.findById(boardLikeDto.getMemberId())
                .orElseThrow(()-> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Board board = boardRepository.findById(boardLikeDto.getBoardId())
                .orElseThrow(()-> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        Member member = memberRepository.findById(board.getMember().getId())
                .orElseThrow(()-> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        String message = sender.getNickname() + "님이 " + "회원님의 " + board.getTitle() + " 글을 좋아합니다.";
        String url = "/api/v1/board_like";
        Timestamp createdAt = currentTimestamp;

        send(member, AlarmType.LIKE, message, url, createdAt, sender.getNickname());
    }

    // 댓글 알림
    @Override
    public void sendCommentAlarm(BoardCommentDto boardCommentDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();
        String sender = boardCommentDto.getNickname();
        Board board = boardRepository.findById(boardCommentDto.getBoardId())
                .orElseThrow(()-> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        Member member = memberRepository.findById(board.getMember().getId())
                .orElseThrow(()-> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        String message = sender + "님이 " + "회원님의 " + board.getTitle() + " 글에 댓글을 남겼습니다.";
        String url = "/api/v1/board-comment";
        Timestamp createdAt = currentTimestamp;

        send(member, AlarmType.COMMENT, message, url, createdAt, sender);
    }

    // 대댓글 알림
    @Override
    public void sendRecommentAlarm(BoardReCommentDto boardReCommentDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();
        Member sender = memberRepository.findById(boardReCommentDto.getMemberId())
                .orElseThrow(()-> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        BoardComment boardComment = boardCommentRepository.findById(boardReCommentDto.getBoardCommentId())
                .orElseThrow(() -> new CommonException(ErrorCode.COMMENT_NOT_FOUND));

        Member member = memberRepository.findById(boardComment.getMember().getId())
                .orElseThrow(()-> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        String message = sender.getNickname() + "님이 댓글을 남겼습니다.";
        String url = "/api/v1/board-recomment";
        Timestamp createdAt = currentTimestamp;

        send(member, AlarmType.RECOMMENT, message, url, createdAt, sender.getNickname());
    }

    // 알림 조회
    @Override
    public Page<AlarmDTO> readMemberAlarms(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));
        // 알림을 페이지 단위로 가져오기
        Page<Alarm> alarms = alarmRepository.findByMemberIdOrderByCreatedAtDesc(member, pageable);

        // Alarm -> AlarmDTO로 변환
        return alarms.map(this::toAlarmDTO);
    }

    // 알림 읽음
    @Override
    public AlarmDTO readStatusAlarm(AlarmDTO alarmDTO) {
        Alarm alarm = alarmRepository.findById(alarmDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.ALARM_NOT_FOUND));

        if (!alarm.getMemberId().getId().equals(alarmDTO.getMemberId())) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }

        alarm.setReadStatus(true);
        Alarm alarm1 = alarmRepository.save(alarm);

        AlarmDTO alarmResponseDTO = toAlarmDTO(alarm1);

        return alarmResponseDTO;
    }

    private AlarmDTO toAlarmDTO(Alarm alarm) {
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setId(alarm.getId());
        alarmDTO.setMemberId(alarm.getMemberId().getId());
        alarmDTO.setMessage(alarm.getMessage());
        alarmDTO.setCreatedAt(alarm.getCreatedAt());
        alarmDTO.setReadStatus(alarm.getReadStatus());
        alarmDTO.setSender(alarm.getSender());
        alarmDTO.setUrl(alarm.getUrl());
        return alarmDTO;
    }

}