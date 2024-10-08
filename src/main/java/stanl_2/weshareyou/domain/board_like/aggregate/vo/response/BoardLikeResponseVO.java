package stanl_2.weshareyou.domain.board_like.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikeResponseVO {
    @NonNull
    private Long boardId;

    @NonNull
    private Long memberId;
}
