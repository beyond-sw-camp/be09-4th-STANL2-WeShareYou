package stanl_2.weshareyou.domain.board.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BoardDeleteResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private Boolean active;
}
