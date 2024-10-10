package stanl_2.weshareyou.domain.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.weshareyou.domain.alarm.service.AlarmService;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.global.common.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam Long id,
                                             @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                             String lastEventId) {

        return alarmService.subscribe(id, lastEventId);
    }
}
