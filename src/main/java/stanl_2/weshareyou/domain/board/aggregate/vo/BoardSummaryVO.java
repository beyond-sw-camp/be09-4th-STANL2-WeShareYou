package stanl_2.weshareyou.domain.board.aggregate.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardSummaryVO {

    @NotNull
    private String memberProfileUrl;

    @NotNull
    private String memberNickname;

    @NotNull
    private String title;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer commentCount;

    @NotNull
    private Integer likesCount;
}
