package stanl_2.weshareyou.domain.board.aggregate.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NotNull
public class CommentVO {

    private Long commentId;
    private String Content;
    private String createdAt;
    private String updatedAt;
    private Long memberId;
}
