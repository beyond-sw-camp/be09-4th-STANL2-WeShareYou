package stanl_2.weshareyou.domain.board.aggregate.vo.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardCreateRequestVO {

    @NonNull
    private long id;

    @NonNull
    private String title;

}
