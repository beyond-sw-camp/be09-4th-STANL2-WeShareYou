package stanl_2.weshareyou.domain.notice.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

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
    private Timestamp createdAt;

    @NotNull
    private Long adminId;
}
