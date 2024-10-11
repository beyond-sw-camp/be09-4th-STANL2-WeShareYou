package stanl_2.weshareyou.domain.board_comment.aggregate.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentDto {
        private String content;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Long memberId;
        private Long boardId;
}
