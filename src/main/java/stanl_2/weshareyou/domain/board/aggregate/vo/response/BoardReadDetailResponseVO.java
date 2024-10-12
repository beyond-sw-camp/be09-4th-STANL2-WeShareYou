package stanl_2.weshareyou.domain.board.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardReadDetailResponseVO {

    @NotNull
    private String imageUrl;

    @NotNull
    private String content;

    @NotNull
    private Integer likesCount;

    @NotNull
    private String memberProfileUrl;

    @NotNull
    private String memberNickname;

    @NotNull
    private List<BoardCommentDto> comment;

}
