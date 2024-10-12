package stanl_2.weshareyou.domain.alarm.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.AlarmType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlarmDTO {

    private Long id;
    private String message;
    private String url;
    private AlarmType alarmType;
    private Boolean readStatus;
    private String createdAt;
    private String sender;
    private Long memberId;

}


