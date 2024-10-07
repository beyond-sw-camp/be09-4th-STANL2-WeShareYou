package stanl_2.weshareyou.domain.board.aggregate.vo.response;

import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BoardCreateResponseVO {

    @NonNull
    private String title;

}
