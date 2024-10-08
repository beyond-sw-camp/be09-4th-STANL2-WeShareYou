package stanl_2.weshareyou.domain.board_like.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikeRequestVO {
    @NotNull(message = "게시판 ID를 입력하여 주세요.")
    private Long boardId;

    @NotNull
    private Long memberId;
}
