package stanl_2.weshareyou.domain.board_recomment.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class BoardReCommentReadResponseVO {
    @NotNull
    @Size(min=2, max=1000, message = "1자 이상 1000자 이내로 입력하여 주세요.")
    private String content;

    @NotNull
    private Timestamp createdAt;

    @NotNull
    private Timestamp updatedAt;

    @NotNull
    private String nickname;

    @NotNull
    private Long boardCommentId;

}
