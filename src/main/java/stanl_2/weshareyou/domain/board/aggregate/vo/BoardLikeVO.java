package stanl_2.weshareyou.domain.board.aggregate.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardLikeVO {

    private Long boardId;
    private Long memberId;

}
