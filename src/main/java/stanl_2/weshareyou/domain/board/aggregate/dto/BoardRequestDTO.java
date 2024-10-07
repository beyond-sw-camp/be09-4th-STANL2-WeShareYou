package stanl_2.weshareyou.domain.board.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO {

    private long id;
    private String title;

}
