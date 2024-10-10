package stanl_2.weshareyou.domain.board_comment.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardCommentCreateResponseVO {

    @NotNull
    @Size(min=2, max=1000, message = "1자 이상 1000자 이내로 입력하여 주세요.")
    private String content;

    @NotNull
    private Long memberId;

    @NotNull
    private Long boardId;
}
