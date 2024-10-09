package stanl_2.weshareyou.domain.notice.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeReadAllResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String createdAt;

    @NotNull
    private Long adminId;
}
