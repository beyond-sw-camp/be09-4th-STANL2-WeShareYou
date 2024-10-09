package stanl_2.weshareyou.domain.notice.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeReadByIdResponseVO {
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String createdAt;

    @NotNull
    private String updatedAt;

    @NotNull
    private Boolean active;

    @NotNull
    private Long adminId;
}
