package stanl_2.weshareyou.domain.board.aggregate.dto;

import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursorDTO {

    private Long id;
    private TAG tag;
    private Long cursorId;
    private List<BoardDTO> comment;
    private boolean hasNext;
    private Integer size;
}
