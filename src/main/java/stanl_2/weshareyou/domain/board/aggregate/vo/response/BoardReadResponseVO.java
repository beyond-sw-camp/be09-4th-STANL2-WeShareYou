package stanl_2.weshareyou.domain.board.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;
import stanl_2.weshareyou.domain.board.aggregate.vo.BoardSummaryVO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BoardReadResponseVO {

    @NotNull
    private TAG tag;

    @NotNull
    private Long cursorId;

    @NotNull
    private List<BoardSummaryVO> comment;

    @NotNull
    private boolean hasNext;
}
