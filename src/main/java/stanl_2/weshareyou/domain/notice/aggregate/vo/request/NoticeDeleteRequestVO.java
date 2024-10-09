package stanl_2.weshareyou.domain.notice.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeDeleteRequestVO {
    @NotNull
    private Long id;
    @NotNull
    private Long adminId;
}
