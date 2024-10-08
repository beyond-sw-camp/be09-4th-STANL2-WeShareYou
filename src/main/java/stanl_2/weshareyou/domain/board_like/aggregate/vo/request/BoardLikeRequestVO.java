package stanl_2.weshareyou.domain.board_like.aggregate.vo.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardLikeRequestVO {
    @NonNull
    private Long boardId;

    @NonNull
    private Long memberId;
}
