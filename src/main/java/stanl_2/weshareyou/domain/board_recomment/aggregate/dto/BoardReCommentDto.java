package stanl_2.weshareyou.domain.board_recomment.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardReCommentDto {
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String nickname;
    private Long memberId;
    private Long boardCommentId;
}
