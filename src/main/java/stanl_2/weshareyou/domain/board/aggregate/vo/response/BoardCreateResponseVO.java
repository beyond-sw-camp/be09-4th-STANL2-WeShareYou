package stanl_2.weshareyou.domain.board.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

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

    private String imageUrl;

    @NotNull
    private TAG tag;

    @NotNull
    private Long memberId;

}
