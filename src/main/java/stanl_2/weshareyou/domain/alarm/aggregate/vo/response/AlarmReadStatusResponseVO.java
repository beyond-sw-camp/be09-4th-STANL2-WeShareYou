package stanl_2.weshareyou.domain.alarm.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AlarmReadStatusResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private String url;

    @NotNull
    private Boolean readStatus;

    @NotNull
    private String createdAt;

    @NotNull
    private String sender;

    @NotNull
    private Long memberId;

}
