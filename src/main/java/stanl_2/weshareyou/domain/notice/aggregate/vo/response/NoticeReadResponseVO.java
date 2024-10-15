package stanl_2.weshareyou.domain.notice.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.notice.aggregate.vo.NoticeSummaryVO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeReadResponseVO {

    @NotNull
    private Long cursorId;

    @NotNull
    private List<NoticeSummaryVO> comment;

    @NotNull
    private Boolean hasNext;
}
