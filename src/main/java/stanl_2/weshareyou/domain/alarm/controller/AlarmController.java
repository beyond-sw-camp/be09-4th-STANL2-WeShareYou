package stanl_2.weshareyou.domain.alarm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.weshareyou.domain.alarm.aggregate.vo.response.AlarmReadMemberResponseVO;
import stanl_2.weshareyou.domain.alarm.aggregate.vo.response.AlarmReadStatusResponseVO;
import stanl_2.weshareyou.domain.alarm.service.AlarmService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;
    private final ModelMapper modelMapper;

    @Autowired
    public AlarmController(AlarmService alarmService, ModelMapper modelMapper) {
        this.alarmService = alarmService;
        this.modelMapper = modelMapper;
    }

    /**
     * 내용: 알림 연결
     * req: localhost:8080/api/v1/alarm/subscribe?id=3
     * header -> Accept : text/event-stream
     * res:
     * EventSteam Created. [memberId=3]
     * {
     *     "id": 15,
     *     "message": "usertwo님이 댓글을 남겼습니다.",
     *     "url": "/api/v1/board-recomment",
     *     "readStatus": false,
     *     "createdAt": "2024-10-12T00:10:41",
     *     "sender": "usertwo",
     *     "memberId": 3
     * }
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam Long memberId,
                                             @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                             String lastEventId) {

        return alarmService.subscribe(memberId, lastEventId);
    }

    /**
     * 내용: 알림 조회
     * req: localhost:8080/api/v1/alarm?memberId=3
     * res:
     * {
     *     "success": true,
     *     "result": [
     *         {
     *             "id": 15,
     *             "message": "usertwo님이 댓글을 남겼습니다.",
     *             "url": "/api/v1/board-recomment",
     *             "readStatus": false,
     *             "createdAt": "2024-10-12T00:10:41",
     *             "memberId": 3
     *         },
     *         {
     *             "id": 14,
     *             "message": "usertwo님이 댓글을 남겼습니다.",
     *             "url": "/api/v1/board-recomment",
     *             "readStatus": false,
     *             "createdAt": "2024-10-12T00:09:35",
     *             "memberId": 3
     *         },
     *     ],
     *     "error": null
     * }
     */
    @GetMapping("")
    public ApiResponse<?> readMemberAlarms(@RequestParam Long memberId) {
        List<AlarmDTO> alarmResponseDTO = alarmService.readMemberAlarms(memberId);

        List<AlarmReadMemberResponseVO> alarmReadMemberResponseVOs = alarmResponseDTO.stream().map(dto -> {
            AlarmReadMemberResponseVO vo = new AlarmReadMemberResponseVO();
            vo.setId(dto.getId());
            vo.setMessage(dto.getMessage());
            vo.setCreatedAt(dto.getCreatedAt());
            vo.setReadStatus(dto.getReadStatus());
            vo.setMemberId(dto.getMemberId());
            vo.setUrl(dto.getUrl());
            return vo;
        }).collect(Collectors.toList());

        return ApiResponse.ok(alarmReadMemberResponseVOs);
    }

    /**
     * 내용: 알림 읽음 처리
     * req: localhost:8080/api/v1/alarm/12
     * res:
     * {
     *     "success": true,
     *     "result": {
     *         "id": 12,
     *         "message": "usertwo님이 댓글을 남겼습니다.",
     *         "url": "/api/v1/board-recomment",
     *         "readStatus": true,
     *         "createdAt": "2024-10-12T00:01:46",
     *         "sender": "usertwo",
     *         "memberId": 3
     *     },
     *     "error": null
     * }
     */
    @PostMapping("/{alarmId}")
    public ApiResponse<?> readStatusAlarm(@PathVariable Long alarmId) {
        AlarmDTO alarmResponseDTO = alarmService.readStatusAlarm(alarmId);
        AlarmReadStatusResponseVO alarmReadStatusResponseVO = modelMapper.map(alarmResponseDTO, AlarmReadStatusResponseVO.class);

        return ApiResponse.ok(alarmReadStatusResponseVO);
    }
}
