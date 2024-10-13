package stanl_2.weshareyou.domain.alarm.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.alarm.aggregate.entity.Alarm;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AlarmResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private String url;

    @NotNull
    private Boolean readStatus;

    @NotNull
    private Timestamp createdAt;

    @NotNull
    private String sender;

    @NotNull
    private Long memberId;

    public AlarmResponseVO(Alarm alarm) {
        this.id = alarm.getId();
        this.message = alarm.getMessage();
        this.url = alarm.getUrl();
        this.readStatus = alarm.getReadStatus();
        this.createdAt = alarm.getCreatedAt();
        this.sender = alarm.getSender();
        this.memberId = alarm.getMemberId().getId();
    }
}
