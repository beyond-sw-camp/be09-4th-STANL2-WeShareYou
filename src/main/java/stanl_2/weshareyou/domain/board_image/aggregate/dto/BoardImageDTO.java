package stanl_2.weshareyou.domain.board_image.aggregate.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardImageDTO {

    private Long id;
    private String imageUrl;
    private String fileName;

}
