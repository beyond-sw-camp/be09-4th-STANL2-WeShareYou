package stanl_2.weshareyou.domain.notice.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeCreateRequestVO {
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Long adminId;
}
