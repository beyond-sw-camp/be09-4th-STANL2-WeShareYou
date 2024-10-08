package stanl_2.weshareyou.domain.board_like.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikeResponseVO {
    @NotNull(message = "게시판 ID를 입력하여 주세요.")
    private Long boardId;

    @NotNull
    private Long memberId;
}
