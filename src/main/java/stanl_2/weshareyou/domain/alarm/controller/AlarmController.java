package stanl_2.weshareyou.domain.alarm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.weshareyou.domain.alarm.aggregate.vo.response.AlarmReadMemberResponseVO;
import stanl_2.weshareyou.domain.alarm.aggregate.vo.response.AlarmReadStatusResponseVO;
import stanl_2.weshareyou.domain.alarm.service.AlarmService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * req: localhost:8080/api/v1/alarm/subscribe
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
    public SseEmitter subscribe(@RequestAttribute("id") Long memberId,
                                             @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                             String lastEventId) {

        return alarmService.subscribe(memberId, lastEventId);
    }

    /**
     * 내용: 알림 조회
     * req: localhost:8080/api/v1/alarm?page=0&size=10
     * res:
     * {
     *     "success": true,
     *     "result": {
     *         "alarms": [
     *             {
     *                 "id": 19,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:21:44",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 17,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:21:43",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 18,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:21:43",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 16,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:21:42",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 15,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:21:41",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 14,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:21:40",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 13,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:19:39",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 12,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:19:38",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 11,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T01:19:37",
     *                 "memberId": 4
     *             },
     *             {
     *                 "id": 10,
     *                 "message": "가지남님이 댓글을 남겼습니다.",
     *                 "url": "/api/v1/board-recomment",
     *                 "readStatus": false,
     *                 "createdAt": "2024-10-13T00:35:18",
     *                 "memberId": 4
     *             }
     *         ],
     *         "totalPages": 2,
     *         "pageSize": 10,
     *         "currentPage": 0,
     *         "totalElements": 13
     *     },
     *     "error": null
     * }
     */
    @GetMapping("")
    public ApiResponse<?> readMemberAlarms(@RequestAttribute("id") Long memberId,
                                           @PageableDefault(size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AlarmDTO> alarmResponseDTO = alarmService.readMemberAlarms(memberId, pageable);

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

        // 페이지 정보 + 알림 리스트
        Map<String, Object> response = new HashMap<>();
        response.put("alarms", alarmReadMemberResponseVOs);  // 알림 리스트
        response.put("currentPage", alarmResponseDTO.getNumber());  // 현재 페이지 번호
        response.put("totalPages", alarmResponseDTO.getTotalPages());  // 총 페이지 수
        response.put("totalElements", alarmResponseDTO.getTotalElements());  // 전체 알림 개수
        response.put("pageSize", alarmResponseDTO.getSize());  // 페이지 크기


        return ApiResponse.ok(response);
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
    @PutMapping("/{alarmId}")
    public ApiResponse<?> readStatusAlarm(@PathVariable Long alarmId, @RequestAttribute("id") Long memberId) {
        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setId(alarmId);
        alarmDTO.setMemberId(memberId);

        AlarmDTO alarmResponseDTO = alarmService.readStatusAlarm(alarmDTO);
        AlarmReadStatusResponseVO alarmReadStatusResponseVO = modelMapper.map(alarmResponseDTO, AlarmReadStatusResponseVO.class);

        return ApiResponse.ok(alarmReadStatusResponseVO);
    }
}
