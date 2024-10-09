package stanl_2.weshareyou.domain.board.aggregate.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NotNull
public class BoardLikeVO {

    private Long boardId;
    private Long memberId;

}
