package stanl_2.weshareyou.domain.notice.aggregate.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeSummaryVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Long adminId;
}
