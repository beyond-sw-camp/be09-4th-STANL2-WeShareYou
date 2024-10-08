package stanl_2.weshareyou.domain.board.aggregate.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentVO {

    private Long commentId;
    private String Content;
    private String createdAt;
    private String updatedAt;
    private Long memberId;
}
