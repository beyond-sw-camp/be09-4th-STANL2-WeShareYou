package stanl_2.weshareyou.domain.board_like.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikeResponseVO {
    @NotNull
    private Long boardId;

    @NotNull
    private Long memberId;
}
