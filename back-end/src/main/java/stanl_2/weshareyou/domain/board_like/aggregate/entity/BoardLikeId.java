package stanl_2.weshareyou.domain.board_like.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeId implements Serializable {

    private Long member;

    private Long board;
}