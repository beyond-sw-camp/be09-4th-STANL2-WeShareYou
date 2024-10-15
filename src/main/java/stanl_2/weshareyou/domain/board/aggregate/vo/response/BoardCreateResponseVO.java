package stanl_2.weshareyou.domain.board.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardCreateResponseVO {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<BoardImage> imageUrl;

    @NotNull
    private TAG tag;
}
