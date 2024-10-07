package stanl_2.weshareyou.domain.board.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {

    private long id;
    private String title;
}
